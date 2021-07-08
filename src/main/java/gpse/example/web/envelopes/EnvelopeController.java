package gpse.example.web.envelopes;

import gpse.example.domain.addressbook.AddressBook;
import gpse.example.domain.addressbook.Entry;
import gpse.example.domain.documents.*;
import gpse.example.domain.envelopes.Envelope;
import gpse.example.domain.envelopes.EnvelopeServiceImpl;
import gpse.example.domain.exceptions.*;
import gpse.example.domain.security.JwtAuthorizationFilter;
import gpse.example.domain.signature.Signatory;
import gpse.example.domain.signature.SignatureType;
import gpse.example.domain.users.User;
import gpse.example.domain.users.UserServiceImpl;
import gpse.example.web.DocumentFilter;
import gpse.example.domain.email.*;
import gpse.example.domain.email.trusteddomain.*;
import gpse.example.web.JSONResponseObject;
import gpse.example.web.documents.DocumentPutRequest;
import gpse.example.web.documents.GuestToken;
import gpse.example.web.documents.GuestTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The envelopeController class handles the request from the frontend and
 * conducts the corresponding backend actions.
 */
@RestController
@CrossOrigin("http://localhost:8088")
@RequestMapping("/api")
public class EnvelopeController {

    private static final String USER_ID = "userID";
    private static final String ENVELOPE_ID = "envelopeID";
    private static final int FORBIDDEN = 403;
    private static final int INTERNAL_ERROR = 500;
    private static final int STATUS_CODE_OK = 200;
    private static final String DOCUMENT_URL = "/document/";
    private static final Logger LOG = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    private final EnvelopeServiceImpl envelopeService;
    private final UserServiceImpl userService;
    private final DocumentServiceImpl documentService;
    private final DocumentFilter documentFilter;
    private final GuestTokenService guestTokenService;


    @Lazy
    @Autowired
    private DomainSetterService domainSetterService;

    @Lazy
    @Autowired
    private SMTPServerHelper smtpServerHelper;

    @Autowired
    private EmailTemplateService emailTemplateService;

    /**
     * The default constructor for an envelope Controller.
     *
     * @param envelopeService the envelopeService
     * @param userService     the userService
     * @param documentService the documentService
     * @param documentFilter  the documentFilter
     * @param guestTokenService the guestTokenService
     */
    @Autowired
    public EnvelopeController(final EnvelopeServiceImpl envelopeService, final UserServiceImpl userService,
                              final DocumentServiceImpl documentService, final DocumentFilter documentFilter,
                              final GuestTokenService guestTokenService) {
        this.envelopeService = envelopeService;
        this.userService = userService;
        this.documentService = documentService;
        this.documentFilter = documentFilter;
        this.guestTokenService = guestTokenService;
    }

    /**
     * The createEnvelope method does a post request to create a new envelope.
     *
     * @param ownerID the email address of the creator.
     * @param name    the name of the new envelope.
     * @return the new envelope.
     * @throws UploadFileException if the envelope could not be uploaded.
     */

    @PostMapping("/user/{userID}/envelopes")
    public EnvelopeGetResponse createEnvelope(final @PathVariable(USER_ID) String ownerID,
                                              final @RequestParam("name") String name) throws UploadFileException {
        try {
            final User owner = userService.getUser(ownerID);
            final Envelope envelope = envelopeService.addEnvelope(name, owner);
            return new EnvelopeGetResponse(envelope, envelope.getOwner(), envelope.getOwner().getEmail());
        } catch (IOException | UsernameNotFoundException e) {
            throw new UploadFileException(e);
        }
    }

    /**
     * The fillEnvelope method does a put request to add a Document to an existing envelope.
     *
     * @param envelopeID         the ID of the envelope.
     * @param ownerID            the email of the document creator
     * @param documentPutRequest the command object keeping the information for a document to be created
     * @return the envelope in which the document was added to.
     */

    @PutMapping("/user/{userID}/envelopes/{envelopeID:\\d+}")
    public JSONResponseObject fillEnvelope(final @PathVariable(ENVELOPE_ID) long envelopeID,
                                           final @PathVariable(USER_ID) String ownerID,
                                           final @RequestBody DocumentPutRequest documentPutRequest) {
        final JSONResponseObject response = new JSONResponseObject();
        try {
            final Envelope envelope = envelopeService.getEnvelope(envelopeID);
            if (!envelope.getOwnerID().equals(ownerID)) {
                response.setStatus(FORBIDDEN);
                response.setMessage("Forbidden. Not permitted to upload document.");
                return response;
            }
            final Document document = documentService.creation(documentPutRequest, ownerID,
                userService);
            final Envelope savedEnvelope = envelopeService.updateEnvelope(envelope, document);
            Document savedDocument = savedEnvelope.getDocumentList().get(0);
            for (final Document doc : savedEnvelope.getDocumentList()) {
                if (doc.getDocumentMetaData().getMetaTimeStampUpload().isAfter(
                    savedDocument.getDocumentMetaData().getMetaTimeStampUpload())) {
                    savedDocument = doc;
                }
            }
            savedDocument.setLinkToDocumentview("http://localhost:8080/de/envelope/" + savedEnvelope.getId()
                + DOCUMENT_URL + savedDocument.getId());
            final SignatoryManagement signatoryManagement = savedDocument.getSignatoryManagement();
            if (savedDocument.isOrderRelevant() && signatoryManagement.getCurrentSignatory() != null) {
                sendUserInvitation(signatoryManagement.getCurrentSignatory().getEmail(),
                    userService.getUser(savedDocument.getOwner()),
                        savedDocument, envelopeService.getEnvelope(envelopeID),
                        signatoryManagement.getCurrentSignatory().getSignatureType());
            } else {
                for (int i = 0; i < signatoryManagement.getSignatories().size(); i++) {
                    sendUserInvitation(signatoryManagement.getSignatories().get(i).getEmail(),
                        userService.getUser(savedDocument.getOwner()), savedDocument,
                        envelopeService.getEnvelope(envelopeID),
                            signatoryManagement.getSignatories().get(i).getSignatureType());
                }
            }
            addIntoAddressBook(ownerID, savedDocument.getSignatoryManagement().getSignatories());

            envelopeService.saveEnvelope(savedEnvelope);
            response.setStatus(STATUS_CODE_OK);
            response.setMessage("Success");
            return response;
        } catch (CreatingFileException | DocumentNotFoundException | IOException | UsernameNotFoundException
            | MessageGenerationException e) {
            response.setStatus(INTERNAL_ERROR);
            response.setMessage("The document could not be uploaded.");
            return response;
        }
    }

    private void addIntoAddressBook(final String ownerID, final List<Signatory> signatories) {
        final User currentUser = userService.getUser(ownerID);
        final AddressBook addressBook = currentUser.getAddressBook();
        List<Signatory> filteredSignatories;
        if (addressBook.isAddAllAutomatically()) {
            filteredSignatories = signatories;
            if (addressBook.isAddDomainAutomatically()) {
                filteredSignatories = signatories.stream().filter(signatory ->
                    signatory.getEmail()
                        .matches(domainSetterService.getDomainSettings().get(0).getTrustedMailDomain()))
                    .collect(Collectors.toList());
            }
            for (final Signatory signatory: filteredSignatories) {
                try {
                    final User user = userService.getUser(signatory.getEmail());
                    addressBook.addEntry(new Entry(user));
                } catch (UsernameNotFoundException e) {
                    final Entry entry = new Entry();
                    entry.setEmail(signatory.getEmail());
                    addressBook.addEntry(entry);
                }
            }
            userService.saveUser(currentUser);
        }
    }

    private void sendUserInvitation(final String userID, final User owner, final Document document,
                                    final Envelope envelope, final SignatureType signatureType)
        throws MessageGenerationException {
        try {
            final User signatory = userService.getUser(userID);
            sendRegisteredUserInvitation(owner, document, envelope, signatureType, signatory);
        } catch (UsernameNotFoundException exception) {
            sendGuestUserInvitation(userID, owner, document, envelope, signatureType);
        }
    }

    private void sendGuestUserInvitation(final String userID, final User owner, final Document document,
                                         final Envelope envelope, final SignatureType signatureType)
                throws MessageGenerationException {
        EmailTemplate template;
        try {
            template = emailTemplateService.findSystemTemplateByName("GuestInvitationTemplate");
        } catch (TemplateNameNotFoundException e) {
            return;
        }
        final TemplateDataContainer container = new TemplateDataContainer();
        container.setFirstNameOwner(owner.getFirstname());
        container.setLastNameOwner(owner.getLastname());
        container.setDocumentTitle(document.getDocumentTitle());
        final GuestToken token = guestTokenService.saveGuestToken(new GuestToken(userID, document.getId()));
        container.setLink("http://localhost:8080/de/" + "envelope/" + envelope.getId() + DOCUMENT_URL
            + document.getId() + "/" + token.getToken());
        if (signatureType.equals(SignatureType.REVIEW)) {
            smtpServerHelper.sendTemplatedEmail(userID, template, container, Category.READ, owner);
        } else if (signatureType.equals(SignatureType.SIMPLE_SIGNATURE)) {
            smtpServerHelper.sendTemplatedEmail(userID, template, container, Category.SIGN, owner);
        } else {
            container.setLink("http://localhost:8080/de/landing");
            try {
                template = emailTemplateService.findSystemTemplateByName("AdvancedGuestInvitationTemplate");
            } catch (TemplateNameNotFoundException e) {
                return;
            }
            smtpServerHelper.sendTemplatedEmail(userID, template, container, Category.TODO, owner);
        }
    }

    private void sendRegisteredUserInvitation(final User owner, final Document document, final Envelope envelope,
                                              final SignatureType signatureType, final User signatory)
                throws MessageGenerationException {
        EmailTemplate template = owner.getEmailTemplates().get(0);
        for (final EmailTemplate temp : owner.getEmailTemplates()) {
            if (temp.getTemplateID() == document.getProcessEmailTemplateId()) {
                template = temp;
            }
        }
        final TemplateDataContainer container = new TemplateDataContainer();
        container.setFirstNameReciever(signatory.getFirstname());
        container.setLastNameReciever(signatory.getLastname());
        container.setFirstNameOwner(owner.getFirstname());
        container.setLastNameOwner(owner.getLastname());
        container.setDocumentTitle(document.getDocumentTitle());
        container.setEnvelopeName(envelope.getName());
        container.setEndDate(document.getEndDate().toString());
        container.setLink(document.getLinkToDocumentview());
        Category category;
        if (signatureType.equals(SignatureType.ADVANCED_SIGNATURE)
            || signatureType.equals(SignatureType.SIMPLE_SIGNATURE)) {
            category = Category.SIGN;
        } else {
            category = Category.READ;
        }
        smtpServerHelper.sendTemplatedEmail(signatory.getEmail(), template, container, category, owner);
    }


    /**
     * The getEnvelope method returns one particular envelope specified by id.
     *
     * @param envelopeID the id of the envelope to be downloaded.
     * @param userID     the id of the user.
     * @return the response object
     * @throws DocumentNotFoundException if the envelope was not found.
     */

    @GetMapping("/user/{userID}/envelopes/{envelopeID:\\d+}")
    public EnvelopeGetResponse getEnvelope(final @PathVariable(ENVELOPE_ID) long envelopeID,
                                           final @PathVariable(USER_ID) String userID)
        throws DocumentNotFoundException {
        final Envelope envelope = envelopeService.getEnvelope(envelopeID);
        final User owner = userService.getUser(envelope.getOwnerID());
        return new EnvelopeGetResponse(envelope, owner, userID);
    }

    /**
     * The getAllEnvelopes methods gets all envelopes from the database and filters
     * them using the filter method.
     *
     * @param userID the id of the user doing the request.
     * @return the filtered envelope list.
     */

    @GetMapping("/user/{userID}/envelopes")
    public List<EnvelopeGetResponse> getAllEnvelopes(final @PathVariable(USER_ID) String userID) {
        final List<Envelope> envelopeList = envelopeService.getEnvelopes();
        final List<EnvelopeGetResponse> envelopeGetResponseList = new ArrayList<>();
        for (final Envelope envelope : envelopeList) {
            final User owner = userService.getUser(envelope.getOwnerID());
            envelopeGetResponseList.add(new EnvelopeGetResponse(envelope, owner, userID));
        }
        return documentFilter.filterEnvelopes(envelopeGetResponseList, userID);

    }

    /**
     * The getMapping for the request to get the settings of all documents in an envelope.
     *
     * @param envelopeID the id of the relating envelope
     * @return a fitting response in form of a list containing documentSetting-Objects.
     */
    @GetMapping("user/{userID}/envelope/{envelopeID}/settings")
    public EnvelopeSettingsResponse getEnvelopeSettings(final @PathVariable(ENVELOPE_ID) long envelopeID) {
        try {
            return new EnvelopeSettingsResponse(envelopeService.getEnvelope(envelopeID));
        } catch (DocumentNotFoundException exception) {
            LOG.debug("Document not found", exception);
            return new EnvelopeSettingsResponse();
        }
    }
}


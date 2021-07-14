package gpse.example.web.envelopes;

import gpse.example.domain.addressbook.*;
import gpse.example.domain.documents.*;
import gpse.example.domain.email.*;
import gpse.example.domain.email.trusteddomain.DomainSetterService;
import gpse.example.domain.envelopes.Envelope;
import gpse.example.domain.envelopes.EnvelopeServiceImpl;
import gpse.example.domain.exceptions.*;
import gpse.example.domain.security.JwtAuthorizationFilter;
import gpse.example.domain.signature.Signatory;
import gpse.example.domain.users.User;
import gpse.example.domain.users.UserServiceImpl;
import gpse.example.web.DocumentFilter;
import gpse.example.web.JSONResponseObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import gpse.example.web.documents.DocumentPutRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private static final String HTTP_LOCALHOST = "http://localhost:";

    @Value("${server.port}")
    private int serverPort;

    private final EnvelopeServiceImpl envelopeService;
    private final UserServiceImpl userService;
    private final DocumentServiceImpl documentService;
    private final DocumentFilter documentFilter;
    private final EmailManagement emailManagement;

    @Lazy
    @Autowired
    private DomainSetterService domainSetterService;

    /**
     * The default constructor for an envelope Controller.
     *
     * @param envelopeService   the envelopeService
     * @param userService       the userService
     * @param documentService   the documentService
     * @param documentFilter    the documentFilter
     */
    @Autowired
    public EnvelopeController(final EnvelopeServiceImpl envelopeService, final UserServiceImpl userService,
                              final DocumentServiceImpl documentService, final DocumentFilter documentFilter,
                              final EmailManagement emailManagement) {
        this.envelopeService = envelopeService;
        this.userService = userService;
        this.documentService = documentService;
        this.documentFilter = documentFilter;
        this.emailManagement = emailManagement;
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
            savedDocument.setLinkToDocumentView(HTTP_LOCALHOST + serverPort + "/de/envelope/" + savedEnvelope.getId()
                + DOCUMENT_URL + savedDocument.getId());
            final SignatoryManagement signatoryManagement = savedDocument.getSignatoryManagement();
            if (savedDocument.isOrderRelevant() && signatoryManagement.getCurrentSignatory() != null) {
                emailManagement.sendInvitation(savedDocument, envelopeID, signatoryManagement.getCurrentSignatory());
            } else {
                for (int i = 0; i < signatoryManagement.getSignatories().size(); i++) {
                    if (!signatoryManagement.getSignatories().get(i).getEmail().equals(savedDocument.getOwner())) {
                        emailManagement.sendInvitation(savedDocument, envelopeID,
                            signatoryManagement.getSignatories().get(i));
                    }
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
        List<Signatory> filteredSignatories = signatories;
        if (addressBook.isAddAllAutomatically() || addressBook.isAddDomainAutomatically()) {
            if (!addressBook.isAddAllAutomatically() && addressBook.isAddDomainAutomatically()) {
                filteredSignatories = signatories.stream().filter(signatory ->
                    signatory.getEmail()
                        .matches(domainSetterService.getDomainSettings().get(0).getTrustedMailDomain()))
                    .collect(Collectors.toList());
            }
            for (final Signatory signatory : filteredSignatories) {
                try {
                    final User user = userService.getUser(signatory.getEmail());
                    addressBook.addEntry(new Entry(user));
                } catch (UsernameNotFoundException e) {
                    final Entry entry = new Entry();
                    entry.setEmail(signatory.getEmail());
                    addressBook.addEntry(entry);
                }
            }
        }
        userService.saveUser(currentUser);
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
     * @param userID the id of the user
     * @return a fitting response in form of a list containing documentSetting-Objects.
     */
    @GetMapping("user/{userID}/envelope/{envelopeID}/settings")
    public EnvelopeSettingsResponse getEnvelopeSettings(final @PathVariable(ENVELOPE_ID) long envelopeID,
                                                        final @PathVariable String userID) {
        try {
            final Envelope envelope = envelopeService.getEnvelope(envelopeID);
            if (envelope.getOwnerID().equals(userID)) {
                return new EnvelopeSettingsResponse(envelope);
            }
            return new EnvelopeSettingsResponse();

        } catch (DocumentNotFoundException exception) {
            LOG.debug("Document not found", exception);
            return new EnvelopeSettingsResponse();
        }
    }
}


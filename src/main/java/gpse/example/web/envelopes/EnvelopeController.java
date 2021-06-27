package gpse.example.web.envelopes;

import gpse.example.domain.documents.*;
import gpse.example.domain.envelopes.Envelope;
import gpse.example.domain.envelopes.EnvelopeServiceImpl;
import gpse.example.domain.exceptions.*;
import gpse.example.domain.signature.SignatoryServiceImpl;
import gpse.example.domain.signature.SignatureType;
import gpse.example.domain.users.User;
import gpse.example.domain.users.UserServiceImpl;
import gpse.example.util.email.*;
import gpse.example.web.JSONResponseObject;
import gpse.example.web.documents.DocumentPutRequest;
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
    private static final String NEW_LINE = "\n";

    private final EnvelopeServiceImpl envelopeService;
    private final UserServiceImpl userService;
    private final SignatoryServiceImpl signatoryService;
    private final DocumentServiceImpl documentService;
    @Lazy
    @Autowired
    private DocumentCreator documentCreator;
    @Lazy
    @Autowired
    private SMTPServerHelper smtpServerHelper;
    @Autowired
    private EmailTemplateService emailTemplateService;
    /**
     * The default constructor for an envelope Controller.
     *
     * @param envelopeService  the envelopeService
     * @param userService      the userService
     * @param signatoryService the signatoryService
     * @param documentService  the documentService
     */
    @Autowired
    public EnvelopeController(final EnvelopeServiceImpl envelopeService, final UserServiceImpl userService,
                              final SignatoryServiceImpl signatoryService, final DocumentServiceImpl documentService) {
        this.envelopeService = envelopeService;
        this.userService = userService;
        this.signatoryService = signatoryService;
        this.documentService = documentService;
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
            Envelope envelope = envelopeService.addEnvelope(name, owner);
            return new EnvelopeGetResponse(envelope, envelope.getOwner(), envelope.getOwner());
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
        JSONResponseObject response = new JSONResponseObject();
        try {
            final Envelope envelope = envelopeService.getEnvelope(envelopeID);
            if (!envelope.getOwnerID().equals(ownerID)) {
                response.setStatus(FORBIDDEN);
                response.setMessage("Forbidden. Not permitted to upload document.");
                return response;
            }
            final Document document = documentService.creation(documentPutRequest, envelope, ownerID,
                userService);
            if (!document.isOrderRelevant()) {
                for (int i = 0; i < document.getSignatories().size(); i++) {
                    setupUserInvitation(document.getSignatories().get(i).getUser(),
                        userService.getUser(document.getOwner()), document,
                        envelopeService.getEnvelope(envelopeID), document.getSignatories().get(i).getSignatureType());
                }
            } else {
                setupUserInvitation(document.getCurrentSignatory().getUser(),
                    userService.getUser(document.getOwner()), document,
                    envelopeService.getEnvelope(envelopeID), document.getCurrentSignatory().getSignatureType());
            }
            envelopeService.updateEnvelope(envelope, document);
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

    private void setupUserInvitation(User signatory, User owner, Document document,
                                     Envelope envelope, SignatureType signatureType)
        throws MessageGenerationException {

        EmailTemplate template = document.getProcessEmailTemplate();
        TemplateDataContainer container = new TemplateDataContainer();
        container.setFirstNameReciever(signatory.getFirstname());
        container.setLastNameReciever(signatory.getLastname());
        container.setFirstNameOwner(owner.getFirstname());
        container.setLastNameOwner(owner.getLastname());
        container.setDocumentTitle(document.getDocumentTitle());
        container.setEnvelopeName(envelope.getName());
        container.setEndDate(document.getEndDate().toString());
        //TODO Link to documentview
        container.setLink("http://localhost:8080/de/link/to/document/view");
        Category category;
        if (signatureType.equals(SignatureType.ADVANCED_SIGNATURE)
            || signatureType.equals(SignatureType.SIMPLE_SIGNATURE)) {
            category = Category.SIGN;
        } else {
            category = Category.READ;
        }
        smtpServerHelper.sendTemplatedEmail(signatory.getEmail(), template, container, category);
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
        final User currentUser = userService.getUser(userID);
        final User owner = userService.getUser(envelope.getOwnerID());
        return new EnvelopeGetResponse(envelope, owner, currentUser);
    }

    /**
     * The downloadEnvelope method downloads an envelope with all documents.
     *
     * @param envelopeID the id of the envelope to be downloaded.
     * @param userID     the id of the user doing the request.
     * @param path       the path where the envelope should be downloaded.
     * @return the response object
     * @throws DownloadFileException if the download was not successful.
     */
    @GetMapping("/user/{userID}/envelopes/{envelopeID:\\d+}/download")
    public EnvelopeGetResponse downloadEnvelope(final @PathVariable(ENVELOPE_ID) long envelopeID,
                                                final @PathVariable(USER_ID) String userID,
                                                final @RequestParam("path") String path)
        throws DownloadFileException {
        try {
            final User currentUser = userService.getUser(userID);
            final Envelope envelope = envelopeService.getEnvelope(envelopeID);
            final User owner = userService.getUser(envelope.getOwnerID());
            documentCreator.downloadEnvelope(envelope, path);
            return new EnvelopeGetResponse(envelope, owner, currentUser);
        } catch (CreatingFileException | IOException | DocumentNotFoundException e) {
            throw new DownloadFileException(e);
        }
    }

    /**
     * The getAllEnvelopes methods gets all envelopes from the database and filters
     * them using the filter method.
     *
     * @param userID  the id of the user doing the request.
     * @return the filtered envelope list.
     */

    @GetMapping("/user/{userID}/envelopes")
    public List<EnvelopeGetResponse> getAllEnvelopes(final @PathVariable(USER_ID) String userID) {
        final User currentUser = userService.getUser(userID);
        List<Envelope> envelopeList = envelopeService.getEnvelopes();
        final List<EnvelopeGetResponse> envelopeGetResponseList = new ArrayList<>();
        for (final Envelope envelope : envelopeList) {
            final User owner = userService.getUser(envelope.getOwnerID());
            envelopeGetResponseList.add(new EnvelopeGetResponse(envelope, owner, currentUser));
        }
        return envelopeGetResponseList;

    }

    /**
     * The filter method filters an envelope list on multiple criteria.
     *
     * @param request      the Request object which keeps the filter data.
     * @param envelopeList the list containing all envelopes in the database.
     * @return the filtered envelope list.
     */
    public List<Envelope> filter(final EnvelopeGetRequest request, final List<Envelope> envelopeList) {
        final List<Envelope> resultList = new ArrayList<>();
        final List<Envelope> filteredEnvelopeList = envelopeList
            .stream()
            .filter(envelope -> envelope.hasName(request.getNameFilter()))
            .filter(envelope -> envelope.hasID(request.getEnvelopeIDFilter()))
            .filter(envelope -> envelope.hasOwnerID(request.getOwnerIDFilter()))
            .filter(envelope -> envelope.hasCreationDate(request.getCreationDateFilterFrom(),
                request.getCreationDateFilterTo()))
            .collect(Collectors.toList());
        for (final Envelope envelope : filteredEnvelopeList) {
            final List<Document> filteredDocumentList = envelope.getDocumentList()
                .stream()
                .filter(document -> document.hasTitle(request.getTitleFilter()))
                .filter(document -> document.hasSignatureType(request.getSignatureTypeFilter()))
                .filter(document -> document.hasState(request.getStateFilter()))
                .filter(document -> document.hasEndDate(request.getEndDateFilterFrom(), request.getEndDateFilterTo()))
                .filter(document -> document.hasDataType(request.getDataType()))
                .filter(document -> document.hasSignatories(request.getSignatoryIDs()))
                .filter(document -> document.hasReaders(request.getReaderIDs()))
                .filter(document -> document.hasSigned(request.isSigned()))
                .filter(document -> document.hasRead(request.isRead()))
                .collect(Collectors.toList());
            final Envelope filteredEnvelope = new Envelope(envelope.getName(), filteredDocumentList,
                envelope.getOwner());
            resultList.add(filteredEnvelope);
        }
        return resultList;
    }

    /**
     * The getMapping for the request to get the settings of all documents in an envelope.
     * @param envelopeID the id of the relating envelope
     * @return a fitting response in form of a list containing documentSetting-Objects.
     */
    @GetMapping("user/{userID}/envelope/{envelopeID}/settings")
    public EnvelopeSettingsResponse getEnvelopeSettings(final @PathVariable(ENVELOPE_ID) long envelopeID) {
        try {
            return new EnvelopeSettingsResponse(envelopeService.getEnvelope(envelopeID));
        } catch (DocumentNotFoundException exception) {
            exception.printStackTrace();
            return new EnvelopeSettingsResponse();
        }
    }
}



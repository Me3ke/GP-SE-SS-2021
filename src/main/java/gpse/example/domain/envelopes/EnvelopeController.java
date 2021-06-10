package gpse.example.domain.envelopes;

import gpse.example.domain.documents.*;
import gpse.example.domain.exceptions.*;
import gpse.example.domain.signature.SignatoryServiceImpl;
import gpse.example.domain.users.User;
import gpse.example.domain.users.UserServiceImpl;
import gpse.example.util.email.MessageGenerationException;
import gpse.example.util.email.SMTPServerHelper;
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

    private final EnvelopeServiceImpl envelopeService;
    private final UserServiceImpl userService;
    private final SignatoryServiceImpl signatoryService;
    private final DocumentServiceImpl documentService;
    private final DocumentCreator documentCreator = new DocumentCreator();
    @Lazy
    @Autowired
    private SMTPServerHelper smtpServerHelper;
    /**
     * The default constructor for an envelope Controller.
     *
     * @param envelopeService the envelopeService
     * @param userService the userService
     * @param signatoryService the signatoryService
     * @param documentService the documentService
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
    @PostMapping("/user/{userID}/envelopes/envelopes")
    public Envelope createEnvelope(final @PathVariable(USER_ID) String ownerID,
                                   final @RequestParam("name") String name) throws UploadFileException {
        try {
            final User owner = userService.getUser(ownerID);
            final Envelope envelope = envelopeService.addEnvelope(name, owner);
            return envelope;
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
     * @throws UploadFileException if the document could not be uploaded.
     */
    @PutMapping("/user/{userID}/envelopes/{envelopeID:\\d+}")
    public Envelope fillEnvelope(final @PathVariable(ENVELOPE_ID) long envelopeID,
                                 final @PathVariable(USER_ID) String ownerID,
                                 final @RequestBody DocumentPutRequest documentPutRequest)
        throws UploadFileException {
        try {
            userService.getUser(ownerID);
            final Envelope envelope = envelopeService.getEnvelope(envelopeID);
            final Document document = documentService.creation(documentPutRequest, envelope, ownerID,
                userService, signatoryService);
            if (!document.isOrderRelevant()) {
                for (int i = 0; i < document.getSignatories().size(); i++) {
                    smtpServerHelper.sendSignatureInvitation(document.getSignatories().get(i).getUser().getEmail(),
                        userService.getUser(document.getOwner()),
                        document.getSignatories().get(i).getUser().getLastname(), document);
                }
            } else {
                smtpServerHelper.sendSignatureInvitation(document.getCurrentSignatory().getUser().getEmail(),
                    userService.getUser(document.getOwner()),
                    document.getCurrentSignatory().getUser().getLastname(), document);
            }
            return envelopeService.updateEnvelope(envelope, document);
        } catch (CreatingFileException | DocumentNotFoundException | IOException | UsernameNotFoundException
            | MessageGenerationException e) {
            throw new UploadFileException(e);
        }
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
     * @param userID the id of the user doing the request.
     * @param path the path where the envelope should be downloaded.
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
     * @param request the Request object which keeps the filter data.
     * @return the filtered envelope list.
     */
    @GetMapping("/user/{userID}/envelopes")
    public List<EnvelopeGetResponse> getAllEnvelopes(final @PathVariable(USER_ID) String userID,
                                                     final @RequestBody EnvelopeGetRequest request) {
        final User currentUser = userService.getUser(userID);
        List<Envelope> envelopeList = envelopeService.getEnvelopes();
        envelopeList = filter(request, envelopeList);
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
}



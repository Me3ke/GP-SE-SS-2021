package gpse.example.domain.documents;

import gpse.example.domain.envelopes.Envelope;
import gpse.example.domain.envelopes.EnvelopeServiceImpl;
import gpse.example.domain.exceptions.CreatingFileException;
import gpse.example.domain.exceptions.DocumentNotFoundException;
import gpse.example.domain.exceptions.DownloadFileException;
import gpse.example.domain.exceptions.UploadFileException;
import gpse.example.domain.signature.Signatory;
import gpse.example.domain.users.User;
import gpse.example.domain.users.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The DocumentController class handles the requests from the frontend and
 * conducts the corresponding backend actions.
 */
@RestController
@CrossOrigin("http://localhost:8088")
public class DocumentController {

    private static final String ENVELOPE_ID = "envelopeID";
    private static final String USER_ID = "userID";
    private static final String DOCUMENT_ID = "documentID";

    private final EnvelopeServiceImpl envelopeService;
    private final UserServiceImpl userService;
    private final DocumentServiceImpl documentService;

    /**
     * The default constructor which initialises the services by autowiring.
     *
     * @param envelopeService the envelopeService
     * @param userService     the userService
     * @param documentService the documentService
     */
    @Autowired
    public DocumentController(final EnvelopeServiceImpl envelopeService, final UserServiceImpl userService,
                              final DocumentServiceImpl documentService) {
        this.envelopeService = envelopeService;
        this.userService = userService;
        this.documentService = documentService;
    }

    /**
     * The getDocumentFromEnvelope method gets a get request and creates an appropriate response.
     *
     * @param envelopeID the id of the envelope which contains the document.
     * @param userID     the id of the user doing the request.
     * @param documentID the id of the document asked for.
     * @param download   a boolean which indicates if a document should be downloaded.
     * @return the DocumentGet response
     * @throws DocumentNotFoundException if the document was not in this particular envelope, or
     *                                   if there was no document with this id in the database.
     * @throws DownloadFileException     if something went wrong while downloading the file.
     */
    @GetMapping("api.elsa.de/user/{userID}/envelopes/{envelopeID:\\d+}/documents/{documentID:\\d+}")
    public DocumentGetResponse getDocumentFromEnvelope(final @PathVariable(ENVELOPE_ID) long envelopeID,
                                                       final @PathVariable(USER_ID) String userID,
                                                       final @PathVariable(DOCUMENT_ID) long documentID,
                                                       final @RequestParam("download") boolean download)
        throws DocumentNotFoundException, DownloadFileException {
        Document document;
        if (download) {
            try {
                final DocumentCreator documentCreator = new DocumentCreator();
                document = documentService.getDocument(documentID);
                documentCreator.download(document, null);
            } catch (CreatingFileException | IOException e) {
                throw new DownloadFileException(e);
            }
        }
        final Envelope envelope = envelopeService.getEnvelope(envelopeID);
        final List<Document> documentList = envelope.getDocumentList();
        boolean isInEnvelope = false;
        for (final Document currentDocument : documentList) {
            if (currentDocument.getId() == documentID) {
                isInEnvelope = true;
                break;
            }
        }
        if (isInEnvelope) {
            document = documentService.getDocument(documentID);
            //TODO change to get owner
            return new DocumentGetResponse(document, userService.getUser(userID));
        } else {
            throw new DocumentNotFoundException();
        }
    }

    /**
     * The uploadNewDocumentVersion method does a put request to update.
     *
     * @param documentPutRequest the request object containing all the necessary data.
     * @param userID             the email of the user uploading the new document.
     * @param envelopeID         the envelope in which the new version should be uploaded.
     * @param documentID         the id of the document to be replaced.
     * @return the id of the new document.
     * @throws UploadFileException if something goes wrong while uploading the new version.
     */
    @PutMapping("api.elsa.de/user/{userID}/envelopes/{envelopeID:\\d+}/documents/{documentID:\\d+}")
    public long uploadNewDocumentVersion(final @RequestBody DocumentPutRequest documentPutRequest,
                                         final @RequestParam(USER_ID) String userID,
                                         final @RequestParam(ENVELOPE_ID) long envelopeID,
                                         final @RequestParam(DOCUMENT_ID) long documentID)
        throws UploadFileException {
        try {
            userService.getUser(userID);
            final Document oldDocument = documentService.getDocument(documentID);
            documentService.remove(oldDocument);
            documentPutRequest.setPath(oldDocument.getDocumentFile().getAbsolutePath());
            final List<User> signatories = new ArrayList<>();
            final List<String> signatoriesID = documentPutRequest.getSignatoriesID();
            final List<User> readers = new ArrayList<>();
            final List<String> readersID = documentPutRequest.getReadersID();
            for (final String currentID : signatoriesID) {
                signatories.add(userService.getUser(currentID));
            }
            for (final String currentID : readersID) {
                readers.add(userService.getUser(currentID));
            }
            final Envelope envelope = envelopeService.getEnvelope(envelopeID);
            envelope.removeDocument(oldDocument);
            final Envelope updatedEnvelope = envelopeService.updateEnvelope(envelopeID, documentPutRequest,
                oldDocument.getOwner(), signatories, readers);
            final List<Document> documentList = updatedEnvelope.getDocumentList();
            final Document newDocument = documentList.get(documentList.size() - 1);
            return newDocument.getId();
        } catch (CreatingFileException | DocumentNotFoundException | IOException | UsernameNotFoundException e) {
            throw new UploadFileException(e);
        }
    }

    /**
     * The review method does a review for a given user on a given document.
     *
     * @param userID     the id of the user reading the document.
     * @param envelopeID the envelope in which the document is situated.
     * @param documentID the document to be reviewed.
     * @throws DocumentNotFoundException if the document was not found.
     */
    @PutMapping("api.elsa.de/user/{*userID}/envelopes/{envelopeID:\\d+}/documents/{documentID:\\d+}/review")
    public void review(final @RequestParam(USER_ID) String userID,
                       final @RequestParam(ENVELOPE_ID) long envelopeID,
                       final @RequestParam(DOCUMENT_ID) long documentID) throws DocumentNotFoundException {
        final User reader = userService.getUser(userID);
        envelopeService.getEnvelope(envelopeID);
        final Document document = documentService.getDocument(documentID);
        final List<Signatory> readers = document.getReaders();
        for (final Signatory currentReader : readers) {
            if (currentReader.getUser().equals(reader)) {
                currentReader.setStatus(true);
            }
        }
        documentService.addDocument(document);
    }

    /**
     * The sign method does a signing for a given user on a given document.
     *
     * @param userID     the id of the user reading the document.
     * @param envelopeID the envelope in which the document is situated.
     * @param documentID the document to be reviewed.
     * @throws DocumentNotFoundException if the document was not found.
     */
    @PutMapping("api.elsa.de/user/{*userID}/envelopes/{envelopeID:\\d+}/documents/{documentID:\\d+}/sign")
    public void sign(final @RequestParam(USER_ID) String userID,
                     final @RequestParam(ENVELOPE_ID) long envelopeID,
                     final @RequestParam(DOCUMENT_ID) long documentID) throws DocumentNotFoundException {
        final User reader = userService.getUser(userID);
        envelopeService.getEnvelope(envelopeID);
        final Document document = documentService.getDocument(documentID);
        final List<Signatory> signatories = document.getSignatories();
        for (final Signatory currentSignatory : signatories) {
            if (currentSignatory.getUser().equals(reader)) {
                //TODO sign the document
                currentSignatory.setStatus(true);
            }
        }
        documentService.addDocument(document);
    }
}

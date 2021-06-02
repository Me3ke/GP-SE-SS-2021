package gpse.example.domain.documents;

import gpse.example.domain.Protocol;
import gpse.example.domain.envelopes.Envelope;
import gpse.example.domain.envelopes.EnvelopeServiceImpl;
import gpse.example.domain.exceptions.CreatingFileException;
import gpse.example.domain.exceptions.DocumentNotFoundException;
import gpse.example.domain.exceptions.DownloadFileException;
import gpse.example.domain.exceptions.UploadFileException;
import gpse.example.domain.signature.Signatory;
import gpse.example.domain.signature.SignatoryServiceImpl;
import gpse.example.domain.users.User;
import gpse.example.domain.users.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * The DocumentController class handles the requests from the frontend and
 * conducts the corresponding backend actions.
 */
//TODO secured for permissions
//TODO Reader Class?
//TODO properties for download
//TODO split documentController in two separate controllers.

@RestController
@CrossOrigin("http://localhost:8088")
@RequestMapping("/api")
public class DocumentController {

    private static final String ENVELOPE_ID = "envelopeID";
    private static final String USER_ID = "userID";
    private static final String DOCUMENT_ID = "documentID";

    private EnvelopeServiceImpl envelopeService;
    private UserServiceImpl userService;
    private DocumentServiceImpl documentService;
    private SignatoryServiceImpl signatoryService;
    private DocumentMetaDataServiceImpl documentMetaDataService;
    private DocumentCreator documentCreator = new DocumentCreator();

    /**
     * The default constructor which initialises the services by autowiring.
     *
     * @param envelopeService the envelopeService
     * @param userService     the userService
     * @param documentService the documentService
     * @param signatoryService the signatoryService
     * @param documentMetaDataService the metaDataService
     */
    @Autowired
    public DocumentController(final EnvelopeServiceImpl envelopeService, final UserServiceImpl userService,
                              final DocumentServiceImpl documentService, final SignatoryServiceImpl signatoryService,
                              final DocumentMetaDataServiceImpl documentMetaDataService) {
        this.envelopeService = envelopeService;
        this.userService = userService;
        this.documentService = documentService;
        this.signatoryService = signatoryService;
        this.documentMetaDataService = documentMetaDataService;
    }

    /**
     * The getDocumentFromEnvelope method gets a get request and creates an appropriate response.
     *
     * @param envelopeID the id of the envelope which contains the document.
     * @param userID     the id of the user doing the request.
     * @param documentID the id of the document asked for.
     * @return the DocumentGet response
     * @throws DocumentNotFoundException if the document was not in this particular envelope, or
     *                                   if there was no document with this id in the database.
     * @throws DownloadFileException     if something went wrong while downloading the file.
     */
    @GetMapping("/user/{userID}/envelopes/{envelopeID:\\d+}/documents/{documentID:\\d+}")
    public DocumentGetResponse getDocumentFromEnvelope(final @PathVariable(ENVELOPE_ID) long envelopeID,
                                                       final @PathVariable(USER_ID) String userID,
                                                       final @PathVariable(DOCUMENT_ID) long documentID)
        throws DocumentNotFoundException {
        Document document;
        final User currentUser = userService.getUser(userID);
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
            return new DocumentGetResponse(document, userService.getUser(document.getOwner()), currentUser);
        } else {
            throw new DocumentNotFoundException();
        }
    }

    /**
     * The downloadDocument method downloads a document and saves it in the downloads directory.
     * @param envelopeID the id of the envelope the document is in.
     * @param userID the id of the user doing the request.
     * @param documentID the id of the document.
     * @param path the path where the file should be downloaded.
     * @return a documentGetResponse of the downloaded Document.
     * @throws DocumentNotFoundException if the document was not found.
     * @throws DownloadFileException if something went wrong while downloading.
     */
    @GetMapping("/user/{userID}/envelopes/{envelopeID:\\d+}/documents/{documentID:\\d+}/download")
    public DocumentGetResponse downloadDocument(final @PathVariable(ENVELOPE_ID) long envelopeID,
                                                final @PathVariable(USER_ID) String userID,
                                                final @PathVariable(DOCUMENT_ID) long documentID,
                                                final @RequestParam("path") String path)
        throws DocumentNotFoundException, DownloadFileException {
        envelopeService.getEnvelope(envelopeID);
        Document document;
        try {
            document = documentService.getDocument(documentID);
            documentCreator.download(document, path);
        } catch (CreatingFileException | IOException e) {
            throw new DownloadFileException(e);
        }
        final User currentUser = userService.getUser(userID);
        return new DocumentGetResponse(document, userService.getUser(document.getOwner()), currentUser);
    }

    /**
     * The uploadNewDocumentVersion method does a put request to update.
     *
     * @param documentPutRequest the request object containing all the necessary data.
     * @param ownerID             the email of the user uploading the new document.
     * @param envelopeID         the envelope in which the new version should be uploaded.
     * @param documentID         the id of the document to be replaced.
     * @return the id of the new document.
     * @throws UploadFileException if something goes wrong while uploading the new version.
     */
    @PutMapping("/user/{userID}/envelopes/{envelopeID:\\d+}/documents/{documentID:\\d+}")
    public long uploadNewDocumentVersion(final @RequestBody DocumentPutRequest documentPutRequest,
                                         final @PathVariable(USER_ID) String ownerID,
                                         final @PathVariable(ENVELOPE_ID) long envelopeID,
                                         final @PathVariable(DOCUMENT_ID) long documentID)
        throws UploadFileException {
        try {
            userService.getUser(ownerID);
            final Envelope envelope = envelopeService.getEnvelope(envelopeID);
            final Document oldDocument = documentService.getDocument(documentID);
            documentService.remove(oldDocument);
            signatoryService.delete(oldDocument.getSignatories());
            envelope.removeDocument(oldDocument);
            final ArchivedDocument archivedDocument = new ArchivedDocument(oldDocument);
            documentService.addDocument(archivedDocument);
            envelopeService.updateEnvelope(envelope, archivedDocument);
            final Document newDocument = documentService.creation(documentPutRequest, envelope, ownerID,
                userService, signatoryService);
            documentMetaDataService.saveDocumentMetaData(newDocument.getDocumentMetaData());
            envelopeService.updateEnvelope(envelope, newDocument);
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
     * @return true if the review was successful and false if not.
     */
    @PutMapping("/user/{userID}/envelopes/{envelopeID:\\d+}/documents/{documentID:\\d+}/review")
    public boolean review(final @PathVariable(USER_ID) String userID,
                          final @PathVariable(ENVELOPE_ID) long envelopeID,
                          final @PathVariable(DOCUMENT_ID) long documentID)
        throws DocumentNotFoundException {
        final User reader = userService.getUser(userID);
        envelopeService.getEnvelope(envelopeID);
        final Document document = documentService.getDocument(documentID);
        boolean documentFinished = true;
        if (document.getState().equals(DocumentState.OPEN)) {
            final List<Signatory> readers = document.getReaders();
            for (final Signatory currentReader : readers) {
                if (currentReader.getUser().equals(reader)) {
                    currentReader.setStatus(true);
                    //TODO save reader in readerRepo
                }
                if (!currentReader.isStatus()) {
                    documentFinished = false;
                }
            }
            if (documentFinished) {
                document.setState(DocumentState.READ);
            }
            documentService.addDocument(document);
            return true;
        } else {
            return false;
        }
    }

    /**
     * The sign method does a signing for a given user on a given document.
     *
     * @param userID     the id of the user reading the document.
     * @param envelopeID the envelope in which the document is situated.
     * @param documentID the document to be reviewed.
     * @throws DocumentNotFoundException if the document was not found.
     * @return true if the signing was successful and false if not.
     */
    //TODO if orderRelevant test if current user is next in line.
    @PutMapping("/user/{userID}/envelopes/{envelopeID:\\d+}/documents/{documentID:\\d+}/sign")
    public boolean sign(final @PathVariable(USER_ID) String userID,
                     final @PathVariable(ENVELOPE_ID) long envelopeID,
                     final @PathVariable(DOCUMENT_ID) long documentID)
        throws DocumentNotFoundException {
        final User signatory = userService.getUser(userID);
        envelopeService.getEnvelope(envelopeID);
        final Document document = documentService.getDocument(documentID);
        boolean documentFinished = true;
        if (document.getState().equals(DocumentState.READ)) {
            final List<Signatory> signatories = document.getSignatories();
            for (final Signatory currentSignatory : signatories) {
                if (currentSignatory.getUser().equals(signatory)) {
                    //TODO sign the document
                    currentSignatory.setStatus(true);
                    signatoryService.saveSignatory(currentSignatory);
                }
                if (!currentSignatory.isStatus()) {
                    documentFinished = false;
                }
            }
            if (documentFinished) {
                document.setState(DocumentState.CLOSED);
                final ArchivedDocument archivedDocument = new ArchivedDocument(document);
                documentService.remove(document);
                documentService.addDocument(archivedDocument);
                signatoryService.delete(document.getSignatories());
            } else {
                documentService.addDocument(document);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param userID
     * @param envelopeID
     * @param documentID
     * @return
     */
    @GetMapping("/user/{userID}/envelopes/{envelopeID:\\d+}/documents/{documentID:\\d+}/protocol")
    public byte[] showProtocol(final @PathVariable(USER_ID) String userID,
                               final @PathVariable(ENVELOPE_ID) long envelopeID,
                               final @PathVariable(DOCUMENT_ID) long documentID) {
        //TODO createProtocol
        return new byte[0];
    }

    /**
     *
     * @param userID
     * @param envelopeID
     * @param documentID
     * @param path
     * @return
     */
    @GetMapping("/user/{userID}/envelopes/{envelopeID:\\d+}/documents/{documentID:\\d+}/protocol/download")
    public byte[] downloadProtocol(final @PathVariable(USER_ID) String userID,
                                   final @PathVariable(ENVELOPE_ID) long envelopeID,
                                   final @PathVariable(DOCUMENT_ID) long documentID,
                                   final @RequestParam("path") String path) {
        //TODO createProtocol
        //TODO downloadProtocol
        return new byte[0];
    }
}

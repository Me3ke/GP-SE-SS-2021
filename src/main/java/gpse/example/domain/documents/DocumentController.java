package gpse.example.domain.documents;

import gpse.example.domain.Protocol;
import gpse.example.domain.envelopes.Envelope;
import gpse.example.domain.envelopes.EnvelopeServiceImpl;
import gpse.example.domain.exceptions.CreatingFileException;
import gpse.example.domain.exceptions.DocumentNotFoundException;
import gpse.example.domain.exceptions.DownloadFileException;
import gpse.example.domain.exceptions.UploadFileException;
import gpse.example.domain.signature.SignatoryServiceImpl;
import gpse.example.domain.signature.SignatureType;
import gpse.example.domain.users.User;
import gpse.example.domain.users.UserServiceImpl;
import gpse.example.web.JSONResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.codec.binary.Base64;

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
    private static final int STATUS_CODE_DOCUMENT_CLOSED = 452;
    private static final String PROTOCOL_NAME = "Protocol_";
    private static final String ATTACHMENT = "attachment; filename=";
    private static final int STATUS_CODE_INVALID_SIGNATURE = 456;
    private static final int STATUS_CODE_OK = 200;
    private final EnvelopeServiceImpl envelopeService;
    private final UserServiceImpl userService;
    private final DocumentServiceImpl documentService;
    private final SignatoryServiceImpl signatoryService;
    private final DocumentMetaDataServiceImpl documentMetaDataService;
    private final DocumentCreator documentCreator = new DocumentCreator();


    /**
     * The default constructor which initialises the services by autowiring.
     *
     * @param envelopeService         the envelopeService
     * @param userService             the userService
     * @param documentService         the documentService
     * @param signatoryService        the signatoryService
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
     *
     * @param envelopeID the id of the envelope the document is in.
     * @param userID     the id of the user doing the request.
     * @param documentID the id of the document.
     * @return a documentGetResponse of the downloaded Document.
     * @throws DocumentNotFoundException if the document was not found.
     * @throws DownloadFileException     if something went wrong while downloading.
     */
    @GetMapping("/user/{userID}/envelopes/{envelopeID:\\d+}/documents/{documentID:\\d+}/download")
    public ResponseEntity<byte[]> downloadDocument(final @PathVariable(ENVELOPE_ID) long envelopeID,
                                                   final @PathVariable(USER_ID) String userID,
                                                   final @PathVariable(DOCUMENT_ID) long documentID)
        throws DocumentNotFoundException, DownloadFileException {
        userService.getUser(userID);
        envelopeService.getEnvelope(envelopeID);
        final Document document = documentService.getDocument(documentID);
        final String name = document.getDocumentTitle() + "." + document.getDocumentType();
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, ATTACHMENT + name)
            .body(document.getData());
    }

    /**
     * The uploadNewDocumentVersion method does a put request to update.
     *
     * @param documentPutRequest the request object containing all the necessary data.
     * @param ownerID            the email of the user uploading the new document.
     * @param envelopeID         the envelope in which the new version should be uploaded.
     * @param documentID         the id of the document to be replaced.
     * @return the id of the new document.
     * @throws UploadFileException if something goes wrong while uploading the new version.
     */

    @PutMapping("/user/{userID}/envelopes/{envelopeID:\\d+}/documents/{documentID:\\d+}")
    public DocumentPutResponse uploadNewDocumentVersion(final @RequestBody DocumentPutRequest documentPutRequest,
                                                        final @PathVariable(USER_ID) String ownerID,
                                                        final @PathVariable(ENVELOPE_ID) long envelopeID,
                                                        final @PathVariable(DOCUMENT_ID) long documentID)
        throws UploadFileException {
        try {
            userService.getUser(ownerID);
            final Envelope envelope = envelopeService.getEnvelope(envelopeID);
            final Document oldDocument = documentService.getDocument(documentID);
            //TODO old document does not have to be removed from the database
            envelope.removeDocument(oldDocument);
            signatoryService.delete(oldDocument.getSignatories());
            documentService.remove(oldDocument);
            documentMetaDataService.delete(oldDocument.getDocumentMetaData());
            final Document archivedDocument = new ArchivedDocument(oldDocument);
            documentMetaDataService.saveDocumentMetaData(archivedDocument.getDocumentMetaData());
            signatoryService.saveSignatories(archivedDocument.getSignatories());
            final Document savedDocument = documentService.addDocument(archivedDocument);
            //TODO archived document should not be saved in envelope!
            final Document newDocument = documentService.creation(documentPutRequest, envelope, ownerID,
                userService, signatoryService);
            newDocument.setPreviousVersion(savedDocument);
            envelopeService.updateEnvelope(envelope, newDocument);
            return new DocumentPutResponse(savedDocument.getId(), newDocument.getId());
        } catch (CreatingFileException | DocumentNotFoundException | IOException | UsernameNotFoundException e) {
            throw new UploadFileException(e);
        }
    }

    /**
     * The review method does a review for a given user on a given document.
     *
     * @param userID     the id of the user reading the document.
     * @param documentID the document to be reviewed.
     * @return true if the review was successful and false if not.
     * @throws DocumentNotFoundException if the document was not found.
     */
    @PutMapping("/user/{userID}/envelopes/{envelopeID:\\d+}/documents/{documentID:\\d+}/review")
    public JSONResponseObject review(final @PathVariable(USER_ID) String userID,
                                     final @PathVariable(DOCUMENT_ID) long documentID)
        throws DocumentNotFoundException {
        return computeSignatureRequest(userID, documentID, SignatureType.REVIEW);
    }


    /**
     * The sign method does a signing for a given user on a given document.
     *
     * @param userID     the id of the user reading the document.
     * @param documentID the document to be reviewed.
     * @return true if the signing was successful and false if not.
     * @throws DocumentNotFoundException if the document was not found.
     */
    //TODO if orderRelevant test if current user is next in line.
    @PutMapping("/user/{userID}/envelopes/{envelopeID:\\d+}/documents/{documentID:\\d+}/signSimple")
    public JSONResponseObject signSimple(final @PathVariable(USER_ID) String userID,
                              final @PathVariable(DOCUMENT_ID) long documentID)
        throws DocumentNotFoundException {
        return computeSignatureRequest(userID, documentID, SignatureType.SIMPLE_SIGNATURE);
    }

    /**
     * The sign method does a signing for a given user on a given document.
     *
     * @param userID     the id of the user reading the document.
     * @param documentID the document to be reviewed.
     * @param advancedSignatureRequest the request Object containing the advanced signature
     * @return true if the signing was successful and false if not.
     * @throws DocumentNotFoundException if the document was not found.
     */
    //TODO if orderRelevant test if current user is next in line.
    @PutMapping("/user/{userID}/envelopes/{envelopeID:\\d+}/documents/{documentID:\\d+}/signAdvanced")
    public JSONResponseObject signAdvanced(final @PathVariable(USER_ID) String userID,
                                           final @PathVariable(DOCUMENT_ID) long documentID,
                                           final @RequestBody AdvancedSignatureRequest advancedSignatureRequest)
            throws DocumentNotFoundException {
        final Document document = documentService.getDocument(documentID);
        if (document.verifySignature(userService.getUser(userID), advancedSignatureRequest.getSignature())) {
            final JSONResponseObject response = computeSignatureRequest(userID,
                documentID, SignatureType.ADVANCED_SIGNATURE);
            if (response.getStatus() == STATUS_CODE_OK) {
                document.advancedSignature(userID, advancedSignatureRequest.getSignature());
            }
            return response;
        } else {
            final JSONResponseObject response = new JSONResponseObject();
            response.setStatus(STATUS_CODE_INVALID_SIGNATURE);
            response.setMessage("Invalid signature for this document");
            return response;
        }
    }

    private JSONResponseObject computeSignatureRequest(final String userID, final long documentID,
                                                       final SignatureType signatureType)
            throws DocumentNotFoundException {
        final User reader = userService.getUser(userID);
        final Document document = documentService.getDocument(documentID);
        final JSONResponseObject response = new JSONResponseObject();
        if (document.getState().equals(DocumentState.CLOSED)) {
            response.setStatus(STATUS_CODE_DOCUMENT_CLOSED);
            response.setMessage("This document is closed");
            return response;
        } else {
            final SignatureManagement signatureManagement = new SignatureManagement(signatoryService, documentService);
            return signatureManagement.manageSignatureRequest(reader, document, signatureType);
        }
    }

    /**
     * The getDocumentHistory method does a get request to get the document history.
     *
     * @param documentID the id of the document of which the history is requested.
     * @return a list of all previous versions and the latest one.
     * @throws DocumentNotFoundException if the document was not found.
     */
    @GetMapping("/user/{userID}/envelopes/{envelopeID:\\d+}/documents/{documentID:\\d+}/history")
    public List<Document> getDocumentHistory(final @PathVariable(DOCUMENT_ID) long documentID)
        throws DocumentNotFoundException {
        return documentService.getDocument(documentID).getHistory();
    }

    /**
     * getting bytearry to show in frontend.
     *
     * @param documentID documentId
     * @return byteArray
     */
    @GetMapping("/documents/{documentID:\\d+}/protocol")
    public byte[] showProtocol(final @PathVariable(DOCUMENT_ID) long documentID)
        throws DocumentNotFoundException, CreatingFileException {
        final Protocol protocol = new Protocol(documentService.getDocument(documentID));
        try {
            return Base64.encodeBase64(protocol.writeProtocol().toByteArray());
        } catch (IOException e) {
            throw new CreatingFileException(e);
        }
    }

    /**
     * download the protocol to given path.
     *
     * @param documentID document that should be protocoled
     * @return bytearry to download
     */
    @GetMapping("/documents/{documentID:\\d+}/protocol/download")
    public ResponseEntity<byte[]> downloadProtocol(final @PathVariable(DOCUMENT_ID) long documentID)
        throws DocumentNotFoundException, CreatingFileException {
        final Document document = documentService.getDocument(documentID);
        final Protocol protocol = new Protocol(document);
        try {
            byte[] protocolBytes = protocol.writeProtocol().toByteArray();
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, ATTACHMENT + PROTOCOL_NAME + documentID)
                .body(protocolBytes);
        } catch (IOException e) {
            throw new CreatingFileException(e);
        }
    }
}

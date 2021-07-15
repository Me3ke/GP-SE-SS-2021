package gpse.example.web.documents;

import gpse.example.domain.corporatedesign.CorporateDesignService;
import gpse.example.domain.documents.Document;
import gpse.example.domain.documents.DocumentServiceImpl;
import gpse.example.domain.documents.DocumentState;
import gpse.example.domain.documents.SignatoryManagement;
import gpse.example.domain.envelopes.Envelope;
import gpse.example.domain.envelopes.EnvelopeServiceImpl;
import gpse.example.domain.exceptions.*;
import gpse.example.domain.protocol.Protocol;
import gpse.example.domain.signature.Signatory;
import gpse.example.domain.signature.SignatureType;
import gpse.example.domain.users.UserServiceImpl;
import gpse.example.web.JSONResponseObject;
import gpse.example.web.envelopes.DocumentOverviewResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

/**
 * The DocumentController class handles the requests from the frontend and
 * conducts the corresponding backend actions.
 */
//TODO split documentController in two separate controllers.

@RestController
@CrossOrigin("http://localhost:8088")
@RequestMapping("/api")
public class DocumentController {

    private static final String ENVELOPE_ID = "envelopeID";
    private static final String USER_ID = "userID";
    private static final String DOCUMENT_ID = "documentID";
    private static final int STATUS_CODE_DOCUMENT_NOT_FOUND = 453;
    private static final int STATUS_CODE_OK = 200;
    private static final String PROTOCOL_NAME = "Protocol_";
    private static final String ATTACHMENT = "attachment; filename=";
    private static final String TOKEN = "token";
    private static final String DOCUMENT_URL = "/document/";
    private static final String HTTP_LOCALHOST = "http://localhost:";
    private static final String ENVELOPE_URL = "/de/envelope/";
    @Value("${server.port}")
    private int serverPort;
    private final EnvelopeServiceImpl envelopeService;
    private final UserServiceImpl userService;
    private final DocumentServiceImpl documentService;
    private final GuestTokenService guestTokenService;
    private final DocumentControllerUtil documentControllerUtil;

    @Autowired
    private CorporateDesignService corporateDesignService;

    /**
     * The default constructor which initialises the services by autowiring.
     *
     * @param envelopeService     the envelopeService
     * @param userService         the userService
     * @param documentService     the documentService
     * @param guestTokenService   the guestTokenService
     * @param documentControllerUtil  the utilitys
     */
    @Autowired
    public DocumentController(final EnvelopeServiceImpl envelopeService, final UserServiceImpl userService,
                              final DocumentServiceImpl documentService, final GuestTokenService guestTokenService,
                              final DocumentControllerUtil documentControllerUtil) {

        this.envelopeService = envelopeService;
        this.userService = userService;
        this.documentService = documentService;
        this.guestTokenService = guestTokenService;
        this.documentControllerUtil = documentControllerUtil;

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
        final Envelope envelope = envelopeService.getEnvelope(envelopeID);
        final List<Document> documentList = envelope.getDocumentList();
        if (documentControllerUtil.isInEnvelope(documentID, documentList)) {
            final Document document = documentService.getDocument(documentID);
            final SignatoryManagement signatoryManagement = document.getSignatoryManagement();
            if (document.getDocumentType().equals("pdf")) {
                final List<Signatory> signatories = signatoryManagement.getSignatories();
                for (final Signatory signatory : signatories) {
                    if (signatory.getEmail().equals(userID)) {
                        signatory.setSeen(true);
                    }
                }
                documentService.addDocument(document);

            }

            return new DocumentGetResponse(document, userService.getUser(document.getOwner()), userID);
        } else {
            throw new DocumentNotFoundException();
        }
    }

    /**
     * Getting the document with guestToken.
     *
     * @param envelopeID id of envelope
     * @param documentID id of document
     * @param token      String Token to get the tokenobject (or not if its expired? or wrong)
     * @return the documentGetResponse
     * @throws DocumentNotFoundException is thrown if the document that the request relates to does not exist.
     */
    @GetMapping("/envelopes/{envelopeID:\\d+}/documents/{documentID:\\d+}/token/{token}")
    public DocumentGetResponse getDocumentGuestAccess(@PathVariable(ENVELOPE_ID) final long envelopeID,
                                                      @PathVariable(DOCUMENT_ID) final long documentID,
                                                      @PathVariable(TOKEN) final String token)
        throws DocumentNotFoundException {
        final Optional<GuestToken> guestTokenOptional = guestTokenService.findGuestTokenByToken(token);

        if (guestTokenOptional.isEmpty()) {
            return null;
        } else if (guestTokenOptional.get().getDocumentId() == documentID) {
            final Envelope envelope = envelopeService.getEnvelope(envelopeID);
            final List<Document> documentList = envelope.getDocumentList();
            if (documentControllerUtil.isInEnvelope(documentID, documentList)) {
                final Document document = documentService.getDocument(documentID);
                final SignatoryManagement signatoryManagement = document.getSignatoryManagement();
                final List<Signatory> signatories = signatoryManagement.getSignatories();
                for (final Signatory signatory : signatories) {
                    if (signatory.getEmail().equals(guestTokenOptional.get().getUsername())) {
                        return new DocumentGetResponse(document, userService.getUser(document.getOwner()),
                                guestTokenOptional.get().getUsername());
                    }
                }
            } else {
                throw new DocumentNotFoundException();
            }
        }
        return null;
    }

    /**
     * The downloadDocument method downloads a document and saves it in the downloads directory.
     *
     * @param envelopeID the id of the envelope the document is in.
     * @param userID     the id of the user doing the request.
     * @param documentID the id of the document.
     * @return a documentGetResponse of the downloaded Document.
     * @throws DocumentNotFoundException if the document was not found.
     */
    @GetMapping("/user/{userID}/envelopes/{envelopeID:\\d+}/documents/{documentID:\\d+}/download")
    public ResponseEntity<byte[]> downloadDocument(final @PathVariable(ENVELOPE_ID) long envelopeID,
                                                   final @PathVariable(USER_ID) String userID,
                                                   final @PathVariable(DOCUMENT_ID) long documentID)
            throws DocumentNotFoundException {
        userService.getUser(userID);
        envelopeService.getEnvelope(envelopeID);
        final Document document = documentService.getDocument(documentID);
        final SignatoryManagement signatoryManagement = document.getSignatoryManagement();
        final String name = document.getDocumentTitle() + "." + document.getDocumentType();
        final List<Signatory> signatories = signatoryManagement.getSignatories();
        for (final Signatory signatory : signatories) {
            if (signatory.getEmail().equals(userID)) {
                signatory.setSeen(true);
            }
        }

        documentService.addDocument(document);

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, ATTACHMENT + name)
            .body(document.getData());
    }

    /**
     * Gives back archived document.
     *
     * @param userID     the id of the user doing the request.
     * @param documentID the id of the document.
     * @return a documentGetResponse of the archived document
     * @throws DocumentNotFoundException s thrown if the document that the request relates to does not exist.
     */
    @GetMapping("/user/{userID}/archivedDocument/{documentID}")
    public DocumentGetResponse getArchivedDocuments(@PathVariable("userID") final String userID,
                                                    @PathVariable("documentID") final long documentID)
        throws DocumentNotFoundException {
        final Document document = documentService.getDocument(documentID);
        if (document.getSignatureProcessData().getState() == DocumentState.ARCHIVED) {
            return new DocumentGetResponse(document, userService.getUser(document.getOwner()), userID);
        }
        return null;
    }

    /**
     * The uploadNewDocumentVersion method does a put request to update. If the user that stated the request is not
     * the owner of the envelope or the oldDocument is already archived, the oldDocument will be returned as the
     * new one and nothing else happens.
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
        throws UploadFileException, MessageGenerationException, TemplateNameNotFoundException {
        try {
            userService.getUser(ownerID);
            final Envelope envelope = envelopeService.getEnvelope(envelopeID);
            final Document oldDocument = documentService.getDocument(documentID);
            if (envelope.getOwnerID().equals(ownerID)
                    || oldDocument.getSignatureProcessData().getState().equals(DocumentState.ARCHIVED)) {
                envelope.removeDocument(oldDocument);
                oldDocument.getSignatureProcessData().setDraft(false);
                oldDocument.getSignatureProcessData().setState(DocumentState.ARCHIVED);
                final Document newDocument = documentService.creation(documentPutRequest, ownerID,
                        userService);
                envelopeService.updateEnvelope(envelope, newDocument);
                newDocument.setPreviousVersion(oldDocument);
                newDocument.setLinkToDocumentView(HTTP_LOCALHOST + serverPort + ENVELOPE_URL + envelope.getId()
                        + DOCUMENT_URL + newDocument.getId());
                documentService.addDocument(newDocument);
                documentControllerUtil.informSignatories(newDocument, envelopeID);
                return new DocumentPutResponse(oldDocument.getId(), newDocument.getId());
            } else {
                return new DocumentPutResponse(oldDocument.getId(), oldDocument.getId());
            }
        } catch (CreatingFileException | DocumentNotFoundException | IOException | UsernameNotFoundException e) {
            throw new UploadFileException(e);
        }
    }

    @PutMapping("/token/{token}/envelopes/{envelopeID:\\d+}/documents/{documentID:\\d+}/signSimple")
    public JSONResponseObject signSimpleAsGuest(final @PathVariable(TOKEN) String token,
                                                final @PathVariable(DOCUMENT_ID) long documentID,
                                                final @PathVariable(ENVELOPE_ID) long envelopeID)
        throws DocumentNotFoundException, TemplateNameNotFoundException, MessageGenerationException {
        return documentControllerUtil.computeGuestSignatureRequest(token, documentID,
                SignatureType.SIMPLE_SIGNATURE, envelopeID);
    }

    @PutMapping("/token/{token}/envelopes/{envelopeID:\\d+}/documents/{documentID:\\d+}/review")
    public JSONResponseObject reviewAsGuest(final @PathVariable(TOKEN) String token,
                                            final @PathVariable(DOCUMENT_ID) long documentID,
                                            final @PathVariable(ENVELOPE_ID) long envelopeID)
        throws DocumentNotFoundException, TemplateNameNotFoundException, MessageGenerationException {
        return documentControllerUtil.computeGuestSignatureRequest(token, documentID,
                SignatureType.REVIEW, envelopeID);
    }

    /**
     * The review method does a review for a given user on a given document.
     *
     * @param userID     the id of the user reading the document.
     * @param documentID the document to be reviewed.
     * @param envelopeID the envelope the document refers to.
     * @return true if the review was successful and false if not.
     * @throws DocumentNotFoundException if the document was not found.
     */
    @PutMapping("/user/{userID}/envelopes/{envelopeID:\\d+}/documents/{documentID:\\d+}/review")
    public JSONResponseObject review(final @PathVariable(USER_ID) String userID,
                                     final @PathVariable(DOCUMENT_ID) long documentID,
                                     final @PathVariable(ENVELOPE_ID) long envelopeID)
        throws DocumentNotFoundException, MessageGenerationException, TemplateNameNotFoundException {
        return documentControllerUtil.computeSignatureRequest(userID, documentID, SignatureType.REVIEW, envelopeID);
    }


    /**
     * The sign method does a signing for a given user on a given document.
     *
     * @param userID     the id of the user reading the document.
     * @param documentID the document to be reviewed.
     * @param envelopeID the envelope the document refers to.
     * @return true if the signing was successful and false if not.
     * @throws DocumentNotFoundException if the document was not found.
     */
    @PutMapping("/user/{userID}/envelopes/{envelopeID:\\d+}/documents/{documentID:\\d+}/signSimple")
    public JSONResponseObject signSimple(final @PathVariable(USER_ID) String userID,
                                         final @PathVariable(DOCUMENT_ID) long documentID,
                                         final @PathVariable(ENVELOPE_ID) long envelopeID)
        throws DocumentNotFoundException, MessageGenerationException, TemplateNameNotFoundException {
        return documentControllerUtil.computeSignatureRequest(userID, documentID,
                SignatureType.SIMPLE_SIGNATURE, envelopeID);
    }

    /**
     * The sign method does a signing for a given user on a given document.
     *
     * @param userID                   the id of the user reading the document.
     * @param documentID               the document to be reviewed.
     * @param envelopeID               the envelope the document refers to.
     * @param advancedSignatureRequest the request Object containing the advanced signature
     * @return true if the signing was successful and false if not.
     * @throws DocumentNotFoundException if the document was not found.
     */
    @PutMapping("/user/{userID}/envelopes/{envelopeID:\\d+}/documents/{documentID:\\d+}/signAdvanced")
    public JSONResponseObject signAdvanced(final @PathVariable(USER_ID) String userID,
                                           final @PathVariable(DOCUMENT_ID) long documentID,
                                           final @PathVariable(ENVELOPE_ID) long envelopeID,
                                           final @RequestBody AdvancedSignatureRequest advancedSignatureRequest)
        throws DocumentNotFoundException, MessageGenerationException, TemplateNameNotFoundException {
        final Document document = documentService.getDocument(documentID);
        final JSONResponseObject response = documentControllerUtil.computeSignatureRequest(userID,
                documentID, SignatureType.ADVANCED_SIGNATURE, envelopeID);
        if (response.getStatus() == STATUS_CODE_OK) {
            document.advancedSignature(userID, advancedSignatureRequest.getSignature());
            documentService.addDocument(document);
        }
        return response;
    }

    /**
     * The getDocumentHistory method does a get request to get the document history.
     *
     * @param userID     the id of the user getting the history.
     * @param documentID the id of the document of which the history is requested.
     * @param envelopeID the envelope id
     * @return a list of all previous versions and the latest one.
     * @throws DocumentNotFoundException if the document was not found.
     */
    @GetMapping("/user/{userID}/envelopes/{envelopeID:\\d+}/documents/{documentID:\\d+}/history")
    public List<DocumentOverviewResponse> getDocumentHistory(@PathVariable final String userID,
                                                             final @PathVariable(DOCUMENT_ID) long documentID,
                                                             final @PathVariable(ENVELOPE_ID) long envelopeID)
        throws DocumentNotFoundException {
        final Document document = documentService.getDocument(documentID);
        final List<Document> documentHistory = document.getHistory();
        final List<DocumentOverviewResponse> responseHistory = new ArrayList<>();
        if (envelopeService.getEnvelope(envelopeID).getDocumentList().contains(document)) {
            for (final Document doc : documentHistory) {
                responseHistory.add(new DocumentOverviewResponse(doc, userService.getUser(doc.getOwner()),
                    userID));
            }
        }
        return responseHistory;
    }

    /**
     * getting bytearry to show in frontend.
     *
     * @param documentID documentId
     * @return byteArray
     */
    @GetMapping("/documents/{documentID:\\d+}/protocol")
    public byte[] showProtocol(final @PathVariable(DOCUMENT_ID) long documentID)
        throws DocumentNotFoundException, CreatingFileException, CorporateDesignNotFoundException {
        final Protocol protocol = new Protocol(documentService.getDocument(documentID));
        try {
            return Base64.encodeBase64(protocol.writeProtocol(userService, corporateDesignService).toByteArray());
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
        throws DocumentNotFoundException, CreatingFileException, CorporateDesignNotFoundException {
        final Document document = documentService.getDocument(documentID);
        final Protocol protocol = new Protocol(document);
        try {
            // TODO Document Title is null after updated Document
            final byte[] protocolBytes = protocol.writeProtocol(userService, corporateDesignService).toByteArray();
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, ATTACHMENT + PROTOCOL_NAME + documentID)
                .body(protocolBytes);
        } catch (IOException e) {
            throw new CreatingFileException(e);
        }
    }

    /**
     * The getDocumentProgress method is used to track the progress of the signing process.
     * It uses DocumentProgress response to create an appropriate response.
     *
     * @param userID     the id of the user currently wanting to see the progress.
     * @param envelopeID the id of the envelope in which the document appears to be.
     * @param documentID the id of the document which's progress should be tracked.
     * @return the documentProgressResponse
     * @throws DocumentNotFoundException if the document was not found.
     */
    @GetMapping("/user/{userID}/envelopes/{envelopeID:\\d+}/documents/{documentID:\\d+}/progress")
    public DocumentProgressResponse getDocumentProgress(final @PathVariable(USER_ID) String userID,
                                                        final @PathVariable(ENVELOPE_ID) long envelopeID,
                                                        final @PathVariable(DOCUMENT_ID) long documentID)
        throws DocumentNotFoundException {
        userService.getUser(userID);
        envelopeService.getEnvelope(envelopeID);
        final Document document = documentService.getDocument(documentID);
        final SignatoryManagement signatoryManagement = document.getSignatoryManagement();
        final List<Signatory> signatories = signatoryManagement.getSignatories();
        final LinkedHashSet<Signatory> signatoryLinkedHashSet = new LinkedHashSet<>(signatories);
        signatories.clear();
        signatories.addAll(signatoryLinkedHashSet);

        return new DocumentProgressResponse(signatoryManagement.getSignatories(), signatoryManagement.getReaders(),
            document.getSignatureProcessData().getEndDate());
    }

    /**
     * Put request for changing documentsettings.
     *
     * @param documentID          id of the document that should be changed
     * @param documentSettingsCMD Container for the relevant settings
     * @return JsonResponse containing statuscode
     */
    @PutMapping("/document/{documentID}/settings")
    public JSONResponseObject changeSettings(final @PathVariable(DOCUMENT_ID) long documentID,
                                             final @RequestBody DocumentSettingsCMD documentSettingsCMD) {
        final JSONResponseObject response = new JSONResponseObject();
        try {
            final Document document = documentService.getDocument(documentID);
            document.getSignatureProcessData().setOrderRelevant(documentSettingsCMD.isOrderRelevant());
            document.getSignatureProcessData().setEndDate(documentSettingsCMD.convertEndDate());
            document.getSignatureProcessData().setShowHistory(documentSettingsCMD.isShowHistory());
            if (document.getSignatureProcessData().isDraft()) {
                document.getSignatureProcessData().setDraft(documentSettingsCMD.isDraft());
            }
            if (documentSettingsCMD.isArchiveTask()) {
                document.getSignatureProcessData().setState(DocumentState.ARCHIVED);
            }
            final List<Signatory> signatories = new ArrayList<>();
            final List<SignatorySetting> signatorySettings = documentSettingsCMD.getSignatories();
            Signatory signatory;
            for (final SignatorySetting signatorySetting : signatorySettings) {
                signatory = new Signatory(signatorySetting.getEmail(),
                    signatorySetting.getSignatureTypeAsEnum());
                signatory.setStatus(signatorySetting.isStatus());
                signatory.setReminder(signatorySetting.getReminderTiming());
                signatory.setSignedOn(signatorySetting.convertSignedOn());
                signatories.add(signatory);
            }
            document.getSignatoryManagement().setSignatories(signatories);
            documentControllerUtil.setDocumentState(document);
            documentService.addDocument(document);

            response.setStatus(STATUS_CODE_OK);
        } catch (DocumentNotFoundException e) {
            response.setStatus(STATUS_CODE_DOCUMENT_NOT_FOUND);
            response.setMessage("Document not found.");
        }
        return response;
    }

    /**
     * Request for get the info if user has seen specified document.
     *
     * @param documentId id of document
     * @param userId     id of user
     * @return Response entity with the boolean in the body and documentId in the header
     * @throws DocumentNotFoundException thrown if there is no document with specified Id
     */
    @GetMapping("/document/{documentID}/user/{userID}/seen")
    public ResponseEntity<Boolean> documentHasBeenSeen(@PathVariable("documentID") final long documentId,
                                                       @PathVariable("userID") final String userId)
        throws DocumentNotFoundException {
        final Document document = documentService.getDocument(documentId);
        final SignatoryManagement signatoryManagement = document.getSignatoryManagement();
        for (final Signatory signatory : signatoryManagement.getSignatories()) {
            if (signatory.getEmail().equals(userId)) {
                return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, Long.toString(documentId))
                    .body(signatory.isSeen());
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .header(HttpHeaders.CONTENT_DISPOSITION, Long.toString(documentId))
            .body(null);
    }
}

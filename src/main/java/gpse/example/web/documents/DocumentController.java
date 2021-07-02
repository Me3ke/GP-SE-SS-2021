package gpse.example.web.documents;

import gpse.example.domain.Protocol;
import gpse.example.domain.documents.*;
import gpse.example.domain.envelopes.Envelope;
import gpse.example.domain.envelopes.EnvelopeServiceImpl;
import gpse.example.domain.exceptions.CreatingFileException;
import gpse.example.domain.exceptions.DocumentNotFoundException;
import gpse.example.domain.exceptions.DownloadFileException;
import gpse.example.domain.exceptions.UploadFileException;
import gpse.example.domain.signature.Signatory;
import gpse.example.domain.signature.SignatureType;
import gpse.example.domain.users.UserServiceImpl;
import gpse.example.util.email.*;
import gpse.example.web.JSONResponseObject;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
    private static final int STATUS_CODE_DOCUMENT_CLOSED = 452;
    private static final int STATUS_CODE_TOKEN_DOESNT_EXIST = 423;
    private static final String PROTOCOL_NAME = "Protocol_";
    private static final String ATTACHMENT = "attachment; filename=";
    private static final String TOKEN = "token";
    private final EnvelopeServiceImpl envelopeService;
    private final UserServiceImpl userService;
    private final DocumentServiceImpl documentService;
    private final GuestTokenService guestTokenService;
    private final SignatureManagement signatureManagement;

    @Lazy
    @Autowired
    private SMTPServerHelper smtpServerHelper;
    @Autowired
    private EmailTemplateService emailTemplateService;

    /**
     * The default constructor which initialises the services by autowiring.
     *
     * @param envelopeService         the envelopeService
     * @param userService             the userService
     * @param documentService         the documentService
     * @param guestTokenService       the guestTokenService
     * @param signatureManagement     the signatureManagement
     */
    @Autowired
    public DocumentController(final EnvelopeServiceImpl envelopeService, final UserServiceImpl userService,
                              final DocumentServiceImpl documentService, final GuestTokenService guestTokenService,
                              final SignatureManagement signatureManagement) {

        this.envelopeService = envelopeService;
        this.userService = userService;
        this.documentService = documentService;
        this.guestTokenService = guestTokenService;
        this.signatureManagement = signatureManagement;
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
            if (document.getDocumentType().equals("pdf")) {
                final List<Signatory> signatories = document.getSignatories();
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
     * @param envelopeID id of envelope
     * @param documentID id of document
     * @param token String Token to get the tokenobject (or not if its expired? or wrong)
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
            System.out.println("Test1");
            return null;
        } else if (guestTokenOptional.get().getDocumentId() == documentID) {
            Document document;
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
                final List<Signatory> signatories = document.getSignatories();
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
        System.out.println("Test2");
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
        final List<Signatory> signatories = document.getSignatories();
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
        throws UploadFileException, MessageGenerationException, TemplateNameNotFoundException {
        try {
            userService.getUser(ownerID);
            final Envelope envelope = envelopeService.getEnvelope(envelopeID);
            final Document oldDocument = documentService.getDocument(documentID);
            envelope.removeDocument(oldDocument);
            documentService.remove(oldDocument);
            //System.out.println("old document: " + oldDocument.getSignatories());
            final Document archivedDocument = new ArchivedDocument(oldDocument);
            final Document savedDocument = documentService.addDocument(archivedDocument);
            //TODO archived document should not be saved in envelope!

            final Document newDocument = documentService.creation(documentPutRequest, ownerID,
                userService);
            newDocument.setPreviousVersion(savedDocument);
            envelopeService.updateEnvelope(envelope, newDocument);
            informSignatories(newDocument);
            return new DocumentPutResponse(savedDocument.getId(), newDocument.getId());
        } catch (CreatingFileException | DocumentNotFoundException | IOException | UsernameNotFoundException e) {
            throw new UploadFileException(e);
        }
    }

    /**
     * The informSignatories method uses the smtpServerHelper to inform the
     * signatories of a document that their signature has been invalidated while
     * uploading a new version.
     * @param document the new document.
     * @throws TemplateNameNotFoundException if the template does not exist.
     * @throws MessageGenerationException if the message could not be generated.
     */
    private void informSignatories(final Document document)
        throws TemplateNameNotFoundException, MessageGenerationException {
        final EmailTemplate emailTemplate = emailTemplateService.findSystemTemplateByName("NewVersionTemplate");
        final TemplateDataContainer container = new TemplateDataContainer();
        container.setDocumentTitle(document.getDocumentTitle());
        container.setLink("LinkToDocumentView");
        if (document.isOrderRelevant()) {
            for (final Signatory signatory : document.getSignatories()) {
                if (signatory.isStatus() || document.getCurrentSignatory().equals(signatory)) {
                    smtpServerHelper.sendTemplatedEmail(signatory.getEmail(), emailTemplate,
                        container, Category.NEW_VERSION, userService.getUser(document.getOwner()));
                }
            }
        } else {
            for (final Signatory signatory : document.getSignatories()) {
                smtpServerHelper.sendTemplatedEmail(signatory.getEmail(), emailTemplate,
                    container, Category.NEW_VERSION, userService.getUser(document.getOwner()));
            }
        }
    }

    @PutMapping("/token/{token}/documents/{documentID:\\d+}/signSimple")
    public JSONResponseObject signSimpleAsGuest(@PathVariable(TOKEN) String token,
                                                @PathVariable(DOCUMENT_ID) long documentID)
        throws DocumentNotFoundException, TemplateNameNotFoundException, MessageGenerationException {
        return computeGuestSignatureRequest(token, documentID, SignatureType.SIMPLE_SIGNATURE);
    }

    @PutMapping("/token/{token}/documents/{documentID:\\d+}/review")
    public JSONResponseObject reviewAsGuest(final @PathVariable(TOKEN) String token,
                                            final @PathVariable(DOCUMENT_ID) long documentID)
        throws DocumentNotFoundException, TemplateNameNotFoundException, MessageGenerationException {
        return computeGuestSignatureRequest(token, documentID, SignatureType.REVIEW);
    }

    private JSONResponseObject computeGuestSignatureRequest(final String token, final long documentID,
                                                            final SignatureType signatureType)
        throws DocumentNotFoundException, MessageGenerationException, TemplateNameNotFoundException {
        final Optional<GuestToken> guestTokenOptional = guestTokenService.findGuestTokenByToken(token);

        if (guestTokenOptional.isEmpty()) {
            final JSONResponseObject response = new JSONResponseObject();
            response.setStatus(STATUS_CODE_TOKEN_DOESNT_EXIST);
            response.setMessage("The token that has benn send with the request is not valid for this server.");
            return response;
        } else if (guestTokenOptional.get().getDocumentId() == documentID) {
            final GuestToken guestToken = guestTokenOptional.get();
            return computeSignatureRequest(guestToken.getUsername(), guestToken.getDocumentId(),
                signatureType);
        } else {
            throw new DocumentNotFoundException();
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
    @PutMapping("/user/{userID}/documents/{documentID:\\d+}/review")
    public JSONResponseObject review(final @PathVariable(USER_ID) String userID,
                                     final @PathVariable(DOCUMENT_ID) long documentID)
        throws DocumentNotFoundException, MessageGenerationException, TemplateNameNotFoundException {
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
    @PutMapping("/user/{userID}/documents/{documentID:\\d+}/signSimple")
    public JSONResponseObject signSimple(final @PathVariable(USER_ID) String userID,
                                         final @PathVariable(DOCUMENT_ID) long documentID)
        throws DocumentNotFoundException, MessageGenerationException, TemplateNameNotFoundException {
        return computeSignatureRequest(userID, documentID, SignatureType.SIMPLE_SIGNATURE);
    }

    /**
     * The sign method does a signing for a given user on a given document.
     *
     * @param userID                   the id of the user reading the document.
     * @param documentID               the document to be reviewed.
     * @param advancedSignatureRequest the request Object containing the advanced signature
     * @return true if the signing was successful and false if not.
     * @throws DocumentNotFoundException if the document was not found.
     */
    //TODO if orderRelevant test if current user is next in line.
    @PutMapping("/user/{userID}/documents/{documentID:\\d+}/signAdvanced")
    public JSONResponseObject signAdvanced(final @PathVariable(USER_ID) String userID,
                                           final @PathVariable(DOCUMENT_ID) long documentID,
                                           final @RequestBody AdvancedSignatureRequest advancedSignatureRequest)
        throws DocumentNotFoundException, MessageGenerationException, TemplateNameNotFoundException {
        final Document document = documentService.getDocument(documentID);
        final JSONResponseObject response = computeSignatureRequest(userID,
            documentID, SignatureType.ADVANCED_SIGNATURE);
        if (response.getStatus() == STATUS_CODE_OK) {
            document.advancedSignature(userID, advancedSignatureRequest.getSignature());
            documentService.addDocument(document);
        }
        return response;
    }

    private JSONResponseObject computeSignatureRequest(final String userID, final long documentID,
                                                       final SignatureType signatureType)
        throws DocumentNotFoundException, MessageGenerationException, TemplateNameNotFoundException {
        final Document document = documentService.getDocument(documentID);
        final JSONResponseObject response = new JSONResponseObject();
        if (document.getState().equals(DocumentState.CLOSED)) {
            response.setStatus(STATUS_CODE_DOCUMENT_CLOSED);
            response.setMessage("This document is closed");
            return response;
        } else {
            return signatureManagement.manageSignatureRequest(userID, document, signatureType);
        }
    }


    /*
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
            return Base64.encodeBase64(protocol.writeProtocol(userService).toByteArray());
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
            // TODO Document Title is null after updated Document
            final byte[] protocolBytes = protocol.writeProtocol(userService).toByteArray();
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
     * @param userID the id of the user currently wanting to see the progress.
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

        final List<Signatory> signatories = document.getSignatories();
        final LinkedHashSet<Signatory> signatoryLinkedHashSet = new LinkedHashSet<>(signatories);
        signatories.clear();
        signatories.addAll(signatoryLinkedHashSet);

        return new DocumentProgressResponse(document.getSignatories(), document.getReaders(), document.getEndDate());
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
            document.setOrderRelevant(documentSettingsCMD.isOrderRelevant());
            document.setEndDate(documentSettingsCMD.convertEndDate());
            final List<Signatory> signatories = new ArrayList<>();
            final List<SignatorySetting> signatorySettings = documentSettingsCMD.getSignatories();
            Signatory signatory;
            for (final SignatorySetting signatorySetting : signatorySettings) {
                signatory = new Signatory(signatorySetting.getUsername(),
                    signatorySetting.getSignatureType());
                signatory.setStatus(signatorySetting.isStatus());
                signatory.setReminder(signatorySetting.getReminderTiming());
                signatory.setSignedOn(signatorySetting.convertSignedOn());
                signatories.add(signatory);
            }
            document.setSignatories(signatories);
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
        for (final Signatory signatory : document.getSignatories()) {
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

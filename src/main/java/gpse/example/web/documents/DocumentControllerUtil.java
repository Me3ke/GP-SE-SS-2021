package gpse.example.web.documents;

import gpse.example.domain.documents.Document;
import gpse.example.domain.documents.DocumentService;
import gpse.example.domain.documents.DocumentState;
import gpse.example.domain.documents.SignatureManagement;
import gpse.example.domain.email.EmailManagement;
import gpse.example.domain.exceptions.DocumentNotFoundException;
import gpse.example.domain.exceptions.MessageGenerationException;
import gpse.example.domain.exceptions.TemplateNameNotFoundException;
import gpse.example.domain.signature.Signatory;
import gpse.example.domain.signature.SignatureType;
import gpse.example.web.JSONResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DocumentControllerUtil {


    private static final int STATUS_CODE_OK = 200;
    private static final int STATUS_CODE_DOCUMENT_CLOSED = 452;
    private static final int STATUS_CODE_TOKEN_DOESNT_EXIST = 423;
    private static final int STATUS_CODE_DOCUMENT_IS_IN_DRAFT_STATE = 424;
    private static final String DRAFT_MESSAGE = "The document is still in draft state, it cannot be signed yet.";
    private static final String THIS_DOCUMENT_IS_CLOSED = "This document is closed";
    private final EmailManagement emailManagement;
    private final DocumentService documentService;
    private final GuestTokenService guestTokenService;
    private final SignatureManagement signatureManagement;

    @Autowired
    public DocumentControllerUtil(final EmailManagement emailManagement, final DocumentService documentService,
                                  final GuestTokenService guestTokenService,
                                  final SignatureManagement signatureManagement) {
        this.emailManagement = emailManagement;
        this.documentService = documentService;
        this.guestTokenService = guestTokenService;
        this.signatureManagement = signatureManagement;
    }

    /**
     * The setDocumentState method evaluates the readers and signatories and creates an
     * initial state of the document based on that.
     *
     * @param document the document itself.
     */
    protected void setDocumentState(final Document document) {
        if (document.getSignatoryManagement().getCurrentSignatory() == null) {
            if (document.getSignatureProcessData().isDraft()) {
                document.getSignatureProcessData().setState(DocumentState.REVIEW);
            } else {
                document.getSignatureProcessData().setState(DocumentState.ARCHIVED);
            }
        } else {
            if (document.getSignatoryManagement().getCurrentSignatory().getSignatureType()
                    .equals(SignatureType.SIMPLE_SIGNATURE)
                    || document.getSignatoryManagement().getCurrentSignatory().getSignatureType()
                    .equals(SignatureType.ADVANCED_SIGNATURE)) {
                document.getSignatureProcessData().setState(DocumentState.SIGN);
            } else {
                document.getSignatureProcessData().setState(DocumentState.REVIEW);
            }
        }
    }

    protected JSONResponseObject computeGuestSignatureRequest(final String token, final long documentID,
                                                            final SignatureType signatureType, final long envelopeID)
            throws DocumentNotFoundException, MessageGenerationException, TemplateNameNotFoundException {
        final Document document = documentService.getDocument(documentID);
        final JSONResponseObject response = documentIsClosedOrInDraft(document);
        if (response.getStatus() != STATUS_CODE_OK) {
            return response;
        }
        final Optional<GuestToken> guestTokenOptional = guestTokenService.findGuestTokenByToken(token);
        if (guestTokenOptional.isEmpty()) {
            response.setStatus(STATUS_CODE_TOKEN_DOESNT_EXIST);
            response.setMessage("The token that has benn send with the request is not valid for this server.");
            return response;
        } else if (guestTokenOptional.get().getDocumentId() == documentID) {
            final GuestToken guestToken = guestTokenOptional.get();
            return computeSignatureRequest(guestToken.getUsername(), guestToken.getDocumentId(),
                    signatureType, envelopeID);
        } else {
            throw new DocumentNotFoundException();
        }
    }

    protected JSONResponseObject computeSignatureRequest(final String userID, final long documentID,
                                                       final SignatureType signatureType, final long envelopeID)
            throws DocumentNotFoundException, MessageGenerationException, TemplateNameNotFoundException {
        final Document document = documentService.getDocument(documentID);
        final JSONResponseObject response = documentIsClosedOrInDraft(document);
        if (response.getStatus() != STATUS_CODE_OK) {
            return response;
        }
        return signatureManagement.manageSignatureRequest(userID, document, signatureType, envelopeID);
    }


    protected JSONResponseObject documentIsClosedOrInDraft(final Document document) {
        final JSONResponseObject response = new JSONResponseObject();
        response.setStatus(STATUS_CODE_OK);
        if (document.getSignatureProcessData().isDraft()) {
            response.setStatus(STATUS_CODE_DOCUMENT_IS_IN_DRAFT_STATE);
            response.setMessage(DRAFT_MESSAGE);
            return response;
        }
        if (document.getSignatureProcessData().getState().equals(DocumentState.ARCHIVED)) {
            response.setStatus(STATUS_CODE_DOCUMENT_CLOSED);
            response.setMessage(THIS_DOCUMENT_IS_CLOSED);
            return response;
        }
        return response;
    }

    protected boolean isInEnvelope(final long documentID, final List<Document> documentList) {
        boolean isInEnvelope = false;
        for (final Document currentDocument : documentList) {
            if (currentDocument.getId() == documentID) {
                isInEnvelope = true;
                break;
            }
        }
        return isInEnvelope;
    }

    protected void informSignatories(final Document document, final long envelopeID)
            throws TemplateNameNotFoundException, MessageGenerationException, DocumentNotFoundException {

        if (document.getSignatureProcessData().isOrderRelevant()) {
            currentDocumentOrderRelevant(document, envelopeID);
        } else {
            currentDocumentOrderIrrelevant(document, envelopeID);
        }
    }

    private void currentDocumentOrderIrrelevant(final Document document, final long envelopeID)
            throws TemplateNameNotFoundException, MessageGenerationException, DocumentNotFoundException {
        if (document.getPreviousVersion().getSignatureProcessData().isOrderRelevant()) {
            previousDocumentOrderRelevant(document, envelopeID);
        } else {
            previousDocumentOrderIrrelevant(document, envelopeID);
        }
    }

    private void previousDocumentOrderIrrelevant(final Document document, final long envelopeID)
            throws TemplateNameNotFoundException, MessageGenerationException, DocumentNotFoundException {
        for (final Signatory signatory : document.getPreviousVersion().getSignatoryManagement()
                .getSignatories()) {
            emailManagement.sendNewVersion(document, envelopeID, signatory);
        }
        for (final Signatory signatory : document.getSignatoryManagement().getSignatories()) {
            if (!containsSignatory(document.getPreviousVersion(), signatory)) {
                emailManagement.sendInvitation(document, envelopeID, signatory);
            }
        }
    }

    private void previousDocumentOrderRelevant(final Document document, final long envelopeID)
            throws TemplateNameNotFoundException, MessageGenerationException, DocumentNotFoundException {
        for (final Signatory signatory : document.getPreviousVersion().getSignatoryManagement()
                .getSignatories()) {
            if (signatory.isStatus()) {
                emailManagement.sendNewVersion(document, envelopeID, signatory);
            } else if (containsSignatory(document, signatory)) {
                emailManagement.sendInvitation(document, envelopeID, signatory);
            }
        }
        for (final Signatory signatory : document.getSignatoryManagement().getSignatories()) {
            if (!containsSignatory(document.getPreviousVersion(), signatory)) {
                emailManagement.sendInvitation(document, envelopeID, signatory);
            }
        }
    }

    private void currentDocumentOrderRelevant(final Document document, final long envelopeID)
            throws TemplateNameNotFoundException, MessageGenerationException, DocumentNotFoundException {
        if (document.getPreviousVersion().getSignatureProcessData().isOrderRelevant()) {
            for (final Signatory signatory : document.getPreviousVersion().getSignatoryManagement()
                    .getSignatories()) {
                if (signatory.isStatus()) {
                    emailManagement.sendNewVersion(document, envelopeID, signatory);
                }
            }
            emailManagement.sendNewVersion(document, envelopeID,
                    document.getPreviousVersion().getSignatoryManagement().getCurrentSignatory());
        } else {
            for (final Signatory signatory : document.getPreviousVersion().getSignatoryManagement()
                    .getSignatories()) {
                emailManagement.sendNewVersion(document, envelopeID, signatory);
            }
        }
        emailManagement.sendInvitation(document, envelopeID, document.getSignatoryManagement()
                .getCurrentSignatory());
    }


    private boolean containsSignatory(final Document document, final Signatory signatory) {
        for (final Signatory prevSignatory : document.getSignatoryManagement().getSignatories()) {
            if (prevSignatory.getEmail().equals(signatory.getEmail())) {
                return true;
            }
        }
        return false;
    }
}

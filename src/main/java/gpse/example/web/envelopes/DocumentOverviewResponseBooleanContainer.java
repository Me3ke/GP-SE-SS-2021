package gpse.example.web.envelopes;

import gpse.example.domain.documents.Document;
import gpse.example.domain.documents.OrderManager;
import gpse.example.domain.documents.SignatoryManagement;
import gpse.example.domain.signature.Signatory;
import gpse.example.domain.signature.SignatureType;

import java.util.List;

/**
 * The class containing the booleans for the DocumentOverViewResponse.
 */
public class DocumentOverviewResponseBooleanContainer {

    private boolean signatory;
    private boolean reader;
    private boolean signed;
    private boolean read;
    private boolean turnToReview;
    private boolean turnToSign;
    private boolean showHistory;
    private boolean draft;

    protected DocumentOverviewResponseBooleanContainer(final Document document, final String currentUser) {
        this.signatory = false;
        this.read = false;
        this.signed = false;
        this.showHistory = document.getSignatureProcessData().isShowHistory();
        this.draft = document.getSignatureProcessData().isDraft();
        final SignatoryManagement signatoryManagement = document.getSignatoryManagement();
        final List<Signatory> readers = signatoryManagement.getReaders();
        final List<Signatory> signatories = signatoryManagement.getSignatories();
        final SignatureType signatureType = checkSignatories(currentUser, signatories);
        checkReaders(currentUser, readers);
        final OrderManager orderManager = new OrderManager();
        turnToReview = orderManager.manageSignatoryTurn(currentUser, document, SignatureType.REVIEW);
        turnToSign = orderManager.manageSignatoryTurn(currentUser, document, signatureType);
    }

    private void checkReaders(final String currentUser, final List<Signatory> readers) {
        for (final Signatory currentReader : readers) {
            if (currentReader.getEmail().equals(currentUser)) {
                this.reader = true;
                if (currentReader.isStatus()) {
                    this.read = true;
                    break;
                }
            }
        }
    }

    private SignatureType checkSignatories(final String currentUser, final List<Signatory> signatories) {
        SignatureType signatureType = SignatureType.NO_SIGNATURE;
        for (final Signatory currentSignatory : signatories) {
            if (currentSignatory.getEmail().equals(currentUser)
                    && (currentSignatory.getSignatureType().equals(SignatureType.SIMPLE_SIGNATURE)
                    || currentSignatory.getSignatureType().equals(SignatureType.ADVANCED_SIGNATURE))) {
                this.signatory = true;
                signatureType = currentSignatory.getSignatureType();
                if (currentSignatory.isStatus()) {
                    this.signed = true;
                    break;
                }
            }
        }
        return signatureType;
    }

    public boolean isSignatory() {
        return signatory;
    }

    public void setSignatory(final boolean signatory) {
        this.signatory = signatory;
    }

    public boolean isReader() {
        return reader;
    }

    public void setReader(final boolean reader) {
        this.reader = reader;
    }

    public boolean isSigned() {
        return signed;
    }

    public void setSigned(final boolean signed) {
        this.signed = signed;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(final boolean read) {
        this.read = read;
    }

    public boolean isTurnToReview() {
        return turnToReview;
    }

    public void setTurnToReview(final boolean turnToReview) {
        this.turnToReview = turnToReview;
    }

    public boolean isTurnToSign() {
        return turnToSign;
    }

    public void setTurnToSign(final boolean turnToSign) {
        this.turnToSign = turnToSign;
    }

    public boolean isShowHistory() {
        return showHistory;
    }

    public void setShowHistory(final boolean showHistory) {
        this.showHistory = showHistory;
    }

    public boolean isDraft() {
        return draft;
    }

    public void setDraft(final boolean draft) {
        this.draft = draft;
    }
}

package gpse.example.web.envelopes;

import gpse.example.domain.documents.Document;
import gpse.example.domain.documents.DocumentState;
import gpse.example.domain.documents.OrderManager;
import gpse.example.domain.documents.SignatoryManagement;
import gpse.example.domain.signature.Signatory;
import gpse.example.domain.signature.SignatureType;
import gpse.example.domain.users.User;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * The class used to send only the necessary information regarding documents on the overviewpage.
 */
public class DocumentOverviewResponse {

    private final String title;
    private final DocumentState state;
    private final User owner;
    private final String creationDate;
    private final String endDate;
    private final String dataType;
    private final String identifier;
    private boolean signatory;
    private boolean reader;
    private boolean signed;
    private boolean read;
    private boolean turnToReview;
    private boolean turnToSign;
    private boolean showHistory;
    private boolean draft;
    private final long id;

    /**
     * The default constructor creates the documentGet based on an existing document
     * which is created from the Database beforehand.
     *
     * @param document    the corresponding document for the request.
     * @param owner       the owner of the document.
     * @param currentUser the user doing the request
     */
    public DocumentOverviewResponse(final Document document, final User owner, final String currentUser) {
        this.title = document.getDocumentTitle();
        this.owner = owner;
        //Replaced with uploadDate
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        this.creationDate = document.getDocumentMetaData().getMetaTimeStampUpload().format(formatter);
        if (document.getEndDate() != null) {
            this.endDate = document.getEndDate().format(formatter);
        } else {
            this.endDate = "";
        }
        this.dataType = document.getDocumentType();
        this.state = document.getState();
        this.identifier = document.getDocumentMetaData().getIdentifier();
        this.showHistory = document.isShowHistory();
        this.draft = document.isDraft();
        this.signatory = false;
        this.read = false;
        this.signed = false;
        this.id = document.getId();
        final SignatoryManagement signatoryManagement = document.getSignatoryManagement();
        final List<Signatory> signatories = signatoryManagement.getSignatories();
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
        final List<Signatory> readers = signatoryManagement.getReaders();
        for (final Signatory currentReader : readers) {
            if (currentReader.getEmail().equals(currentUser)) {
                this.reader = true;
                if (currentReader.isStatus()) {
                    this.read = true;
                    break;
                }
            }
        }
        final OrderManager orderManager = new OrderManager();
        turnToReview = orderManager.manageSignatoryTurn(currentUser, document, SignatureType.REVIEW);
        turnToSign = orderManager.manageSignatoryTurn(currentUser, document, signatureType);
    }

    public String getTitle() {
        return title;
    }

    public DocumentState getState() {
        return state;
    }

    public User getOwner() {
        return owner;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getDataType() {
        return dataType;
    }

    public String getIdentifier() {
        return identifier;
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

    public void setDraft(boolean draft) {
        this.draft = draft;
    }

    public long getId() {
        return id;
    }
}

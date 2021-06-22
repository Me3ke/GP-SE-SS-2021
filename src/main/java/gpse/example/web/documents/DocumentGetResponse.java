package gpse.example.web.documents;

import gpse.example.domain.documents.Document;
import gpse.example.domain.documents.DocumentMetaData;
import gpse.example.domain.documents.DocumentState;
import gpse.example.domain.documents.OrderManager;
import gpse.example.domain.signature.Signatory;
import gpse.example.domain.signature.SignatureType;
import gpse.example.domain.users.User;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * A class which represents a Response for a Document Get request.
 */
public class DocumentGetResponse {

    private final String title;
    private final DocumentState state;
    private final User owner;
    private final String creationDate;
    private final String endDate;
    private final SignatureType signatureType;
    private final String dataType;
    private final String identifier;
    private boolean signatory;
    private boolean reader;
    private boolean signed;
    private boolean read;
    private final byte[] data;
    private boolean turnToReview;
    private boolean turnToSign;
    private final long id;

   /* public List<Signatory> getSignatories() {
        return signatories;
    }

    private final List<Signatory> signatories;
*/
    /**
     * The default constructor creates the documentGet based on an existing document
     * which is created from the Database beforehand.
     *
     * @param document    the corresponding document for the request.
     * @param owner       the owner of the document.
     * @param currentUser the user doing the request
     */
    public DocumentGetResponse(final Document document, final User owner, final User currentUser) {
        this.title = document.getDocumentTitle();
        this.owner = owner;
        final DocumentMetaData metaData = document.getDocumentMetaData();
        //Replaced with uploadDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        this.creationDate = metaData.getMetaTimeStampUpload().format(formatter);
        this.endDate = document.getEndDate().format(formatter);
        this.dataType = document.getDocumentType();
        this.data = document.getData();
        this.state = document.getState();
        this.identifier = document.getDocumentMetaData().getIdentifier();
        this.signatory = false;
        this.read = false;
        this.signed = false;
        this.id = document.getId();
        final List<Signatory> signatories = document.getSignatories();
        //this.signatories = document.getSignatories();
        SignatureType signatureType = SignatureType.NO_SIGNATURE;
        for (final Signatory currentSignatory : signatories) {
            if (currentSignatory.getUser().equals(currentUser)
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
        this.signatureType = signatureType;
        final List<Signatory> readers = document.getReaders();
        for (final Signatory currentReader : readers) {
            if (currentReader.getUser().equals(currentUser)) {
                this.reader = true;
                if (currentReader.isStatus()) {
                    this.read = true;
                    break;
                }
            }
        }
        OrderManager orderManager = new OrderManager();
        turnToReview = orderManager.manageSignatoryTurn(currentUser, document, SignatureType.REVIEW);
        turnToSign = orderManager.manageSignatoryTurn(currentUser, document, this.signatureType);
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

    public SignatureType getSignatureType() {
        return signatureType;
    }

    public String getDataType() {
        return dataType;
    }

    public boolean isSigned() {
        return signed;
    }

    public boolean isRead() {
        return read;
    }

    public byte[] getData() {
        return Arrays.copyOf(data, data.length);
    }

    public boolean isSignatory() {
        return signatory;
    }

    public boolean isReader() {
        return reader;
    }

    public boolean isTurnToReview() {
        return turnToReview;
    }

    public void setTurnToReview(boolean turnToReview) {
        this.turnToReview = turnToReview;
    }

    public boolean isTurnToSign() {
        return turnToSign;
    }

    public void setTurnToSign(boolean turnToSign) {
        this.turnToSign = turnToSign;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setSignatory(boolean signatory) {
        this.signatory = signatory;
    }

    public void setReader(boolean reader) {
        this.reader = reader;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public long getId() {
        return id;
    }
}

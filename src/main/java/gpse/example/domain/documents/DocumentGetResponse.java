package gpse.example.domain.documents;

import gpse.example.domain.signature.Signatory;
import gpse.example.domain.signature.SignatureType;
import gpse.example.domain.users.User;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * A class which represents a Response for a Document Get request.
 */
public class DocumentGetResponse {

    private String title;
    private DocumentState state;
    private User owner;
    private LocalDateTime creationDate;
    private LocalDateTime endDate;
    private SignatureType signatureType;
    private String dataType;
    private boolean signed;
    private boolean read;
    private byte[] data;

    /**
     * The default constructor creates the documentGet based on an existing document
     * which is created from the Database beforehand.
     * @param document the corresponding document for the request.
     * @param owner the user, who created the request.
     */
    public DocumentGetResponse(final Document document, final User owner) {
        this.title = document.getDocumentTitle();
        this.owner = owner;
        final DocumentMetaData metaData = document.getDocumentMetaData();
        this.creationDate = metaData.getCreationDate();
        this.endDate = document.getEndDate();
        this.signatureType = document.getSignatureType();
        this.dataType = document.getDocumentType();
        final List<Signatory> signatories = document.getSignatories();
        this.signed = true;
        for (final Signatory signatory : signatories) {
            if (!signatory.isStatus()) {
                this.signed = false;
            }
        }
        final List<Signatory> readers = document.getReaders();
        this.read = true;
        for (final Signatory reader : readers) {
            if (!reader.isStatus()) {
                this.read = false;
            }
        }
        this.data = document.getData();
        this.state = document.getState();
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getEndDate() {
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

}

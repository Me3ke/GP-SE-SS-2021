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
    private boolean signatory;
    private boolean reader;
    private boolean signed;
    private boolean read;
    private byte[] data;

    /**
     * The default constructor creates the documentGet based on an existing document
     * which is created from the Database beforehand.
     * @param document the corresponding document for the request.
     * @param owner the owner of the document.
     * @param currentUser the user doing the request
     */
    public DocumentGetResponse(final Document document, final User owner, final User currentUser) {
        this.title = document.getDocumentTitle();
        this.owner = owner;
        final DocumentMetaData metaData = document.getDocumentMetaData();
        //Replaced with uploadDate
        this.creationDate = metaData.getMetaTimeStampUpload();
        this.endDate = document.getEndDate();
        this.signatureType = document.getSignatureType();
        this.dataType = document.getDocumentType();
        this.data = document.getData();
        this.state = document.getState();
        this.signatory = false;
        this.read = false;
        this.signed = false;
        final List<Signatory> signatories = document.getSignatories();
        for (final Signatory currentSignatory : signatories) {
            if (currentSignatory.getUser().equals(currentUser)) {
                this.signatory = true;
                if (currentSignatory.isStatus()) {
                    this.signed = true;
                }
            }
        }
        final List<Signatory> readers = document.getReaders();
        for (final Signatory currentReader : readers) {
            if (currentReader.getUser().equals(currentUser)) {
                this.reader = true;
                if (currentReader.isStatus()) {
                    this.read = true;
                }
            }
        }
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

    public boolean isSignatory() {
        return signatory;
    }

    public boolean isReader() {
        return reader;
    }
}

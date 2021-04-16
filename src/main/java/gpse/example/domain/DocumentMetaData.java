package gpse.example.domain;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * The model representing the meta data of a document.
 *
 * @author Jan Kronsbein & Alexander Heide
 * @since 04-13-2021
 */
public class DocumentMetaData {
    private final String metaUserID;
    private final Timestamp metaTimeStampUpload;
    private final String metaDocumentTitle;
    private String identifier;
    private String creationDate;
    private String lastModified;
    private String lastAccess;
    private long size;

    /**
     * The constructor responsible for creating an identifier out of existing meta data.
     *
     * @param metaUserID          the String containing the user id
     * @param metaTimeStampUpload the Timestamp created during the upload
     * @param metaDocumentTitle   the document file name
     */
    public DocumentMetaData(final String metaUserID, final Timestamp metaTimeStampUpload,
                            final String metaDocumentTitle) {
        this.metaUserID = metaUserID;
        this.metaTimeStampUpload = metaTimeStampUpload;
        this.metaDocumentTitle = metaDocumentTitle;
        generateHashString();
    }

    /**
     * The constructor responsible for instancing meta data with an existing identifier.
     *
     * @param metaUserID          the String containing the user id
     * @param metaTimeStampUpload the Timestamp created during the upload
     * @param metaDocumentTitle   the document file name
     * @param creationDate        the date of creation of the document
     * @param lastModified        the date of last modification on the document
     * @param lastAccess          the date of last access on the document
     * @param size                the size of the document
     */
    public DocumentMetaData(final String metaUserID, final Timestamp metaTimeStampUpload,
                            final String metaDocumentTitle, final String creationDate, final String lastModified,
                            final String lastAccess, final long size) {
        this.metaUserID = metaUserID;
        this.metaTimeStampUpload = metaTimeStampUpload;
        this.metaDocumentTitle = metaDocumentTitle;
        this.creationDate = creationDate;
        this.lastModified = lastModified;
        this.lastAccess = lastAccess;
        this.size = size;
        generateHashString();
    }

    private void generateHashString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(metaUserID).append(metaTimeStampUpload.toString()).append(metaDocumentTitle);
        HashSHA hashSHA = new HashSHA();
        this.identifier = hashSHA.computeHash(stringBuilder.toString());
    }

    /**
     * Method responsible for comparing two documentMetaData objects.
     * @param object any other object
     * @return boolean, true if same, false otherwise
     */
    public boolean equalsTo(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        DocumentMetaData that = (DocumentMetaData) object;
        return Objects.equals(metaUserID, that.metaUserID)
                && Objects.equals(metaTimeStampUpload, that.metaTimeStampUpload)
                && Objects.equals(metaDocumentTitle, that.metaDocumentTitle)
                && Objects.equals(identifier, that.identifier);
    }

    public String getMetaUserID() {
        return metaUserID;
    }

    public Timestamp getMetaTimeStampUpload() {
        return metaTimeStampUpload;
    }

    public String getMetaDocumentTitle() {
        return metaDocumentTitle;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getLastModified() {
        return lastModified;
    }

    public String getLastAccess() {
        return lastAccess;
    }

    public long getSize() {
        return size;
    }
}

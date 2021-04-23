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

    /**
     * The constructor responsible for creating an identifier out of existing meta data.
     *
     * @param metaUserID          the String containing the user id
     * @param metaTimeStampUpload the Timestamp created during the upload
     * @param metaDocumentTitle   the document file name
     */
    public DocumentMetaData(String metaUserID, Timestamp metaTimeStampUpload, String metaDocumentTitle) {
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
     * @param identifier          the already existing identifier
     */
    public DocumentMetaData(String metaUserID, Timestamp metaTimeStampUpload,
                            String metaDocumentTitle, String identifier) {
        this.metaUserID = metaUserID;
        this.metaTimeStampUpload = metaTimeStampUpload;
        this.metaDocumentTitle = metaDocumentTitle;
        this.identifier = identifier;
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
    public boolean equalsTo(Object object) {
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

    public String getIdentifier() {
        return identifier;
    }
}

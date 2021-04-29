package gpse.example.domain;

import java.nio.file.attribute.FileTime;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * The model representing the meta data of a document.
 *
 * @author Jan Kronsbein & Alexander Heide
 * @since 04-13-2021
 */
public class DocumentMetaData {
    //TODO change to localDateTime
    private final Timestamp metaTimeStampUpload;
    private final String metaDocumentTitle;
    private String identifier;
    private LocalDateTime creationDate;
    private LocalDateTime lastModified;
    private LocalDateTime lastAccess;
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
        this.metaTimeStampUpload = metaTimeStampUpload;
        this.metaDocumentTitle = metaDocumentTitle;
    }

    /**
     * The constructor responsible for instancing meta data with an existing identifier.
     *
     * @param metaTimeStampUpload the Timestamp created during the upload
     * @param metaDocumentTitle   the document file name
     * @param creationDate        the date of creation of the document
     * @param lastModified        the date of last modification on the document
     * @param lastAccess          the date of last access on the document
     * @param size                the size of the document
     */
    public DocumentMetaData(final Timestamp metaTimeStampUpload, final String metaDocumentTitle,
                            final FileTime creationDate, final FileTime lastModified,
                            final FileTime lastAccess, final long size) {
        this.metaTimeStampUpload = metaTimeStampUpload;
        this.metaDocumentTitle = metaDocumentTitle;
        this.creationDate = formatDateTime(creationDate);
        this.lastModified = formatDateTime(lastModified);
        this.lastAccess = formatDateTime(lastAccess);
        this.size = size;
        final HashSHA hashSHA = new HashSHA();
        this.identifier = hashSHA.computeHash( metaTimeStampUpload.toString() + metaDocumentTitle);
    }

    /**
     * The formatDateTime methods converts the file times to a more readable format.
     *
     * @param fileTime the file time.
     * @return returns a String of the time in a new format.
     */
    private LocalDateTime formatDateTime(final FileTime fileTime) {
        final LocalDateTime localDateTime = fileTime
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
        return localDateTime;
    }

    /**
     * Method responsible for comparing two documentMetaData objects.
     *
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
        final DocumentMetaData that = (DocumentMetaData) object;
        return Objects.equals(metaTimeStampUpload, that.metaTimeStampUpload)
            && Objects.equals(metaDocumentTitle, that.metaDocumentTitle)
            && Objects.equals(identifier, that.identifier);
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public LocalDateTime getLastAccess() {
        return lastAccess;
    }

    public long getSize() {
        return size;
    }

}

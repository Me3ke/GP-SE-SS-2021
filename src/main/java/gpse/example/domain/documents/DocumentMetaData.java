package gpse.example.domain.documents;

import gpse.example.util.HashSHA;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The model representing the meta data of a document.
 *
 * @author Jan Kronsbein & Alexander Heide
 * @since 04-13-2021
 */
@Entity
public class DocumentMetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private LocalDateTime metaTimeStampUpload;

    @Column
    private String metaDocumentTitle;

    @Column
    private String metaUserID;

    @Column
    private String identifier;

    /*
    @Column
    private LocalDateTime creationDate;

    @Column
    private LocalDateTime lastAccess;

     */

    @Column
    private LocalDateTime lastModified;

    @Column
    private long size;

    /**
     * The constructor responsible for instancing meta data with an existing identifier.
     *
     * @param metaTimeStampUpload the Timestamp created during the upload
     * @param metaDocumentTitle   the document file name
     * @param lastModified        the date of last modification on the document
     * @param size                the size of the document
     * @param metaUserID          an ID referring to the owner of the envelope this document is a part of.
     */
    public DocumentMetaData(final LocalDateTime metaTimeStampUpload, final String metaDocumentTitle,
                            final LocalDateTime lastModified, final long size, final String metaUserID) {
        this.metaTimeStampUpload = metaTimeStampUpload;
        this.metaDocumentTitle = metaDocumentTitle;
        //this.creationDate = formatDateTime(creationDate);
        this.lastModified = lastModified;
        //this.lastAccess = formatDateTime(lastAccess);
        this.size = size;
        this.metaUserID = metaUserID;
        generateHashString();
    }

    protected DocumentMetaData() {

    }


    /**
     * Constructor for meta data that is important for archived documents.
     * @param documentMetaData the old meta data.
     */
    public DocumentMetaData(DocumentMetaData documentMetaData) {
        this.metaTimeStampUpload = documentMetaData.getMetaTimeStampUpload();
        this.metaDocumentTitle = documentMetaData.getMetaDocumentTitle();
        //this.creationDate = formatDateTime(creationDate);
        this.lastModified = documentMetaData.getLastModified();
        //this.lastAccess = formatDateTime(lastAccess);
        this.size = documentMetaData.getSize();
        this.metaUserID = documentMetaData.getMetaUserID();
        this.identifier = documentMetaData.getIdentifier();
    }
/*
    /**
     * The formatDateTime methods converts the file times to a more readable format.
     *
     * @param fileTime the file time.
     * @return returns a String of the time in a new format.
     */
    /*
    private LocalDateTime formatDateTime(final FileTime fileTime) {
        final LocalDateTime localDateTime = fileTime
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
        return localDateTime;
    }
    */

    private void generateHashString() {
        final HashSHA hashSHA = new HashSHA();
        this.identifier = hashSHA.computeHash(metaUserID + metaTimeStampUpload.toString() + metaDocumentTitle);
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

    public LocalDateTime getMetaTimeStampUpload() {
        return metaTimeStampUpload;
    }

    public String getMetaDocumentTitle() {
        return metaDocumentTitle;
    }

    public String getIdentifier() {
        return identifier;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public long getSize() {
        return size;
    }

    public String getMetaUserID() {
        return metaUserID;
    }

    public long getId() {
        return id;
    }
}

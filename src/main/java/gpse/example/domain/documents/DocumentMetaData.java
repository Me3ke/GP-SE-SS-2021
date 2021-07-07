package gpse.example.domain.documents;

import gpse.example.util.HashSHA;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column
    private long size;

    /**
     * The constructor responsible for instancing meta data with an existing identifier.
     *
     * @param metaTimeStampUpload the Timestamp created during the upload
     * @param metaDocumentTitle   the document file name
     * @param size                the size of the document
     * @param metaUserID          an ID referring to the owner of the envelope this document is a part of.
     */
    public DocumentMetaData(final LocalDateTime metaTimeStampUpload, final String metaDocumentTitle,
                            final long size, final String metaUserID) {
        this.metaTimeStampUpload = metaTimeStampUpload;
        this.metaDocumentTitle = metaDocumentTitle;
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
    public DocumentMetaData(final DocumentMetaData documentMetaData) {
        this.metaTimeStampUpload = documentMetaData.getMetaTimeStampUpload();
        this.metaDocumentTitle = documentMetaData.getMetaDocumentTitle();
        this.size = documentMetaData.getSize();
        this.metaUserID = documentMetaData.getMetaUserID();
        this.identifier = documentMetaData.getIdentifier();
    }

    private void generateHashString() {
        final HashSHA hashSHA = new HashSHA();
        this.identifier = hashSHA.computeHash(metaUserID + metaTimeStampUpload.toString() + metaDocumentTitle);
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

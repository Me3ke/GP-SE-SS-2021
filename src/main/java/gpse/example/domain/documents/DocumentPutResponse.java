package gpse.example.domain.documents;


/**
 * The response for a document put response for uploading new versions.
 */
public class DocumentPutResponse {

    private final long archivedDocumentID;
    private final long newDocumentID;

    /**
     * The default constructor for a document put response.
     * @param archivedDocumentID the id of the archived document.
     * @param newDocumentID the id of the document created from the request.
     */

    public DocumentPutResponse(final long archivedDocumentID, final long newDocumentID) {
        this.archivedDocumentID = archivedDocumentID;
        this.newDocumentID = newDocumentID;

    }

    public long getArchivedDocumentID() {
        return archivedDocumentID;
    }

    public long getNewDocumentID() {
        return newDocumentID;
    }
}

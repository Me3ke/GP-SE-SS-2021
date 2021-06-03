package gpse.example.domain.documents;


/**
 * .
 */
public class DocumentPutResponse {

    private final long archivedDocumentID;
    private final long newDocumentID;

    /**
     *
     * @param archivedDocumentID
     * @param newDocumentID
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

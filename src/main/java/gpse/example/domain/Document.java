package gpse.example.domain;

/**
 * The model for the document responsible for initialising the necessary details about the document file.
 */
public class Document {
    /**
     * The documentMetaData containing the identifier as well as other information.
     */
    private DocumentMetaData documentMetaData;

    public Document() {
    }

    public Document(DocumentMetaData documentMetaData) {
        this.documentMetaData = documentMetaData;
    }

    DocumentMetaData getDocumentMetaData(){
        return documentMetaData;
    }
}

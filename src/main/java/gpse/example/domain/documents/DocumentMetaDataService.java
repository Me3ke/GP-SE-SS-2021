package gpse.example.domain.documents;

/**
 * The interface for DocumentMetaDataServices.
 */
public interface DocumentMetaDataService {
    DocumentMetaData saveDocumentMetaData(DocumentMetaData documentMetaData);
    void removeAll();
    void delete(DocumentMetaData documentMetaData);
}

package gpse.example.domain.documents;

import gpse.example.domain.exceptions.DocumentNotFoundException;

import java.util.List;

/**
 * the interface for Document Services.
 */
public interface DocumentService {
    Document addDocument(Document document);
    Document getDocument(long id) throws DocumentNotFoundException;
    Document loadDocumentByName(String name);
    List<Document> getDocuments();
    void remove(Document document);
}

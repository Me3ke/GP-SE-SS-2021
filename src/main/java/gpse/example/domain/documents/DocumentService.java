package gpse.example.domain.documents;

import gpse.example.domain.exceptions.DocumentNotFoundException;

import java.io.IOException;
import java.util.List;

/**
 * the interface for Document Services.
 */
public interface DocumentService {
    Document store(DocumentPut documentPut, String ownerID) throws IOException;
    Document getDocument(long id) throws DocumentNotFoundException;
    List<Document> getDocuments();
}

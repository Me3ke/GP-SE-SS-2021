package gpse.example.domain.documents;

import java.io.IOException;
import java.util.List;

/**
 * the interface for Document Services.
 */
public interface DocumentService {
    Document store(DocumentCmd documentCmd, String ownerID) throws IOException;
    Document getDocument(long id);
    List<Document> getDocuments();
}

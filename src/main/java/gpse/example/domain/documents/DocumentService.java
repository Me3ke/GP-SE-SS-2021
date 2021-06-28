package gpse.example.domain.documents;

import gpse.example.domain.exceptions.CreatingFileException;
import gpse.example.domain.exceptions.DocumentNotFoundException;
import gpse.example.domain.users.UserServiceImpl;
import gpse.example.web.documents.DocumentPutRequest;

import java.io.IOException;
import java.util.List;

/**
 * the interface for Document Services.
 */
public interface DocumentService {
    Document addDocument(Document document);
    Document getDocument(long id) throws DocumentNotFoundException;
    List<Document> getDocuments();
    void remove(Document document);
    Document creation(DocumentPutRequest documentPutRequest, String ownerID,
                      UserServiceImpl userService)
        throws CreatingFileException, IOException;
}

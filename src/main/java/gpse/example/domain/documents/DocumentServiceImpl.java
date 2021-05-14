package gpse.example.domain.documents;

import gpse.example.domain.exceptions.DocumentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * the class that implements the DocumentService interface for communication with the database.
 */
@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository repo;

    @Autowired
    public DocumentServiceImpl(final DocumentRepository repo) {
        this.repo = repo;
    }

    //TODO
    @Override
    public Document store(final DocumentPutRequest documentPutRequest, final String ownerID) {
        return null;
    }

    @Override
    public Document getDocument(final long id) throws DocumentNotFoundException {
        return repo.findById(id).orElseThrow(DocumentNotFoundException::new);
    }

    @Override
    public List<Document> getDocuments() {
        final List<Document> documents = new ArrayList<>();
        repo.findAll().forEach(documents :: add);
        return documents;
    }
}

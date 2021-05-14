package gpse.example.domain.documents;

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

    @Override
    public Document store(final DocumentPut documentPut, final String ownerID) {
        /*
        final DocumentCreator documentCreator = new DocumentCreator();
        final Document document = documentCreator.createDocument(documentCmd, ownerID);
        return repo.save(document);
         */
        return null;
    }

    @Override
    public Document getDocument(final long id) {
        return repo.findById(id).get();
    }

    @Override
    public List<Document> getDocuments() {
        final List<Document> documents = new ArrayList<>();
        repo.findAll().forEach(documents :: add);
        return documents;
    }
}

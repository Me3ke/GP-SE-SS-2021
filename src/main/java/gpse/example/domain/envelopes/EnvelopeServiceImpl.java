package gpse.example.domain.envelopes;

import gpse.example.domain.documents.Document;
import gpse.example.domain.documents.DocumentPut;
import gpse.example.domain.documents.DocumentCreator;
import gpse.example.domain.exceptions.CreatingFileException;
import gpse.example.domain.exceptions.DocumentNotFoundException;
import gpse.example.domain.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * the class that implements the EnvelopeService interface for communication with the database.
 */
@Service
public class EnvelopeServiceImpl implements EnvelopeService {

    private final EnvelopeRepository repo;

    @Autowired
    public EnvelopeServiceImpl(final EnvelopeRepository repo) {
        this.repo = repo;
    }

    @Override
    public Envelope addEnvelope(final String name, final User owner) throws IOException {
        final Envelope envelope = owner.createNewEnvelope(name);
        return repo.save(envelope);
    }

    @Override
    public Optional<Envelope> getEnvelope(final long id) {
        return repo.findById(id);
    }

    @Override
    public Envelope updateEnvelope(final long id, final DocumentPut documentPut, final String ownerID,
                                   final List<User> signatories, final List<User> readers)
                                    throws CreatingFileException, DocumentNotFoundException, IOException {
        final DocumentCreator documentCreator = new DocumentCreator();
        final Envelope envelope = getEnvelope(id)
            .orElseThrow(DocumentNotFoundException::new);
        final Document document = documentCreator.createDocument(documentPut, ownerID,
                                                                    signatories, readers);
        envelope.addDocument(document);
        return repo.save(envelope);
    }

    @Override
    public List<Envelope> getEnvelopes() {
        final List<Envelope> envelopes = new ArrayList<>();
        repo.findAll().forEach(envelopes::add);
        return envelopes;
    }
}

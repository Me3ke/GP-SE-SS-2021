package gpse.example.domain.envelopes;

import gpse.example.domain.documents.Document;
import gpse.example.domain.documents.DocumentPutRequest;
import gpse.example.domain.documents.DocumentCreator;
import gpse.example.domain.documents.DocumentRepository;
import gpse.example.domain.exceptions.CreatingFileException;
import gpse.example.domain.exceptions.DocumentNotFoundException;
import gpse.example.domain.signature.Signatory;
import gpse.example.domain.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * the class that implements the EnvelopeService interface for communication with the database.
 */
@Service
public class EnvelopeServiceImpl implements EnvelopeService {

    private final EnvelopeRepository envelopeRepository;
    private final DocumentRepository documentRepository;

    @Autowired
    public EnvelopeServiceImpl(final EnvelopeRepository envelopeRepository,
                               final DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
        this.envelopeRepository = envelopeRepository;
    }

    @Override
    public Envelope addEnvelope(final String name, final User owner) throws IOException {
        final Envelope envelope = owner.createNewEnvelope(name);
        return envelopeRepository.save(envelope);
    }

    @Override
    public Envelope getEnvelope(final long id) throws DocumentNotFoundException {
        return envelopeRepository.findById(id).orElseThrow(DocumentNotFoundException::new);
    }

    @Override
    public Envelope updateEnvelope(final long id, final DocumentPutRequest documentPutRequest, final String ownerID,
                                   final List<User> signatories, final List<User> readers)
        throws CreatingFileException, DocumentNotFoundException, IOException {
        final Envelope envelope = getEnvelope(id);
        final DocumentCreator documentCreator = new DocumentCreator();
        final Document document = documentCreator.createDocument(documentPutRequest, ownerID,
                                                                    signatories, readers);
        envelope.addDocument(document);
        for (final Document currentDocument : envelope.getDocumentList()) {
            for (final Signatory signatory : currentDocument.getSignatories()) {
                signatory.setStatus(false);
            }
            documentRepository.save(document);
        }
        return envelopeRepository.save(envelope);
    }

    @Override
    public List<Envelope> getEnvelopes() {
        final List<Envelope> envelopes = new ArrayList<>();
        envelopeRepository.findAll().forEach(envelopes::add);
        return envelopes;
    }

    @Override
    public void remove(final Envelope envelope) {
        envelopeRepository.delete(envelope);
    }

    @Override
    public Envelope saveEnvelope(final Envelope envelope) {
        return envelopeRepository.save(envelope);
    }
}

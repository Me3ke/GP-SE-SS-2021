package gpse.example.domain.envelopes;

import gpse.example.domain.documents.Document;
import gpse.example.domain.exceptions.CreatingFileException;
import gpse.example.domain.exceptions.DocumentNotFoundException;
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

    @Autowired
    public EnvelopeServiceImpl(final EnvelopeRepository envelopeRepository) {
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
    public Envelope updateEnvelope(final Envelope envelope, final Document document)
        throws CreatingFileException, DocumentNotFoundException, IOException {
        envelope.addDocument(document);
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

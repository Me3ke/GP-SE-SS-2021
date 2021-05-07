package gpse.example.domain.envelopes;

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

    private final EnvelopeRepository repo;

    @Autowired
    public EnvelopeServiceImpl(final EnvelopeRepository repo) {
        this.repo = repo;
    }

    @Override
    public Envelope store(final String name, final User owner) throws IOException {
       final Envelope envelope = owner.createNewEnvelope(name);
       return repo.save(envelope);
    }

    @Override
    public Envelope getEnvelope(final long id) {
        return repo.findById(id).get();
    }

    @Override
    public List<Envelope> getEnvelopes() {
        final List<Envelope> envelopes = new ArrayList<>();
        repo.findAll().forEach(envelopes::add);
        return envelopes;
    }
}

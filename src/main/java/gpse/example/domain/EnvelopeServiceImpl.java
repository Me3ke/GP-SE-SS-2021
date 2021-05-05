package gpse.example.domain;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * the class that implements the EnvelopeService interface for communication with the database.
 */
public class EnvelopeServiceImpl implements EnvelopeService {

    private EnvelopeRepository repo;

    @Autowired
    public EnvelopeServiceImpl(final EnvelopeRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Envelope> getEnvelopes() {
        final List<Envelope> envelopes = new ArrayList<>();
        repo.findAll().forEach(envelopes::add);
        return envelopes;
    }
}

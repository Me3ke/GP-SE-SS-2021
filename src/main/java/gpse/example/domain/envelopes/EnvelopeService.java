package gpse.example.domain.envelopes;

import gpse.example.domain.users.User;

import java.io.IOException;
import java.util.List;

/**
 * the interface for EnvelopeServices.
 */
public interface EnvelopeService {
    Envelope store(String name, User owner) throws IOException;
    Envelope getEnvelope(long id);
    List<Envelope> getEnvelopes();
}

package gpse.example.domain.envelopes;

import gpse.example.domain.documents.DocumentPut;
import gpse.example.domain.exceptions.CreatingFileException;
import gpse.example.domain.exceptions.DocumentNotFoundException;
import gpse.example.domain.users.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * the interface for EnvelopeServices.
 */
@Service
public interface EnvelopeService {
    Optional<Envelope> getEnvelope(long id);

    List<Envelope> getEnvelopes();

    Envelope addEnvelope(String name, User owner) throws IOException;

    Envelope updateEnvelope(final long id, final DocumentPut documentPut, final String ownerID,
                            final List<User> signatories, final List<User> readers)
        throws CreatingFileException, DocumentNotFoundException, IOException;
}


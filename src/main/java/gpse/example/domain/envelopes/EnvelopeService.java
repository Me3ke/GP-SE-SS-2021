package gpse.example.domain.envelopes;

import gpse.example.domain.documents.DocumentCmd;
import gpse.example.domain.exceptions.CreatingFileException;
import gpse.example.domain.exceptions.DocumentNotFoundException;
import gpse.example.domain.users.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * the interface for EnvelopeServices.
 */
public interface EnvelopeService {
    Optional<Envelope> getEnvelope(long id);

    List<Envelope> getEnvelopes();

    Envelope addEnvelope(String name, User owner) throws IOException;

    Envelope updateEnvelope(final long id, final DocumentCmd documentCmd, final String ownerID)
        throws CreatingFileException, DocumentNotFoundException, IOException;
}


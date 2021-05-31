package gpse.example.domain.envelopes;

import gpse.example.domain.documents.Document;
import gpse.example.domain.exceptions.CreatingFileException;
import gpse.example.domain.exceptions.DocumentNotFoundException;
import gpse.example.domain.users.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * the interface for EnvelopeServices.
 */
@Service
public interface EnvelopeService {
    Envelope getEnvelope(long id) throws DocumentNotFoundException;

    List<Envelope> getEnvelopes();

    Envelope addEnvelope(String name, User owner) throws IOException;

    Envelope updateEnvelope(final Envelope envelope, final Document document)
        throws CreatingFileException, DocumentNotFoundException, IOException;

    void remove(Envelope envelope);

    Envelope saveEnvelope(Envelope envelope);
}


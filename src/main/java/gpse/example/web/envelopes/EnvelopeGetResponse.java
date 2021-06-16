package gpse.example.web.envelopes;

import gpse.example.domain.documents.Document;
import gpse.example.domain.envelopes.Envelope;
import gpse.example.web.documents.DocumentGetResponse;
import gpse.example.domain.users.User;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * A class which represents a Response for a Envelope Get request.
 */
public class EnvelopeGetResponse {

    private final long id;
    private final String name;
    private final User owner;
    private final String creationDate;
    private final List<DocumentGetResponse> documents;

    /**
     * The default constructor for an envelope response.
     * @param envelope The envelope on which the response is based.
     * @param owner the owner of the envelope.
     * @param currentUser the user doing the request.
     */
    public EnvelopeGetResponse(final Envelope envelope, final User owner, final User currentUser) {
        this.id = envelope.getId();
        this.name = envelope.getName();
        this.owner = owner;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        this.creationDate = envelope.getCreationDate().format(formatter);
        this.documents = new ArrayList<>();
        for (final Document document : envelope.getDocumentList()) {
            //rework soon to not mix up owners
            this.documents.add(new DocumentGetResponse(document, owner, currentUser.getEmail()));
        }
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public List<DocumentGetResponse> getDocuments() {
        return documents;
    }
}

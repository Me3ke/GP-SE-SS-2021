package gpse.example.domain.envelopes;

import gpse.example.domain.documents.Document;
import gpse.example.domain.documents.DocumentGetResponse;
import gpse.example.domain.users.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A class which represents a Response for a Envelope Get request.
 */
public class EnvelopeGetResponse {

    private long id;
    private String name;
    private User owner;
    private LocalDateTime creationDate;
    private List<DocumentGetResponse> documents;

    /**
     * The default constructor for an envelope response.
     * @param envelope The envelope on which the response is based.
     * @param owner the owner of the envelope.
     */
    public EnvelopeGetResponse(final Envelope envelope, final User owner) {
        this.id = envelope.getId();
        this.name = envelope.getName();
        this.owner = owner;
        this.creationDate = envelope.getCreationDate();
        this.documents = new ArrayList<>();
        for (final Document document : envelope.getDocumentList()) {
            //rework soon to not mix up owners
            this.documents.add(new DocumentGetResponse(document, owner));
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public List<DocumentGetResponse> getDocuments() {
        return documents;
    }
}

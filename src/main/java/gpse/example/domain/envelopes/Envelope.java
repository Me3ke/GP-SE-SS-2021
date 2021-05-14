package gpse.example.domain.envelopes;

import gpse.example.domain.documents.Document;
import gpse.example.domain.documents.DocumentState;
import gpse.example.domain.users.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The model for the envelop which stores the necessary documents in a list.
 * Implements Iterable to make searching a document easier.
 */
@Entity
public class Envelope implements Iterable<Document> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    /**
     * The documentList containing all the documents.
     * The name of the envelop.
     * The envelopFile which the envelop as a directory.
     */
    @ManyToOne
    private User owner;

    @OneToMany
    private List<Document> documentList = new ArrayList<>();

    @Column
    private String name;

    @Column
    private LocalDateTime creationDate;

    @Column
    private DocumentState state;

    /**
     * Constructor which creates an envelop containing all files in the
     * list and the name.
     *
     * @param name      The name of the envelop to be created.
     * @param documents the list of documents for the envelop. At least one.
     * @param owner     the owner of this envelope
     */
    public Envelope(final String name, final List<Document> documents, final User owner) {
        this.name = name;
        this.owner = owner;
        this.creationDate = LocalDateTime.now();
        for (final Document document : documents) {
            this.documentList.add(document);
        }
    }

    /**
     * Creates an envelope without any documents.
     * @param name
     * @param owner
     */
    public Envelope(final String name, final User owner) {
        this.name = name;
        this.owner = owner;
        this.creationDate = LocalDateTime.now();
        this.state = DocumentState.OPEN;
    }

    protected Envelope() {

    }

    public long getId() {
        return id;
    }

    public void addDocument(final Document document) {
        documentList.add(document);
    }

    public void removeDocument(final int index) {
        documentList.remove(index);
    }

    @Override
    public Iterator<Document> iterator() {
        return documentList.iterator();
    }

    public List<Document> getDocumentList() {
        return documentList;
    }

    public String getName() {
        return name;
    }

    public String getOwnerID() {
        return owner.getEmail();
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public DocumentState getState() {
        return state;
    }

    public void setState(final DocumentState state) {
        this.state = state;
    }
}

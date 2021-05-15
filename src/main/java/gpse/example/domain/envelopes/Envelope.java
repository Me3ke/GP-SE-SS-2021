package gpse.example.domain.envelopes;

import gpse.example.domain.documents.Document;
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
    }

    protected Envelope() {

    }

    public void addDocument(final Document document) {
        documentList.add(document);
    }

    public void removeDocument(final Document document) {
        documentList.remove(document);
    }

    //--------- Filter methods ------------

    /**
     * The filter method for envelope names.
     * @param nameFilter the filter for the name.
     * @return true if the name of this envelope contains the filter.
     */
    public boolean hasName(final String nameFilter) {
        return this.getName().contains(nameFilter);
    }

    /**
     * The filter method for envelopeIDs.
     * @param idFilter the id of the specific envelope.
     * @return true if this envelope has the id in the filter.
     */
    public boolean hasID(final long idFilter) {
        if (idFilter == 0) {
            return true;
        }
        return this.getId() == idFilter;
    }

    /**
     * The filter method for ownerIDs.
     * @param ownerIDFilter the if of the specific owner.
     * @return true if the owner in the filter, owns this envelope.
     */
    public boolean hasOwnerID(final String ownerIDFilter) {
        return this.owner.getEmail().contains(ownerIDFilter);
    }

    /**
     * The filter method for creation Date.
     * @param creationDateFrom earliest Date for the filter.
     * @param creationDateTo latest Date for the filter.
     * @return true if the creationDate is in this range given by the filters.
     */
    public boolean hasCreationDate(final LocalDateTime creationDateFrom, final LocalDateTime creationDateTo) {
        if (creationDateFrom == null && creationDateTo == null) {
            return true;
        } else if (creationDateFrom == null) {
            return this.creationDate.isBefore(creationDateTo);
        } else if (creationDateTo == null) {
            return this.creationDate.isAfter(creationDateFrom);
        } else {
            return this.creationDate.isAfter(creationDateFrom) && this.creationDate.isBefore(creationDateTo);
        }
    }

    //--------- Getter and Setter ------------

    public long getId() {
        return id;
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

    public User getOwner() {
        return owner;
    }
}

package gpse.example.domain.signature;

import gpse.example.domain.documents.Document;
import gpse.example.domain.users.User;

import javax.persistence.*;

/**
 * the class that models a signatory for a document.
 */
@Entity
public class Signatory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @ManyToOne
    private Document document;

    @ManyToOne
    private User user;

    @Column
    private boolean status;

    /**
     * Default constructor for a Signatory. Status is initialized with false.
     * @param document The Document to which the signatory belongs to.
     * @param user the user which has to sign the corresponding document.
     */
    public Signatory(final Document document, final User user) {
        this.document = document;
        this.user = user;
        this.status = false;
    }

    protected Signatory() {

    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(final Document document) {
        this.document = document;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(final boolean status) {
        this.status = status;
    }
}

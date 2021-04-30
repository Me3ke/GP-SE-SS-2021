package gpse.example.domain;

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

    public Signatory(final Document document, final User user) {
        this.document = document;
        this.user = user;
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

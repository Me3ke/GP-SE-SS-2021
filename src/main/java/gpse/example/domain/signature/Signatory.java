package gpse.example.domain.signature;

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
    private User user;

    @Column
    private boolean status;

    /**
     * Default constructor for a Signatory. Status is initialized with false.
     * @param user the user which has to sign the corresponding document.
     */
    public Signatory(final User user) {
        this.user = user;
        this.status = false;
    }

    protected Signatory() {

    }

    public long getId() {
        return id;
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

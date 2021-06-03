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

    @Column
    private SignatureType signatureType;

    /**
     * Default constructor for a Signatory. Status is initialized with false.
     * @param user the user that has to sign the corresponding document.
     * @param signatureType the signatureType the signatory refers to.
     */
    public Signatory(final User user, final SignatureType signatureType) {
        this.user = user;
        this.status = false;
        this.signatureType = signatureType;
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

    public SignatureType getSignatureType() {
        return signatureType;
    }

    public void setSignatureType(SignatureType signatureType) {
        this.signatureType = signatureType;
    }
}

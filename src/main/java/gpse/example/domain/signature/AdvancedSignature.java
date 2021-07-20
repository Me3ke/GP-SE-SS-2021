package gpse.example.domain.signature;

import javax.persistence.*;

/**
 * the class that models an advanced signature with all necessary information.
 */
@Entity
public class AdvancedSignature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private String userEmail;

    @Lob
    @Column
    private byte[] signature;

    protected AdvancedSignature() {
    }

    /**
     * the standard constructor for advancedSignatures.
     *
     * @param userEmail the email of the signing user
     * @param signature the signature
     */
    public AdvancedSignature(final String userEmail, final byte[] signature) {
        this.userEmail = userEmail;
        this.signature = signature.clone();
    }

    public long getId() {
        return id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public byte[] getSignature() {
        return signature.clone();
    }

    public void setUserEmail(final String userEmail) {
        this.userEmail = userEmail;
    }

    public void setSignature(final byte[] signature) {
        this.signature = signature.clone();
    }

}

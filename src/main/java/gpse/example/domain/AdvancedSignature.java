package gpse.example.domain;

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

    @Column
    private byte[] signature;

    @Column
    private int keyIndex;

    protected AdvancedSignature() {
    }

    /**
     * the standard constructor for advancedSignatures.
     *
     * @param userEmail the email of the signing user
     * @param signature the signature
     * @param keyIndex  the index of the key the signature has been made with
     */
    public AdvancedSignature(final String userEmail, final byte[] signature, final int keyIndex) {
        this.userEmail = userEmail;
        this.signature = signature;
        this.keyIndex = keyIndex;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public byte[] getSignature() {
        return signature;
    }

    public int getKeyIndex() {
        return keyIndex;
    }

    public void setUserEmail(final String userEmail) {
        this.userEmail = userEmail;
    }

    public void setSignature(final byte[] signature) {
        this.signature = signature;
    }

    public void setKeyIndex(final int keyIndex) {
        this.keyIndex = keyIndex;
    }
}

package gpse.example.domain;


import javax.persistence.*;
import java.security.PublicKey;

/**
 * the class that models a key pair in the backend.
 */
@Entity
public class Keys {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private PublicKey publicKey;

    @Column
    private String privateKey;

    public Keys(final PublicKey publicKey, final String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    protected Keys() {

    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(final PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(final String privateKey) {
        this.privateKey = privateKey;
    }
}


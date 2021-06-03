package gpse.example.domain.users;

/**
 * The class used to get the String representing a publicKey from an API-Request.
 * The class used to get the public key from any request-body.
 */
public class PublicKeyCmd {
    private String publicKey;

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(final String publicKey) {
        this.publicKey = publicKey;
    }

}

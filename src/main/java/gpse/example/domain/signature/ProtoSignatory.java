package gpse.example.domain.signature;

import gpse.example.domain.users.User;

/**
 * Used to transport information regarding signatories.
 */
public class ProtoSignatory {

    private final User user;
    private final SignatureType signatureType;

    public ProtoSignatory(User user, SignatureType signatureType) {
        this.user = user;
        this.signatureType = signatureType;
    }

    public User getUser() {
        return user;
    }

    public SignatureType getSignatureType() {
        return signatureType;
    }
}

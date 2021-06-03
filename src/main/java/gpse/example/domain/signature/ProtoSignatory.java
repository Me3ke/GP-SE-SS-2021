package gpse.example.domain.signature;

import gpse.example.domain.users.User;

public class ProtoSignatory {

    private User user;
    private SignatureType signatureType;

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

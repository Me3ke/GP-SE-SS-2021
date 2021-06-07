package gpse.example.domain.signature;

import gpse.example.domain.exceptions.SignatureTypeFromIntegerException;
import gpse.example.domain.users.User;

/**
 * Used to transport information regarding signatories.
 */
public class ProtoSignatory {

    private String userID;
    private int signatureType;

    public ProtoSignatory(final String userID, final int signatureType) {
        this.userID = userID;
        this.signatureType = signatureType;
    }

    public String getUserID() {
        return userID;
    }

    public SignatureType getSignatureType() {
        try {
            return SignatureType.fromInteger(signatureType);
        } catch (SignatureTypeFromIntegerException e) {
            return SignatureType.NO_SIGNATURE;
        }
    }
}

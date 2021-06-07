package gpse.example.domain.signature;

import gpse.example.domain.exceptions.SignatureTypeFromIntegerException;

/**
 * Used to transport information regarding signatories.
 */
public class ProtoSignatory {

    private String email;
    private int type;

    public ProtoSignatory(final String email, final int type) {
        this.email = email;
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public SignatureType getType() {
        try {
            return SignatureType.fromInteger(type);
        } catch (SignatureTypeFromIntegerException e) {
            return SignatureType.NO_SIGNATURE;
        }
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setType(int type) {
        this.type = type;
    }
}

package gpse.example.domain.signature;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    /**
     * This method returns the SignatureType of the protoUser.
     * @return the type of the signature
     */
    @JsonIgnore
    public SignatureType getSignatureType() {
        try {
            return SignatureType.fromInteger(type);
        } catch (SignatureTypeFromIntegerException e) {
            return SignatureType.NO_SIGNATURE;
        }
    }

    public int getType() {
        return type;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setType(final int type) {
        this.type = type;
    }
}

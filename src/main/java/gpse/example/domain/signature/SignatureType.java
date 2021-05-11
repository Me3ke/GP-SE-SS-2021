package gpse.example.domain.signature;

import gpse.example.domain.exceptions.SignatureTypeFromIntegerException;

/**
 * This is the model enum class for a SignatureType.
 * An instance of SignatureType can be used to represent
 * the SignatureType of an instance of the Document class.
 * @author Jan Kronsbein & Alexander Heide
 * @since 04-17-2021
 */
public enum SignatureType {
    /**
     * NO_SIGNATURE can represent the absence of a Signature.
     */
    NO_SIGNATURE(-1),
    /**
     * SIMPLE_SIGNATURE can represent the simple option of a Signature.
     */
    SIMPLE_SIGNATURE(0),
    /**
     * ADVANCED_SIGNATURE can represent the advanced option of a Signature.
     */
    ADVANCED_SIGNATURE(1);

    private final int intRepresentation;


    SignatureType(final int intRepresentation) {
        this.intRepresentation = intRepresentation;
    }

    /**
     * This static method can be accessed to create a new SignatureType.
     * @param intRepresentation Integer value to define a SignatureType. Valid inputs: -1, 0, 1
     * @return new instance of SignatureType
     */
    public static SignatureType fromInteger(final int intRepresentation)
        throws SignatureTypeFromIntegerException {
        switch (intRepresentation) {
            case -1:
                return SignatureType.NO_SIGNATURE;
            case 0:
                return SignatureType.SIMPLE_SIGNATURE;
            case 1:
                return SignatureType.ADVANCED_SIGNATURE;
            default:
                throw new SignatureTypeFromIntegerException();
        }

    }

    public Integer toInteger() {
        return intRepresentation;
    }
}

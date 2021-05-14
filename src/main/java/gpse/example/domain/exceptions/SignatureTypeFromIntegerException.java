package gpse.example.domain.exceptions;

/**
 * This is an Exception class for SignatureType.fromInteger() errors.
 * Gets thrown if an invalid value was passed.
 * @author Jan Kronsbein & Alexander Heide
 * @since 04-13-2021
 */
public class SignatureTypeFromIntegerException extends Exception {
    static final long serialVersionUID = 42L;
    /**
     * This message hold a description of the Exception.
     */
    static final String MESSAGE = "Invalid input for SignatureType.fromInteger(int i), valid scope: -1,0,1";
    public SignatureTypeFromIntegerException() {
        super();
    }
    @Override
    public String getMessage() {
        return MESSAGE;
    }
}

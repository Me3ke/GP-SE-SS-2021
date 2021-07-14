package gpse.example.domain.exceptions;


/**
 * Exception for the Security config.
 */
public class SecurityConfigException extends Exception {

    public static final long serialVersionUID = 5L;
    public SecurityConfigException(final Exception exception) {
        super(exception);
    }
}

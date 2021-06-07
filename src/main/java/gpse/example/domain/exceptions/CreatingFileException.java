package gpse.example.domain.exceptions;

import java.io.IOException;

/**
 * The creatingFileException is thrown if a new File could not be created from given data.
 */
public class CreatingFileException extends Exception {

    /* default */
    static final long serialVersionUID = 7821966487856958574L;

    /* default */
    /**
     * This is the default message.
     */
    static final String MESSAGE = "Could not create a new File with given Data";

    public CreatingFileException() {
        super(); }
    public CreatingFileException(final IOException ioException) {
        super(ioException);
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}


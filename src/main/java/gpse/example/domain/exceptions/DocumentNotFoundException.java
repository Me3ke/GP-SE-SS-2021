package gpse.example.domain.exceptions;

/**
 * The DocumentNotFoundException is thrown if a path is not leading to a valid document.
 */
public class DocumentNotFoundException extends Exception {

    /*default*/ static final long serialVersionUID = 7821966487856958574L;

    /**
     * This is the default message.
     */
    /*default*/static final String MESSAGE = "Document is not valid Document or data is inconsistent.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}


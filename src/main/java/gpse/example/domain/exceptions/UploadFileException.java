package gpse.example.domain.exceptions;


/**
 * The UploadFileException is thrown if there was a Failure during document upload.
 */
public class UploadFileException extends Exception {

    static final long serialVersionUID = 7821966487856958574L;

    /**
     * This is the default message.
     */
    static final String MESSAGE = "Could not upload the document into the envelope";

    public UploadFileException(final Exception exception) {
        super(exception);
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}

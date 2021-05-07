package gpse.example.domain.exceptions;

import java.io.IOException;

/**
 * The UploadFileException is thrown if there was a Failure during document upload.
 */
public class UploadFileException extends Exception {

    private static final long serialVersionUID = 7821966487856958574L;

    /**
     * This is the default message
     */
    static final String MESSAGE = "Could not upload the document into the envelope";

    public UploadFileException(final IOException ioException) {
        super(ioException);
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}

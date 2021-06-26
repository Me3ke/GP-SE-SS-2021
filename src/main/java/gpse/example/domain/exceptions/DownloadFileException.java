package gpse.example.domain.exceptions;

/**
 * The downloadFileException is thrown if problems occur during download.
 */
public class DownloadFileException extends Exception {


    /* default */ static final long serialVersionUID = 8697857770103640856L;


    /**
     * This is the default message.
     */
    /* default */ static final String MESSAGE = "Could not download the document";

    public DownloadFileException(final Exception exception) {
        super(exception);
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}

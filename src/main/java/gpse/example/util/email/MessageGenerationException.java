package gpse.example.util.email;

/**
 * Exception thrown by generateMessage method in case there are params missing.
 * Is finally catched in Controller to send errormessage to frontend and remove broken messages.
 */
public class MessageGenerationException extends Exception {

    public static final long serialVersionUID = 25;
    private final long thrownByMessageID;

    public MessageGenerationException(final long messageID) {
        super();
        thrownByMessageID = messageID;
    }

    public MessageGenerationException(final long messageID, final Throwable throwable) {
        super(throwable);
        thrownByMessageID = messageID;
    }
    public long getThrownByMessageID() {
        return this.thrownByMessageID;
    }

}

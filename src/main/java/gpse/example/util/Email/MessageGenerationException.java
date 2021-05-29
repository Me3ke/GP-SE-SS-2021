package gpse.example.util.Email;

public class MessageGenerationException extends Exception {

    private long thrownByMessageID;

    public MessageGenerationException() {
        super();
    }

    public MessageGenerationException(long messageID) {
        super();
        thrownByMessageID = messageID;
    }

    public long getThrownByMessageID() {
        return this.thrownByMessageID;
    }
}

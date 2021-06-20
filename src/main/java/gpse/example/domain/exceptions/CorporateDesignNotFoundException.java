package gpse.example.domain.exceptions;

/**
 * The CorporateDesignNotFoundException is thrown if the given corporate Design does not exists.
 */
public class CorporateDesignNotFoundException extends Exception {

    static final long serialVersionUID = -3751989777455505798L;

    /**
     * This is the default message.
     */
    static final String MESSAGE = "Corporate Design was not found.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}

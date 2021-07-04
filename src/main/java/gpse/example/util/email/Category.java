package gpse.example.util.email;

/**
 * Enumeration for email categories.
 */
public enum Category {

    /**
     * category is to do.
     * For admin if there is a new guest signatory for example.
     */
    TODO,

    /**
     * category is sign.
     * If the user has to sign a document.
     */
    SIGN,

    /**
     * category is read.
     * If the user has to sign a document.
     */
    READ,

    /**
     * category progress.
     * Reminder for sign or read or progress finished email.
     */
    PROGRESS,

    /**
     * category is new version.
     * If a new Version of a document is uploaded and signatures
     * have been invalidated.
     */
    NEW_VERSION,

    /**
     * category is system.
     * For emails such as registration etc.
     */
    SYSTEM

}

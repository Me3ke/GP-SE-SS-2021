package gpse.example.domain.documents;

/**
 * A class representing the possible states of a document.
 */
public enum DocumentState {

    /**
     * If the there are unsigned signatories and readers left it has open state.
     */
    OPEN(-1),
    /**
     * Changes to read state if all users have read the document.
     */
    READ(0),
    /**
     * Changes to signed state if all users have read the document.
     */
    SIGNED(1),
    /**
     * Changes to read and signed state if all users have read and signed the document.
     */
    READ_AND_SIGNED(2);

    private final int intRepresentation;


    DocumentState(final int intRepresentation) {
        this.intRepresentation = intRepresentation;
    }

    /**
     * The fromInteger method creates a DocumentState from an integer representation.
     * @param intRepresentation the integer representation
     * @return the corresponding document state.
     * @throws IllegalStateException if the integer cannot be assinged to a state.
     */
    public static DocumentState fromInteger(final int intRepresentation) {
        switch (intRepresentation) {
            case -1:
                return DocumentState.OPEN;
            case 0:
                return DocumentState.READ;
            case 1:
                return DocumentState.SIGNED;
            case 2:
                return DocumentState.READ_AND_SIGNED;
            default:
                throw new IllegalStateException();
        }
    }

    public Integer toInteger() {
        return intRepresentation;
    }
}


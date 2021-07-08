package gpse.example.domain.documents;

/**
 * A class representing the possible states of a document.
 */
public enum DocumentState {

    /**
     * If the there are unsigned signatories and readers left it has the review state,
     * where reviews should be made.
     */
    REVIEW(-1),
    /**
     * Changes to sign state if all users have read the document. This is when the signatures are made.
     */
    SIGN(0),
    /**
     * Changes to closed state if all users have signed the document. All documents that are archived should be closed.
     */
    ARCHIVED(1);

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
                return DocumentState.REVIEW;
            case 0:
                return DocumentState.SIGN;
            case 1:
                return DocumentState.ARCHIVED;
            default:
                throw new IllegalStateException();
        }
    }

    public Integer toInteger() {
        return intRepresentation;
    }
}


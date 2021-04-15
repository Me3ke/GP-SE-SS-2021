package gpse.example.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * The model for the document responsible for initialising the necessary details about the document file.
 */
public class Document {
    /**
     * The documentMetaData containing the identifier as well as other information.
     */
    private DocumentMetaData documentMetaData;
    private List<String> unsignedSignatories = new ArrayList<>();
    private List<String> signedSignatories = new ArrayList<>();

    public Document() {
    }

    public Document(final DocumentMetaData documentMetaData) {
        this.documentMetaData = documentMetaData;
    }

    public Document(final DocumentMetaData documentMetaData, final List<String> signatories) {
        this.documentMetaData = documentMetaData;
        this.unsignedSignatories = signatories;
    }

    /**
     * adds a new signatory to the unsignedSignatory list.
     *
     * @param signatory the ID (e-Mail address) of the new signatory
     */
    public void addUnsignedSignatory(final String signatory) {
        if (unsignedSignatories == null) {
            unsignedSignatories = new ArrayList<>();
        }
        if (!(unsignedSignatories.contains(signatory) || signedSignatories.contains(signatory))) {
            unsignedSignatories.add(signatory);
        }
    }

    /**
     * adds a signatory to the signedSignatory list, if it is in the unsigned-list.
     * Deletes the signatory from the unsigned-list
     *
     * @param signatory the ID (e-Mail address) of the signatory
     */
    public void addSignedSignatory(final String signatory) {
        if (signedSignatories == null) {
            signedSignatories = new ArrayList<>();
        }
        if (unsignedSignatories.contains(signatory)) {
            deleteUnsignedSignatory(signatory);
            signedSignatories.add(signatory);
        }
    }

    /**
     * deletes a signatory from the unsigned list.
     *
     * @param signatory the ID (e-Mail address) of the signatory
     */
    public void deleteUnsignedSignatory(final String signatory) {
        if (unsignedSignatories.contains(signatory)) {
            unsignedSignatories.remove(signatory);
        }
    }

    /**
     * deletes a signatory from the signed list.
     *
     * @param signatory the ID (e-Mail address) of the signatory
     */
    public void deleteSignedSignatory(final String signatory) {
        if (signedSignatories.contains(signatory)) {
            signedSignatories.remove(signatory);
        }
    }

    public List<String> getUnsignedSignatories() {
        return unsignedSignatories;
    }

    public List<String> getSignedSignatories() {
        return signedSignatories;
    }

    /**
     * returns all signatories (unsigned + signed) for the document.
     *
     * @return all signatories for the document.
     */
    public List<String> getSignatories() {
        final List<String> signatories = new ArrayList<>(unsignedSignatories);
        signatories.addAll(signedSignatories);
        return signatories;
    }
}

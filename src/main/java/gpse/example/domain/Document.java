package gpse.example.domain;

import java.security.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The model for the document responsible for initialising the necessary details about the document file.
 */
public class Document {

    private static final String SIGNING_ALGORITHM = "SHA256withRSA";
    /**
     * The documentMetaData containing the identifier as well as other information.
     */
    private DocumentMetaData documentMetaData;
    private List<String> unsignedSignatories = new ArrayList<>();
    private List<String> signedSignatories = new ArrayList<>();
    private Map<String, byte[]> advancedSignatures = new HashMap<>();


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

    public void advancedSignature(final User user) {
        final String mail = user.getEmail();
        if (unsignedSignatories.contains(mail)) {
            advancedSignatures.put(mail, user.advancedSign(this.documentMetaData.getIdentifier()));
            addSignedSignatory(mail);
        }
    }

    public boolean verifySignature(final User user) {
        final String mail = user.getEmail();
        final byte[] signature = advancedSignatures.get(mail);
        boolean valid = false;
        if (advancedSignatures.containsKey(mail)) {
            try {
                final Signature sign = Signature.getInstance(SIGNING_ALGORITHM);
                final byte[] id = this.documentMetaData.getIdentifier().getBytes();
                final PublicKey[] publicKeys = user.getAllPublicKeys();
                for (final PublicKey publicKey : publicKeys) {
                    sign.initVerify(publicKey);
                    sign.update(id);
                    valid |= sign.verify(signature);
                    if (valid) {
                        break;
                    }
                }
            } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException exception) {
                System.out.println(exception.getMessage());
            }
        }
        return valid;
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

    public Map<String, byte[]> getAdvancedSignatures() {
        return advancedSignatures;
    }
}

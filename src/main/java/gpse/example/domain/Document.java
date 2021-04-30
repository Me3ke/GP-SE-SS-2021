package gpse.example.domain;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

//TODO alle Kommentare und Tests Überarbeiten
//TODO evtl Packages erstellen?

/**
 * The model for the document responsible for initialising the necessary details about the document file.
 */
@Entity
public class Document {

    //needed for verification
    private static final String SIGNING_ALGORITHM = "SHA256withRSA";
    /**
     * The documentMetaData containing the identifier as well as other information.
     * The path leading to the document.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @OneToOne
    private DocumentMetaData documentMetaData;

    @OneToMany
    private List<Signatory> signatories = new ArrayList<>();

    @OneToMany
    private List<AdvancedSignature> advancedSignatures = new ArrayList<>();

    @Column
    private File documentFile;

    @Column
    private String documentType;

    @Column
    private SignatureType signatureType = SignatureType.NO_SIGNATURE;

    public Document() {
    }

    public Document(final DocumentMetaData documentMetaData) {
        this.documentMetaData = documentMetaData;
    }

    public Document(final DocumentMetaData documentMetaData, final List<Signatory> signatories) {
        this.documentMetaData = documentMetaData;
        this.signatories = signatories;
    }

    /**
     * The constructor for a document with a given path to a file.
     * Additionally formats some of the metadata. Uses placeholders for
     * metaUserID, upload timestamp and documentID, for later implementation.
     * Also has to be checked for harmful content in the future.
     * This works only if documentTitle has no dot.
     *
     * @param path        The path leading to the file.
     * @param signatories The list of signatories for a document.
     * @throws IOException throws the exception if filepath was invalid.
     */
    public Document(final String path, final List<Signatory> signatories) throws IOException {
        this.signatories = signatories;
        final Path documentPath = Paths.get(path);
        this.documentFile = new File(path);
        final BasicFileAttributes attr = Files.readAttributes(documentPath, BasicFileAttributes.class);
        final String[] filename = documentFile.getName().split("\\.");
        final String title = filename[0];
        this.documentType = filename[1];
        this.documentMetaData = new DocumentMetaData(new Timestamp(1), title, attr.creationTime(),
            attr.lastModifiedTime(), attr.lastAccessTime(), attr.size());
    }

    /**
     * adds a new user as a signatory to the signatory list.
     *
     * @param signatory the user object that is needed as a signatory
     */
    public void addSignatory(final User signatory) {
        signatories.add(new Signatory(this, signatory));
    }

    /**
     * the Method to add an advanced signature for a specific user to the document.
     *
     * @param user the user that signs the document
     * @param signature the signature that has been made
     * @param index the index of the key the signature has been made with
     */
    //TODO
    public void advancedSignature(final String user, final byte[] signature, final int index) {
        boolean userIsSignatory = false;
        for (int i = 0; i < signatories.size(); i++) {
            if (signatories.get(i).getUser().getEmail().equals(user)) {
                userIsSignatory = true;
                advancedSignatures.add(new AdvancedSignature(user, signature, index));
                setSigned(i);
            }
        }
        if (!userIsSignatory) {
            System.out.println("The user is not a signatory for this document");
        }
    }

    /**
     * the method used to verify a signature for a specific user, by checking all public keys a user has.
     *
     * @param user the user who relates to the signature that needs to be checked
     * @return true, if one of the public keys matches with the signature.If thet is not the case we return false.
     */
    //TODO
    public boolean verifySignature(final User user) {

        final AdvancedSignature signatureInfo = getUsersSignature(user.getEmail());
        boolean valid = false;
        if (signatureInfo != null) {
            final byte[] signature = signatureInfo.getSignature();
            final int keyIndex = signatureInfo.getKeyIndex();
            try {
                final Signature sign = Signature.getInstance(SIGNING_ALGORITHM);
                final byte[] id = this.documentMetaData.getIdentifier().getBytes();
                final PublicKey publicKey = user.getPublicKey(keyIndex);
                sign.initVerify(publicKey);
                sign.update(id);
                valid = sign.verify(signature);
            } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException exception) {
                System.out.println(exception.getMessage());
            }
        }
        return valid;
    }

    private AdvancedSignature getUsersSignature(final String user) {
        for (final AdvancedSignature advancedSignature : advancedSignatures) {
            if (advancedSignature.getUserEmail().equals(user)) {
                return advancedSignature;
            }
        }
        return null;
    }

    public void setSigned(final int index) {
        signatories.get(index).setStatus(true);
    }

    public DocumentMetaData getDocumentMetaData() {
        return documentMetaData;
    }

    public File getDocumentFile() {
        return documentFile;
    }

    public String getDocumentTitle() {
        return documentMetaData.getMetaDocumentTitle();
    }

    public String getDocumentType() {
        return documentType;
    }

    public SignatureType getSignatureType() {
        return signatureType;
    }

    public void setSignatureType(final SignatureType signatureType) {
        this.signatureType = signatureType;
    }

    public List<AdvancedSignature> getAdvancedSignatures() {
        return advancedSignatures;
    }

    public List<Signatory> getSignatories() {
        return signatories;
    }
}


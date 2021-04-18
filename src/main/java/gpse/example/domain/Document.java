package gpse.example.domain;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
     * The path leading to the document.
     */
    private DocumentMetaData documentMetaData;
    //these Lists/Maps have to be initialized from the beginning, to avoid nullpointer exceptions
    private List<String> unsignedSignatories = new ArrayList<>();
    private List<String> signedSignatories = new ArrayList<>();
    private Map<String, byte[]> advancedSignatures = new HashMap<>();
    private Path documentPath;
    private File documentFile;
    private int documentID;
    private String documentTitle;
    private String documentType;
    private SignatureType signatureType = SignatureType.NO_SIGNATURE;

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
    public Document(final String path, final List<String> signatories) throws IOException {
        this.unsignedSignatories = signatories;
        this.documentPath = Paths.get(path);
        this.documentFile = new File(path);
        this.documentID = 1;
        this.signedSignatories = new ArrayList<>();
        final BasicFileAttributes attr = Files.readAttributes(documentPath, BasicFileAttributes.class);
        final String[] filename = documentFile.getName().split("\\.");
        final String title = filename[0];
        this.documentTitle = title;
        this.documentType = filename[1];
        final String timeOfCreation = formatDateTime(attr.creationTime());
        final String timeOfLastMod = formatDateTime(attr.lastModifiedTime());
        final String timeOfLastAccess = formatDateTime(attr.lastAccessTime());
        this.documentMetaData = new DocumentMetaData("01", new Timestamp(1), documentTitle, timeOfCreation,
            timeOfLastMod, timeOfLastAccess, attr.size());
    }

    /**
     * The formatDateTime methods converts the file times to a more readable format.
     *
     * @param fileTime the file time.
     * @return returns a String of the time in a new format.
     */
    private String formatDateTime(final FileTime fileTime) {
        final LocalDateTime localDateTime = fileTime
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
        return localDateTime.format(
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
    }

    /**
     * adds a new signatory to the unsignedSignatory list.
     *
     * @param signatory the ID (e-Mail address) of the new signatory
     */
    public void addUnsignedSignatory(final String signatory) {
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
        if (unsignedSignatories.contains(signatory)) {
            deleteUnsignedSignatory(signatory);
            signedSignatories.add(signatory);
        }
    }

    /**
     * the Method to add an advanced signature for a specific user to the document.
     * @param user the user that signs the document
     */
    public void advancedSignature(final User user) {
        final String mail = user.getEmail();
        if (unsignedSignatories.contains(mail)) {
            //the new signature is generated by the user and saved in a map to keep the relation beetween user and
            //signature
            advancedSignatures.put(mail, user.advancedSign(this.documentMetaData.getIdentifier()));
            addSignedSignatory(mail);
        }
    }

    /**
     * the method used to verify a signature for a specific user, by checking all public keys a user has.
     * @param user the user who relates to the signature that needs to be checked
     * @return true, if one of the public keys matches with the signature.If thet is not the case we return false.
     */
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
        unsignedSignatories.remove(signatory);
    }

    /**
     * deletes a signatory from the signed list.
     *
     * @param signatory the ID (e-Mail address) of the signatory
     */
    public void deleteSignedSignatory(final String signatory) {
        signedSignatories.remove(signatory);
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


    public List<String> getUnsignedSignatories() {
        return unsignedSignatories;
    }

    public List<String> getSignedSignatories() {
        return signedSignatories;
    }

    public DocumentMetaData getDocumentMetaData() {
        return documentMetaData;
    }

    public Path getDocumentPath() {
        return documentPath;
    }

    public File getDocumentFile() {
        return documentFile;
    }

    public int getDocumentID() {
        return documentID;
    }

    public String getDocumentTitle() {
        return documentTitle;
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

    public Map<String, byte[]> getAdvancedSignatures() {
        return advancedSignatures;
    }
}


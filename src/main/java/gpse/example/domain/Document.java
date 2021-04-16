package gpse.example.domain;

import java.util.ArrayList;
import java.util.List;
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

/**
 * The model for the document responsible for initialising the necessary details about the document file.
 */
public class Document {
    /**
     * The documentMetaData containing the identifier as well as other information.
     * The path leading to the document.
     *
     *
     */
    private DocumentMetaData documentMetaData;
    private List<String> unsignedSignatories;
    private List<String> signedSignatories;
    private Path documentPath;
    private File documentFile;
    private int documentID;
    private String documentTitle;
    private String documentType;

    /**
     * The constructor for a document with a given path to a file.
     * Additionally formats some of the metadata. Uses placeholders for
     * metaUserID, upload timestamp and documentID, for later implementation.
     * Also has to be checked for harmful content in the future.
     * This works only if documentTitle has no dot.
     * @param path The path leading to the file.
     * @param signatories The list of signatories for a document.
     * @throws IOException throws the exception if filepath was invalid.
     */
    @SuppressWarnings("PMD.AvoidReassigningParameters")
    public Document(final String path, List<String> signatories) throws IOException {
        if (signatories == null) {
            signatories = new ArrayList<>();
            this.unsignedSignatories = signatories;
        } else {
            this.unsignedSignatories = signatories;
        }
        this.documentPath = Paths.get(path);
        this.documentFile = new File(path);
        this.documentID = 1;
        this.signedSignatories = new ArrayList<>();
        BasicFileAttributes attr = Files.readAttributes(documentPath, BasicFileAttributes.class);
        String[] filename = documentFile.getName().split("\\.");
        String title = filename[0];
        this.documentTitle = title;
        this.documentType = filename[1];
        String timeOfCreation = formatDateTime(attr.creationTime());
        String timeOfLastMod = formatDateTime(attr.lastModifiedTime());
        String timeOfLastAccess = formatDateTime(attr.lastAccessTime());
        this.documentMetaData = new DocumentMetaData("01", new Timestamp(1), title, timeOfCreation,
                                                        timeOfLastMod, timeOfLastAccess, attr.size());
    }

    /**
     * The formatDateTime methods converts the file times to a more readable format.
     * @param fileTime the file time.
     * @return returns a String of the time in a new format.
     */
    private String formatDateTime(final FileTime fileTime) {
        LocalDateTime localDateTime = fileTime
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
}

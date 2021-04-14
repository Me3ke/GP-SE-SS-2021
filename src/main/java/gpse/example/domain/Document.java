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

/**
 * The model for the document responsible for initialising the necessary details about the document file.
 */
public class Document {
    /**
     * The documentMetaData containing the identifier as well as other information.
     * The path leading to the document.
     */
    private DocumentMetaData documentMetaData;
    private Path documentPath;
    private File documentFile;

    public Document() {
    }

    public Document(DocumentMetaData documentMetaData) {
        this.documentMetaData = documentMetaData;
    }

    /**
     * The constructor for a document with a given path to a file.
     * Additionally formats some of the metadata. Uses placeholders for
     * metaUserID, upload timestamp and documentID, for later implementation.
     * Also has to be checked for harmful content in the future.
     * @param path The path leading to the file.
     * @throws IOException throws the exception if filepath was invalid.
     */
    public Document(String path) throws IOException {
        this.documentPath = Paths.get(path);
        this.documentFile = new File(path);
        BasicFileAttributes attr = Files.readAttributes( documentPath, BasicFileAttributes.class);
        String[] filename = documentFile.getName().split("\\.");                                                    //only if documentTitle has no dot
        String title = filename[0];
        String extension = filename[1];
        String timeOfCreation = formatDateTime(attr.creationTime());
        String timeOfLastMod = formatDateTime(attr.lastModifiedTime());
        String timeOfLastAccess = formatDateTime(attr.lastAccessTime());
        this.documentMetaData = new DocumentMetaData( "01", new Timestamp(1),title, 1, extension,
                                                        timeOfCreation, timeOfLastMod, timeOfLastAccess, attr.size());
    }

    /**
     * The formatDateTime methods converts the file times to a more readable format.
     * @param fileTime the file time.
     * @return returns a String of the time in a new format.
     */
    public String formatDateTime(FileTime fileTime) {
        LocalDateTime localDateTime = fileTime
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
        return localDateTime.format(
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
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
}

package gpse.example.domain.documents;

import gpse.example.domain.envelopes.Envelope;
import gpse.example.domain.exceptions.CreatingFileException;
import gpse.example.domain.signature.ProtoSignatory;
import gpse.example.domain.users.UserService;
import gpse.example.web.documents.DocumentPutRequest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The DocumentCreator is a factory method responsible for creating documents and envelops.
 */
@SuppressWarnings("PMD.AvoidFileStream")
public class DocumentCreator {

    private static final String PATH_TO_DOWNLOADS = "src/main/resources/Downloads/";

    /**
     * The download methods creates a new File of the document given using the writeInNewFileMethod.
     *
     * @param document the document for the new File.
     * @param path     the in which the document should be downloaded.
     * @return the document with a new documentFile.
     * @throws CreatingFileException if creating the file failed.
     * @throws IOException           if creating the file failed.
     */
    public Document download(final Document document, final String path)
        throws CreatingFileException, IOException {
        if (document.getData() == null) {
            throw new CreatingFileException(new IOException());
        }
        writeInNewFile(document.getData(), document.getDocumentType(),
            document.getDocumentTitle(), path);
        return document;
    }

    /**
     * The createDocFromData creates a document if it has been uploaded, using the put request body.
     *
     * @param documentPutRequest the command object which keeps the information for the document.
     * @param ownerID            the email adress of the User who want to create the document.
     * @param signatories        the list of signatories for this document.
     * @param userService        the service used to handle ProtoSignatories.
     * @return the created document.
     * @throws IOException           if the data is incorrect.
     * @throws CreatingFileException if the path is not specified.
     */
    //does not include directories.
    public Document createDocument(final DocumentPutRequest documentPutRequest, final String ownerID,
                                   final List<ProtoSignatory> signatories, UserService userService)
        throws CreatingFileException, IOException {
        if (documentPutRequest.getData().length == 0) {
            throw new CreatingFileException(new IOException());
        }
        final Document document = new Document(documentPutRequest, new ArrayList<>(),
            ownerID);
        setDocumentState(signatories, document);
        setSignatories(signatories, document, userService);
        return document;
    }

    /**
     * The setSignatories method creates signatory objects and refers them to the documents.
     * A reader uses the same class as Signatory because both processes are similar.
     *
     * @param signatories a list of users containing all the people to sign or read the document.
     * @param document    the document itself.
     */
    private void setSignatories(final List<ProtoSignatory> signatories,
                                final Document document, UserService userService) {
        if (signatories != null) {
            for (final ProtoSignatory signatory : signatories) {
                document.addSignatory(userService.getUser(signatory.getEmail()), signatory.getType());
            }
        }
    }

    /**
     * The setDocumentState method evaluates the readers and signatories and creates an
     * initial state of the document based on that.
     *
     * @param signatories a list of users containing all the people to sign the document.
     * @param document    the document itself.
     */
    private void setDocumentState(final List<ProtoSignatory> signatories, final Document document) {
        if (signatories == null) {
            document.setState(DocumentState.CLOSED);
        } else if (document.getReaders().size() == 0 && !document.isOrderRelevant()) {
            document.setState(DocumentState.READ);
        } else {
            document.setState(DocumentState.OPEN);
        }
    }

    /**
     * The writeInNewFile methods creates a new File from a given byte array and file extension.
     *
     * @param bytes the byte array from another file.
     * @param type  the file extension from another file.
     * @param name  the name of the new created file.
     * @param path  the path of the new created file.
     * @return the newly created File
     * @throws CreatingFileException if FileInputStream creates an error.
     */
    @SuppressWarnings({"PMD.AvoidFileStream", "PMD.UseTryWithResources"})
    public File writeInNewFile(final byte[] bytes, final String type,
                               final String name, final String path) throws CreatingFileException {
        final File file = checkPath(type, name, path);
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            if (file.createNewFile()) {
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos);
                bos.write(bytes);
            } else {
                throw new CreatingFileException(new IOException());
            }
        } catch (IOException e) {
            throw new CreatingFileException(e);
        } finally {
            close(fos, bos);
        }
        return file;
    }

    /**
     * The checkPath method checks if the given path is valid. If there is no path given the
     * method uses the default path (src/main/resources/Downloads). If there is no Download directory
     * the method creates one.
     *
     * @param type the type of the file.
     * @param name the name of the file.
     * @param path the target path.
     * @return the file with the correct path-
     * @throws CreatingFileException if there were invalid inputs or errors in some creations.
     */
    private File checkPath(final String type, final String name, final String path) throws CreatingFileException {
        final File pathFile = new File(PATH_TO_DOWNLOADS);
        if (!pathFile.exists() && !pathFile.mkdir()) {
            throw new CreatingFileException(new IOException());
        }
        File file;
        if (path.equals("")) {
            file = new File(PATH_TO_DOWNLOADS + name + "." + type);
        } else {
            file = new File(path + name + "." + type);
        }
        if (file.exists() && !file.delete()) {
            throw new CreatingFileException();
        }
        return file;
    }

    /**
     * The close method avoids too much complexity in writeInNewFile.
     *
     * @param fos the FileOutputStream.
     * @param bos the BufferedOutputStream.
     * @throws CreatingFileException if the streams cannot be closed.
     */
    private void close(final FileOutputStream fos, final BufferedOutputStream bos) throws CreatingFileException {
        try {
            assert bos != null;
            bos.close();
            fos.close();
        } catch (IOException e) {
            throw new CreatingFileException(e);
        }
    }

    /**
     * The downloadEnvelope methods downloads a whole envelope.
     *
     * @param envelope the envelope to be downloaded.
     * @param path     the path where the envelope should be downloaded.
     * @throws IOException           if the data is incorrect.
     * @throws CreatingFileException if the path is not specified.
     */
    public void downloadEnvelope(final Envelope envelope, final String path) throws IOException, CreatingFileException {
        final List<Document> documentList = envelope.getDocumentList();
        String pathToEnvelope;
        if (path.equals("")) {
            pathToEnvelope = PATH_TO_DOWNLOADS + envelope.getName();
        } else {
            pathToEnvelope = path + envelope.getName();
        }
        if (new File(pathToEnvelope).mkdir()) {
            for (final Document document : documentList) {
                download(document, pathToEnvelope);
            }
        } else {
            throw new CreatingFileException(new IOException());
        }
    }

/*

    /**
     * The convertPathsToDocuments method gets the paths of the documents and
     * uses the recursive directoryToDocuments method to create files from
     * these and all (sub-)directories.
     *
     * @param associateSignatories a map which maps the paths to a list of signatories.
     * @param name                 the name of the envelope that is created in the method
     * @param owner                the owner of the envelope that will be created by this method
     * @return returns an envelop.
public List<Document> convertPathsToDocuments(final Map<String, List<Signatory>> associateSignatories, final User owner,
                                                  final String name) {
        final List<Document> documentList = new ArrayList<>();
        try {
            final Set<String> keys = new HashSet<>(associateSignatories.keySet());
            for (final String path : keys) {
                final File file = new File(path);
                if (file.isDirectory()) {
                    final List<Signatory> signatories = associateSignatories.get(file.getPath());
                    associateSignatories.remove(file.getPath());
                    documentList.addAll(directoryToDocuments(file, signatories, owner));
                } else {
                    final Document document = new Document(path, associateSignatories.get(path), owner.getEmail());
                    documentList.add(document);
                }
            }
        } catch (IOException e) {
            System.out.println("path invalid.");
        }
        return new Envelope(name, documentList, owner);
    }


    /**
     * The directoryToDocuments method is called if a path to be converted is a directory.
     * The method will call itself to create all documents from the files in all (sub) directories.
     *
     * @param directory     The directory where all files are searched in.
     * @param associatedSig a list of signatories associated with the given directory.
     * @return returns the instantiated list of documents in this directory.
     * @throws IOException if a path was invalid.

    private List<Document> directoryToDocuments(final File directory,
                                                final List<Signatory> associatedSig,
                                                final User owner) throws IOException {
        final List<Document> directoryFileList = new ArrayList<>();
        final List<File> directoryFiles = Arrays.asList(directory.listFiles().clone());
        for (final File directoryFile : directoryFiles) {
            if (directoryFile.isDirectory()) {
                final List<Document> directoryList = directoryToDocuments(directoryFile, associatedSig, owner);
                directoryFileList.addAll(directoryList);
            } else {
                final Document document = new Document(directoryFile.getPath(), associatedSig, owner.getEmail());
                directoryFileList.add(document);
            }
        }
        return directoryFileList;
    }
    */
}


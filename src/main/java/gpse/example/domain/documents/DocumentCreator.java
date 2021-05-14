package gpse.example.domain.documents;

import gpse.example.domain.exceptions.CreatingFileException;
import gpse.example.domain.users.User;

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
     * @param document the document for the new File.
     * @return the document with a new documentFile.
     * @throws CreatingFileException if creating the file failed.
     * @throws IOException if creating the file failed.
     */
    public Document download(final Document document)
        throws CreatingFileException, IOException {
        if (document.getData() == null) {
            throw new CreatingFileException(new IOException());
        }
        final File file = writeInNewFile(document.getData(), document.getDocumentType(), document.getDocumentTitle());
        document.setDocumentFile(file);
        return document;
    }

    /**
     * The createDocFromData creates a document if it has been uploaded, using the put request body.
     *
     * @param documentPutRequest the command object which keeps the information for the document.
     * @param ownerID     the email adress of the User who want to create the document.
     * @param signatories the list of signatories for this document.
     * @param readers     the list of readers for this document.
     * @return the created document.
     * @throws IOException if the data is incorrect.
     */
    //does not include directories.
    public Document createDocument(final DocumentPutRequest documentPutRequest, final String ownerID, final List<User> signatories,
                                   final List<User> readers) throws CreatingFileException, IOException {
        if (documentPutRequest.getPath().equals("")) {
            throw new CreatingFileException(new IOException());
        }
        final Document document = new Document(documentPutRequest.getPath(), new ArrayList<>(), ownerID, new ArrayList<>());
        setDocumentState(signatories, readers, document);
        setReadersAndSignatories(signatories, readers, document);
        document.setEndDate(documentPutRequest.getEndDate());
        document.setOrderRelevant(documentPutRequest.isOrderRelevant());
        return document;
    }

    /**
     * The setReadersAndSignatories method creates signatory objects and refers them to the documents.
     * A reader uses the same class as Signatory because both processes are similar.
     * @param signatories a list of users containing all the people to sign the document.
     * @param readers a list of users containing all the people to read the document before signing.
     * @param document the document itself.
     */
    private void setReadersAndSignatories(final List<User> signatories, final List<User> readers,
                                          final Document document) {
        if (signatories != null) {
            for (final User signatory : signatories) {
                document.addSignatory(signatory);
            }
        }
        if (readers != null) {
            for (final User reader : readers) {
                document.addReader(reader);
            }
        }
    }

    /**
     * The setDocumentState method evaluates the readers and signatories and creates an
     * initial state of the document based on that.
     * @param signatories a list of users containing all the people to sign the document.
     * @param readers a list of users containing all the people to read the document before signing
     * @param document the document itself.
     */
    private void setDocumentState(final List<User> signatories, final List<User> readers, final Document document) {
        if (readers == null && signatories == null) {
            document.setState(DocumentState.READ_AND_SIGNED);
        } else if (signatories == null) {
            document.setState(DocumentState.SIGNED);
        } else if (readers == null) {
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
     * @return the newly created File
     * @throws IOException if FileInputStream creates an error.
     */
    @SuppressWarnings("PMD.AvoidFileStream")
    private File writeInNewFile(final byte[] bytes, final String type, final String name) throws CreatingFileException {
        final File file = new File(PATH_TO_DOWNLOADS + name + "." + type);
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        if (file.exists()) {
            file.delete();
        }
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
            try {
                assert bos != null;
                bos.close();
                fos.close();
            } catch (IOException e) {
                System.out.println("something went wrong while closing.");
            }
        }
        return file;
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


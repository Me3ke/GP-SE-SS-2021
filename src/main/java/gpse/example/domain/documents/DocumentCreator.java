package gpse.example.domain.documents;

import gpse.example.domain.exceptions.CreatingFileException;
import gpse.example.domain.exceptions.DocumentNotFoundException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * The DocumentCreator is a factory method responsible for creating documents and envelops.
 */
@SuppressWarnings("PMD.AvoidFileStream")
public class DocumentCreator {

    /**
     * The createDocFromData creates a document from an existing byte array or an existing path.
     * @param documentCmd the command object which keeps the information for the document.
     * @param ownerID the email adress of the User who want to create the document.
     * @return the created document.
     * @throws IOException if the data is incorrect.
     */
    //does not include directories.
    public Document createDocument(final DocumentCmd documentCmd, final String ownerID)
                                    throws DocumentNotFoundException, CreatingFileException, IOException {
        Document document;
        if (documentCmd.getPath().equals("")) {
            File documentFile;
            if (documentCmd.getData() == null) {
                throw new CreatingFileException(new IOException());
            } else {
                documentFile = writeInNewFile(documentCmd.getData(), documentCmd.getType(),
                    documentCmd.getTitle());
            }
            document = new Document(documentFile.getAbsolutePath(), documentCmd.getSignatories(),
                                        ownerID, documentCmd.getReaders());
        } else {
            final File file = new File(documentCmd.getPath());
            if (file.exists() && file.isFile()) {
                document = new Document(documentCmd.getPath(), documentCmd.getSignatories(), ownerID,
                    documentCmd.getReaders());
            } else {
                throw new DocumentNotFoundException();
            }
        }
        document.setEndDate(documentCmd.getEndDate());
        document.setOrderRelevant(documentCmd.isOrderRelevant());
        return document;
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
        final File file = new File("src/main/resources/Downloads/" + name + "." + type);
        FileOutputStream fos = null;
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            fos = new FileOutputStream(file);
            fos.write(bytes);
        } catch (IOException e) {
            throw new CreatingFileException(e);
        } finally {
            try {
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


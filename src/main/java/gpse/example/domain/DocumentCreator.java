package gpse.example.domain;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * The DocumentCreator is a factory method responsible for creating documents and envelops.
 */
public class DocumentCreator {

    /**
     * The convertPathsToDocuments method gets the paths of the documents and
     * uses the recursive directoryToDocuments method to create files from
     * these and all (sub-)directories.
     * @param associateSignatories a map which maps the paths to a list of signatories.
     * @return returns the list of all instantiated Documents.
     */
    public List<Document> convertPathsToDocuments(final Map<String, List<String>> associateSignatories) {
        final List<Document> documentList = new ArrayList<>();
        try {
            final Set<String> keys = new HashSet<>(associateSignatories.keySet());
            for (final String path : keys) {
                final File file = new File(path);
                if (file.isDirectory()) {
                    final List<String> signatories = associateSignatories.get(file.getPath());
                    associateSignatories.remove(file.getPath());
                    documentList.addAll(directoryToDocuments(file, signatories));
                } else {
                    final Document document = new Document(path, associateSignatories.get(path));
                    documentList.add(document);
                }
            }
        } catch (IOException e) {
            System.out.println("path invalid.");
        }
        return documentList;
    }

    /**
     * The create envelop methods creates an envelop from a given name and
     * a list of all documents to be in the envelop.
     * @param name the name of the envelop.
     * @param documentList the list which contains the documents to be in the envelop.
     * @return the instantiated envelop.
     */
    public Envelop createEnvelop(final String name, final List<Document> documentList) {
        final Envelop envelop = new Envelop(name, documentList);
        return envelop;
    }

    /**
     * The directoryToDocuments method is called if a path to be converted is a directory.
     * The method will call itself to create all documents from the files in all (sub) directories.
     * @param directory The directory where all files are searched in.
     * @param associatedSig a list of signatories associated with the given directory.
     * @return returns the instantiated list of documents in this directory.
     * @throws IOException if a path was invalid.
     */
    private List<Document> directoryToDocuments(final File directory,
                                                final List<String> associatedSig) throws IOException {
        final List<Document> directoryFileList = new ArrayList<>();
        final List<File> directoryFiles = Arrays.asList(directory.listFiles().clone());
        for (final File directoryFile : directoryFiles) {
            if (directoryFile.isDirectory()) {
                final List<Document> directoryList = directoryToDocuments(directoryFile, associatedSig);
                directoryFileList.addAll(directoryList);
            } else {
                final Document document = new Document(directoryFile.getPath(), associatedSig);
                directoryFileList.add(document);
            }
        }
        return directoryFileList;
    }
}

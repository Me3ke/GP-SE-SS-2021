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
     *
     * @param associateSignatories a map which maps the paths to a list of signatories.
     * @param name                 the name of the envelope that is created in the method
     * @param owner                the owner of the envelope that will be created by this method
     * @return returns an envelop.
     */
    public Envelope convertPathsToDocuments(final Map<String, List<Signatory>> associateSignatories, final User owner,
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
     */
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
}

package gpse.example.domain;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The model for the envelop which stores the necessary documents in a list.
 * Implements Iterable to make searching a document easier.
 */
public class Envelop implements Iterable<Document> {

    /**
     * The documentList containing all the documents.
     * The name of the envelop.
     * The envelopFile which the envelop as a directory.
     */
    private List<Document> documentList;
    private String name;
    private File envelopFile;

    /**
     * Constructor which takes a given directory and creates an envelop
     * containing all of the files in the given directory.
     * @param path the path leading to the directory.
     * @throws IOException if the path was invalid.
     */
    public Envelop(final String path) throws IOException {
        documentList = new ArrayList<>();
        this.envelopFile = new File(path);
        this.name = envelopFile.getName();
        File[] filesArr = envelopFile.listFiles();
        List<File> files = new ArrayList<>();
        createEnvelop(filesArr, files);
    }

    /**
     * Constructor which creates an envelop containing all files in the
     * list and the name.
     * @param name The name of the envelop to be created.
     * @param paths A List of paths of all files in the envelop to be created.
     * @throws IOException if the path was invalid.
     */
    public Envelop(final String name, final List<String> paths) throws IOException {
        documentList = new ArrayList<>();
        this.name = name;
        List<File> files = new ArrayList<>();
        File[] filesArr = new File[paths.size()];
        for (int i = 0; i < paths.size(); i++) {
            filesArr[i] = new File(paths.get(i));
        }
        createEnvelop(filesArr, files);
    }

    /**
     * The createEnvelop method creates the envelop. It distinguishes between
     * directories and files and uses the listf method to put all files from all
     * directories together.
     * @param filesArr The array in which the files are stored.
     * @param files The list of files which is used as an intermediate for the recursion.
     * @throws IOException is thrown if a name of a path is invalid.
     */
    private void createEnvelop(final File[] filesArr, final List<File> files) throws IOException {
        for (File currentFile : filesArr) {
            if (currentFile.isDirectory()) {
                listf(currentFile, files);
            } else if (currentFile.isFile() && currentFile.exists()) {
                files.add(currentFile);
            }
        }
        for (File currentFile: files) {
            Document currentDocument = new Document(currentFile.getPath(), null);
            documentList.add(currentDocument);
        }
    }

    /**
     * The listf method is called if an envelop should be created from files
     * which include one or several directories. It uses recursion to add every
     * file in every subdirectory into the list.
     * @param directory the file which is found to be a directory.
     * @param files the list in which all files in subdirectories are added.
     */
    private void listf(final File directory, final List<File> files) {
        File[] fList = directory.listFiles();
        if (fList != null) {
            for (File file : fList) {
                if (file.isFile()) {
                    files.add(file);
                } else if (file.isDirectory()) {
                    listf(file, files);
                }
            }
        }
    }

    public void addDocument(final Document document) {
        documentList.add(document);
    }

    public void removeDocument(final Document document) {
        documentList.remove(document);
    }

    @Override
    public Iterator<Document> iterator() {
        return documentList.iterator();
    }

    public List<Document> getDocumentList() {
        return documentList;
    }

    public String getName() {
        return name;
    }

    public File getEnvelopFile() {
        return envelopFile;
    }
}

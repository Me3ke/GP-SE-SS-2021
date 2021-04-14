package gpse.example.domain;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The model for the envelop which stores the necessary documents in a list.
 * Implements Iterable to make searching a document easier.
 */
public class Envelop implements Iterable<Document> {

    /**
     * The documentList containing all the documents
     * The envelopPath which is the path to the envelop
     * The name of the envelop
     * The envelopFile which the envelop as a directory
     */
    private List<Document> documentList;
    private Path envelopPath;
    private String name;
    private File envelopFile;

    /**
     * Constructor which takes a given directory and creates an envelop
     * containing all of the files in the given directory.
     * @param path the path leading to the directory.
     * @throws IOException if the path was invalid.
     */
    public Envelop(String path) throws IOException {
        documentList = new ArrayList<>();
        this.envelopPath = Paths.get(path);
        this.envelopFile = new File(path);
        this.name = envelopFile.getName();
        File[] files = envelopFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            Document currentDocument = new Document(files[i].getPath());
            addDocument(currentDocument);
        }
    }

    /**
     * Constructor which creates an envelop containing all files in the
     * list and the name.
     * @param name The name of the envelop to be created.
     * @param paths A List of paths of all files in the envelop to be created.
     * @throws IOException if the path was invalid.
     */
    public Envelop(String name, List<String> paths) throws IOException {
        documentList = new ArrayList<>();
        this.name = name;
        for (int i = 0; i < paths.size(); i++) {
            Document currentDocument = new Document(paths.get(i));
            documentList.add(currentDocument);
        }
    }

    public void addDocument(Document document) {
        documentList.add(document);
    }

    public void removeDocument(Document document) {
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
}

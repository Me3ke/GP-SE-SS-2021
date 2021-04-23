package gpse.example.domain;

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
    private List<Document> documentList = new ArrayList<>();
    private String name;

    /**
     * Constructor which creates an envelop containing all files in the
     * list and the name.
     * @param name The name of the envelop to be created.
     * @param documents the list of documents for the envelop. At least one.
     */
    public Envelop(final String name, final List<Document> documents) {
        this.name = name;
        for (final Document document : documents) {
            this.documentList.add(document);
        }
    }

    public void addDocument(final Document document) {
        documentList.add(document);
    }

    public void removeDocument(final int index) {
        documentList.remove(index);
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

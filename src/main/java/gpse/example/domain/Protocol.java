package gpse.example.domain;

import com.sun.istack.NotNull;
import gpse.example.model.Signature;
import gpse.example.util.PDFConversionException;
import gpse.example.util.PDFWriter;
import gpse.example.util.XMLTransformationException;

import java.util.ArrayList;
import java.util.List;

/**
 * class modeling the protocol of an document
 */
public class Protocol {
    private List<Document> history;
    private final String id;
    private Document document;
    private DocumentMetaData documentMetaData;
    private List<Signature> signatures;
    private List<Signature> oldSignatures;
    private PDFWriter pdfWriter;

    /**
     * constructor of protocol getting information from specified document
     * @param doc  document that should be protocoled
     */
    public Protocol(@NotNull Document doc) {
        document = doc;
        signatures = new ArrayList<>();
        oldSignatures = new ArrayList<>();
        id = new HashSHA().computeHash(doc.getDocumentMetaData().getIdentifier());
        pdfWriter = new PDFWriter();



    }

    void addNewVersion(Document newDoc) {
        history.add(document);
        for (int i = 0; i < signatures.size(); i++) {
            oldSignatures.add(signatures.get(i));
            signatures.remove(i);
            i--;

        }
        this.document = newDoc;
    }

    void printPDF() {

        ArrayList<String> signatoryNames = new ArrayList<>();
        ArrayList<String> historyIDs = new ArrayList<>();

        for (int i = 0; i < signatures.size(); i++) {
            signatoryNames.add(signatures.get(i).getUser().getName());
        }

        for (int i = 0; i < history.size(); i++) {
            historyIDs.add(history.get(i).getDocumentMetaData().getIdentifier());
        }

        try {
            pdfWriter.generateXML(documentMetaData.getMetaUserID(), signatoryNames, historyIDs, this.id);
        } catch (XMLTransformationException xte) {
            System.out.println(xte.getMessage());
            return;
        }

        try {
            pdfWriter.convertToPDF(this.id);
        } catch (PDFConversionException pce) {
            System.out.println(pce.getMessage());
        }
    }


    /*void addSignature(Signature sig, Document doc) {
        if (doc.isCorrectSignature(sig)){
            signatures.add(sig);
        }
    }*/
}

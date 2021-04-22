package gpse.example.model;

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
    private final int id;
    private User owner;
    private Envelope currentEnvelope;
    private List<Signature> signatures;
    private List<Signature> oldSignatures;

    /**
     * constructor of protocol getting information from specified document
     * @param env  should be changed to document
     */
    public Protocol(Envelope env) {
        signatures = new ArrayList<>();
        oldSignatures = new ArrayList<>();
        owner = env.getOwner();
        currentEnvelope = env;
        id = generateID();
    }

    void addVersion(Document doc) {
        history.add(doc);
        for (int i = 0; i < signatures.size(); i++) {
            if (signatures.get(i).getUser().equals(doc.getUser())) {
                oldSignatures.add(signatures.get(i));
                signatures.remove(i);
            }
        }

    }

    void addVersion(Envelope env) {
        history.add(env.getdocuments());
        oldSignatures.addAll(signatures);
        signatures.clear();
    }

    void printPDF() {
        PDFWriter pdfWriter = new PDFWriter();
        ArrayList<String> signatoryNames = new ArrayList<>();
        ArrayList<String> historyIDs = new ArrayList<>();

        for (int i = 0; i < signatures.size(); i++) {
            signatoryNames.add(signatures.get(i).getUser().getName());
        }

        for (int i = 0; i < history.size(); i++) {
            historyIDs.add(history.get(i).getDocumentID());
        }

        try {
            pdfWriter.generateXML(owner.getName(), signatoryNames, historyIDs, this.id);
        } catch (XMLTransformationException xte) {
            System.out.println(xte.getMessage());
        }

        try {
            pdfWriter.convertToPDF(this.id);
        } catch (PDFConversionException pce) {
            System.out.println(pce.getMessage());
        }

        /* try {
            File protocol = new File(PATH + "/" + ID + ".txt");
            FileWriter myWriter = new FileWriter(PATH + "/" + ID + ".txt");
            if(protocol.createNewFile()){
                myWriter.write(writeProtocol());
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } */
    }

    /* String writeProtocol(){
        StringBuilder s = new StringBuilder();
        s.append("Document: " + ID + "\n");
        s.append("Owner: " + owner.getName() + "\n");
        s.append("Version: " + history.size() + "\n");
        s.append("Signatures:" + "\n");
        for(int i = 0; i < signatures.size(); i++){
            s.append(signatures.get(i).getUser().getName() + "\t" + signatures.get(i).getTimeStamp() + "\n");
        }
        s.append("\n");
        return s.toString();
    } */

    private int generateID() {
        return (int) (Math.random()*100);
    }

    /* public User getOwner() {
        return owner;
    }

    Document getCurrentEnv() {
        return history.get(history.size() - 1);
    } */

    void addSignature(Signature sig, Document doc) {
        if (doc.isCorrectSignature(sig)){
            signatures.add(sig);
        }
    }
}

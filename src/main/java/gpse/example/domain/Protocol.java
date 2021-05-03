package gpse.example.domain;

import com.sun.istack.NotNull;
import gpse.example.util.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * class modeling the protocol of an document
 * able to print a PDF protocol
 */
public class Protocol {
    String envName;
    Envelope envelope;
    private PDFWriter writer;

    /**
     * constructor of protocol
     * @param env  envelope that should be protocoled
     */
    public Protocol(@NotNull Envelope env) {
        envelope = env;
        envName = envelope.getName();
        writer = new PDFWriter();
    }


    public void printProtocol(){
        for(int i = 0; i < envelope.getDocumentList().size(); i++) {
            printDocumentProtocol(envelope.getDocumentList().get(i), i);
        }
    }

    void printDocumentProtocol(Document document, int num) {

        ArrayList<String> signatoryNames = new ArrayList<>();
        ArrayList<String> historyIDs = new ArrayList<>();

        for (int i = 0; i < document.getSignatories().size(); i++) {
            signatoryNames.add(document.getSignatories().get(i).getUser().getName());
        }

        for (int i = 0; i < history.size(); i++) {
            historyIDs.add(history.get(i).getDocumentMetaData().getIdentifier());
        }

        try {
            writer.printPDF(envName + "/" + num, document.getDocumentMetaData().getMetaUserID(),
                signatoryNames, historyIDs, document.getDocumentMetaData().getIdentifier());
        } catch (IOException ioe) {
            System.out.println("Something went wrong");
        }
    }
}

package gpse.example.domain;

import com.sun.istack.NotNull;
import gpse.example.util.*;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * class modeling the protocol of an document.
 * able to print a PDF protocol
 */
@Entity
public class Protocol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long protocolID;

    @OneToOne
    private Envelope envelope;

    private PDFWriter writer;

    /**
     * constructor of protocol.
     * @param env  envelope that should be protocoled
     */
    public Protocol(@NotNull final Envelope env) {
        envelope = env;
        envName = envelope.getName();
        writer = new PDFWriter();
    }

    /**
     * print protocol file for all documents in envelope.
     */
    public void printProtocol() {
        for (int i = 0; i < envelope.getDocumentList().size(); i++) {
            printDocumentProtocol(envelope.getDocumentList().get(i), i);
        }
    }

    private void printDocumentProtocol(final Document document, final int num) {

        final ArrayList<String> signatoryNames = new ArrayList<>();
        final ArrayList<String> historyIDs = new ArrayList<>();

        for (int i = 0; i < document.getSignatories().size(); i++) {
            signatoryNames.add(document.getSignatories().get(i).getUser().getName());
        }

        for (int i = 0; i < history.size(); i++) {
            historyIDs.add(history.get(i).getDocumentMetaData().getIdentifier());
        }

        try {
            writer.printPDF(protocolID + "." + num, document.getDocumentMetaData().getMetaUserID(),
                signatoryNames, historyIDs, document.getDocumentMetaData().getIdentifier());
        } catch (IOException ioe) {
            System.out.println("print protocol failed");
        }
    }

    public long getProtocolID() {
        return protocolID;
    }

    public void setProtocolID(long protocolID) {
        this.protocolID = protocolID;
    }

    public Envelope getEnvelope() {
        return envelope;
    }

    public void setEnvelope(Envelope envelope) {
        this.envelope = envelope;
    }
}

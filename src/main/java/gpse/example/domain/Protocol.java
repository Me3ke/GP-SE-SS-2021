package gpse.example.domain;

import com.sun.istack.NotNull;
import gpse.example.util.*;
import gpse.example.domain.documents.*;
import gpse.example.domain.envelopes.Envelope;


import javax.persistence.*;
import java.io.File;
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

    @Column
    private String pathProtocolDir;

    private transient PDFWriter writer;

    /**
     * constructor of protocol.
     * @param env  envelope that should be protocoled
     */
    public Protocol(@NotNull final Envelope env) {
        envelope = env;
        pathProtocolDir = "./src/main/resources/out/" + protocolID;
        writer = new PDFWriter();
    }

    public Protocol() {

    }

    /**
     * print protocol file for all documents in envelope.
     * stored in /resources/protocolID/.
     */
    public void printProtocol() {
        final File file = new File(pathProtocolDir);
        if ((!file.exists()) && (!file.mkdirs())) {
           System.out.println("Verzeichnis wurde nicht angelegt");
           return;
        }
        for (int i = 0; i < envelope.getDocumentList().size(); i++) {
            printDocumentProtocol(envelope.getDocumentList().get(i));
        }
    }

    private void printDocumentProtocol(final Document document) {

        final ArrayList<String> signatoryNames = new ArrayList<>();
        final ArrayList<String> historyIDs = new ArrayList<>();

        for (int i = 0; i < document.getSignatories().size(); i++) {
            signatoryNames.add(document.getSignatories().get(i).getUser().getLastname());
        }

        /*for (int i = 0; i < history.size(); i++) {
            historyIDs.add(history.get(i).getDocumentMetaData().getIdentifier());
        }*/

        try {
            writer.printPDF(protocolID, document.getDocumentMetaData().getMetaUserID(),
                signatoryNames, historyIDs, document.getDocumentMetaData().getIdentifier());
        } catch (IOException ioe) {
            System.out.println("print protocol failed");
        }
    }

    public long getProtocolID() {
        return protocolID;
    }

    public void setProtocolID(final long protocolID) {
        this.protocolID = protocolID;
    }

    public Envelope getEnvelope() {
        return envelope;
    }

    public void setEnvelope(final Envelope envelope) {
        this.envelope = envelope;
    }

    public String getPathProtocolDir() {
        return pathProtocolDir;
    }

    public void setPathProtocolDir(final String pathProtocolDir) {
        this.pathProtocolDir = pathProtocolDir;
    }
}

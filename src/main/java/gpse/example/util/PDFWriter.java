package gpse.example.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * PDFWriter writes protocol pdf files with specified data with printPDF(data ...).
 */
 public class PDFWriter {

    /**
     * Location of output file.
     */
    private static final String PROTOCOL_OUTPUT_PATH = "./src/main/resources/output/";

    /**
     * file type postfix.
     */
    private static final String PDF = ".pdf";

    /**
     * vertical position of first line.
     */
    private static final int TOP_OF_PAGE = 700;

    /**
     * distance between to regular lines.
     */
    private static final int LINE_DIST = 25;

    /**
     * Text margin to left side.
     */
    private static final int MARGIN_LEFT = 75;

    /**
     * standard font size.
     */
    private static final int FONT_SIZE = 12;

    /**
     * just a factor for multiply margin at the topic.
     */
    private static final int THREE = 3;

    /**
     * printing the protocol with specified user data.
     * @param protocolID id of Protocol class / protocolEnvelope
     * @param owner name of owner
     * @param signatures List with signature names
     * @param history List with old version document title
     * @param documentID id of the document getting protocoled
     * @throws IOException
     */
    public void printPDF(final long protocolID, final String owner, final List<String> signatures,
                         final List<String> history, final String documentID) throws IOException {

        int lineCount = TOP_OF_PAGE;
        File file;
        if (!(new File(PROTOCOL_OUTPUT_PATH + protocolID + "/" + documentID + PDF)).exists()) {
            createPDF(protocolID, documentID);
        }
        file = new File(PROTOCOL_OUTPUT_PATH + protocolID + "/" + documentID + PDF);


        try (PDDocument protocol = PDDocument.load(file);
             PDPageContentStream contentStream = new PDPageContentStream(protocol, protocol.getPage(0))) {
            // (x|y) = (0|0) bottom left; new line forbidden
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_BOLD, 2 * FONT_SIZE);
            contentStream.newLineAtOffset(THREE * MARGIN_LEFT, lineCount);
            contentStream.showText("Protokoll: " + documentID);
            contentStream.endText();

            lineCount -= 2 * LINE_DIST;
            addLine("Bezüglich Dokument " +  protocolID, lineCount, contentStream);

            lineCount -= LINE_DIST;
            addLine("Dokumenteneigentümer: " +  owner, lineCount, contentStream);

            lineCount -= LINE_DIST;
            addLine("Signiert von: ", lineCount, contentStream);

            for (final String signature : signatures) {
                lineCount -= LINE_DIST;
                addLine(signature, lineCount, contentStream);
            }

            lineCount = lineCount - LINE_DIST;
            addLine("Historie: ", lineCount, contentStream);

            for (final String docVersion : history) {
                lineCount = lineCount - LINE_DIST;
                addLine(docVersion, lineCount, contentStream);
            }
            protocol.save(file);
        }
    }

    /**
     * adding a line to pdf document at specified offset with specified value.
     * @param value value to add to pdf file
     * @param offSet line offset from 0 to 700 from bottom of page
     * @param contentStream Stream that knows the file to that is written
     * @throws IOException
     */
    private void addLine(final String value, final int offSet,
                         final PDPageContentStream contentStream) throws IOException {
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, FONT_SIZE);
        contentStream.newLineAtOffset(MARGIN_LEFT, offSet);
        contentStream.showText(value);
        contentStream.endText();
    }

    /**
     * creating new pdf file in case it does not exist.
     * @param protocolID Id of protocol that should be created
     * @throws IOException
     */
    private void createPDF(final long protocolID, final String documentID) throws IOException {

        try (PDDocument protocol = new PDDocument()) {
            final PDPage page = new PDPage();
            protocol.addPage(page);
            protocol.save(PROTOCOL_OUTPUT_PATH + protocolID + "/" + documentID + PDF);
        }
    }
}

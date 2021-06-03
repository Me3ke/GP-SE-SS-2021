package gpse.example.domain;

import com.sun.istack.NotNull;
import gpse.example.domain.signature.Signatory;
import gpse.example.domain.documents.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;


/**
 * class modeling the protocol of an document.
 * able to print a PDF protocol
 */
public class Protocol {

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

    private static final String PROTOCOL = "Protokoll: ";

    private static final int MAX_LINE_LENGTH = 36;

    private Document document;

    public Protocol(@NotNull final Document document) {
        this.document = document;
    }

    /**
     * printing the protocol with specified user data.
     * @return a stream which contains the written pdf protocol.
     * @throws IOException
     */
    public ByteArrayOutputStream writeProtocol() throws IOException {

        final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        int lineCount = TOP_OF_PAGE;
        final String title = document.getDocumentTitle();
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (PDDocument protocol = new PDDocument()) {
            final PDPage page = new PDPage();
            protocol.addPage(page);
            try (PDPageContentStream contentStream = new PDPageContentStream(protocol, protocol.getPage(0))) {
                // (x|y) = (0|0) bottom left; new line forbidden
                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_BOLD, 2 * FONT_SIZE);
                contentStream.newLineAtOffset(THREE * MARGIN_LEFT, lineCount);
                if ((PROTOCOL + title).length() <= MAX_LINE_LENGTH) {
                    contentStream.showText(PROTOCOL + title);
                } else {
                    contentStream.showText(PROTOCOL);
                    lineCount -= LINE_DIST;
                    contentStream.endText();
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.TIMES_BOLD, 2 * FONT_SIZE);
                    contentStream.newLineAtOffset(MARGIN_LEFT, lineCount);
                    contentStream.showText(title);
                }
                contentStream.endText();

                lineCount -= 2 * LINE_DIST;
                addLine("DokumentenID:  " + document.getId(), lineCount, contentStream);

                lineCount -= LINE_DIST;
                addLine("Dokumenteneigentümer: " + document.getOwner(), lineCount, contentStream);

                lineCount -= LINE_DIST;
                addLine("Signiert von: ", lineCount, contentStream);

                for (final Signatory signatory : document.getSignatories()) {
                    lineCount -= LINE_DIST;
                    if (signatory.isStatus()) {
                        addLine(signatory.getUser().getUsername() + " Am: "
                            + formatter.format(signatory.getSignedOn()), lineCount, contentStream);
                    } else {
                        addLine(signatory.getUser().getUsername() + " noch nicht signiert",
                            lineCount, contentStream);
                    }
                }

                lineCount = lineCount - LINE_DIST;
                addLine("Historie: ", lineCount, contentStream);

                for (final Document document : document.getHistory()) {
                    lineCount = lineCount - LINE_DIST;
                    addLine(document.getDocumentTitle(), lineCount, contentStream);
                }
            }
            protocol.save(output);
        }
        return output;
    }

    /**
     * adding a line to pdf document at specified offset with specified value.
     *
     * @param value         value to add to pdf file
     * @param offSet        line offset from 0 to 700 from bottom of page
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
}

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
import java.util.List;


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

    private static final int FONT_SIZE = 12;

    private static final String PROTOKOLL = "Protokoll: ";

    private static final int MAX_LINE_LENGTH = 36;
    private static final float SPACING_TWO_FIVE = 2.5f;
    private static final float SPACING_ONE_FIVE = 1.5f;
    private static final float SPACING_TWO = 2.0f;
    private static final String SIGNATURE_OF = "Signiert von: ";

    private final Document document;

    public Protocol(@NotNull final Document document) {
        this.document = document;
    }

    /**
     * printing the protocol with specified user data.
     * @return a stream which contains the written pdf protocol.
     * @throws IOException throws an IO-Exception if something goes wrong with the outputStream
     */
    public ByteArrayOutputStream writeProtocol() throws IOException {

        final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        //TODO make linecount method so we can check if its <= 0 and create new page if needed
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
                contentStream.newLineAtOffset(MARGIN_LEFT, lineCount);
                if ((PROTOKOLL + title).length() <= MAX_LINE_LENGTH) {
                    contentStream.showText(PROTOKOLL + title);
                } else {
                    contentStream.showText(PROTOKOLL);
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

                if (document.getEndDate() != null) {
                    lineCount -= LINE_DIST;
                    addLine("Offen bis: " + document.getEndDate(), lineCount, contentStream);
                }

                lineCount -= LINE_DIST;
                addLine(SIGNATURE_OF, lineCount, contentStream);

                for (final Signatory signatory : document.getSignatories()) {
                    lineCount -= LINE_DIST;
                    if (signatory.isStatus()) {
                        addIndentedLine(signatory.getEmail() + "    Am: "
                            + formatter.format(signatory.getSignedOn()), lineCount, SPACING_ONE_FIVE, contentStream);
                    } else {
                        addIndentedLine(signatory.getEmail() + "    noch nicht signiert",
                            lineCount, SPACING_ONE_FIVE, contentStream);
                    }
                }

                lineCount = lineCount - LINE_DIST;
                addLine("Historie: ", lineCount, contentStream);
                final List<Document> history = document.getHistory();
                history.remove(0);
                for (final Document document : history) {

                    lineCount = lineCount - LINE_DIST;
                    addIndentedLine(document.getDocumentTitle() + " vom "
                        + formatter.format(document.getDocumentMetaData().getMetaTimeStampUpload()),
                        lineCount, SPACING_ONE_FIVE, contentStream);

                    lineCount -= LINE_DIST;
                    addIndentedLine(SIGNATURE_OF, lineCount, SPACING_TWO, contentStream);

                    for (final Signatory signatory : document.getSignatories()) {
                        lineCount -= LINE_DIST;
                        if (signatory.isStatus()) {
                            addIndentedLine(signatory.getEmail() + "    (ungültig) ",
                                lineCount, SPACING_TWO_FIVE, contentStream);
                        } else {
                            addIndentedLine(signatory.getEmail() + "    nicht signiert",
                                lineCount, SPACING_TWO_FIVE, contentStream);
                        }
                    }


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
     * @throws IOException throws an IO-Exception if something goes wrong with the outputStream
     */
    private void addLine(final String value, final int offSet,
                         final PDPageContentStream contentStream) throws IOException {
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, FONT_SIZE);
        contentStream.newLineAtOffset(MARGIN_LEFT, offSet);
        contentStream.showText(value);
        contentStream.endText();
    }

    private void addIndentedLine(final String value, final int offSet, final float leftSpacing,
                                 final PDPageContentStream contentStream) throws IOException {
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, FONT_SIZE);
        contentStream.newLineAtOffset(MARGIN_LEFT * leftSpacing, offSet);
        contentStream.showText(value);
        contentStream.endText();
    }
}

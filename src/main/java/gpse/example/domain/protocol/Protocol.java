package gpse.example.domain.protocol;

import com.sun.istack.NotNull;
import gpse.example.domain.documents.Document;
import gpse.example.domain.signature.AdvancedSignature;
import gpse.example.domain.signature.Signatory;
import gpse.example.domain.users.UserService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

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
     * Text margin to left side.
     */
    private static final int MARGIN_LEFT = 75;

    private static final int FONT_SIZE = 12;

    /**
     * Dimensions for the image signatures.
     */
    private static final int IMAGE_WIDTH = 80;

    private static final int IMAGE_HEIGHT = 30;


    private static final String PROTOKOLL = "Protokoll: ";

    private static final int MAX_LINE_LENGTH = 36;
    private static final float SPACING_TWO_FIVE = 2.5f;
    private static final float SPACING_ONE_FIVE = 1.5f;
    private static final float SPACING_TWO = 2.0f;
    private static final String SIGNATURE_OF = "Signiert von: ";
    private static final int LINE_LENGTH = 50;
    private static final int MARGIN_BOTTOM = 100;

    private final Document document;

    private int pageCount;

    public Protocol(@NotNull final Document document) {
        this.document = document;
    }

    /**
     * printing the protocol with specified user data.
     *
     * @param userService the userService that is used to get the userObjects that signatories refer to.
     * @return a stream which contains the written pdf protocol.
     * @throws IOException throws an IO-Exception if something goes wrong with the outputStream
     */
    public ByteArrayOutputStream writeProtocol(final UserService userService) throws IOException {

        final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        //TODO make linecount method so we can check if its <= 0 and create new page if needed
        final LineCounter lineCounter = new LineCounter();
        final String title = document.getDocumentTitle();
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        pageCount = 0;
        try (PDDocument protocol = new PDDocument()) {
            final PDPage page = new PDPage();
            protocol.addPage(page);
            try (PDPageContentStream contentStream = new PDPageContentStream(protocol, protocol.getPage(pageCount))) {
                // (x|y) = (0|0) bottom left; new line forbidden
                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_BOLD, 2 * FONT_SIZE);
                contentStream.newLineAtOffset(MARGIN_LEFT, lineCounter.getCount());
                if ((PROTOKOLL + title).length() <= MAX_LINE_LENGTH) {
                    contentStream.showText(PROTOKOLL + title);
                } else {
                    contentStream.showText(PROTOKOLL);
                    lineCounter.addLines(1);
                    contentStream.endText();
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.TIMES_BOLD, 2 * FONT_SIZE);
                    contentStream.newLineAtOffset(MARGIN_LEFT, lineCounter.getCount());
                    contentStream.showText(title);
                }
                contentStream.endText();

                lineCounter.addLines(2);
                addLine("DokumentenID:  " + document.getId(), lineCounter.getCount(), contentStream);

                lineCounter.addLines(1);
                addLine("Dokumenteneigentümer: " + document.getOwner(), lineCounter.getCount(), contentStream);

                if (document.getEndDate() != null) {
                    lineCounter.addLines(1);
                    addLine("Offen bis: " + document.getEndDate(), lineCounter.getCount(), contentStream);
                }
            }

            try (PDPageContentStream contentStream = newPageIfNeeded(lineCounter, protocol)) {
                lineCounter.addLines(1);
                addLine(SIGNATURE_OF, lineCounter.getCount(), contentStream);
            }
                for (final Signatory signatory : document.getSignatoryManagement().getSignatories()) {
                    try (PDPageContentStream contentStream = newPageIfNeeded(lineCounter, protocol)) {
                        lineCounter.addLines(1);
                        if (signatory.isStatus()) {
                            addIndentedLine(signatory.getEmail() + "    Am: "
                                    + formatter.format(signatory.getSignedOn()),
                                lineCounter.getCount(), SPACING_ONE_FIVE, contentStream);
                            if (userService.getUser(signatory.getEmail()).getImageSignature().length != 0) {
                                lineCounter.addLines(2);
                                addImageLine(userService.getUser(signatory.getEmail()).getImageSignature(), protocol,
                                    lineCounter.getCount(), contentStream);
                            }
                        } else {
                            addIndentedLine(signatory.getEmail() + "    noch nicht signiert",
                                lineCounter.getCount(), SPACING_ONE_FIVE, contentStream);
                        }
                    }
                }

            try (PDPageContentStream contentStream = newPageIfNeeded(lineCounter, protocol)) {
                lineCounter.addLines(1);
                addLine("Historie: ", lineCounter.getCount(), contentStream);
            }
                final List<Document> history = document.getHistory();
                history.remove(0);
                for (final Document document : history) {
                    try (PDPageContentStream contentStream = newPageIfNeeded(lineCounter, protocol)) {
                        lineCounter.addLines(1);
                        addIndentedLine(document.getDocumentTitle() + " vom "
                                + formatter.format(document.getDocumentMetaData().getMetaTimeStampUpload()),
                            lineCounter.getCount(), SPACING_ONE_FIVE, contentStream);

                        lineCounter.addLines(1);
                        addIndentedLine(SIGNATURE_OF, lineCounter.getCount(), SPACING_TWO, contentStream);
                    }
                    for (final Signatory signatory : document.getSignatoryManagement().getSignatories()) {
                        try (PDPageContentStream contentStream = newPageIfNeeded(lineCounter, protocol)) {
                            lineCounter.addLines(1);
                            if (signatory.isStatus()) {
                                addIndentedLine(signatory.getEmail() + "    (ungültig) ",
                                    lineCounter.getCount(), SPACING_TWO_FIVE, contentStream);
                            } else {
                                addIndentedLine(signatory.getEmail() + "    nicht signiert",
                                    lineCounter.getCount(), SPACING_TWO_FIVE, contentStream);
                            }
                        }
                    }

                }

            try (PDPageContentStream contentStream = newPageIfNeeded(lineCounter, protocol)) {
                lineCounter.addLines(1);
                addLine("Signatur-Hashes für erweiterte Signaturen", lineCounter.getCount(), contentStream);
            }
            for (final AdvancedSignature signature : document.getAdvancedSignatures()) {
                try (PDPageContentStream contentStream = newPageIfNeeded(lineCounter, protocol)) {
                    lineCounter.addLines(1);
                    addIndentedLine(signature.getUserEmail(), lineCounter.getCount(), SPACING_ONE_FIVE, contentStream);
                }
                final String hash = new String(signature.getSignature());
                final String[] hashLines = getSubStrings(hash);
                for (final String hashline : hashLines) {
                    try (PDPageContentStream contentStream = newPageIfNeeded(lineCounter, protocol)) {
                        lineCounter.addLines(1);
                        addIndentedLine(hashline, lineCounter.getCount(), SPACING_TWO, contentStream);
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

    private void addImageLine(final byte[] image, final PDDocument document, final int offSet,
                              final PDPageContentStream contentStream) throws IOException {
        final PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, image, null);
        contentStream.drawImage(pdImage, MARGIN_LEFT * 2, offSet, IMAGE_WIDTH, IMAGE_HEIGHT);
    }

    private PDPageContentStream newPageIfNeeded(final LineCounter lineCounter,
                                                final PDDocument protocol) throws IOException {
        if (lineCounter.getCount() <= MARGIN_BOTTOM) {
            final PDPage newPage = new PDPage();
            protocol.addPage(newPage);
            pageCount++;
            lineCounter.setCount(TOP_OF_PAGE);
        }
        return new PDPageContentStream(protocol, protocol.getPage(pageCount), PDPageContentStream.AppendMode.APPEND,
            false);
    }

    private String[] getSubStrings(final String string) {
        String[] subStrings = new String[string.length() / LINE_LENGTH + 1];
        if (string.length() > LINE_LENGTH) {
            for (int i = 0; i < subStrings.length - 1; i++) {
                subStrings[i] = string.substring(i * LINE_LENGTH, (i + 1) * LINE_LENGTH);
            }
            subStrings[subStrings.length - 1] = string.substring((subStrings.length - 1) * LINE_LENGTH);
        } else {
            subStrings[0] = string;
        }
        return subStrings;
    }
}

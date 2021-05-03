package gpse.example.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PDFWriter{

    public void printPDF(String docTitle, String owner, List<String> signatures, List<String> history, String protocolID)
        throws IOException{

        int lineCount = 700;
        File file;
        if (!(new File("./src/main/resources/output/Protocol_" + protocolID + ".pdf")).exists()) {
            createPDF(protocolID);
        }
        file = new File("./src/main/resources/output/Protocol_" + protocolID + ".pdf");

        PDDocument protocol = PDDocument.load(file);
        PDPageContentStream contentStream = new PDPageContentStream(protocol, protocol.getPage(0));
        try {
            // (x|y) = (0|0) bottom left; new line forbidden
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_BOLD, 20);
            contentStream.newLineAtOffset(250, lineCount);
            contentStream.showText( "Protokoll: " + protocolID);
            contentStream.endText();

            lineCount = lineCount - 50;
            addLine("Bezüglich Dokument " +  docTitle, lineCount, contentStream);

            lineCount = lineCount - 25;
            addLine("Dokumenteneigentümer: " +  owner, lineCount, contentStream);

            lineCount = lineCount - 25;
            addLine("Signiert von: ", lineCount, contentStream);

            for(int i = 0; i< signatures.size(); i++){
                lineCount = lineCount - 25;
                addLine(signatures.get(i), lineCount, contentStream);
            }

            lineCount = lineCount - 25;
            addLine("Historie: ", lineCount, contentStream);

            for(int i = 0; i< history.size(); i++){
                lineCount = lineCount - 25;
                addLine(history.get(i), lineCount, contentStream);
            }
        } finally {
        contentStream.close();
        protocol.save(file);
        protocol.close();
        }

    }

    void addLine(String value, int offSet, PDPageContentStream contentStream) throws IOException {
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        contentStream.newLineAtOffset(75, offSet);
        contentStream.showText(value);
        contentStream.endText();
    }

    private void createPDF(String protocolID) throws IOException {
        PDDocument protocol = new PDDocument();
        try {
            PDPage page1 = new PDPage();
            protocol.addPage(page1);
            protocol.save("./src/main/resources/output/Protocol_" + protocolID + ".pdf");

        } finally {
            protocol.close();
        }
    }
}

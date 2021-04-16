package gpse.example.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

class DocumentTest {

    private Path documentPath;
    private File documentFile;
    private int documentID;
    private String documentTitle;
    private String documentType;

    @Test
    public void testDocument() {
        try {
            Document document = new Document("src/main/resources/Manf.pdf");
        } catch(IOException e) {
            System.out.println("Document Path is invalid");
        }
    }

    @Test
    public void testDocumentID() {
        Document document = null;
        try {
            document = new Document("src/main/resources/Manf.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertThat(document.getDocumentID()).isEqualTo(1);
    }

    @Test
    public void testDocumentTitle() {
        Document document = null;
        try {
            document = new Document("src/main/resources/Manf.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertThat(document.getDocumentTitle()).isEqualTo("Manf");
    }

    @Test
    public void testDocumentType() {
        Document document = null;
        try {
            document = new Document("src/main/resources/Manf.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertThat(document.getDocumentType()).isEqualTo("pdf");
    }
}

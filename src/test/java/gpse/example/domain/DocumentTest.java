package gpse.example.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class DocumentTest {

    @Test
    public void testDocument() {
        try {
            Document document = new Document("src/main/resources/Manf.pdf");
            Assertions.assertThat(document.getDocumentMetaData().getDocumentID()).isEqualTo(1);
            Assertions.assertThat(document.getDocumentMetaData().getMetaUserID()).isEqualTo("01");
            Assertions.assertThat(document.getDocumentMetaData().getDocumentType()).isEqualTo("pdf");
            Assertions.assertThat(document.getDocumentMetaData().getMetaDocumentTitle()).isEqualTo("Manf");
            Assertions.assertThat(document.getDocumentMetaData().getCreationDate()).isEqualTo("14.04.2021 16:28:16");
            Assertions.assertThat(document.getDocumentMetaData().getLastAccess()).isEqualTo("14.04.2021 16:28:18");
            Assertions.assertThat(document.getDocumentMetaData().getLastModified()).isEqualTo("14.04.2021 16:28:18");
            Assertions.assertThat(document.getDocumentMetaData().getSize()).isEqualTo(28207);
        } catch(IOException e) {
            System.out.println("Document Path is invalid");
        }
    }
}

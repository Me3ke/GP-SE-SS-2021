package gpse.example.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class EnvelopTest {

    @Test
    public void testDocumentList() {
        try {
            Envelop envelop = new Envelop("src/main/resources");
            List<Document> documentList = new ArrayList<>();
            documentList.add(new Document("src/main/resources/application.properties"));
            documentList.add(new Document("src/main/resources/Manf.pdf"));
            documentList.add(new Document("src/main/resources/Test/Goin.docx"));
            documentList.add(new Document("src/main/resources/Theben.txt"));
            for (int i = 0; i < documentList.size(); i++) {
                Assertions.assertThat(envelop.getDocumentList().get(i).getDocumentTitle()).isEqualTo(documentList.get(i).getDocumentTitle());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSameDocument() {
        List<String> documentList = new ArrayList<>();
        documentList.add("src/main/resources/Manf.pdf");
        documentList.add("src/main/resources/Manf.pdf");
        try {
            Envelop envelop = new Envelop("theben", documentList );
            Assertions.assertThat(envelop.getDocumentList().get(0).getDocumentMetaData().getIdentifier()).isEqualTo(envelop.getDocumentList().get(1).getDocumentMetaData().getIdentifier());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

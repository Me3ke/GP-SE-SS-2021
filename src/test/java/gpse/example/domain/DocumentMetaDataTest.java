package gpse.example.domain;

import gpse.example.domain.documents.Document;
import gpse.example.domain.documents.DocumentMetaData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.LocalDateTime;

class DocumentMetaDataTest {
    @Test
    public void testSameMetaData() {
        FileTime time = FileTime.from(Instant.now());
        long size = 729810;
        DocumentMetaData documentMetaData1 = new DocumentMetaData(
                LocalDateTime.now(), "HelloWorld", time, time , time, size, "1");
        Assertions.assertTrue(documentMetaData1.equalsTo(documentMetaData1));
    }
    @Test
    public void testDifferentDates() {
        LocalDateTime date1 = LocalDateTime.now();
        FileTime time = FileTime.from(Instant.now());
        long size = 729810;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Assertions.fail();
        }
        LocalDateTime date2 = LocalDateTime.now();
        DocumentMetaData documentMetaData1 = new DocumentMetaData(
                date1, "HelloWorld", time, time , time, size, "1");
        DocumentMetaData documentMetaData2 = new DocumentMetaData(
                date2, "HelloWorld", time, time , time, size, "1");
        Assertions.assertFalse(documentMetaData1.equalsTo(documentMetaData2));
    }
    @Test
    public void testDifferentIDs() {
        LocalDateTime date = LocalDateTime.now();
        FileTime time = FileTime.from(Instant.now());
        long size = 729810;
        DocumentMetaData documentMetaData1 = new DocumentMetaData(
                date, "HelloWorld", time, time , time, size, "1");
        DocumentMetaData documentMetaData2 = new DocumentMetaData(
                date, "HelloWorld", time, time , time, size, "2");
        Assertions.assertFalse(documentMetaData1.equalsTo(documentMetaData2));
    }
    @Test
    public void testDifferentTitle() {
        LocalDateTime date = LocalDateTime.now();
        FileTime time = FileTime.from(Instant.now());
        long size = 729810;
        DocumentMetaData documentMetaData1 = new DocumentMetaData(
                date, "HelloWorld", time, time , time, size, "1");
        DocumentMetaData documentMetaData2 = new DocumentMetaData(
                date, "GoodByeWorld", time, time , time, size,"1");
        Assertions.assertFalse(documentMetaData1.equalsTo(documentMetaData2));
    }

    @Test
    public void testSize() {
        Document document = null;
        String owner = "someID";
        try {
            document = new Document("src/main/resources/Manf.pdf", null, owner, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(document.getDocumentMetaData().getSize(), 28207);
    }

    /*
    @Test
    public void testCreationDate() {
        Document document = null;
        try {
            document = new Document("src/main/resources/Manf.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(document.getDocumentMetaData().getLastModified(), "16.04.2021 19:46:37");
    }

    @Test
    public void testLastAccess() {
        Document document = null;
        try {
            document = new Document("src/main/resources/Manf.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(document.getDocumentMetaData().getLastAccess(), "16.04.2021 19:46:43");
    }

    @Test
    public void testLastModified() {
        Document document = null;
        try {
            document = new Document("src/main/resources/Manf.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(document.getDocumentMetaData().getLastModified(), "16.04.2021 19:46:37");
    }
    */
}

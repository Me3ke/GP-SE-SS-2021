package gpse.example.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

class DocumentMetaDataTest {
    @Test
    public void testSameMetaData() {
        Date date = new Date();
        long millis = date.getTime();
        DocumentMetaData documentMetaData1 = new DocumentMetaData("1",
                new Timestamp(millis), "HelloWorld");
        Assertions.assertTrue(documentMetaData1.equalsTo(documentMetaData1));
    }
    @Test
    public void testDifferentDates() {
        Date date1 = new Date();
        long millis1 = date1.getTime();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Assertions.fail();
        }
        Date date2 = new Date();
        long millis2 = date2.getTime();
        DocumentMetaData documentMetaData1 = new DocumentMetaData("1",
                new Timestamp(millis1), "HelloWorld");
        DocumentMetaData documentMetaData2 = new DocumentMetaData("1",
                new Timestamp(millis2), "HelloWorld");
        Assertions.assertFalse(documentMetaData1.equalsTo(documentMetaData2));
    }
    @Test
    public void testDifferentIDs() {
        Date date = new Date();
        long millis = date.getTime();
        DocumentMetaData documentMetaData1 = new DocumentMetaData("1",
                new Timestamp(millis), "HelloWorld");
        DocumentMetaData documentMetaData2 = new DocumentMetaData("2",
                new Timestamp(millis), "HelloWorld");
        Assertions.assertFalse(documentMetaData1.equalsTo(documentMetaData2));
    }
    @Test
    public void testDifferentTitle() {
        Date date = new Date();
        long millis = date.getTime();
        DocumentMetaData documentMetaData1 = new DocumentMetaData("1",
                new Timestamp(millis), "HelloWorld");
        DocumentMetaData documentMetaData2 = new DocumentMetaData("1",
                new Timestamp(millis), "GoodByeWorld");
        Assertions.assertFalse(documentMetaData1.equalsTo(documentMetaData2));
    }

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

    @Test
    public void testSize() {
        Document document = null;
        try {
            document = new Document("src/main/resources/Manf.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(document.getDocumentMetaData().getSize(), 28207);
    }
}

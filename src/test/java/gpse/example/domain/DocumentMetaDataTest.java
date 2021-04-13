package gpse.example.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
}
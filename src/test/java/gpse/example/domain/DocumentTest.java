package gpse.example.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DocumentTest {

    private final static String mail = "superCooleMail@yoohooo.de";

    @Nested
    public class UnsignedSignatory {

        @Test
        public void addIsNotInUnsigned() {
            final Document document = new Document();
            document.addUnsignedSignatory(mail);
            assertTrue(document.getUnsignedSignatories().contains(mail));
        }

        @Test
        public void addIsInUnsigned() {
            final Document document = new Document();

            document.addUnsignedSignatory(mail);
            document.addUnsignedSignatory(mail);
            final ArrayList<String> expected = new ArrayList<>();
            expected.add(mail);
            assertEquals(document.getUnsignedSignatories(), expected);
        }

        @Test
        public void addIsInSigned() {
            final Document document = new Document();
            document.addSignedSignatory(mail);
            document.addUnsignedSignatory(mail);
            assertTrue(document.getUnsignedSignatories().contains(mail));

        }


        @Test
        public void delIsInUnsigned() {
            final Document document = new Document();
            document.addUnsignedSignatory(mail);
            document.deleteUnsignedSignatory(mail);
            assertTrue(document.getUnsignedSignatories().isEmpty());
        }

        @Test
        public void delIsNotInUnsigned(){
            final Document document = new Document();
            document.deleteUnsignedSignatory(mail);
            assertTrue(document.getUnsignedSignatories().isEmpty());
        }
    }


    @Nested
    public class SignedSignatory {

        @Test
        public void addIsNotInSignedIsInUnsigned() {
            final Document document = new Document();
            document.addUnsignedSignatory(mail);
            document.addSignedSignatory(mail);
            assertTrue(document.getSignedSignatories().contains(mail) && document.getUnsignedSignatories().isEmpty());
        }

        @Test
        public void addIsInSigned() {
            final Document document = new Document();
            document.addUnsignedSignatory(mail);
            document.addSignedSignatory(mail);
            document.addSignedSignatory(mail);
            final ArrayList<String> expected = new ArrayList<>();
            expected.add(mail);
            assertEquals(document.getSignedSignatories(), expected);
        }

        @Test
        public void addIsNotInSignedIsNotInUnsigned() {
            final Document document = new Document();
            document.addSignedSignatory(mail);
            assertTrue(document.getUnsignedSignatories().isEmpty());

        }

        @Test
        public void addIsInSignedIsNotInUnsigned() {
            final Document document = new Document();
            document.addSignedSignatory(mail);
            document.addSignedSignatory(mail);
            assertTrue(document.getUnsignedSignatories().isEmpty());
        }

        @Test
        public void delIsInSigned() {
            final Document document = new Document();
            document.addUnsignedSignatory(mail);
            document.addSignedSignatory(mail);
            document.deleteSignedSignatory(mail);
            assertTrue(document.getUnsignedSignatories().isEmpty());
        }

        @Test
        public void delIsNotInSigned() {
            final Document document = new Document();
            document.deleteSignedSignatory(mail);
            assertTrue(document.getSignedSignatories().isEmpty());
        }
    }
}

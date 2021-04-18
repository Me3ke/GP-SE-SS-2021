package gpse.example.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
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
        public void delIsNotInUnsigned() {
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

    @Nested
    public class AdvancedSignature {

        // Cannot be tested yet, because DocumentMEtaData needs a Timestamp, which cannot be generated because it has been deprecated
        /*
        @Test
        public void userIsSignatory() {
            final DocumentMetaData documentMetaData = new DocumentMetaData();
            final Document document = new Document(documentMetaData);
            final User user = new User("HansSchnider1@bla.com", "Hs", "Sneier", "15");
            user.newKeypair();
            document.addUnsignedSignatory(user.getEmail());
            document.advancedSignature(user);
            assertTrue(document.getUnsignedSignatories().isEmpty()
                && document.getSignedSignatories().contains(user.getEmail())
                && document.getAdvancedSignatures().containsKey(user.getEmail()));
        }*/

        @Test
        public void userIsNotSignatory() {
            final Document document = new Document();
            final User user = new User("ansSchnider1@bla.com", "s", "Snier", "5");
            user.newKeypair();
            document.advancedSignature(user);
            assertTrue(document.getSignedSignatories().isEmpty()
                && document.getAdvancedSignatures().isEmpty());
        }

        // To be added when signatures can be made and Timestamp does not cause problems anymore
        /*
        @Test
        public void verifySignature
         */
    }
}

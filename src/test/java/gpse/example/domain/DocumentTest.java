package gpse.example.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DocumentTest {

    private final static String MAIL = "superCooleMAIL@yoohooo.de";
    private final static String PATH_INVALID = "Document Path is invalid";
    private final static String PATH = "src/main/resources/Manf.pdf";
    private final static String TITLE_TEST = "Manf";
    private final static String TYPE_TEST = "pdf";
    private final static int ID_TEST = 1;

    @Nested
    public class DocumentData {
        /*


        @Test
        public void testDocumentTitle() {
            Document document = null;
            try {
                document = new Document(PATH, null);
            } catch (IOException e) {
                System.out.println(PATH_INVALID);
            }
            Assertions.assertThat(document.getDocumentTitle()).isEqualTo(TITLE_TEST);
        }

        @Test
        public void testDocumentType() {
            Document document = null;
            try {
                document = new Document(PATH, null);
            } catch (IOException e) {
                System.out.println(PATH_INVALID);
            }
            Assertions.assertThat(document.getDocumentType()).isEqualTo(TYPE_TEST);
        }
    }

    @Nested
    public class UnsignedSignatory {

        @Test
        public void addIsNotInUnsigned() {
            final Document document = new Document();
            document.addUnsignedSignatory(MAIL);
            assertTrue(document.getUnsignedSignatories().contains(MAIL));
        }

        @Test
        public void addIsInUnsigned() {
            final Document document = new Document();

            document.addUnsignedSignatory(MAIL);
            document.addUnsignedSignatory(MAIL);
            final ArrayList<String> expected = new ArrayList<>();
            expected.add(MAIL);
            assertEquals(document.getUnsignedSignatories(), expected);
        }

        @Test
        public void addIsInSigned() {
            final Document document = new Document();
            document.addSignedSignatory(MAIL);
            document.addUnsignedSignatory(MAIL);
            assertTrue(document.getUnsignedSignatories().contains(MAIL));

        }


        @Test
        public void delIsInUnsigned() {
            final Document document = new Document();
            document.addUnsignedSignatory(MAIL);
            document.deleteUnsignedSignatory(MAIL);
            assertTrue(document.getUnsignedSignatories().isEmpty());
        }

        @Test
        public void delIsNotInUnsigned(){
            final Document document = new Document();
            document.deleteUnsignedSignatory(MAIL);
            assertTrue(document.getUnsignedSignatories().isEmpty());
        }
    }


    @Nested
    public class SignedSignatory {

        @Test
        public void addIsNotInSignedIsInUnsigned() {
            final Document document = new Document();
            document.addUnsignedSignatory(MAIL);
            document.addSignedSignatory(MAIL);
            assertTrue(document.getSignedSignatories().contains(MAIL) && document.getUnsignedSignatories().isEmpty());
        }

        @Test
        public void addIsInSigned() {
            final Document document = new Document();
            document.addUnsignedSignatory(MAIL);
            document.addSignedSignatory(MAIL);
            document.addSignedSignatory(MAIL);
            final ArrayList<String> expected = new ArrayList<>();
            expected.add(MAIL);
            assertEquals(document.getSignedSignatories(), expected);
        }

        @Test
        public void addIsNotInSignedIsNotInUnsigned() {
            final Document document = new Document();
            document.addSignedSignatory(MAIL);
            assertTrue(document.getUnsignedSignatories().isEmpty());

        }

        @Test
        public void addIsInSignedIsNotInUnsigned() {
            final Document document = new Document();
            document.addSignedSignatory(MAIL);
            document.addSignedSignatory(MAIL);
            assertTrue(document.getUnsignedSignatories().isEmpty());
        }

        @Test
        public void delIsInSigned() {
            final Document document = new Document();
            document.addUnsignedSignatory(MAIL);
            document.addSignedSignatory(MAIL);
            document.deleteSignedSignatory(MAIL);
            assertTrue(document.getUnsignedSignatories().isEmpty());
        }

        @Test
        public void delIsNotInSigned() {
            final Document document = new Document();
            document.deleteSignedSignatory(MAIL);
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
            document.addUnsignedSignatory(user.getEMAIL());
            document.advancedSignature(user);
            assertTrue(document.getUnsignedSignatories().isEmpty()
                && document.getSignedSignatories().contains(user.getEMAIL())
                && document.getAdvancedSignatures().containsKey(user.getEMAIL()));
        }*/

        /*
        @Test
        public void userIsNotSignatory() {
            final Document document = new Document();
            final User user = new User("ansSchnider1@bla.com", "s", "Snier", "5");
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

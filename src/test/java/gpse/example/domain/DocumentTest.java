package gpse.example.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DocumentTest {

    private final static String MAIL = "superCooleMail@yoohooo.de";
    private final static String PATH_INVALID = "Document Path is invalid";
    private final static String PATH = "src/main/resources/Manf.pdf";
    private final static String TITLE_TEST = "Manf";
    private final static String TYPE_TEST = "pdf";
    private final static int ID_TEST = 1;

    @Nested
    public class DocumentData {

        @Test
        public void testDocumentID() {
            Document document = null;
            try {
                document = new Document(PATH, null);
            } catch (IOException e) {
                System.out.println();
            }
            Assertions.assertThat(document.getDocumentID()).isEqualTo(ID_TEST);
        }

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
            Document document = null;
            try {
                document = new Document(PATH, null);
            } catch (IOException e) {
                System.out.println(PATH_INVALID);
            }
            document.addUnsignedSignatory(MAIL);
            assertTrue(document.getUnsignedSignatories().contains(MAIL));
        }

        @Test
        public void addIsInUnsigned() {
            Document document = null;
            try {
                document = new Document(PATH, null);
            } catch (IOException e) {
                System.out.println(PATH_INVALID);
            }
            document.addUnsignedSignatory(MAIL);
            document.addUnsignedSignatory(MAIL);
            final ArrayList<String> expected = new ArrayList<>();
            expected.add(MAIL);
            assertEquals(document.getUnsignedSignatories(), expected);
        }

        @Test
        public void addIsInSigned() {
            Document document = null;
            try {
                document = new Document(PATH, null);
            } catch (IOException e) {
                System.out.println(PATH_INVALID);
            }
            document.addSignedSignatory(MAIL);
            document.addUnsignedSignatory(MAIL);
            assertTrue(document.getUnsignedSignatories().contains(MAIL));

        }


        @Test
        public void delIsInUnsigned() {
            Document document = null;
            try {
                document = new Document(PATH, null);
            } catch (IOException e) {
                System.out.println(PATH_INVALID);
            }
            document.addUnsignedSignatory(MAIL);
            document.deleteUnsignedSignatory(MAIL);
            assertTrue(document.getUnsignedSignatories().isEmpty());
        }

        @Test
        public void delIsNotInUnsigned(){
            Document document = null;
            try {
                document = new Document(PATH, null);
            } catch (IOException e) {
                System.out.println(PATH_INVALID);
            }
            document.deleteUnsignedSignatory(MAIL);
            assertTrue(document.getUnsignedSignatories().isEmpty());
        }
    }


    @Nested
    public class SignedSignatory {

        @Test
        public void addIsNotInSignedIsInUnsigned() {
            Document document = null;
            try {
                document = new Document(PATH, null);
            } catch (IOException e) {
                System.out.println(PATH_INVALID);
            }
            document.addUnsignedSignatory(MAIL);
            document.addSignedSignatory(MAIL);
            assertTrue(document.getSignedSignatories().contains(MAIL) && document.getUnsignedSignatories().isEmpty());
        }

        @Test
        public void addIsInSigned() {
            Document document = null;
            try {
                document = new Document(PATH, null);
            } catch (IOException e) {
                System.out.println(PATH_INVALID);
            }
            document.addUnsignedSignatory(MAIL);
            document.addSignedSignatory(MAIL);
            document.addSignedSignatory(MAIL);
            final ArrayList<String> expected = new ArrayList<>();
            expected.add(MAIL);
            assertEquals(document.getSignedSignatories(), expected);
        }

        @Test
        public void addIsNotInSignedIsNotInUnsigned() {
            Document document = null;
            try {
                document = new Document(PATH, null);
            } catch (IOException e) {
                System.out.println(PATH_INVALID);
            }
            document.addSignedSignatory(MAIL);
            assertTrue(document.getUnsignedSignatories().isEmpty());

        }

        @Test
        public void addIsInSignedIsNotInUnsigned() {
            Document document = null;
            try {
                document = new Document(PATH, null);
            } catch (IOException e) {
                System.out.println(PATH_INVALID);
            }
            document.addSignedSignatory(MAIL);
            document.addSignedSignatory(MAIL);
            assertTrue(document.getUnsignedSignatories().isEmpty());
        }

        @Test
        public void delIsInSigned() {
            Document document = null;
            try {
                document = new Document(PATH, null);
            } catch (IOException e) {
                System.out.println(PATH_INVALID);
            }
            document.addUnsignedSignatory(MAIL);
            document.addSignedSignatory(MAIL);
            document.deleteSignedSignatory(MAIL);
            assertTrue(document.getUnsignedSignatories().isEmpty());
        }

        @Test
        public void delIsNotInSigned() {
            Document document = null;
            try {
                document = new Document(PATH, null);
            } catch (IOException e) {
                System.out.println(PATH_INVALID);
            }
            document.deleteSignedSignatory(MAIL);
            assertTrue(document.getSignedSignatories().isEmpty());
        }
    }
}

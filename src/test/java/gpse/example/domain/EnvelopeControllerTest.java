package gpse.example.domain;

import gpse.example.domain.documents.Document;
import gpse.example.domain.documents.DocumentCreator;
import gpse.example.domain.documents.DocumentPutRequest;
import gpse.example.domain.envelopes.Envelope;
import gpse.example.domain.envelopes.EnvelopeController;
import gpse.example.domain.envelopes.EnvelopeGetRequest;
import gpse.example.domain.exceptions.CreatingFileException;
import gpse.example.domain.users.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnvelopeControllerTest {
    /*

    private UserServiceImpl userService;
    private EnvelopeServiceImpl envelopeService;

    @Test
    public void testCreateEnvelope() {
        EnvelopeController envelopeController = new EnvelopeController(envelopeService, userService);
        try {
            Envelope envelope = envelopeController.createEnvelope("peter.müller@mail.com", "Theben");
            long id = envelope.getId();
            Optional<Envelope> envelopeFromDataBaseOpt = envelopeService.getEnvelope(id);
            Envelope envelopeFromDataBase = null;
            if (envelopeFromDataBaseOpt.isPresent()) {
                envelopeFromDataBase = envelopeFromDataBaseOpt.get();
            }
            Assertions.assertThat(envelopeFromDataBase.getName()).isEqualTo(envelope.getName());
            Assertions.assertThat(envelopeFromDataBase.getCreationDate()).isEqualTo(envelope.getCreationDate());
        } catch (UploadFileException e) {
            System.out.println("Error");
        }

    }

    @Test
    public void testFillEnvelope() {
        EnvelopeController envelopeController = new EnvelopeController(envelopeService, userService);
        try {
            Envelope envelope = envelopeController.createEnvelope("peter.müller@mail.com", "Theben");
            long id = envelope.getId();
            DocumentPut documentPut = new DocumentPut();
            documentPut.setPath("src/main/resources/Manf.pdf");
            documentPut.setReaders(null);
            documentPut.setSignatories(null);
            documentPut.setOrderRelevant(false);
            documentPut.setEndDate(LocalDateTime.of(2011,05,11,10,30));
            DocumentCreator documentCreator = new DocumentCreator();
            Document document = documentCreator.createDocument(documentPut, "peter.müller@mail.com");
            envelope.addDocument(document);
            envelope = envelopeController.fillEnvelope(id,"peter.müller@mail.com", documentPut);
            Optional<Envelope> envelopeFromDataBaseOpt = envelopeService.getEnvelope(id);
            Envelope envelopeFromDataBase = null;
            if (envelopeFromDataBaseOpt.isPresent()) {
                envelopeFromDataBase = envelopeFromDataBaseOpt.get();
            }
            Assertions.assertThat(envelopeFromDataBase.getName()).isEqualTo(envelope.getName());
            Assertions.assertThat(envelopeFromDataBase.getDocumentList().get(0)).isEqualTo(envelope.getDocumentList().get(0));
        } catch (UploadFileException e) {
            System.out.println("Error");
        } catch (DocumentNotFoundException e) {
            System.out.println("DNFE");
        } catch (CreatingFileException | IOException e) {
            System.out.println("CFE");
        }
    }

     */
    @Test
    public void testFilter() {
        /*
        DocumentCreator documentCreator = new DocumentCreator();
        DocumentPutRequest documentPut = new DocumentPutRequest();
        documentPut.setReadersID(Arrays.asList("goin@manf.de","theben@manf.de"));
        documentPut.setSignatoriesID(Arrays.asList("goin@manf.de","theben@manf.de","fredde@manf.de"));
        documentPut.setOrderRelevant(false);
        documentPut.setEndDate(LocalDateTime.of(2021,5,25,10,30));
        documentPut.setTitle("first");
        documentPut.setType("pdf");
        documentPut.setPath("src/main/resources/Manf.pdf");
        DocumentPutRequest documentPut1 = new DocumentPutRequest();
        documentPut1.setReadersID(Arrays.asList("goin@manf.de","theben@manf.de","fredde@manf.de"));
        documentPut1.setSignatoriesID(Arrays.asList("goin@manf.de","theben@manf.de"));
        documentPut1.setOrderRelevant(false);
        documentPut1.setEndDate(LocalDateTime.of(2021, 5,15,10,30));
        documentPut1.setTitle("second");
        documentPut1.setType("pdf");
        documentPut1.setPath("src/main/resources/Manf.pdf");
        User mekken = new User("mekken@mekken.de", "mekken","mekken","12233");
        User goin = new User("goin@manf.de", "goin","ganf","12233");
        User theben = new User("theben@manf.de", "theben","wieg","12233");
        User fredde = new User("fredde@manf.de", "fredde","thrien","12233");
        Envelope envelope = new Envelope("Theben", mekken);
        Envelope envelope1 = new Envelope("123", theben);
        try {
            Document document = documentCreator.createDocument(documentPut,mekken.getEmail(), Arrays.asList(goin,theben,fredde), Arrays.asList(goin,theben));
            Document document1 = documentCreator.createDocument(documentPut1,mekken.getEmail(), Arrays.asList(goin,theben), Arrays.asList(goin,theben,fredde));
            envelope.addDocument(document);
            envelope.addDocument(document1);
            envelope1.addDocument(document1);
        } catch (CreatingFileException | IOException e) {
            e.printStackTrace();
        }
        List<Envelope> envelopeList = new ArrayList<>();
        envelopeList.add(envelope);
        envelopeList.add(envelope1);
        EnvelopeController envelopeController = new EnvelopeController();
        EnvelopeGetRequest envelopeGetRequest = new EnvelopeGetRequest();
        envelopeGetRequest.setEnvelopeIDFilter(0);
        envelopeGetRequest.setNameFilter("123");
        envelopeGetRequest.setRead(false);
        envelopeGetRequest.setSigned(false);
        envelopeGetRequest.setCreationDateFilterFrom(null);
        envelopeGetRequest.setCreationDateFilterTo(null);
        envelopeGetRequest.setOwnerIDFilter("");
        envelopeGetRequest.setDataType("");
        envelopeGetRequest.setEndDateFilterFrom(LocalDateTime.now());
        envelopeGetRequest.setEndDateFilterTo(null);
        envelopeGetRequest.setReaderIDs(null);
        envelopeGetRequest.setSignatureTypeFilter(null);
        envelopeGetRequest.setSignatoryIDs(null);
        envelopeGetRequest.setStateFilter(null);
        envelopeGetRequest.setTitleFilter("");
        List<Envelope> filteredEnvelopeList = envelopeController.filter(envelopeGetRequest, envelopeList);
        System.out.println(filteredEnvelopeList.get(0).getDocumentList().get(0).getEndDate());
        System.out.println(envelopeList.get(0).getDocumentList().get(0).getEndDate());
        Assertions.assertNotEquals(filteredEnvelopeList.get(0).getDocumentList().get(0).getEndDate(),
                                envelopeList.get(0).getDocumentList().get(0).getEndDate());
*/
    }
}

package gpse.example.domain;

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
}

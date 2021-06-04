package gpse.example.domain;

import gpse.example.domain.documents.Document;
import gpse.example.domain.documents.DocumentCreator;
import gpse.example.domain.documents.DocumentPutRequest;
import gpse.example.domain.envelopes.Envelope;
import gpse.example.domain.exceptions.CreatingFileException;
import gpse.example.domain.users.User;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProtocolTest {

    @Test
    public void testProtocolToByteArray() {
        /*
        try {
            User user1 = new User("Theben@fredde.de", "GOin", "Manf", "123");
            User user2 = new User("Walter@fredde.de", "GOin", "Manf", "123");
            User user3 = new User("Manfred@fredde.de", "GOin", "Manf", "123");
            User user4 = new User("Gustav@fredde.de", "GOin", "Manf", "123");
            List<User> userList = new ArrayList<>();
            userList.add(user2);
            userList.add(user3);
            userList.add(user4);
            Envelope envelope = new Envelope("Theben", user1);
            DocumentPutRequest documentPut = new DocumentPutRequest();
            byte[] data = Files.readAllBytes(Paths.get("src/main/resources/Manf.pdf"));
            documentPut.setData(data);
            List<String> signatories = new ArrayList<>();
            documentPut.setTitle("Die drei Chinesen mit dem Kontrabass");
            signatories.add("Walter@fredde.de");
            signatories.add("Manfred@fredde.de");
            signatories.add("Gustav@fredde.de");
            documentPut.setDataType("pdf");
            documentPut.setReadersID(signatories);
            documentPut.setSignatoriesID(signatories);
            documentPut.setOrderRelevant(false);
            documentPut.setEndDate(LocalDateTime.of(2011, 05, 11, 10, 30));
            DocumentCreator documentCreator = new DocumentCreator();
            Document document = documentCreator.createDocument(documentPut, "Theben@fredde.de", userList, userList);
            envelope.addDocument(document);
            document.getSignatories().get(0).setStatus(true);
            Thread.sleep(1000);
            document.getSignatories().get(1).setStatus(true);
            Protocol protocol = new Protocol(envelope);
            ByteArrayOutputStream result = protocol.printProtocol();
            byte[] theben = result.toByteArray();
            documentCreator.writeInNewFile(theben, "pdf", "NewTest", "src/main/resources");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CreatingFileException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

         */
    }
}

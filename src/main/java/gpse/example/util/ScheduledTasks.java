package gpse.example.util;

import gpse.example.domain.documents.Document;
import gpse.example.domain.documents.DocumentService;
import gpse.example.domain.signature.Signatory;
import gpse.example.util.email.SMTPServerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ScheduledTasks {

    @Autowired
    DocumentService documentService;

    @Autowired
    SMTPServerHelper smtpServerHelper;

    private static final int MILLISECONDS_PER_DAY = 24 * 60 * 60 * 1000;

    @Scheduled(fixedRate = MILLISECONDS_PER_DAY)
    public void checkForOpenReminder() {
        for (Document doc: documentService.getDocuments()) {
            if(doc.isOrderRelevant()) {
                informSignatoriesInOrder(doc);
            } else {
                informSignatoriesWithoutOrder(doc);
            }
        }
    }

    private void informSignatoriesInOrder(Document doc) {

    }

    private void informSignatoriesWithoutOrder(Document doc) {
        for (Signatory signatory:doc.getSignatories()) {
            if (signatory.getReminder() > -1) {
                if (LocalDateTime.now().isAfter(doc.getEndDate().minusDays(signatory.getReminder()))){
                    //TODO send reminder
                }
            }
        }

    }

}

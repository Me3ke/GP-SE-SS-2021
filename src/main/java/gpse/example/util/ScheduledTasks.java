package gpse.example.util;

import gpse.example.domain.documents.Document;
import gpse.example.domain.documents.DocumentService;
import gpse.example.domain.documents.DocumentState;
import gpse.example.domain.signature.Signatory;
import gpse.example.util.email.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


/**
 * Class for scheduled Tasks.
 */
@Component
public class ScheduledTasks {

    private static final int MILLISECONDS_PER_DAY = 24 * 60 * 60 * 1000;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private SMTPServerHelper smtpServerHelper;

    @Autowired
    private EmailTemplateService emailTemplateService;


    /**
     * Scheduled method to check if there are signatories to get a notification email.
     * @throws MessageGenerationException if message sending failed
     */
    @Scheduled(fixedRate = MILLISECONDS_PER_DAY, initialDelay = 60000)
    public void checkForOpenReminder() throws MessageGenerationException, TemplateNameNotFoundException {
        for (Document doc: documentService.getDocuments()) {
            if (doc.isOrderRelevant() && doc.getState() != DocumentState.CLOSED) {
                informSignatoriesInOrder(doc);
            } else if (!doc.isOrderRelevant() && doc.getState() != DocumentState.CLOSED) {
                informSignatoriesWithoutOrder(doc);
            }
        }
    }

    private void informSignatoriesInOrder(Document doc) throws MessageGenerationException,
        TemplateNameNotFoundException {

       /* for (final Signatory signatory : doc.getSignatories()) {
            if (!signatory.isStatus()) {
                Signatory currentSignatory = signatory;
                break;
            }
        }*/
        Signatory currentSignatory = doc.getCurrentSignatory();
        if (currentSignatory != null && currentSignatory.getReminder() > -1) {
            if (LocalDateTime.now().isAfter(doc.getEndDate().minusDays(currentSignatory.getReminder()))) {
                /*smtpServerHelper.sendReminder(currentSignatory.getUser().getEmail(), currentSignatory.getReminder(),
                    currentSignatory.getUser().getLastname(), doc);*/
                setupUserReminder(doc, currentSignatory);
            }
        }
    }

    private void informSignatoriesWithoutOrder(Document doc) throws MessageGenerationException,
        TemplateNameNotFoundException {
        for (Signatory signatory:doc.getSignatories()) {
            if (signatory.getReminder() > -1) {
                if (LocalDateTime.now().isAfter(doc.getEndDate().minusDays(signatory.getReminder()))) {
                    /*smtpServerHelper.sendReminder(signatory.getUser().getEmail(), signatory.getReminder(),
                        signatory.getUser().getLastname(), doc);*/
                    setupUserReminder(doc, signatory);
                }
            }
        }
    }

    private void setupUserReminder(Document document, Signatory signatory) throws TemplateNameNotFoundException,
        MessageGenerationException {
        EmailTemplate template = emailTemplateService.findSystemTemplateByName("ReminderTemplate");
        TemplateDataContainer container = new TemplateDataContainer();
        container.setEndDate(document.getEndDate().toString());
        container.setDocumentTitle(document.getDocumentTitle());
        container.setLink("http://localhost:8080/link/to/document/view");
        smtpServerHelper.sendTemplatedEmail(signatory.getUser().getEmail(), template, container, Category.PROGRESS);
    }

}

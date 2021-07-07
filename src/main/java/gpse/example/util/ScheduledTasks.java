package gpse.example.util;

import gpse.example.domain.documents.Document;
import gpse.example.domain.documents.DocumentService;
import gpse.example.domain.documents.DocumentState;
import gpse.example.domain.signature.Signatory;
import gpse.example.domain.users.UserService;
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
    private UserService userService;

    @Autowired
    private SMTPServerHelper smtpServerHelper;

    @Autowired
    private EmailTemplateService emailTemplateService;


    /**
     * Scheduled method to check if there are signatories to get a notification email.
     *
     * @throws MessageGenerationException if message sending failed
     */
    @Scheduled(fixedRate = MILLISECONDS_PER_DAY, initialDelay = 60_000)
    public void checkForOpenReminder() throws MessageGenerationException, TemplateNameNotFoundException {
        for (final Document doc : documentService.getDocuments()) {
            if (doc.isOrderRelevant() && doc.getState() != DocumentState.CLOSED) {
                informSignatoriesInOrder(doc);
            } else if (!doc.isOrderRelevant() && doc.getState() != DocumentState.CLOSED) {
                informSignatoriesWithoutOrder(doc);
            }
        }
    }

    private void informSignatoriesInOrder(final Document doc) throws MessageGenerationException,
        TemplateNameNotFoundException {

       /* for (final Signatory signatory : doc.getSignatories()) {
            if (!signatory.isStatus()) {
                Signatory currentSignatory = signatory;
                break;
            }
        }*/
        if (doc.getEndDate() != null) {
            final Signatory currentSignatory = doc.getCurrentSignatory();
            if (currentSignatory != null && currentSignatory.getReminder() > -1
                && LocalDateTime.now().isAfter(doc.getEndDate().minusDays(currentSignatory.getReminder()))) {
            /*smtpServerHelper.sendReminder(currentSignatory.getUser().getEmail(), currentSignatory.getReminder(),
                currentSignatory.getUser().getLastname(), doc);*/
                setupUserReminder(doc, currentSignatory);
            }
        }
    }


    private void informSignatoriesWithoutOrder(final Document doc) throws MessageGenerationException,
        TemplateNameNotFoundException {
        if (doc.getEndDate() != null) {
            for (final Signatory signatory : doc.getSignatories()) {
                if (signatory.getReminder() > -1
                    && LocalDateTime.now().isAfter(doc.getEndDate().minusDays(signatory.getReminder()))) {
                /*smtpServerHelper.sendReminder(signatory.getUser().getEmail(), signatory.getReminder(),
                    signatory.getUser().getLastname(), doc);*/
                    setupUserReminder(doc, signatory);
                }
            }
        }
    }

    private void setupUserReminder(final Document document, final Signatory signatory)
                throws TemplateNameNotFoundException,
        MessageGenerationException {
        final EmailTemplate template = emailTemplateService.findSystemTemplateByName("ReminderTemplate");
        final TemplateDataContainer container = new TemplateDataContainer();
        container.setEndDate(document.getEndDate().toString());
        container.setDocumentTitle(document.getDocumentTitle());
        container.setLink("http://localhost:8080/link/to/document/view");
        smtpServerHelper.sendTemplatedEmail(signatory.getEmail(), template, container, Category.PROGRESS,
            userService.getUser(document.getOwner()));
    }

}

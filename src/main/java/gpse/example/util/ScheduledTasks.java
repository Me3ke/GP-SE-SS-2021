package gpse.example.util;

import gpse.example.domain.documents.Document;
import gpse.example.domain.documents.DocumentService;
import gpse.example.domain.documents.DocumentState;
import gpse.example.domain.email.*;
import gpse.example.domain.exceptions.MessageGenerationException;
import gpse.example.domain.exceptions.TemplateNameNotFoundException;
import gpse.example.domain.signature.Signatory;
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


    /**
     * Scheduled method to check if there are signatories to get a notification email.
     *
     * @throws MessageGenerationException if message sending failed
     */
    @Scheduled(fixedRate = MILLISECONDS_PER_DAY, initialDelay = 60_000)
    public void checkForOpenReminder() throws MessageGenerationException, TemplateNameNotFoundException {
        for (final Document doc : documentService.getDocuments()) {
            if (doc.isOrderRelevant() && doc.getState() != DocumentState.ARCHIVED) {
                informSignatoriesInOrder(doc);
            } else if (!doc.isOrderRelevant() && doc.getState() != DocumentState.ARCHIVED) {
                informSignatoriesWithoutOrder(doc);
            }
        }
    }

    private void informSignatoriesInOrder(final Document doc) throws MessageGenerationException,
        TemplateNameNotFoundException {
        if (doc.getEndDate() != null) {
            final EmailManagement emailManagement = new EmailManagement();
            final Signatory currentSignatory = doc.getSignatoryManagement().getCurrentSignatory();
            if (currentSignatory != null && currentSignatory.getReminder() > -1
                && LocalDateTime.now().isAfter(doc.getEndDate().minusDays(currentSignatory.getReminder()))) {
                emailManagement.sendReminder(doc, currentSignatory);
            }
        }
    }


    private void informSignatoriesWithoutOrder(final Document doc) throws MessageGenerationException,
        TemplateNameNotFoundException {
        if (doc.getEndDate() != null) {
            final EmailManagement emailManagement = new EmailManagement();
            for (final Signatory signatory : doc.getSignatoryManagement().getSignatories()) {
                if (signatory.getReminder() > -1
                    && LocalDateTime.now().isAfter(doc.getEndDate().minusDays(signatory.getReminder()))) {

                    emailManagement.sendReminder(doc, signatory);
                }
            }
        }
    }
}

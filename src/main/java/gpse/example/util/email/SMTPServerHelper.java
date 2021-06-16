package gpse.example.util.email;

import gpse.example.domain.documents.Document;
import gpse.example.domain.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * SMTPServerHelper generates connection to smtpserver and sends emails.
 */
@Component
public class SMTPServerHelper {

    private static final String GREETING = "Guten Tag Herr/Frau %s, %n";

    private static final String GUTEN_TAG_N = "Guten Tag, %n";

    private static final String INITIAL_REGISTER_TEMPLATE = GREETING
        + "um Ihre Emailadresse zu bestätigen klicken sie bitte auf den Bestätigungslink. %n"
        + "Hier bestätigen: %s %n"
        + "%n %n"
        + "Bitte beachten Sie die eingeschränkte Gültigkeit Ihres Bestätigungslinks von 24 Stunden.";

    private static final String REGISTRATION_SUBJECT = "ELSA Registrierung";



    private static final String SIGNATURE_INVITATION = GREETING
        + "%s hat sie zum signieren des Dokuments %s aufgefordert.";

    private static final String SIGNATURE_INVITATION_SUBJECT = "Signatur des Dokuments %s";


    private static final String ADMIN_VALIDATION_INFO = GUTEN_TAG_N
        + "ein neuer Nutzer möchte sich registrieren. %n"
        + "Bitte bestätigen sie die Emailadresse %s ";


    private static final String VALIDATION_SUBJECT = "Registrierungsanfrage";

    private static final String REMINDER_SUBJECT = "Erinnerung an Dokument %s";
    private static final String REMINDER = GREETING
        + "Bitte denken sie daran, dass das Dokument %s innerhalb der nächsten %s Tage abgeschlossen werden soll.";


    /**
     * use Invitation subject.
     */
    private static final String GUEST_INVITATION = GUTEN_TAG_N
        + "Sie wurden von %s gebeten das Dokument %s zu Signieren.%n"
        + "Sie finden das Dokument hier: %s";

    private static final String GUEST_INVITATION_ADVANCED = GUTEN_TAG_N
        + "Sie wurden aufgefordert ein Dokument mit einer Signatur zu signieren, die eine Anmeldung erfordert.%n"
        + "Sie können sich hier http://localhost:8080/de/landingpage/ registrieren.%n"
        + "Dort finden Sie auch weitere Informationen rund um unsere Anwendung.";
    @Autowired
    private final JavaMailSender mailSender;

    public SMTPServerHelper(final JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * sending an Registration email to the specified address.
     *
     * @param recievingUser the recieving user.
     * @param link          validation link.
     */
    public void sendRegistrationEmail(final User recievingUser, final String link) throws MessageGenerationException {

        Message message = new Message();
        message.setRecievingUserMail(recievingUser.getEmail());
        message.setSubject(REGISTRATION_SUBJECT);
        message.setText(String.format(INITIAL_REGISTER_TEMPLATE, recievingUser.getLastname(), link));

        mailSender.send(message.generateMessage());
    }

    /**
     * sending an info mail to an admin containing the requested emailaddress.
     * @param admin admin who should get this validation information
     * @param newUserEmail email adress of the user who needs to be validated
     */
    public void sendValidationInfo(final User admin, final String newUserEmail) throws MessageGenerationException {
        Message message = new Message();
        message.setRecievingUserMail(admin.getEmail());
        message.setSubject(VALIDATION_SUBJECT);
        message.setText(String.format(ADMIN_VALIDATION_INFO, newUserEmail));

        mailSender.send(message.generateMessage());
    }

    /**
     * sending the Invitation for a signature.
     * @param signatoryMail     the signatory who should be reminded
     * @param owner             the owner of the relating document
     * @param lastnameSignatory the lastname of the signatory who should be reminded
     * @param document the document that belongs to the requestet signature
     * @throws MessageGenerationException
     */
    public void sendSignatureInvitation(final String signatoryMail, final User owner, final String lastnameSignatory,
                                        final Document document) throws MessageGenerationException {
        Message message = new Message();
        message.setRecievingUserMail(signatoryMail);
        message.setSubject(String.format(SIGNATURE_INVITATION_SUBJECT, document.getDocumentTitle()));
        message.setText(String.format(SIGNATURE_INVITATION, lastnameSignatory, owner.getFirstname() + " "
            + owner.getLastname(), document.getDocumentTitle()));
        mailSender.send(message.generateMessage());
    }

    /**
     * send a reminder to specified mail address with the given data.
     * @param userEmail the recieving email address
     * @param days days bevor deadline
     * @param userName name of User who recieves the email
     * @param document the document which is the 'reminding' one
     */
    public void sendReminder(final String userEmail, final int days, String userName, Document document)
            throws MessageGenerationException {
        Message message = new Message();
        message.setRecievingUserMail(userEmail);
        message.setSubject(String.format(REMINDER_SUBJECT, document.getDocumentTitle()));
        message.setText(String.format(REMINDER, userName, document.getDocumentTitle(), days));

        mailSender.send(message.generateMessage());
    }


    /**
     * sending an email with the guest invitation template.
     * @param guestMail the reciever mail
     * @param document the document which the invitation belongs to
     * @param link the link to get the document view without log in
     * @throws MessageGenerationException when message could not be generated or send
     */
    public void sendGuestInvitation(final String guestMail, final Document document, final String link)
            throws MessageGenerationException {
        Message message = new Message();
        message.setRecievingUserMail(guestMail);
        message.setSubject(String.format(SIGNATURE_INVITATION_SUBJECT, document.getDocumentTitle()));
        message.setText(String.format(GUEST_INVITATION, document.getOwner(), document.getDocumentTitle(), link));

        mailSender.send(message.generateMessage());
    }

    /**
     * send Information that Registration is required for signing advanced.
     * @param guestMail Mail of guestsignatory
     * @param document document that should be signed advanced
     * @throws MessageGenerationException when message could not be generated or send
     */
    public void sendGuestInvitationAdvanced(final String guestMail, final Document document)
            throws MessageGenerationException {
        Message message = new Message();
        message.setRecievingUserMail(guestMail);
        message.setSubject(String.format(SIGNATURE_INVITATION_SUBJECT, document.getDocumentTitle()));
        message.setText(GUEST_INVITATION_ADVANCED);

        mailSender.send(message.generateMessage());
    }

}

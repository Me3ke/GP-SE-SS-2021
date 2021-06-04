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

    private static final String INITIAL_REGISTER_TEMPLATE = GREETING
        + "um Ihre Emailadresse zu bestätigen klicken sie bitte auf den Bestätigungslink. %n"
        + "Hier bestätigen: %s %n"
        + "%n %n"
        + "Bitte beachten Sie die eingeschränkte Gültigkeit Ihres Bestätigungslinks von 24 Stunden.";

    private static final String REGISTRATION_SUBJECT = "ELSA Registrierung";

    private static final String SIGNATURE_INVITATION = GREETING
        + "%s hat sie zum signieren des Dokuments %s aufgefordert.";

    private static final String SIGNATURE_INVITATION_SUBJECT = "Signatur des Dokuments %s";


    private static final String ADMIN_VALIDATION_INFO = "Guten Tag, %n"
        + "ein neuer Nutzer möchte sich registrieren. %n"
        + "Bitte bestätigen sie die Emailadresse %s ";

    private static final String VALIDATION_SUBJECT = "Registrierungsanfrage";

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
     * sending an info mail to an admin containing the requested emailadress.
     *
     * @param admin        admin who should get this validation information
     * @param newUserEmail email adress of the user who needs to be validated
     */
    public void sendValidationInfo(final User admin, final String newUserEmail) throws MessageGenerationException {
        Message message = new Message();
        message.setRecievingUserMail(admin.getUsername());
        message.setSubject(VALIDATION_SUBJECT);
        message.setText(String.format(ADMIN_VALIDATION_INFO, newUserEmail));

        mailSender.send(message.generateMessage());
    }

    /**
     * sending the reminder for a signature.
     *
     * @param signatoryMail     the signatory who should be reminded
     * @param owner             the owner of the relating document
     * @param lastnameSignatory the lastname of the signatory who should be reminded
     * @param document
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

}

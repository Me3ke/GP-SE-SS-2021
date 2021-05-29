package gpse.example.util.Email;

import gpse.example.domain.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * SMTPServerHelper generates connection to smtpserver and sends emails.
 */
@Component
public class SMTPServerHelper {

    /**
     * Template for sending RegistrationEmail.
     */
    public static final String INITIAL_REGISTER_TEMPLATE = "Guten Tag Herr/Frau %s, %n"
        + "um Ihre Emailadresse zu bestätigen klicken sie bitte auf den Bestätigungslink. %n"
        + "Hier bestätigen: %s %n"
        + "%n %n"
        + "Bitte beachten Sie die eingeschränkte Gültigkeit Ihres Bestätigungslinks von 24 Stunden.";

    /**
     * The subject of Elsas registration emails.
     */
    public static final String REGISTRATION_SUBJECT = "ELSA Registrierung";


    /**
     * Basic template for sending validation requests to admin.
     */
    public static final String ADMIN_VALIDATION_INFO = "Guten Tag, %n"
        + "ein neuer Nutzer möchte sich registrieren. %n"
        + "Bitte bestätigen sie die Emailadresse %s ";

    /**
     * The subject of Elsas validation request emails.
     */
    public static final String VALIDATION_SUBJECT = "Registrierungsanfrage";

    /**
     * should be the from address, but because of whatever it doesnt work with thunderbird and K-9 emailclients.
     */
    public static final String NOREPLY_ADDRESS = "noreply@gmail.com";

    @Autowired
    private final JavaMailSender mailSender;

    public SMTPServerHelper(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * sending an Registration email to the specified address.
     *
     * @param recievingUser the recieving user.
     * @param link validation link.
     */
    public void sendRegistrationEmail(final User recievingUser, final String link) throws MessageGenerationException {
        Message message = new Message();
        message.setRecievingUser(recievingUser);
        message.setSubject(REGISTRATION_SUBJECT);
        message.setText(String.format(INITIAL_REGISTER_TEMPLATE, recievingUser.getLastname(), link));

        mailSender.send(message.generateMessage());
    }

    /**
     * sending an info mail to an admin containing the requested emailadress.
     * @param admin admin who should get this validation information
     * @param newUserEmail email adress of the user who needs to be validated
     */
    public void sendValidationInfo(final User admin, final String newUserEmail) throws MessageGenerationException {
        Message message = new Message();
        message.setRecievingUser(admin);
        message.setSubject(VALIDATION_SUBJECT);
        message.setText(String.format(ADMIN_VALIDATION_INFO, newUserEmail));

        mailSender.send(message.generateMessage());
    }

}

package gpse.example.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
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

    public SMTPServerHelper(final JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * sending an Registration email to the specified address.
     *
     * @param toAddress the email address of the recieving person.
     * @param userName name of the new user.
     * @param link validation link.
     */
    public void sendRegistrationEmail(final String toAddress, final String userName, final String link) {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(NOREPLY_ADDRESS);
        message.setTo(toAddress);
        message.setSubject(REGISTRATION_SUBJECT);
        message.setText(String.format(INITIAL_REGISTER_TEMPLATE, userName, link));

        mailSender.send(message);
    }

    /**
     * sending an info mail to an admin containing the requested emailadress.
     * @param toAddress address of an admin
     * @param newUserEmail email adress of the user who needs to be validated
     */
    public void sendValidationInfo(final String toAddress, final String newUserEmail) {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(NOREPLY_ADDRESS);
        message.setTo(toAddress);
        message.setSubject(VALIDATION_SUBJECT);
        message.setText(String.format(ADMIN_VALIDATION_INFO, newUserEmail));

        mailSender.send(message);
    }

}

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
     * Template for sending RegisterValidationEmail.
     */
    public static final String INITIAL_REGISTER_TEMPLATE = "Guten Tag Herr %s, \n"
        + "um deine Emailadresse zu bestätigen klicke auf den Bestätigungslink. \n"
        + "Hier bestätigen: %s \n"
        + "Dein ELSA-Team";

    /**
     * The subject of Elsas emails.
     */
    public static final String REGISTRATION_SUBJECT = "ELSA Registrierung";

    @Autowired
    private final JavaMailSender mailSender;

    public SMTPServerHelper(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * sending an email to the specified address.
     *
     * @param toAddress the email address of the recieving person.
     * @param userName name of the new user.
     * @param link validation link.
     */

    public void sendRegistrationEmail(final String toAddress, final String userName, final String link) {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@gmail.com");
        message.setTo(toAddress);
        message.setSubject(REGISTRATION_SUBJECT);
        message.setText(String.format(INITIAL_REGISTER_TEMPLATE, userName, link));

        mailSender.send(message);

    }

}

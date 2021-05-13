package gpse.example.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * SMTPServerHelper generates connection to smtpserver and sends emails.
 */
@Component
public class SMTPServerHelper {

    /**
     * Template for sending RegisterValidationEmail.
     */
    public static final String INITIAL_REGISTER_TEMPLATE = "Hallo %s, \n"
        + "um deine Emailadresse zu bestätigen klicke auf den Bestätigungslink. \n"
        + "Hier bestätigen: %s \n"
        + "Dein ELSA-Team";

    /**
     * The subject of Elsas emails.
     */
    public static final String REGISTRATION_SUBJECT = "ELSA Registrierung";

    @Value("${smtp.password}")
    private static String password;

    private static final String TRUE = "true";

    private static JavaMailSenderImpl mailSender;

    @Value("${smtp.host}")
    private static String hostServer;

    @Value("${smtp.port}")
    private static int port;

    @Value("${smtp.username}")
    private static String username;

    /**
     * contructor of theSMTPServerHelper.
     */
    protected SMTPServerHelper() {

    }

    /**
     * set up and log in the the mailSender object.
     * only need to run once while server dont change.
     * WHATCH OUT:
     * if using gmail set up gmail account with no two-factor-Authentification and
     * activate access from unsecure apps.
     */

    public static void setMailSender() {
        mailSender = new JavaMailSenderImpl();
        mailSender.setHost(hostServer);
        mailSender.setPort(port);

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        final Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", TRUE);
        props.put("mail.smtp.starttls.enable", TRUE);
        props.put("mail.debug", TRUE);
    }


    /**
     * sending an email to the specified address.
     *
     * @param toAddress the email address of the recieving person.
     * @param userName name of the new user.
     * @param link validation link.
     */

    public static void sendRegistrationEmail(String toAddress, String userName, String link) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@gmail.com");
        message.setTo(toAddress);
        message.setSubject(REGISTRATION_SUBJECT);
        message.setText(String.format(INITIAL_REGISTER_TEMPLATE, userName, link));

        if (mailSender == null) {
            setMailSender();
        } else {
            mailSender.send(message);
        }
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(final int port) {
        SMTPServerHelper.port = port;
    }

    public static String getHostServer() {
        return hostServer;
    }

    public static void setHostServer(final String hostServer) {
        SMTPServerHelper.hostServer = hostServer;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(final String username) {
        SMTPServerHelper.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(final String password) {
        SMTPServerHelper.password = password;
    }
}

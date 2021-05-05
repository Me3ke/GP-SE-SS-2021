package gpse.example.util;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Properties;

/**
 * SMTPServerHelper generates connection to smtpserver and sends emails.
 */

@Entity
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
    public static final String ELSA_SUBJECT = "ELSA - noreply";


    private static JavaMailSenderImpl mailSender;

    @Column
    private static String hostServer;
    @Column
    private static int port;
    @Id
    @Column
    private static String userName;
    @Column
    private static String password;

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
     * @param hostServer the SMTPServer that is used f.ex. smtp.gmail.com
     * @param port the port at specified server in case of gmail 587
     * @param username the username in case of gmail its the gmail address user@gmail.com
     * @param password the password of the account
     */

    public static void setMailSender(String hostServer, int port, String username, String password) {
        mailSender = new JavaMailSenderImpl();
        mailSender.setHost(hostServer);
        mailSender.setPort(port);

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
    }


    /**
     * sending an email to the specified address.
     * @param toAddress the email address of the recieving person.
     * @param customer name of the new user.
     * @param link validation link.
     */
    public static void sendRegisterEmail(String toAddress, String customer, String link) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@gmail.com");
        message.setTo(toAddress);
        message.setSubject(ELSA_SUBJECT);
        message.setText(String.format(INITIAL_REGISTER_TEMPLATE, customer, link));

        if (mailSender == null) {
            System.out.println("Fehler es muss erst ein Server angemeldet werden. (Im Frontend anzeigen)");
        } else {
            mailSender.send(message);
        }
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        SMTPServerHelper.port = port;
    }

    public static String getHostServer() {
        return hostServer;
    }

    public static void setHostServer(String hostServer) {
        SMTPServerHelper.hostServer = hostServer;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        SMTPServerHelper.userName = userName;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        SMTPServerHelper.password = password;
    }
}

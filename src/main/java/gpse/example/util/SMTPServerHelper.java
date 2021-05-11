package gpse.example.util;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * SMTPServer class representing the connection to smtpserver.
 */

public class SMTPServerHelper {

    /**
     * Template for a test e-mail.
     */
    public static final String TEST_TEMPLATE = "Dies ist ein Test %s !!! \n ELSA";
    /**
     * Template for a test e-mail.
     */
    public static final String TEST_SUBJECT = "Test";
    private static final String TRUE = "true";
    private static JavaMailSenderImpl mailSender;

    private static String hostServer;

    private static int port;

    private static String userName;

    private static String password;

    protected SMTPServerHelper() {

    }

    /**
     * set up and log in the the mailSender object.
     * only need to run once while server dont change.
     * WHATCH OUT:
     * if using gmail set up gmail account with no two-factor-Authentification and
     * activate access from unsecure apps.
     *
     * @param hostServer the SMTPServer that is used f.ex. smtp.gmail.com
     * @param port       the port at specified server in case of gmail 587
     * @param username   the username in case of gmail its the gmail address user@gmail.com
     * @param password   the password of the account
     */

    public static void setMailSender(final String hostServer, final int port, final String username,
                                     final String password) {
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
     */
    public static void sendEmail(final String toAddress) {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@gmail.com");
        message.setTo(toAddress);
        message.setSubject(TEST_SUBJECT);
        message.setText(String.format(TEST_TEMPLATE, toAddress));

        if (mailSender == null) {
            System.out.println("Fehler es muss erst ein Server angemeldet werden. (Im Frontend anzeigen)");
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

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(final String userName) {
        SMTPServerHelper.userName = userName;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(final String password) {
        SMTPServerHelper.password = password;
    }
}

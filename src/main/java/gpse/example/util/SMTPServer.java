package gpse.example.util;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;


public class SMTPServer {
    public static final String TEST_TEMPLATE = "Dies ist ein Test %s !!! \n ELSA";
    public static final String TEST_SUBJECT = "Test";
    private static JavaMailSenderImpl mailSender;

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

    public static void sendEmail(String toAddress) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@gmail.com");
        message.setTo(toAddress);
        message.setSubject(TEST_SUBJECT);
        message.setText(String.format(TEST_TEMPLATE, toAddress));
        mailSender.send(message);
    }

}

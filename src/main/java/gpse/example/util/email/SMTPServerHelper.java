package gpse.example.util.email;

import gpse.example.domain.users.User;
import gpse.example.util.email.trustedDomain.DomainSetter;
import gpse.example.util.email.trustedDomain.DomainSetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * SMTPServerHelper generates connection to smtpserver and sends emails.
 */
@Component
public class SMTPServerHelper {


    private final JavaMailSenderImpl mailSender;

    @Autowired
    private final MessageServiceImpl messageService;

    @Autowired
    private final DomainSetterService domainSetterService;

    private Session session;

    /**
     * something else.
     * @param messageService s
     * @param domainSetterService t
     */
    public SMTPServerHelper(MessageServiceImpl messageService,
                            DomainSetterService domainSetterService) {
        this.mailSender = new JavaMailSenderImpl();
        this.messageService = messageService;
        this.domainSetterService = domainSetterService;
    }
    /**
     * something.
     */
    public void changeDomainSettings() {
        try {
            DomainSetter domainSetter = domainSetterService.getDomainSettings().get(0);
            mailSender.setHost(domainSetter.getHost());
            mailSender.setPort(domainSetter.getPort());
            mailSender.setUsername(domainSetter.getUsername());
            mailSender.setPassword(domainSetter.getPassword());
            Properties properties = mailSender.getJavaMailProperties();
            properties.put("mail.smtp.auth", domainSetter.isMailSMTPAuth());
            properties.put("mail.smtp.starttls.enable", domainSetter.isMailSMTPStartTLSEnable());
             /*session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(, domainSetter.getPassword());
                }
            });*/
        } catch (NullPointerException e) {
            throw e;
        }
    }
    /**
     * sending templated email.
     *
     * @param recieverMail  reciever
     * @param template      specified templateobject
     * @param dataContainer specified data
     * @param category      the category of the email
     * @param sendingUser   the user that sends the mail
     * @throws MessageGenerationException when text, reciever mail, or subject is null
     */
    public void sendTemplatedEmail(final String recieverMail, final EmailTemplate template,
                                   final TemplateDataContainer dataContainer, final Category category,
                                   final User sendingUser)
        throws MessageGenerationException {
        changeDomainSettings();
        Message message = new Message();
        try {
            message.setCategory(category);
            message.setRecievingUserMail(recieverMail);
            message.setupByTemplate(template, dataContainer);
            message.setSendingUser(sendingUser);
            mailSender.send(message.generateHtmlMessage(new MimeMessage(session)));
            messageService.saveMessage(message);
        } catch (InvocationTargetException | MessagingException exc) {
            throw new MessageGenerationException(message.getMessageID(), exc);
        }
    }
}

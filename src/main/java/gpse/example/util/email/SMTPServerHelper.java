package gpse.example.util.email;

import gpse.example.domain.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.lang.reflect.InvocationTargetException;

/**
 * SMTPServerHelper generates connection to smtpserver and sends emails.
 */
@Component
public class SMTPServerHelper {
    @Autowired
    private final JavaMailSender mailSender;

    @Autowired
    private final MessageServiceImpl messageService;


    public SMTPServerHelper(final JavaMailSender mailSender, MessageServiceImpl messageService) {
        this.mailSender = mailSender;
        this.messageService = messageService;
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
        Message message = new Message();
        try {
            message.setCategory(category);
            message.setRecievingUserMail(recieverMail);
            message.setupByTemplate(template, dataContainer);
            message.setSendingUser(sendingUser);
            mailSender.send(message.generateHtmlMessage(mailSender.createMimeMessage()));
            messageService.saveMessage(message);
        } catch (InvocationTargetException | MessagingException exc) {
            throw new MessageGenerationException(message.getMessageID(), exc);
        }
    }
}

package gpse.example.util.email;

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

    public SMTPServerHelper(final JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * sending templated email.
     * @param recieverMail reciever
     * @param template specified templateobject
     * @param dataContainer specified data
     * @throws MessageGenerationException when text, reciever mail, or subject is null
     */
    public void sendTemplatedEmail(final String recieverMail, final EmailTemplate template,
                                   final TemplateDataContainer dataContainer)
        throws MessageGenerationException {
        Message message = new Message();
        try {
            message.setRecievingUserMail(recieverMail);
            message.setupByTemplate(template, dataContainer);
            mailSender.send(message.generateHtmlMessage(mailSender.createMimeMessage()));
        } catch (InvocationTargetException | MessagingException exc) {
            throw new MessageGenerationException(message.getMessageID(), exc);
        }
    }

}

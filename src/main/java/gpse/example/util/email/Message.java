package gpse.example.util.email;

import gpse.example.domain.users.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.*;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

/**
 * Class representing messages that are send via email and are stored in database.
 */
@Entity
public class Message {

    private static final String UTF_EIGHT = "UTF-8";
    private static final String SYSTEM = "System";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long messageID;

    @Column
    private String subject;

    @Column
    private String text;

    @Column
    private LocalDateTime timeStamp;

    @Column
    private String recievingUserMail;

    @ManyToOne
    @JoinColumn
    private User sendingUser;

    public Message() {
        timeStamp = LocalDateTime.now();
        sendingUser = null;
    }

    /**
     * genrates a SimplemailMessage that could be send via Email.
     * @return the Simple mail Message object.
     * @throws MessageGenerationException Thrown if there are params missing that are needed.
     */
    public SimpleMailMessage generateMessage() throws MessageGenerationException {
        final SimpleMailMessage message = new SimpleMailMessage();

        if (sendingUser == null) {
            message.setFrom(SYSTEM);
        } else {
            message.setFrom(sendingUser.getEmail());
        }

        if (recievingUserMail == null || subject == null || text == null) {
            throw new MessageGenerationException(this.messageID);
        }

        message.setTo(recievingUserMail);
        message.setSubject(subject);
        message.setText(text);

        return message;
    }

    /**
     * generates the html Formatted mailmessage.
     * @param message MimeMessage generated by mailsender
     * @return the Filled mailMessage
     * @throws MessagingException thrown by internet address.parse
     * @throws MessageGenerationException thrown if relevant data is missing
     */
    public MimeMessage generateHtmlMessage(MimeMessage message) throws MessagingException, MessageGenerationException {

        message.setSubject(subject, UTF_EIGHT);

        MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_EIGHT);

        if (sendingUser == null) {
            helper.setFrom(SYSTEM);
        } else {
            helper.setFrom(sendingUser.getEmail());
        }

        if (recievingUserMail == null || subject == null || text == null) {
            throw new MessageGenerationException(this.messageID);
        }

        helper.setTo(InternetAddress.parse(recievingUserMail));
        message.setContent(text, "text/html");
        return message;
    }

    /**
     * setting up subject and text by template and data.
     * @param template the template
     * @param dataContainer the data needed to fill placeholder
     * @throws InvocationTargetException thrown by filled template
     */
    public void setupByTemplate(EmailTemplate template, TemplateDataContainer dataContainer)
            throws InvocationTargetException {
        this.subject = template.getSubject();
        this.text = template.filledTemplate(dataContainer);

    }


    public long getMessageID() {
        return messageID;
    }

    public void setMessageID(final long messageID) {
        this.messageID = messageID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(final LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getRecievingUserMail() {
        return recievingUserMail;
    }

    public void setRecievingUserMail(String email) {
        this.recievingUserMail = email;
    }

    public User getSendingUser() {
        return sendingUser;
    }

    public void setSendingUser(final User sendingUser) {
        this.sendingUser = sendingUser;
    }


}

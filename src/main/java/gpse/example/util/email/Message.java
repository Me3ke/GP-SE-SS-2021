package gpse.example.util.email;

import gpse.example.domain.users.User;
import org.springframework.mail.SimpleMailMessage;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Class representing messages that are send via email and are stored in database.
 */
@Entity
public class Message {

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
    private String recievingUser;

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
            message.setFrom("System");
        } else {
            message.setFrom(sendingUser.getEmail());
        }

        if (recievingUser == null || subject == null || text == null) {
            throw new MessageGenerationException(this.messageID);
        }

        message.setTo(recievingUser);
        message.setSubject(subject);
        message.setText(text);

        return message;
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

    public String getRecievingUser() {
        return recievingUser;
    }

    public void setRecievingUser(String user) {
        this.recievingUser = user;
    }

    public User getSendingUser() {
        return sendingUser;
    }

    public void setSendingUser(final User sendingUser) {
        this.sendingUser = sendingUser;
    }
}

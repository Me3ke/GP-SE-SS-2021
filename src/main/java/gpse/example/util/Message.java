package gpse.example.util;

import gpse.example.domain.users.User;
import org.springframework.mail.SimpleMailMessage;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @ManyToOne
    @JoinColumn
    private User recievingUser;

    @ManyToOne
    @JoinColumn
    private User sendingUser;

    public Message() {
        timeStamp = LocalDateTime.now();
        sendingUser = null;
    }

    public SimpleMailMessage generateMessage() throws MessageGenerationException{
        final SimpleMailMessage message = new SimpleMailMessage();
        if (sendingUser == null){
            message.setFrom("System");
        } else {
            message.setFrom(sendingUser.getEmail());
        }

        if (recievingUser == null || subject == null || text == null) {
            throw new MessageGenerationException();
        }

        message.setTo(recievingUser.getEmail());
        message.setSubject(subject);
        message.setText(text);

        return message;
    }

    public long getMessageID() {
        return messageID;
    }

    public void setMessageID(long messageID) {
        this.messageID = messageID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public User getRecievingUser() {
        return recievingUser;
    }

    public void setRecievingUser(User user) {
        this.recievingUser = user;
    }

    public User getSendingUser() {
        return sendingUser;
    }

    public void setSendingUser(User sendingUser) {
        this.sendingUser = sendingUser;
    }
}

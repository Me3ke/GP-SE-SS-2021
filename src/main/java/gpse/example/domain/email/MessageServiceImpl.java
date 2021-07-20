package gpse.example.domain.email;

import gpse.example.domain.users.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of Messageservice implementing the defined methods.
 */
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepo;

    public MessageServiceImpl(final MessageRepository messageRepo) {
        this.messageRepo = messageRepo;
    }

    @Override
    public List<Message> getMessages(final User user) {
        final ArrayList<Message> userMessages = (ArrayList<Message>) messageRepo.findAll();
        userMessages.removeIf(message -> !message.getRecievingUserMail().equals(user.getUsername()));
        return userMessages;
    }

    @Override
    public Message saveMessage(final Message message) {
        return messageRepo.save(message);
    }

    @Override
    public Optional<Message> getMessage(final long messageID) {
        return messageRepo.findById(messageID);
    }

    @Override
    public void removeMessage(final long messageID) {
        messageRepo.deleteById(messageID);
    }
}

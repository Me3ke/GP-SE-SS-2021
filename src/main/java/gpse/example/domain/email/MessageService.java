package gpse.example.domain.email;

import gpse.example.domain.users.User;

import java.util.List;
import java.util.Optional;

/**
 * Message service interface that defines neede methods.
 */
public interface MessageService {
    List<Message> getMessages(User user);
    Message saveMessage(Message message);
    Optional<Message> getMessage(long messageID);
    void removeMessage(long messageID);
}

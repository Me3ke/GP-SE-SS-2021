package gpse.example.util.Email;

import gpse.example.domain.users.User;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    List<Message> getMessages(User user);
    Message saveMessage(Message message);
    Optional<Message> getMessage(long messageID);
    void removeMessage(long messageID);
}

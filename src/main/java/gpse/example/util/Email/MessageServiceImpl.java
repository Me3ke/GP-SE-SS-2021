package gpse.example.util.Email;

import gpse.example.domain.users.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService{

    private MessageRepository messageRepo;

    public MessageServiceImpl(MessageRepository messageRepo) {
        this.messageRepo = messageRepo;
    }

    @Override
    public List<Message> getMessages(User user) {
        ArrayList<Message> userMessages = (ArrayList<Message>) messageRepo.findAll();

        for(int i = 0; i < userMessages.size(); i++) {
            if(userMessages.get(i).getRecievingUser() != user){
                userMessages.remove(i);
                i--;
            }
        }
        return userMessages;
    }

    @Override
    public Message saveMessage(Message message) {
        return messageRepo.save(message);
    }

    @Override
    public Optional<Message> getMessage(long messageID) {
        return messageRepo.findById(messageID);
    }

    @Override
    public void removeMessage(long messageID) {
        messageRepo.deleteById(messageID);
    }
}

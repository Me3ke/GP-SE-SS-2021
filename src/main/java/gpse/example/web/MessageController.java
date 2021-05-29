package gpse.example.web;

import gpse.example.domain.users.UserService;
import gpse.example.util.email.Message;
import gpse.example.util.email.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for the exchange of those messages that are send via email with frontend.
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class MessageController {

    private MessageService messageService;
    private UserService userService;

    @Autowired
    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
    }

    @GetMapping("/messages")
    public List<Message> getMessages(@RequestParam("userid") String userID) {
        return messageService.getMessages(userService.getUser(userID));
    }
}

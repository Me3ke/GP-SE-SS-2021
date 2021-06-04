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

    private static final int STATUS_CODE_DELETE_FAILED = 520;
    private static final int STATUS_CODE_OKAY = 200;
    private final MessageService messageService;
    private final UserService userService;

    @Autowired
    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @GetMapping("/messages")
    public List<Message> getMessages(@RequestParam("userid") String userID) {
        return messageService.getMessages(userService.getUser(userID));
    }

    /**
     * Request for delete a message from Database.
     * @param messageID ID of message that should be deleted
     * @return A response containing statuscode and a message
     */
    @GetMapping("/message/{messageID}/delete")
    public JSONResponseObject deleteMessage(@PathVariable("messageID") final long messageID) {
        JSONResponseObject response = new JSONResponseObject();
        messageService.removeMessage(messageID);
        if (messageService.getMessage(messageID).isPresent()) {
            response.setStatus(STATUS_CODE_DELETE_FAILED);
            response.setMessage("Message could not be deleted");
        } else {
            response.setStatus(STATUS_CODE_OKAY);
        }
        return response;
    }
}

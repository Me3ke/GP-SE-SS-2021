package gpse.example.web.messages;

import gpse.example.domain.users.User;
import gpse.example.domain.users.UserService;
import gpse.example.domain.email.Category;
import gpse.example.domain.email.Message;
import gpse.example.domain.email.MessageService;
import gpse.example.web.JSONResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
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
    private static final int STATUS_MESSAGE_NOT_FOUND = 521;
    private static final int STATUS_NO_PERMISSION = 522;
    private static final int STATUS_USER_NOT_FOUND = 523;
    private static final String USER_ID = "userid";
    private MessageService messageService;
    private UserService userService;

    @Autowired
    public MessageController(final MessageService messageService, final UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    /**
     * Request for getting all messages for the user.
     *
     * @param userID the email of the current user.
     * @return a list of all messages.
     */
    @GetMapping("/user/{userid}/messages")
    public List<Message> getMessages(final @PathVariable(USER_ID) String userID) {
        final User user = userService.getUser(userID);
        final MessageSettingsContainer container = user.getMessageSettings();
        final List<Message> messages = messageService.getMessages(user);
        messages.removeIf(message -> message.getCategory().equals(Category.SYSTEM));
        for (final Iterator<Message> iterator = messages.iterator(); iterator.hasNext();) {
            final Message message = iterator.next();
            final Category category = message.getCategory();
            if (category.equals(Category.READ) && !container.isRead()) {
                iterator.remove();
            } else if (category.equals(Category.SIGN) && !container.isSign()) {
                iterator.remove();
            } else if (category.equals(Category.NEW_VERSION) && !container.isNewVersion()) {
                iterator.remove();
            } else if (category.equals(Category.PROGRESS) && !container.isProgress()) {
                iterator.remove();
            } else if (category.equals(Category.TODO) && !container.isToDo()) {
                iterator.remove();
            }
        }
        return messages;
    }

    /**
     * Request for delete a message from Database.
     *
     * @param messageID ID of message that should be deleted
     * @return A response containing statuscode and a message
     */
    @PutMapping("/message/{messageID}/delete")
    public JSONResponseObject deleteMessage(@PathVariable("messageID") final long messageID) {
        final JSONResponseObject response = new JSONResponseObject();
        messageService.removeMessage(messageID);
        if (messageService.getMessage(messageID).isPresent()) {
            response.setStatus(STATUS_CODE_DELETE_FAILED);
            response.setMessage("Message could not be deleted");
        } else {
            response.setStatus(STATUS_CODE_OKAY);
        }
        return response;
    }

    /**
     * Request for setting a message as "watched".
     *
     * @param userID    the email of the current user.
     * @param messageID the id of the message to be read.
     * @return A response containing the status code.
     */
    @PutMapping("/user/{userid}/messages/{messageID}")
    public JSONResponseObject read(@PathVariable(USER_ID) final String userID,
                                   @PathVariable("messageID") final long messageID) {
        final JSONResponseObject response = new JSONResponseObject();
        final Message message;
        if (messageService.getMessage(messageID).isPresent()) {
            message = messageService.getMessage(messageID).get();
            if (message.getRecievingUserMail().equals(userID)) {
                message.setWatched(true);
                messageService.saveMessage(message);
                response.setStatus(STATUS_CODE_OKAY);
            } else {
                response.setStatus(STATUS_NO_PERMISSION);
                response.setMessage("User has no permission to read this message.");
            }
        } else {
            response.setStatus(STATUS_MESSAGE_NOT_FOUND);
            response.setMessage("Message not found.");
        }
        return response;
    }

    /**
     * Request for a changing the settings for messages.
     *
     * @param userID                   the email of the current user.
     * @param messageSettingsContainer the request body which contains booleans for
     *                                 all categories to be send.
     * @return A response containing the status code.
     */
    @PutMapping("/user/{userid}/messages/settingsChange")
    public JSONResponseObject changeSettings(@PathVariable(USER_ID) final String userID,
                                             @RequestBody final MessageSettingsContainer messageSettingsContainer) {
        final JSONResponseObject response = new JSONResponseObject();
        try {
            final User user = userService.getUser(userID);
            user.getMessageSettings().setNewVersion(messageSettingsContainer.isNewVersion());
            user.getMessageSettings().setProgress(messageSettingsContainer.isProgress());
            user.getMessageSettings().setRead(messageSettingsContainer.isRead());
            user.getMessageSettings().setSign(messageSettingsContainer.isSign());
            user.getMessageSettings().setToDo(messageSettingsContainer.isToDo());
            response.setStatus(STATUS_CODE_OKAY);
            return response;
        } catch (UsernameNotFoundException e) {
            response.setStatus(STATUS_USER_NOT_FOUND);
            response.setMessage("user not found.");
            return response;
        }
    }

    @GetMapping("/user/{userid}/messages/settingsConfig")
    public MessageSettingsContainer getMessageSettingsConfig(@PathVariable(USER_ID) final String userID) {
        return userService.getUser(userID).getMessageSettings();
    }
}

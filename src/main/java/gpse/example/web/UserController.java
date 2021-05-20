package gpse.example.web;

import gpse.example.domain.users.PersonalData;
import gpse.example.domain.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * The controller handling requests regarding the user objects.
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{*userID}/personal")
    public PersonalData showPersonalData(@PathVariable("userID") final String username) {
        return userService.getUser(username).getPersonalData();
    }
}

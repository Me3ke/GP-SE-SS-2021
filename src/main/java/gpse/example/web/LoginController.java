package gpse.example.web;

import java.util.Optional;

import gpse.example.domain.User;
import gpse.example.domain.UserService;
import gpse.example.web.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * The login controller.
 */
@CrossOrigin
@RestController
public class LoginController {

    private final UserService service;

    @Autowired
    public LoginController(final UserService service) {
        this.service = service;
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public String getUser(@RequestBody final User requestUser) {
        final Optional<User> optUser = service.getUser(requestUser.getEmail());
        User user;
        if(optUser.isPresent()) {
            user = optUser.get();
        } else {
            throw new BadRequestException();
        }
        if (user.getPassword().equals(requestUser.getPassword())) {

        }
    }

}

package gpse.example.web;

import gpse.example.domain.users.ConfirmationToken;
import gpse.example.domain.users.ConfirmationTokenService;
import gpse.example.domain.users.User;
import gpse.example.domain.users.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


/**
 * UserController class.
 */
@RestController
@CrossOrigin
@RequestMapping("/api.elsa.de") // ? Was ist die basis?
public class UserController {

    private final UserService userService;

    private final ConfirmationTokenService confirmationTokenService;

    public UserController(UserService service, ConfirmationTokenService confService) {
        userService = service;
        confirmationTokenService = confService;
    }

    @PostMapping("/users/")
    public String signUp(User user) {

        userService.signUpUser(user);

        return "redirect:/login";
    }

    @GetMapping("/users/register/")
    public String confirmMail(@RequestParam("token") String token) {

        Optional<ConfirmationToken> optionalConfirmationToken
            = confirmationTokenService.findConfirmationTokenByToken(token);

        optionalConfirmationToken.ifPresent(userService::confirmUser);

        return "/login";
    }
}

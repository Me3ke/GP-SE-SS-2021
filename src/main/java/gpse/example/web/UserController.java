package gpse.example.web;


import gpse.example.domain.users.ConfirmationToken;
import gpse.example.domain.users.ConfirmationTokenService;
import gpse.example.domain.users.User;
import gpse.example.domain.users.UserService;
import gpse.example.domain.users.PersonalData;
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


    @PostMapping("/users")
    public String signUp(@RequestBody UserSignUpCmd signUpUser) {

        User user = new User(signUpUser.getUsername(), signUpUser.getFirstname(),
            signUpUser.getLastname(), signUpUser.getPassword());
        userService.signUpUser(user);

        return "redirect:/login";
    }

    @GetMapping("/users/register")
    public String confirmMail(@RequestParam("token") String token) {

        System.out.println("confirm Mail Success");
        Optional<ConfirmationToken> optionalConfirmationToken
            = confirmationTokenService.findConfirmationTokenByToken(token);

        optionalConfirmationToken.ifPresent(userService::confirmUser);

        return "/login";
    }

    @GetMapping("/user/{*userID}/personal")
    public PersonalData showPersonalData(@PathVariable("userID") final String username) {
        return userService.getUser(username).getPersonalData();
    }

}

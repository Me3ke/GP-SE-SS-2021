package gpse.example.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gpse.example.domain.users.ConfirmationToken;
import gpse.example.domain.users.ConfirmationTokenService;
import gpse.example.domain.users.User;
import gpse.example.domain.users.UserService;
import gpse.example.domain.users.PersonalData;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


/**
 * UserController class.
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {

    private ObjectMapper mapper;
    private final UserService userService;

    private final ConfirmationTokenService confirmationTokenService;

    public UserController(UserService service, ConfirmationTokenService confService) {
        userService = service;
        confirmationTokenService = confService;
        mapper = new ObjectMapper();
    }


    @PostMapping("/users")
    public String signUp(@RequestBody UserSignUpCmd signUpUser) throws JsonProcessingException {
        JSONResponseObject response = new JSONResponseObject();
        response.setStatus(200);
        try {
            User user = userService.getUser(signUpUser.getUsername());
            response.setStatus(422);
        } catch (UsernameNotFoundException e ) {
            User user = new User(signUpUser.getUsername(), signUpUser.getFirstname(),
                signUpUser.getLastname(), signUpUser.getPassword());
            userService.signUpUser(user);
            return mapper.writeValueAsString(response);
        }
        return mapper.writeValueAsString(response);
    }

    @GetMapping("/users/register")
    public String confirmMail(@RequestParam("token") String token) {

        JSONConfirmationResponseObject response = new JSONConfirmationResponseObject();
        response.setAdminValidation(false);
        response.setStatus(200);
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

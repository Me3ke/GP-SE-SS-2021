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

import java.time.LocalDate;
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

        if (signUpUser.getUsername().isEmpty() || signUpUser.getPassword().isEmpty()) {
            response.setStatus(422);
            response.setMessage("username or password not specified");
            return mapper.writeValueAsString(response);
        } else {
            try {
                User user = userService.getUser(signUpUser.getUsername());

                response.setStatus(422);
                response.setMessage("username " + user.getUsername() + " already exists");
                return mapper.writeValueAsString(response);
            } catch (UsernameNotFoundException e) {
                User user = new User(signUpUser.getUsername(), signUpUser.getFirstname(),
                    signUpUser.getLastname(), signUpUser.getPassword());
                user.addRole("ROLE_USER");
                user.setPersonalData(signUpUser.getPersonalData());
                userService.signUpUser(user);
                response.setStatus(200);
                return mapper.writeValueAsString(response);
            }

        }
    }

    @GetMapping("/users/register")
    public String confirmMail(@RequestParam("token") String token) throws JsonProcessingException {

        JSONResponseObject response = new JSONResponseObject();

        Optional<ConfirmationToken> optionalConfirmationToken
            = confirmationTokenService.findConfirmationTokenByToken(token);

        if(optionalConfirmationToken.isEmpty()) {
            response.setStatus(423);
            response.setMessage("Already validated");
        } else if(confirmationTokenService.isExpired(optionalConfirmationToken.get())) {
            response.setStatus(422);
            response.setMessage("ConfirmationToken Expired");
        } else {
            optionalConfirmationToken.ifPresent(userService::confirmUser);
            response.setStatus(200);
            response.setMessage("Adminvalidation required:" + false );
        }
        return mapper.writeValueAsString(response);
    }

    @GetMapping("/user/{*userID}/personal")
    public PersonalData showPersonalData(@PathVariable("userID") final String username) {
        return userService.getUser(username).getPersonalData();
    }

}

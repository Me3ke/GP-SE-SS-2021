package gpse.example.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gpse.example.domain.users.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


/**
 * UserController class.
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {

    private static final int STATUS_CODE_OK = 200;
    private static final int STATUS_CODE_TOKEN_EXPIRED = 422;
    private static final int STATUS_CODE_TOKEN_DOESNT_EXIST = 423;
    private static final int STATUS_CODE_USER_EXISTS_ALREADY = 421;
    private static final int STATUS_CODE_MISSING_USERDATA = 420;
    private ObjectMapper mapper;
    private final UserService userService;
    private final PersonalDataService personalDataService;
    private final ConfirmationTokenService confirmationTokenService;

    /**
     * Constructor of UserController getting required services.
     * @param service Userservice Object
     * @param confService ConfirmationTokenService object
     * @param personalDataService PersonalDataService object
     */
    @Autowired
    public UserController(UserService service, ConfirmationTokenService confService,
                          PersonalDataService personalDataService) {
        userService = service;
        confirmationTokenService = confService;
        this.personalDataService = personalDataService;
        mapper = new ObjectMapper();
    }

    /**
     * Method to handle an registration and generating user object.
     * @param signUpUser the userdata getting from frontend
     * @return JSONResponse containing statusCode and a message
     * @throws JsonProcessingException thrown by mapper
     */
    @PostMapping("/users")
    public String signUp(@RequestBody UserSignUpCmd signUpUser) throws JsonProcessingException {
        JSONResponseObject response = new JSONResponseObject();
        if (signUpUser.getUsername().isEmpty() || signUpUser.getPassword().isEmpty()) {
            response.setStatus(STATUS_CODE_MISSING_USERDATA);
            response.setMessage("username or password not specified");
            return mapper.writeValueAsString(response);
        } else {
            try {
                User user = userService.getUser(signUpUser.getUsername());

                response.setStatus(STATUS_CODE_USER_EXISTS_ALREADY);
                response.setMessage("username " + user.getUsername() + " already exists");
                return mapper.writeValueAsString(response);
            } catch (UsernameNotFoundException e) {
                User user = new User(signUpUser.getUsername(), signUpUser.getFirstname(),
                    signUpUser.getLastname(), signUpUser.getPassword());
                user.addRole("ROLE_USER");
                PersonalData personalData = signUpUser.generatePersonalData();
                personalDataService.savePersonalData(personalData);
                user.setPersonalData(personalData);
                userService.signUpUser(user);
                response.setStatus(STATUS_CODE_OK);
                return mapper.writeValueAsString(response);
            }

        }
    }

    /**
     * Method for confirm an User and enable his*her account.
     * @param token the confirmationToken sended with Email
     * @return JSONResponse containing statusCode and a message
     * @throws JsonProcessingException thrown by mapper
     */
    @GetMapping("/users/register")
    public String confirmMail(@RequestParam("token") String token) throws JsonProcessingException {

        JSONResponseObject response = new JSONResponseObject();

        Optional<ConfirmationToken> optionalConfirmationToken
            = confirmationTokenService.findConfirmationTokenByToken(token);

        if (optionalConfirmationToken.isEmpty()) {
            response.setStatus(STATUS_CODE_TOKEN_DOESNT_EXIST);
            response.setMessage("Already validated or wrong Token");
        } else if (confirmationTokenService.isExpired(optionalConfirmationToken.get())) {
            response.setStatus(STATUS_CODE_TOKEN_EXPIRED);
            response.setMessage("ConfirmationToken Expired");
        } else {
            optionalConfirmationToken.ifPresent(userService::confirmUser);
            response.setStatus(STATUS_CODE_OK);
            response.setMessage("Adminvalidation required:" + false);
        }
        return mapper.writeValueAsString(response);
    }

    @GetMapping("/user/{*userID}/personal")
    public PersonalData showPersonalData(@PathVariable("userID") final String username) {
        return userService.getUser(username).getPersonalData();
    }

}

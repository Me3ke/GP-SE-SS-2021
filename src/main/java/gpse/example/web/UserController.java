package gpse.example.web;

import gpse.example.domain.users.*;

import gpse.example.util.email.MessageGenerationException;
import gpse.example.util.email.MessageService;
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
    private static final int STATUS_CODE_VALIDATION_FAILED = 424;
    private static final int STATUS_CODE_EMAIL_GENERATION_FAILED = 425;
    private static final String ADMINVALIDATION_REQUIRED = "Adminvalidation required:";
    private final UserService userService;
    private final PersonalDataService personalDataService;
    private final ConfirmationTokenService confirmationTokenService;
    private final MessageService messageService;

    /**
     * Constructor of UserController getting required services.
     * @param service Userservice Object
     * @param confService ConfirmationTokenService object
     * @param personalDataService PersonalDataService object
     * @param messageService the messageService to access the message table
     */
    @Autowired
    public UserController(UserService service, ConfirmationTokenService confService,
                          PersonalDataService personalDataService, MessageService messageService) {
        userService = service;
        confirmationTokenService = confService;
        this.personalDataService = personalDataService;
        this.messageService = messageService;
    }

    /**
     * Method to handle an registration and generating user object.
     * @param signUpUser the userdata getting from frontend
     * @return JSONResponse containing statusCode and a message
     */
    @PostMapping("/newUser")
    public JSONResponseObject signUp(@RequestBody UserSignUpCmd signUpUser) {
        JSONResponseObject response = new JSONResponseObject();
        if (signUpUser.getUsername().isEmpty() || signUpUser.getPassword().isEmpty()) {
            response.setStatus(STATUS_CODE_MISSING_USERDATA);
            response.setMessage("username or password not specified");
            return response;
        } else {
            try {
                User user = userService.getUser(signUpUser.getUsername());

                response.setStatus(STATUS_CODE_USER_EXISTS_ALREADY);
                response.setMessage("username " + user.getUsername() + " already exists");
                return response;
            } catch (UsernameNotFoundException e) {
                User user = new User(signUpUser.getUsername(), signUpUser.getFirstname(),
                    signUpUser.getLastname(), signUpUser.getPassword());
                user.addRole("ROLE_USER");
                PersonalData personalData = signUpUser.generatePersonalData();
                personalDataService.savePersonalData(personalData);
                user.setPersonalData(personalData);
                try {
                    userService.signUpUser(user);
                    response.setStatus(STATUS_CODE_OK);
                } catch (MessageGenerationException mge) {
                    userService.removeUser(user.getUsername());
                    messageService.removeMessage(mge.getThrownByMessageID());
                    response.setStatus(STATUS_CODE_EMAIL_GENERATION_FAILED);
                    response.setMessage("Error generating Confirmationmail. Try again later.");
                }
                return response;
            }

        }
    }

    /**
     * Method for confirm an User and enable his*her account.
     * @param token the confirmationToken sended with Email
     * @return JSONResponse containing statusCode and a message
     */
    @GetMapping("/newUser/register")
    public JSONResponseObject confirmMail(@RequestParam("token") String token) {

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
            final User user = optionalConfirmationToken.get().getUser();

            optionalConfirmationToken.ifPresent(userService::confirmUser);
            response.setStatus(STATUS_CODE_OK);

            if (user.getEmail().matches(".*@techfak\\.de")) {
                response.setMessage(ADMINVALIDATION_REQUIRED + false);
                userService.validateUser(optionalConfirmationToken.get().getUser());
            } else {
                try {
                    userService.infoNewExtUser(user);
                    response.setMessage(ADMINVALIDATION_REQUIRED + true);
                } catch (MessageGenerationException mge) {
                    messageService.removeMessage(mge.getThrownByMessageID());
                    response.setMessage(ADMINVALIDATION_REQUIRED + true + "\n"
                        + "an error occured please call systemadmin");
                    response.setStatus(STATUS_CODE_EMAIL_GENERATION_FAILED);

                    return response;
                }
            }
        }
        return response;
    }

    /**
     * Method to validate an useraccount by admin.
     * @param username the identifier of the useraccount that needs to be validated
     * @return SONResponse containing statusCode and a message
     */
    @GetMapping("/user/{userID}/validate/")
    public JSONResponseObject adminUserValidation(@PathVariable("userID") final String username) {

        JSONResponseObject response = new JSONResponseObject();
        User user = userService.getUser(username);
        userService.validateUser(user);

        if (user.isAdminValidated()) {
            response.setStatus(STATUS_CODE_OK);
        } else {
            response.setStatus(STATUS_CODE_VALIDATION_FAILED);
        }
        return response;
    }

    @GetMapping("/user/{userID}/personal")
    public PersonalData showPersonalData(@PathVariable("userID") final String username) {
        return userService.getUser(username).getPersonalData();
    }
    @GetMapping("/user/{userID}")
    public User showUser(@PathVariable("userID") final String username) {
        return userService.getUser(username);
    }

}

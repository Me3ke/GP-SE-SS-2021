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
    private static final int STATUS_CODE_VALIDATION_FAILED = 424;
    private static final String ADMINVALIDATION_REQUIRED = "Adminvalidation required:";
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
            final User user = optionalConfirmationToken.get().getUser();

            optionalConfirmationToken.ifPresent(userService::confirmUser);
            response.setStatus(STATUS_CODE_OK);

            if (user.getEmail().matches(".*@techfak\\.de")) {
                response.setMessage(ADMINVALIDATION_REQUIRED + false);
                userService.validateUser(optionalConfirmationToken.get().getUser());
            } else {
                response.setMessage(ADMINVALIDATION_REQUIRED + true);
                userService.infoNewExtUser(user);
            }
        }
        return mapper.writeValueAsString(response);
    }

    /**
     * Method to validate an useraccount by admin.
     * @param adminUsername the identifier of the admin account that validates the user
     * @param username the identifier of the useraccount that needs to be validated
     * @return SONResponse containing statusCode and a message
     * @throws JsonProcessingException thrown by mapper
     */
    @GetMapping("/users/{adminID}/validate/{userID}")
    public String adminUserValidation(@PathVariable("adminID") final String adminUsername,
                                      @PathVariable("userID") final String username)
        throws JsonProcessingException {

        JSONResponseObject response = new JSONResponseObject();
        User user = userService.getUser(username);
        User admin = userService.getUser(adminUsername);
        if (admin.getRoles().contains("ROLE_ADMIN")) {
            userService.validateUser(user);
        } else {
            response.setMessage("noAdmin");
        }

        if (user.isAdminValidated()) {
            response.setStatus(STATUS_CODE_OK);
        } else {
            response.setStatus(STATUS_CODE_VALIDATION_FAILED);
        }
        return mapper.writeValueAsString(response);
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

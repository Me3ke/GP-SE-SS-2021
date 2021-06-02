package gpse.example.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.samstevens.totp.exceptions.CodeGenerationException;
import dev.samstevens.totp.exceptions.QrGenerationException;
import gpse.example.domain.signature.StringToKeyConverter;
import gpse.example.domain.users.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
    private final StringToKeyConverter stringToKeyConverter;
    private final SecuritySettingsService securitySettingsService;

    /**
     * Constructor of UserController getting required services.
     * @param service Userservice Object
     * @param confService ConfirmationTokenService object
     * @param personalDataService PersonalDataService object
     * @param securitySettingsService SecuritySettingsService object
     */
    @Autowired
    public UserController(UserService service, ConfirmationTokenService confService,
                          PersonalDataService personalDataService,
                          SecuritySettingsService securitySettingsService) {
        userService = service;
        confirmationTokenService = confService;
        this.personalDataService = personalDataService;
        this.securitySettingsService = securitySettingsService;
        mapper = new ObjectMapper();
        stringToKeyConverter = new StringToKeyConverter();
    }

    /**
     * Method to handle an registration and generating user object.
     * @param signUpUser the userdata getting from frontend
     * @return JSONResponse containing statusCode and a message
     * @throws JsonProcessingException thrown by mapper
     */
    @PostMapping("/user")
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
    @GetMapping("/user/register")
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
    @GetMapping("/user/{adminID}/validate/{userID}")
    public String adminUserValidation(@PathVariable("adminID") final String adminUsername,
                                      @PathVariable("userID") final String username)
        throws JsonProcessingException {

        JSONResponseObject response = new JSONResponseObject();
        User user = userService.getUser(username);
        User admin = userService.getUser(adminUsername);
        if (admin.getRoles().contains("ROLE_ADMIN")) {
            userService.validateUser(user);
        } else {
            response.setMessage(adminUsername +  " is no Admin");
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

    /**
     * Put request to change the public key of the user.
     * @param publicKeyCmd contains the public key in a String format
     * @param username the identifier of the user account that needs to be updated
     */
    @PutMapping("/user/{userID}/publicKey")
    public void changePublicKey(@PathVariable("userID") final String username,
                                @RequestBody final PublicKeyCmd publicKeyCmd) {
        try {
            userService.getUser(username).setPublicKey(stringToKeyConverter.convertString(publicKeyCmd.getPublicKey()));
            System.out.println(userService.getUser(username).getPublicKey());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException exception) {
            exception.printStackTrace();
        }
    }

    @GetMapping("/user/{userID}/settings/2FAconfigurated")
    public boolean checkForTwoFAConfiguration(@PathVariable("userID") final String username) {
        return userService.getUser(username).getSecuritySettings().getSecret() != null;
    }

    /**
     * Get request for getting the qr-code as a byte array.
     * @param username the identifier of the user account that needs to be updated
     * @return the qr code as a byte array
     */
    @GetMapping("/user/{userID}/settings/qrCode")
    public QrCodeGetResponse getQRCode(@PathVariable("userID") final String username) {
        try {
            SecuritySettings securitySettings = userService.getUser(username).getSecuritySettings();
            securitySettings.generateSecret();
            byte[] temp = securitySettings.generateQRCode(username);
            securitySettingsService.saveSecuritySettings(securitySettings);
            return new QrCodeGetResponse(temp);
        } catch (QrGenerationException e) {
            e.printStackTrace();
        }
        return new QrCodeGetResponse(new byte[0]);
    }

    /**
     * the Method used to validate 2-Factor-Auth codes.
     * @param username the id of the relating user
     * @param code the code that should be validated
     * @return true/false
     */
    @PostMapping("/user/{userID}/settings/qrCodeCode")
    public Boolean validateCode(@PathVariable("userID") final String username,
                               @RequestBody final AuthCodeValidationRequest code) throws CodeGenerationException {
        System.out.println("was du möchtest");
        return userService.getUser(username).getSecuritySettings().verifyCode(code.getQrCodeCode());
    }

}

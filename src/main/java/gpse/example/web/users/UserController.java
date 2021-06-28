package gpse.example.web.users;

import dev.samstevens.totp.exceptions.CodeGenerationException;
import dev.samstevens.totp.exceptions.QrGenerationException;
import gpse.example.domain.users.*;
import gpse.example.util.email.*;
import gpse.example.web.AuthCodeValidationRequest;
import gpse.example.web.JSONResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import java.lang.reflect.InvocationTargetException;
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
    private static final String USERID = "userID";
    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final MessageService messageService;
    private final EmailTemplateService emailTemplateService;


    /**
     * Constructor of UserController getting required services.
     *
     * @param service                 Userservice Object
     * @param confService             ConfirmationTokenService object
     * @param messageService          the messageService to access the message table
     * @param emailTemplateService    used to find the basic template by name
     */
    @Autowired
    public UserController(final UserService service, final ConfirmationTokenService confService,
                          final MessageService messageService, final EmailTemplateService emailTemplateService) {
        userService = service;
        confirmationTokenService = confService;
        this.messageService = messageService;
        this.emailTemplateService = emailTemplateService;
    }

    /**
     * Method to handle an registration and generating user object.
     *
     * @param signUpUser the userdata getting from frontend
     * @return JSONResponse containing statusCode and a message
     */
    @PostMapping("/newUser")
    public JSONResponseObject signUp(final @RequestBody UserSignUpCmd signUpUser) throws MessagingException,
        InvocationTargetException, TemplateNameNotFoundException {
        final JSONResponseObject response = new JSONResponseObject();
        if (signUpUser.getUsername().isEmpty() || signUpUser.getPassword().isEmpty()) {
            response.setStatus(STATUS_CODE_MISSING_USERDATA);
            response.setMessage("username or password not specified");
            return response;
        } else {
            try {
                final User user = userService.getUser(signUpUser.getUsername());

                response.setStatus(STATUS_CODE_USER_EXISTS_ALREADY);
                response.setMessage("username " + user.getUsername() + " already exists");
                return response;
            } catch (UsernameNotFoundException e) {
                final User user = new User(signUpUser.getUsername(), signUpUser.getFirstname(),
                    signUpUser.getLastname(), signUpUser.getPassword());
                user.addRole("ROLE_USER");
                final PersonalData personalData = signUpUser.generatePersonalData();
                EmailTemplate standardTemplate =
                    emailTemplateService.findSystemTemplateByName("SignatureInvitationTemplate");
                user.addEmailTemplate(standardTemplate);
                user.setPersonalData(personalData);
                try {
                    userService.signUpUser(user);
                    response.setStatus(STATUS_CODE_OK);
                } catch (MessageGenerationException exc) {
                    userService.removeUser(user.getUsername());
                    messageService.removeMessage(exc.getThrownByMessageID());
                    response.setStatus(STATUS_CODE_EMAIL_GENERATION_FAILED);
                    response.setMessage("Error generating Confirmationmail. Try again later.");
                } catch (TemplateNameNotFoundException exc) {
                    response.setStatus(STATUS_CODE_EMAIL_GENERATION_FAILED);
                    response.setMessage("Template not Found");
                }
                return response;
            }

        }
    }

    /**
     * Method for confirm an User and enable his*her account.
     *
     * @param token the confirmationToken sended with Email
     * @return JSONResponse containing statusCode and a message
     */
    @GetMapping("/newUser/register")
    public JSONResponseObject confirmMail(final @RequestParam("token") String token) {

        final JSONResponseObject response = new JSONResponseObject();

        final Optional<ConfirmationToken> optionalConfirmationToken
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
                } catch (MessageGenerationException | TemplateNameNotFoundException mge) {
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
     *
     * @param username the identifier of the useraccount that needs to be validated
     * @return SONResponse containing statusCode and a message
     */
    @GetMapping("/user/{userID}/validate")
    public JSONResponseObject adminUserValidation(@PathVariable(USERID) final String username) {

        final JSONResponseObject response = new JSONResponseObject();
        final User user = userService.getUser(username);
        userService.validateUser(user);

        if (user.isAccountNonLocked()) {
            response.setStatus(STATUS_CODE_OK);
        } else {
            response.setStatus(STATUS_CODE_VALIDATION_FAILED);
        }
        return response;
    }

    @GetMapping("/user/{userID}/personal")
    public PersonalData showPersonalData(@PathVariable(USERID) final String username) {
        return userService.getUser(username).getPersonalData();
    }

    /**
     * the request handling for changing personal data.
     *
     * @param personalData the new personal data of the user stating the request
     * @param username     the username of the user stating the request
     */
    @PutMapping("user/{userID}/personal")
    public void setPersonalData(@RequestBody final PersonalData personalData,
                                @PathVariable(USERID) final String username) {
        final User user = userService.getUser(username);
        user.setPersonalData(personalData);
        userService.saveUser(user);
    }

    @GetMapping("/user/{userID}")
    public UserResponseObject showUser(@PathVariable(USERID) final String username) {
        return new UserResponseObject(userService.getUser(username));
    }

    /**
     * The method used to signalize that a user has been through his first login.
     *
     * @param username the ID of the user
     * @return the respone with statuscode 200
     */
    @PutMapping("/user/{userID}/firstLogin")
    public JSONResponseObject firstLogin(@PathVariable(USERID) final String username) {
        final User user = userService.getUser(username);
        user.setFirstLogin(true);
        userService.saveUser(user);
        final JSONResponseObject response = new JSONResponseObject();
        response.setStatus(STATUS_CODE_OK);
        return response;
    }

    /**
     * Put request to change the public key of the user.
     *
     * @param publicKeyCmd contains the public key in a String format
     * @param username     the identifier of the user account that needs to be updated
     * @return returns a JSONResponseObject that contains status code.
     */
    @PutMapping("/user/{userID}/publicKey")
    public JSONResponseObject changePublicKey(@PathVariable(USERID) final String username,
                                              @RequestBody final PublicKeyCmd publicKeyCmd) {
        final JSONResponseObject response = new JSONResponseObject();
        final User user = userService.getUser(username);
        if (user.getPublicKey() != null) {
            user.getArchivedPublicKeys().add(user.getPublicKey());
        }
        user.setPublicKey(publicKeyCmd.getPublicKey());
        userService.saveUser(user);
        response.setStatus(STATUS_CODE_OK);
        return response;
    }

    @GetMapping("/user/{userID}/settings/2FAconfigurated")
    public boolean checkForTwoFAConfiguration(@PathVariable(USERID) final String username) {
        return userService.getUser(username).getSecuritySettings().getSecret() != null;
    }

    /**
     * Get request for getting the qr-code as a byte array.
     *
     * @param username the identifier of the user account that needs to be updated
     * @return the qr code as a byte array
     */
    @GetMapping("/user/{userID}/settings/qrCode")
    public QrCodeGetResponse getQRCode(@PathVariable(USERID) final String username) {
        try {
            final SecuritySettings securitySettings = userService.getUser(username).getSecuritySettings();
            securitySettings.generateSecret();
            final byte[] temp = securitySettings.generateQRCode(username);
            userService.saveUser(userService.getUser(username));
            return new QrCodeGetResponse(temp);
        } catch (QrGenerationException e) {
            e.printStackTrace();
        }
        return new QrCodeGetResponse(new byte[0]);
    }

    /**
     * the Method used to validate 2-Factor-Auth codes.
     *
     * @param username the id of the relating user
     * @param code     the code that should be validated
     * @return true/false
     */
    @PostMapping("/user/{userID}/settings/qrCodeCode")
    public Boolean validateCode(@PathVariable(USERID) final String username,
                                @RequestBody final AuthCodeValidationRequest code) throws CodeGenerationException {
        return userService.getUser(username).getSecuritySettings().verifyCode(code.getQrCodeCode());
    }

    @GetMapping("/user/{userID}/settings/PKconfigurated")
    public Boolean checkIfKeyIsConfigurated(@PathVariable(USERID) final String username) {
        return userService.getUser(username).getPublicKey() != null;
    }

    /**
     * The request handler for the settings regarding a two-factor-login.
     *
     * @param username          the username of the user stating the request
     * @param settingTwoFacAuth true if the user wants a two-factor-login, false if not
     */
    @PutMapping("/user/{userID}/settings/twoFactorLogin")
    public void changeTwofaLoginSetting(@PathVariable(USERID) final String username,
                                        @RequestBody final SettingTwoFacAuth settingTwoFacAuth) {
        final User user = userService.getUser(username);
        final SecuritySettings securitySettings = user.getSecuritySettings();
        securitySettings.setTwoFactorLogin(Boolean.parseBoolean(settingTwoFacAuth.getSetting()));
        userService.saveUser(user);
    }

    @GetMapping("/user/{userID}/settings/twoFactorLogin")
    public Boolean getTwofaLoginSetting(@PathVariable(USERID) final String username) {
        return userService.getUser(username).getSecuritySettings().isTwoFactorLogin();
    }


    /**
     * The request handler for the settings regarding the image signature.
     *
     * @param username             the username of the user stating the request
     * @param imageSignatureToSend the request containing the image signature
     * @return the response containing the info if the request was successful or not
     */
    @PutMapping("/user/{userID}/settings/imageSignature")
    public JSONResponseObject changeImageSignature(@PathVariable(USERID) final String username,
                                                   @RequestBody final ImageSignatureToSend imageSignatureToSend) {
        final JSONResponseObject jsonResponseObject = new JSONResponseObject();
        final User user = userService.getUser(username);
        user.setImageSignature(imageSignatureToSend.getImageSignature());
        user.setImageSignatureType(imageSignatureToSend.getImageSignatureType());
        userService.saveUser(user);
        jsonResponseObject.setStatus(STATUS_CODE_OK);
        jsonResponseObject.setMessage("Successfully send image Signature");
        return jsonResponseObject;
    }

    @GetMapping("/user/{userID}/settings/imageSignature")
    public ImageSignatureToSend getImageSignature(@PathVariable(USERID) final String username) {
        return new ImageSignatureToSend(userService.getUser(username).getImageSignature(),
            userService.getUser(username).getImageSignatureType());
    }
}

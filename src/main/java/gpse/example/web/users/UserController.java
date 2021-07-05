package gpse.example.web.users;

import dev.samstevens.totp.exceptions.CodeGenerationException;
import dev.samstevens.totp.exceptions.QrGenerationException;
import gpse.example.domain.security.SecurityConstants;
import gpse.example.domain.users.*;
import gpse.example.util.email.*;
import gpse.example.util.email.trusteddomain.DomainSetterService;
import gpse.example.web.tokens.ConfirmationToken;
import gpse.example.web.tokens.ConfirmationTokenService;
import gpse.example.web.tokens.ResetPasswordToken;
import gpse.example.web.tokens.ResetPasswordTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import gpse.example.web.AuthCodeValidationRequest;
import gpse.example.web.JSONResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private static final int STATUS_CODE_WRONG_ROLE = 227;
    private static final int STATUS_CODE_USER_DOESNT_EXIST = 228;
    private static final int STATUS_CODE_WRONG_USER = 229;
    private static final String ADMINVALIDATION_REQUIRED = "Adminvalidation required:";
    private static final String USERID = "userID";
    private static final String ROLE_USER = "ROLE_USER";
    private static final String USER_NOT_FOUND = "User not Found";
    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final ResetPasswordTokenService resetPasswordTokenService;
    private final MessageService messageService;
    private final EmailTemplateService emailTemplateService;
    private final SMTPServerHelper smtpServerHelper;
    private final DomainSetterService domainSetterService;
    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Lazy
    @Autowired
    private SecurityConstants securityConstants;


    /**
     * Constructor of UserController getting required services.
     *
     * @param service                   Userservice Object
     * @param confService               ConfirmationTokenService object
     * @param messageService            the messageService to access the message table
     * @param emailTemplateService      used to find the basic template by name
     * @param resetPasswordTokenService Service for the resetPasswordToken
     * @param smtpServerHelper          smtpserverhelper to send emails
     * @param domainSetterService       Service to store the domain settings
     */
    @Autowired
    public UserController(final UserService service, final ConfirmationTokenService confService,
                          final MessageService messageService, final EmailTemplateService emailTemplateService,
                          final ResetPasswordTokenService resetPasswordTokenService,
                          final SMTPServerHelper smtpServerHelper,
                          final DomainSetterService domainSetterService) {
        userService = service;
        confirmationTokenService = confService;
        this.messageService = messageService;
        this.emailTemplateService = emailTemplateService;
        this.resetPasswordTokenService = resetPasswordTokenService;
        this.smtpServerHelper = smtpServerHelper;
        this.domainSetterService = domainSetterService;
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
                user.addRole(ROLE_USER);
                final PersonalData personalData = signUpUser.generatePersonalData();
                final EmailTemplate standardTemplate =
                    emailTemplateService.findSystemTemplateByName("SignatureInvitationTemplate");
                final EmailTemplate newTemplate = new EmailTemplate(standardTemplate.getHtmlTemplateBody(),
                    standardTemplate.getSubject(), standardTemplate.getName(), false);
                user.addEmailTemplate(newTemplate);
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

            if (user.getEmail().matches(domainSetterService.getDomainSettings().get(0).getTrustedMailDomain())) {
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
     * Method to chenge password as logged in User.
     *
     * @param password the new password
     * @param token    JWT token which identifies the user
     * @return jsonResponse with statuscode
     */
    @PutMapping("/user/password/change")
    public JSONResponseObject changePassword(@RequestParam("password") final String password,
                                             @RequestHeader final String token) {
        final JSONResponseObject jsonResponseObject = new JSONResponseObject();

        final byte[] signingKey = securityConstants.getJwtSecret().getBytes();
        final Jws<Claims> parsedToken = Jwts.parserBuilder()
            .setSigningKey(signingKey).build()
            .parseClaimsJws(token.replace(securityConstants.getTokenPrefix(), "").strip());

        try {
            final User user = userService.getUser(parsedToken.getBody().getSubject());
            if (user.getRoles().contains(ROLE_USER)) {
                user.setPassword(passwordEncoder.encode(password));
                jsonResponseObject.setStatus(STATUS_CODE_OK);
            } else {
                //TODO Fehlermelden? DOch Responseobject nutzen
                jsonResponseObject.setStatus(STATUS_CODE_WRONG_ROLE);
                jsonResponseObject.setMessage("User has not role user");
            }
            return jsonResponseObject;

        } catch (UsernameNotFoundException unfe) {
            //TODO Fehlermelden? DOch Responseobject nutzen
            jsonResponseObject.setStatus(STATUS_CODE_USER_DOESNT_EXIST);
            jsonResponseObject.setMessage(USER_NOT_FOUND);
            return jsonResponseObject;
        }
    }

    /**
     * GetRequest to get an Token via email to Reset a password.
     *
     * @param userId the requesting users id
     * @return jsonResponse with statuscode
     */
    @GetMapping("/user/{userId}/password/reset")
    public JSONResponseObject sendResetPasswordEmail(@PathVariable("userId") final String userId) {
        final JSONResponseObject jsonResponseObject = new JSONResponseObject();
        try {
            final User user = userService.getUser(userId);
            final ResetPasswordToken resetPasswordToken = new ResetPasswordToken(user.getEmail());
            final ResetPasswordToken savedToken = resetPasswordTokenService.saveResetPasswordToken(resetPasswordToken);
            try {
                final TemplateDataContainer emailContainer = new TemplateDataContainer();
                final EmailTemplate template = emailTemplateService.findSystemTemplateByName("ResetPasswordTemplate");
                emailContainer.setFirstNameReciever(user.getFirstname());
                emailContainer.setLastNameReciever(user.getLastname());
                emailContainer.setLink("http://localhost:8080/de/login/reset/" + savedToken.getToken());
                smtpServerHelper.sendTemplatedEmail(user.getEmail(), template,
                    emailContainer, Category.SYSTEM, null);
                jsonResponseObject.setStatus(STATUS_CODE_OK);
                return jsonResponseObject;
            } catch (MessageGenerationException | TemplateNameNotFoundException mge) {
                resetPasswordTokenService.deleteResetPasswordToken(savedToken.getId());
                jsonResponseObject.setStatus(STATUS_CODE_EMAIL_GENERATION_FAILED);
                return jsonResponseObject;
            }
        } catch (UsernameNotFoundException unfe) {
            jsonResponseObject.setStatus(STATUS_CODE_USER_DOESNT_EXIST);
            jsonResponseObject.setMessage(USER_NOT_FOUND);
            return jsonResponseObject;
        }
    }

    /**
     * GetResponse Method to set a new password.
     * call api/user/newpassword?password={password}&token={token}
     *
     * @param password new password
     * @param token    token which references the user who want to change password
     * @return jsonResponse with statuscode
     */
    @GetMapping("/user/newpassword")
    public JSONResponseObject resetPassword(@RequestParam("password") final String password,
                @RequestParam("token") final String token) {
                    final JSONResponseObject jsonResponseObject = new JSONResponseObject();
                    final Optional<ResetPasswordToken> optionalResetPasswordToken
                            = resetPasswordTokenService.findResetPasswordTokenByToken(token);

                    try {
                        if (optionalResetPasswordToken.isEmpty()) {
                            jsonResponseObject.setStatus(STATUS_CODE_TOKEN_DOESNT_EXIST);
                            return jsonResponseObject;
                        }
                        final User user = userService.getUser(optionalResetPasswordToken.get().getUserId());

                        if (resetPasswordTokenService.isExpired(optionalResetPasswordToken.get())) {
                            jsonResponseObject.setStatus(STATUS_CODE_TOKEN_EXPIRED);
                            return jsonResponseObject;
                        } else {
                            user.setPassword(passwordEncoder.encode(password));
                userService.saveUser(user);
                jsonResponseObject.setStatus(STATUS_CODE_OK);
                return jsonResponseObject;
            }

        } catch (UsernameNotFoundException unfe) {
            jsonResponseObject.setStatus(STATUS_CODE_USER_DOESNT_EXIST);
            return jsonResponseObject;
        }
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

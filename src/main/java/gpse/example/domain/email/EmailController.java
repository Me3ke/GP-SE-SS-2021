package gpse.example.domain.email;

import gpse.example.domain.users.User;
import gpse.example.domain.users.UserService;
import gpse.example.domain.email.trusteddomain.DomainSetter;
import gpse.example.domain.email.trusteddomain.DomainSetterService;
import gpse.example.domain.email.trusteddomain.DomainSettingsGetResponse;
import gpse.example.domain.email.trusteddomain.DomainSettingsPutRequest;
import gpse.example.web.JSONResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Email COntrollerclass.
 */
@RestController
@CrossOrigin("http://localhost:8088")
@RequestMapping("/api")
public class EmailController {
    private static final int STATUS_CODE_OK = 200;
    private static final int STATUS_CODE_WRONG_ROLE = 227;
    private static final String SUCCESS_MESSAGE = "Success";
    private static final String ADMIN_VALIDATION_REQUIRED = "You are not an admin";

    private static final String USER_ID = "userId";

    @Autowired
    private EmailTemplateService emailTemplateService;
    @Autowired
    private UserService userService;
    @Autowired
    private DomainSetterService domainSetterService;


    @GetMapping("/user/{userId}/templates")
    public List<EmailTemplate> getUserTemplates(@PathVariable(USER_ID) final String userId) {
        final User user = userService.getUser(userId);
        return user.getEmailTemplates();
    }

    /**
     * postmapping to set a new template.
     * @param userId userId
     * @param template the templateBody
     */
    @PostMapping("/user/{userId}/templates")
    public void setEmailTemplate(@PathVariable(USER_ID) final String userId,
                                 @RequestBody final TemplatePutRequest template) {
        final User user = userService.getUser(userId);
        user.addEmailTemplate(new EmailTemplate(template.getHtmlBody(), template.getSubject(), template.getName(),
            false));
        userService.saveUser(user);
    }

    /**
     * request to Delete a Template of a user.
     * @param userId user who Owns Template
     * @param templateId id of the Template
     */
    @DeleteMapping("/user/{userId}/templates/{templateId}")
    public void deleteEmailTemplate(@PathVariable(EmailController.USER_ID) final String userId,
                                    @PathVariable("templateId") final long templateId) {
        final User user = userService.getUser(userId);
        for (final EmailTemplate temp:user.getEmailTemplates()) {
            if (temp.getTemplateID() == templateId && !temp.isSystem()) {
                user.getEmailTemplates().remove(temp);
                emailTemplateService.deleteTemplate(templateId);
                break;
            }
        }
        userService.saveUser(user);
    }

    /**
     * PutMapping for reworked templates.
     * @param userId user who Owns Template
     * @param templateId id of the Template
     * @param template the new template data
     */
    @PutMapping("/user/{userId}/templates/{templateId}")
    public void updateEmailTemplate(@PathVariable(EmailController.USER_ID) final String userId,
                                    @PathVariable("templateId") final long templateId,
                                    @RequestBody final TemplatePutRequest template) {

        final User user = userService.getUser(userId);
        for (final EmailTemplate temp : user.getEmailTemplates()) {
            if (temp.getTemplateID() == templateId) {
                temp.setHtmlTemplateBody(template.getHtmlBody());
                temp.setSubject(template.getSubject());
                temp.setName(template.getName());
                break;
            }
        }
        userService.saveUser(user);
    }

    /**
     * Method to get the trusted domain in the frontend.
     * @param token the jwt token
     * @return json Response object
     */

    @GetMapping("/email/settings/trustedDomain")
    public JSONResponseObject getTrustedDomainSettings(@RequestParam("token") final String token) {
        final JSONResponseObject jsonResponseObject = new JSONResponseObject();
        if (userService.checkIfAdmin(token.substring(6))) {
            jsonResponseObject.setStatus(STATUS_CODE_OK);
            jsonResponseObject.setMessage(domainSetterService.getDomainSettings().get(0).getTrustedMailDomain());
        } else {
            jsonResponseObject.setStatus(STATUS_CODE_WRONG_ROLE);
            jsonResponseObject.setMessage(ADMIN_VALIDATION_REQUIRED);
        }
        return jsonResponseObject;
    }

    /**
     * PutMapping for reworked templates.
     * @param token the jwt token
     * @param domain the settings to be applied
     * @return if the request was successful
     */
    @PutMapping("/email/settings/trustedDomain")
    public JSONResponseObject updateTrustedDomain(@RequestParam("token") final String token,
                                                  @RequestParam("domain") final String domain) {

        final JSONResponseObject jsonResponseObject = new JSONResponseObject();
        if (userService.checkIfAdmin(token.substring(6))) {
            final DomainSetter domainSetter = domainSetterService.getDomainSettings().get(0);
            domainSetter.setTrustedMailDomain(domain);
            domainSetterService.saveDomainSettings(domainSetter);
            jsonResponseObject.setStatus(STATUS_CODE_OK);
            jsonResponseObject.setMessage(SUCCESS_MESSAGE);
        } else {
            jsonResponseObject.setStatus(STATUS_CODE_WRONG_ROLE);
            jsonResponseObject.setMessage(ADMIN_VALIDATION_REQUIRED);
        }
        return jsonResponseObject;
    }

    /**
     * The request responsible for sending the domain settings to the frontend.
     * @param token the jwt token
     * @return the domain settings
     */
    @GetMapping("email/settings")
    public DomainSettingsGetResponse getDomainSettings(@RequestParam("token") final String token) {

        if (userService.checkIfAdmin(token.substring(6))) {
            final DomainSetter domainSetter = domainSetterService.getDomainSettings().get(0);
            return new DomainSettingsGetResponse(domainSetter.getHost(), domainSetter.getPort(),
                    domainSetter.getUsername(), domainSetter.isMailSMTPAuth(),
                    domainSetter.isMailSMTPStartTLSEnable());
        } else {
            return null;
        }
    }

    /**
     * The request responsible for sending the domain settings to the backend.
     * @param token the jwt token
     * @param domainSettingsPutRequest the settings to be changed
     * @return if the request was successful or not
     */
    @PutMapping("email/settings")
    public JSONResponseObject updateSMTPSettings(@RequestParam("token") final String token,
                                                 @RequestBody final DomainSettingsPutRequest domainSettingsPutRequest) {
        final JSONResponseObject jsonResponseObject = new JSONResponseObject();
        if (userService.checkIfAdmin(token.substring(6))) {
            final DomainSetter domainSetter = domainSetterService.getDomainSettings().get(0);
            domainSetter.setHost(domainSettingsPutRequest.getHost());
            domainSetter.setPort(domainSettingsPutRequest.getPort());
            domainSetter.setPassword(domainSettingsPutRequest.getPassword());
            domainSetter.setMailSMTPAuth(domainSettingsPutRequest.isMailSMTPAuth());
            domainSetter.setMailSMTPStartTLSEnable(domainSettingsPutRequest.isMailSMTPStartTLSEnable());
            domainSetterService.saveDomainSettings(domainSetter);
            jsonResponseObject.setStatus(STATUS_CODE_OK);
            jsonResponseObject.setMessage(SUCCESS_MESSAGE);
        } else {
            jsonResponseObject.setStatus(STATUS_CODE_WRONG_ROLE);
            jsonResponseObject.setMessage(ADMIN_VALIDATION_REQUIRED);
        }
        return jsonResponseObject;
    }
}

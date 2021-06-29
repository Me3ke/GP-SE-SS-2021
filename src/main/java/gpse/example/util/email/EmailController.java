package gpse.example.util.email;

import gpse.example.domain.users.User;
import gpse.example.domain.users.UserService;
import gpse.example.util.email.trustedDomain.DomainSetter;
import gpse.example.util.email.trustedDomain.DomainSetterService;
import gpse.example.util.email.trustedDomain.DomainSettingsGetResponse;
import gpse.example.util.email.trustedDomain.DomainSettingsPutRequest;
import gpse.example.web.JSONResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
    private static final String SUCCESS_MESSAGE = "Success";
    @Lazy
    @Autowired
    private SMTPServerHelper smtpServerHelper;
    @Autowired
    private EmailTemplateService emailTemplateService;
    @Autowired
    private UserService userService;
    @Autowired
    private DomainSetterService domainSetterService;

    @GetMapping("/user/{userId}/templates")
    public List<EmailTemplate> getUserTemplates(@PathVariable("userId") final String userId) {
        User user = userService.getUser(userId);
        return user.getEmailTemplates();
    }

    /**
     * postmapping to set a new template.
     * @param userId userId
     * @param template the templateBody
     */
    @PostMapping("/user/{userId}/templates")
    public void setEmailTemplate(@PathVariable("userId") final String userId,
                                 @RequestParam("template") final String template) {
        User user = userService.getUser(userId);
        user.addEmailTemplate(new EmailTemplate(template, "ELSA - Signatureinladung/ELSA - signature invitation",
            "name", false));
        userService.saveUser(user);
    }

    /**
     * request to Delete a Template of a user.
     * @param userId user who Owns Template
     * @param templateId id of the Template
     */
    @DeleteMapping("/user/{userId}/templates/{templateId}")
    public void deleteEmailTemplate(@PathVariable("userId") final String userId,
                                    @PathVariable("templateId") final long templateId) {
        User user = userService.getUser(userId);
        for (EmailTemplate temp:user.getEmailTemplates()) {
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
     * @param reworkedTemplate the reworked TemplateBody
     */
    @PutMapping("/user/{userId}/templates/{templateId}")
    public void updateEmailTemplate(@PathVariable("userId") final String userId,
                                    @PathVariable("templateId") final long templateId,
                                    @RequestParam("reworkedTemplate") final String reworkedTemplate) {
        User user = userService.getUser(userId);
        for (EmailTemplate temp : user.getEmailTemplates()) {
            if (temp.getTemplateID() == templateId) {
                temp.setHtmlTemplateBody(reworkedTemplate);
                break;
            }
        }
        userService.saveUser(user);
    }


    @GetMapping("email/settings/trustedDomain")
    public String getTrustedDomainSettings() {
        return domainSetterService.getDomainSettings().get(0).getTrustedMailDomain();
    }

    /**
     * PutMapping for reworked templates.
     * @param domain the settings to be applied
     * @return if the request was successful
     */
    @PutMapping("/email/settings/trustedDomain")
    public JSONResponseObject updateTrustedDomain(@RequestParam("domain") final String domain) {
        JSONResponseObject jsonResponseObject = new JSONResponseObject();
        DomainSetter domainSetter = domainSetterService.getDomainSettings().get(0);
        domainSetter.setTrustedMailDomain(domain);
        domainSetterService.saveDomainSettings(domainSetter);
        jsonResponseObject.setStatus(STATUS_CODE_OK);
        jsonResponseObject.setMessage(SUCCESS_MESSAGE);
        return jsonResponseObject;
    }

    /**
     * The request responsible for sending the domain settings to the frontend.
     * @return the domain settings
     */
    @GetMapping("email/settings")
    public DomainSettingsGetResponse getDomainSettings() {
        DomainSetter domainSetter = domainSetterService.getDomainSettings().get(0);
        return new DomainSettingsGetResponse(domainSetter.getHost(), domainSetter.getPort(),
                domainSetter.getUsername(), domainSetter.isMailSMPTAuth(),
                domainSetter.isMailSMTPStartTLSEnable());
    }

    /**
     * The request responsible for sending the domain settings to the backend.
     * @param domainSettingsPutRequest the settings to be changed
     * @return if the request was successful or not
     */
    @PutMapping("email/settings")
    public JSONResponseObject updateSMTPSettings(@RequestBody DomainSettingsPutRequest domainSettingsPutRequest) {
        JSONResponseObject jsonResponseObject = new JSONResponseObject();
        DomainSetter domainSetter = domainSetterService.getDomainSettings().get(0);
        domainSetter.setHost(domainSettingsPutRequest.getHost());
        domainSetter.setPort(domainSettingsPutRequest.getPort());
        domainSetter.setPassword(domainSettingsPutRequest.getPassword());
        domainSetter.setMailSMPTAuth(domainSettingsPutRequest.isMailSMPTAuth());
        domainSetter.setMailSMTPStartTLSEnable(domainSettingsPutRequest.isMailSMTPStartTLSEnable());
        domainSetterService.saveDomainSettings(domainSetter);
        jsonResponseObject.setStatus(STATUS_CODE_OK);
        jsonResponseObject.setMessage(SUCCESS_MESSAGE);
        return jsonResponseObject;
    }
}

package gpse.example.util.email;

import gpse.example.domain.users.User;
import gpse.example.domain.users.UserService;
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

    @Lazy
    @Autowired
    private SMTPServerHelper smtpServerHelper;
    @Autowired
    private EmailTemplateService emailTemplateService;
    @Autowired
    private UserService userService;

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
        for (EmailTemplate temp:user.getEmailTemplates()) {
            if (temp.getTemplateID() == templateId) {
                temp.setHtmlTemplateBody(reworkedTemplate);
                break;
            }
        }
        userService.saveUser(user);
    }
}

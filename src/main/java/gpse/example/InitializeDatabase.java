package gpse.example;


import gpse.example.domain.corporatedesign.CorporateDesign;
import gpse.example.domain.corporatedesign.CorporateDesignService;
import gpse.example.domain.exceptions.CorporateDesignNotFoundException;
import gpse.example.domain.users.PersonalData;
import gpse.example.domain.users.User;
import gpse.example.domain.users.UserService;
import gpse.example.domain.email.BasicHtmlTemplates;
import gpse.example.domain.email.EmailTemplate;
import gpse.example.domain.email.EmailTemplateService;
import gpse.example.domain.exceptions.TemplateNameNotFoundException;
import gpse.example.domain.email.trusteddomain.DomainSetter;
import gpse.example.domain.email.trusteddomain.DomainSetterService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * This class initializes the Database with some example data on serverstart.
 */
@Service
public class InitializeDatabase implements InitializingBean {

    private static final String BERLINER_STRASSE = "Berliner Straße";
    private static final String USERNAME = "hans.schneider@mail.de";
    private static final String ADMINNAME = "Hans.Schneider.test@gmail.com";
    private static final String LIEBEFELD = "Liebefeld";
    private static final String DEUTSCHLAND = "Deutschland";
    private static final String ROLE_USER = "ROLE_USER";
    private static final String PASSWORD = "{bcrypt}$2y$12$DdtBOd4cDqlvMGXPoNr9L.6YkszYXn364x172BKabx3ucOiYUmTfG";
    private static final String COLOR_ONE = "#f5f5f5";
    private static final String COLOR_TWO = "#D8D8D9";
    private static final String COLOR_THREE = "#ACACAC";
    private static final String COLOR_FOUR = "#ababab";
    private static final String COLOR_FIVE = "#000000";
    private static final String[] DEFAULT_COLORS = {"#47525E", "#436495", COLOR_ONE, COLOR_TWO, COLOR_THREE, COLOR_FOUR,
        "#E5E5E5", "#C9C9C9", "#FFE3E3", "#FFBABA", "#C93A3A", "#a22c2c", COLOR_ONE, COLOR_FIVE, COLOR_TWO, COLOR_ONE,
        "#23292f", COLOR_THREE, COLOR_TWO, COLOR_FOUR, "#070809", "#788796", "#d25959", "#b02f2f", "#651b1b",
        "#501515", "#363f48", COLOR_FIVE};
    private static final String ELSA_SIGNATURE_INVITATION_SUBJECT =
        "ELSA - Signatureinladung/ELSA - signature invitation";
    private static final int STANDARD_PORT = 587;

    private final UserService userService;
    private final CorporateDesignService corporateDesignService;
    private final EmailTemplateService emailTemplateService;
    private final DomainSetterService domainSetterService;

    /**
     * The standard constructor for the class initializing the database.
     *
     * @param userService            used for saving user-objects in the database.
     * @param corporateDesignService used for saving the corporate design in the database.
     * @param emailTemplateService   used for saving the emailTemplates in the database.
     * @param domainSetterService    used for saving the domainSettings in the database
     */
    @Autowired
    public InitializeDatabase(final UserService userService,
                              final CorporateDesignService corporateDesignService,
                              final EmailTemplateService emailTemplateService,
                              final DomainSetterService domainSetterService) {
        this.userService = userService;
        this.corporateDesignService = corporateDesignService;
        this.emailTemplateService = emailTemplateService;
        this.domainSetterService = domainSetterService;
    }

    @Override
    public void afterPropertiesSet() {
        try {
            corporateDesignService.getCorporateDesign(1L);
        } catch (CorporateDesignNotFoundException exception) {
            final CorporateDesign defaultDesign = new CorporateDesign(DEFAULT_COLORS, new byte[0], new byte[0]);
            defaultDesign.setLogo(new byte[0], "");
            defaultDesign.setLogoDark(new byte[0], "");
            corporateDesignService.saveCorporateDesign(defaultDesign);
        }

        saveEmailTemplate(BasicHtmlTemplates.ADMIN_VALIDATION_TEMPLATE,
            "ELSA - Nutzer Validierung/ELSA - User Validation", "AdminValidationTemplate");
        saveEmailTemplate(BasicHtmlTemplates.RESET_PASSWORD_TEMPLATE,
            "ELSA - Passwort Zurücksetzen/ELSA - reset Password", "ResetPasswordTemplate");
        saveEmailTemplate(BasicHtmlTemplates.CONFIRMATION_TEMPLATE, "ELSA - Registrierung/ELSA - registration",
            "ConfirmationTemplate");
        saveEmailTemplate(BasicHtmlTemplates.REMINDER_TEMPLATE,
            "ELSA - Signaturerinnerung/ELSA - Signature reminder", "ReminderTemplate");
        saveEmailTemplate(BasicHtmlTemplates.NEW_VERSION_TEMPLATE,
            "ELSA - Dokument wurde Aktualisiert/ELSA - document is updated", "NewVersionTemplate");
        saveEmailTemplate(BasicHtmlTemplates.GUEST_INVITATION_TEMPLATE,
            ELSA_SIGNATURE_INVITATION_SUBJECT, "GuestInvitationTemplate");
        saveEmailTemplate(BasicHtmlTemplates.PROCESS_FINISHED_TEMPLATE,
            "ELSA - Signaturprozess Abgeschlossen/ELSA - signature process finished",
            "ProcessFinishedTemplate");
        final EmailTemplate template = saveEmailTemplateWithReturnValue(BasicHtmlTemplates
            .SIGNATURE_INVITATION_TEMPLATE, ELSA_SIGNATURE_INVITATION_SUBJECT, "SignatureInvitationTemplate");
        saveEmailTemplate(BasicHtmlTemplates.ADVANCED_GUEST_INVITATION_TEMPLATE,
            ELSA_SIGNATURE_INVITATION_SUBJECT, "AdvancedGuestInvitationTemplate");
        saveEmailTemplate(BasicHtmlTemplates.NEW_COMMENT_TEMPLATE, "ELSA - neuer Kommentar/ELSA - new comment",
            "NewCommentTemplate");
        saveEmailTemplate(BasicHtmlTemplates.ANSWER_COMMENT_TEMPLATE, "ELSA - Kommentar wurde Beantwortet/ELSA -"
            + " comment answered", "AnswerCommentTemplate");

        try {
            userService.getUser(USERNAME);
        } catch (UsernameNotFoundException ex) {
            final PersonalData personalData = new PersonalData(BERLINER_STRASSE, 2, 12_312,
                LIEBEFELD, DEUTSCHLAND, LocalDate.now(), "3213145");
            final User user = new User(USERNAME,
                "Hans",
                "Schneider", PASSWORD);
            user.setPersonalData(personalData);
            user.addRole(ROLE_USER);
            user.setEnabled(true);
            user.setAccountNonLocked(true);
            user.addEmailTemplate(new EmailTemplate(template.getHtmlTemplateBody(), template.getSubject(),
                template.getName(), false));
            user.setToSeenByAdmin();
            userService.saveUser(user);
        }
        try {
            userService.getUser(ADMINNAME);
        } catch (UsernameNotFoundException ex) {
            final PersonalData personalData = new PersonalData(BERLINER_STRASSE, 3, 12_312,
                    LIEBEFELD, DEUTSCHLAND, LocalDate.now(), "3217145");
            final User user = new User(ADMINNAME,
                    "Ruediger",
                    "Spieler", PASSWORD);
            user.addRole(ROLE_USER);
            user.addRole("ROLE_ADMIN");
            user.setEnabled(true);
            user.setAccountNonLocked(true);
            user.setPersonalData(personalData);
            user.addEmailTemplate(template);
            user.setToSeenByAdmin();
            userService.saveUser(user);
        }
        if (domainSetterService.isEmpty()) {
            setDomainSettings();
        }
    }

    private void saveEmailTemplate(final String template, final String subject, final String name) {
        try {
            emailTemplateService.findSystemTemplateByName(name);
        } catch (TemplateNameNotFoundException tne) {
            final EmailTemplate emailTemplate = new EmailTemplate(template, subject, name, true);
            emailTemplateService.saveEmailTemplate(emailTemplate);
        }
    }

    private EmailTemplate saveEmailTemplateWithReturnValue(final String template, final String subject,
                                                           final String name) {
        try {
            return emailTemplateService.findSystemTemplateByName(name);
        } catch (TemplateNameNotFoundException tne) {
            return new EmailTemplate(template, subject, name, true);
        }
    }

    private void setDomainSettings() {
        final DomainSetter domainSetter = new DomainSetter("smtp.gmail.com", STANDARD_PORT,
                "elsabeispiel@gmail.com", "1234elsaSuper", true,
                true, ".*@techfak\\.de");
        domainSetterService.saveDomainSettings(domainSetter);
    }

}

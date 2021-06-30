package gpse.example;


import gpse.example.domain.corporatedesign.CorporateDesign;
import gpse.example.domain.corporatedesign.CorporateDesignService;
import gpse.example.domain.documents.*;
import gpse.example.domain.envelopes.Envelope;
import gpse.example.domain.envelopes.EnvelopeService;
import gpse.example.domain.exceptions.CorporateDesignNotFoundException;
import gpse.example.domain.exceptions.CreatingFileException;
import gpse.example.domain.exceptions.DocumentNotFoundException;
import gpse.example.domain.signature.ProtoSignatory;
import gpse.example.domain.users.*;
import gpse.example.util.email.BasicHtmlTemplates;
import gpse.example.util.email.EmailTemplate;
import gpse.example.util.email.EmailTemplateService;
import gpse.example.util.email.TemplateNameNotFoundException;
import gpse.example.web.documents.DocumentPutRequest;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This class initializes the Database with some example data on serverstart.
 */
@Service
public class InitializeDatabase implements InitializingBean {

    private static final String BERLINER_STRASSE = "Berliner Straße";
    private static final String PROGRAM_PATH = "Programme.pdf";
    private static final String PLAN_PATH = "Essensplan.txt";
    private static final String USERNAME = "hans.schneider@mail.de";
    private static final String ADMINNAME = "Hans.Schneider.test@gmail.com";
    private static final String DOUBLE_BACKSLASH = "\\.";
    private static final long ID_THREE = 3L;
    private static final long ID_FOUR = 4L;
    private static final long ID_FIVE = 5L;
    private static final long ID_SIX = 6L;
    private static final long ID_SEVEN = 7L;
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
    private static final int THREEEE = 3;
    private static final int POST_CODE = 12312;
    private final UserService userService;
    private final DocumentService documentService;
    private final EnvelopeService envelopeService;
    private final CorporateDesignService corporateDesignService;
    private final EmailTemplateService emailTemplateService;

    /**
     * The standard constructor for the class initializing the database.
     *
     * @param userService            used for saving user-objects in the database.
     * @param documentService        used for saving document-objects in the database.
     * @param envelopeService        used for saving envelope-objects in the database.
     * @param corporateDesignService used for saving the corporate design in the database.
     * @param emailTemplateService   used for saving the emailTemplates in the database.
     */
    @Autowired
    public InitializeDatabase(final UserService userService, final DocumentService documentService,
                              final EnvelopeService envelopeService,
                              final CorporateDesignService corporateDesignService,
                              final EmailTemplateService emailTemplateService) {
        this.userService = userService;
        this.documentService = documentService;
        this.envelopeService = envelopeService;
        this.corporateDesignService = corporateDesignService;
        this.emailTemplateService = emailTemplateService;
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
            user.addEmailTemplate(template);
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
            userService.saveUser(user);
        }



        /*final List<Long> documentIDs = new ArrayList<>();
        final List<String> documentPaths = new ArrayList<>();
        documentIDs.add(1L);
        documentPaths.add(PROGRAM_PATH);
        createExampleEnvelope(1, "international congress 2021", documentIDs, documentPaths,
            DocumentState.OPEN, true, false);
        documentIDs.clear();
        documentPaths.clear();
        documentIDs.add(2L);
        documentIDs.add(ID_THREE);
        documentIDs.add(ID_FOUR);
        documentPaths.add(PROGRAM_PATH);
        documentPaths.add("Handout_Kundengespraech.pdf");
        documentPaths.add(PLAN_PATH);
        createExampleEnvelope(2, "Wichtige änderungen am Essensplan", documentIDs,
            documentPaths, DocumentState.OPEN, true, true);
        documentIDs.clear();
        documentPaths.clear();
        documentIDs.add(ID_FIVE);
        documentPaths.add(PLAN_PATH);
        createExampleEnvelope(ID_THREE, "Pläne für die Weltherrschaft", documentIDs,
            documentPaths, DocumentState.OPEN, true, true);
        documentIDs.clear();
        documentPaths.clear();
        documentIDs.add(ID_SIX);
        documentIDs.add(ID_SEVEN);
        documentPaths.add(PROGRAM_PATH);
        documentPaths.add("Dropbox.pdf");
        createExampleEnvelope(ID_FOUR, "Tutorialpläne", documentIDs,
            documentPaths, DocumentState.OPEN, true, true);
         */
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

    private void createExampleEnvelope(final long id, final String name, final List<Long> documentIDs,
                                       final List<String> documentPaths, final DocumentState documentState,
                                       final boolean docsRead, final boolean docsSigned) {
        final User owner = userService.getUser(USERNAME);
        try {
            final Envelope envelope = envelopeService.getEnvelope(id);
            for (int i = 0; i < documentIDs.size(); i++) {
                final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                try (InputStream inputStream = classLoader.getResourceAsStream(documentPaths.get(i))) {
                    final byte[] data = inputStream.readAllBytes();
                    final String[] titleAndType = new File(classLoader.getResource(documentPaths.get(i)).getFile())
                        .getName().split(DOUBLE_BACKSLASH);
                    final Document document = createExampleDocument(documentIDs.get(i), data,
                        documentState, docsRead, docsSigned, titleAndType[0], titleAndType[1]);
                    if (document != null) {
                        envelope.addDocument(document);
                        envelopeService.saveEnvelope(envelope);
                    }
                } catch (CreatingFileException e) {
                    e.printStackTrace();
                }
            }
        } catch (DocumentNotFoundException exception) {
            try {
                final Envelope envelope = owner.createNewEnvelope(name);
                final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                for (int i = 0; i < documentIDs.size(); i++) {
                    try (InputStream inputStream = classLoader.getResourceAsStream(documentPaths.get(i))) {
                        final byte[] data = inputStream.readAllBytes();
                        final String[] titleAndType = new File(classLoader.getResource(documentPaths.get(i)).getFile())
                            .getName().split(DOUBLE_BACKSLASH);
                        envelope.addDocument(
                            createExampleDocument(documentIDs.get(i), data,
                                documentState, docsRead, docsSigned, titleAndType[0], titleAndType[1]));
                        envelopeService.saveEnvelope(envelope);
                    }
                }
            } catch (IOException | CreatingFileException e) {
                exception.printStackTrace();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private Document createExampleDocument(final long id,
                                           final byte[] data, final DocumentState documentState,
                                           final boolean read, final boolean signed, final String title,
                                           final String type) throws CreatingFileException, IOException {
        final User owner = userService.getUser(USERNAME);
        try {
            documentService.getDocument(id);
        } catch (DocumentNotFoundException exception) {
            final DocumentCreator creator = new DocumentCreator();
            final DocumentPutRequest documentPutRequestRequest = new DocumentPutRequest();
            documentPutRequestRequest.setOrderRelevant(true);
            documentPutRequestRequest.setData(data);
            documentPutRequestRequest.setTitle(title);
            documentPutRequestRequest.setDataType(type);
            documentPutRequestRequest.setEndDate("2021-06-13 12:00");
            try {
                final List<ProtoSignatory> signatories = new ArrayList<>();
                if (read) {
                    signatories.add(new ProtoSignatory(owner.getUsername(), 0));
                }
                if (signed) {
                    signatories.add(new ProtoSignatory(owner.getUsername(), 2));
                }
                final Document document = creator.createDocument(documentPutRequestRequest, USERNAME,
                    signatories, documentService);
                try {
                    document.setState(documentState);
                } catch (IllegalStateException stateException) {
                    document.setState(DocumentState.OPEN);
                }
                return documentService.addDocument(document);
            } catch (CreatingFileException | IOException e) {
                throw e;
            }
        }
        return null;
    }

}

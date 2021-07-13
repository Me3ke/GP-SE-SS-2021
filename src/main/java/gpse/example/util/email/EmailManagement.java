package gpse.example.util.email;

import gpse.example.domain.documents.Document;
import gpse.example.domain.envelopes.Envelope;
import gpse.example.domain.envelopes.EnvelopeService;
import gpse.example.domain.exceptions.DocumentNotFoundException;
import gpse.example.domain.signature.Signatory;
import gpse.example.domain.signature.SignatureType;
import gpse.example.domain.users.User;
import gpse.example.domain.users.UserService;
import gpse.example.web.documents.GuestToken;
import gpse.example.web.documents.GuestTokenService;
import gpse.example.web.tokens.ResetPasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Class to manage the Emails.
 */
@Component
public class EmailManagement {

    private static final String ENVELOPE_URL = "http://localhost:8080/de/envelope/";
    private static final String DOCUMENT_URL = "/document/";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Autowired
    private EnvelopeService envelopeService;

    @Autowired
    private UserService userService;

    @Autowired
    private SMTPServerHelper smtpServerHelper;

    @Autowired
    private EmailTemplateService emailTemplateService;

    @Autowired
    private GuestTokenService guestTokenService;


    /**
     * send invitation to signatories Registered and Unregistered.
     * @param document the document to sign
     * @param envelopeID the corresponding envelopeId
     * @param signatory the signatory to invite
     * @throws TemplateNameNotFoundException If Template is not found
     * @throws MessageGenerationException If message generation fails
     */
    public void sendInvitation(final Document document, final long envelopeID, final Signatory signatory)
        throws MessageGenerationException, DocumentNotFoundException {
        final Envelope envelope = envelopeService.getEnvelope(envelopeID);
        final User owner = userService.getUser(document.getOwner());
        try {
            EmailTemplate template = owner.getEmailTemplates().get(0);
            for (final EmailTemplate temp : owner.getEmailTemplates()) {
                if (temp.getTemplateID() == document.getProcessEmailTemplateId()) {
                    template = temp;
                }
            }
            final User signatoryUser = userService.getUser(signatory.getEmail());
            sendUserInvitation(document, signatory, envelope, owner, template, signatoryUser);
        } catch (UsernameNotFoundException exception) {
            sendGuestInvitation(document, envelopeID, signatory, owner);
        }
    }


    /**
     * send new version template to inform about a new version.
     * @param document the updated document
     * @param envelopeID the corresponding envelopeId
     * @param signatory the signatory who is informed
     * @throws TemplateNameNotFoundException If Template is not found
     * @throws MessageGenerationException If message generation fails
     */
    public void sendNewVersion(final Document document, final long envelopeID, final Signatory signatory)
        throws TemplateNameNotFoundException, MessageGenerationException {
        final EmailTemplate emailTemplate = emailTemplateService.findSystemTemplateByName("NewVersionTemplate");
        final TemplateDataContainer container = new TemplateDataContainer();
        container.setDocumentTitle(document.getDocumentTitle());
        try {
            userService.getUser(signatory.getEmail());
            container.setLink(document.getLinkToDocumentview());
            smtpServerHelper.sendTemplatedEmail(signatory.getEmail(), emailTemplate,
                container, Category.NEW_VERSION, userService.getUser(document.getOwner()));
        } catch (UsernameNotFoundException exception) {
            final GuestToken token = guestTokenService.saveGuestToken(new GuestToken(signatory.getEmail(),
                document.getId()));
            container.setLink(ENVELOPE_URL + envelopeID + DOCUMENT_URL
                + document.getId() + "/" + token.getToken());
            smtpServerHelper.sendTemplatedEmail(signatory.getEmail(), emailTemplate,
                container, Category.NEW_VERSION, userService.getUser(document.getOwner()));
        }
    }

    /**
     * Send process finished template if a process is finished.
     * @param document the document which process is finished
     * @throws TemplateNameNotFoundException If Template is not found
     * @throws MessageGenerationException If message generation fails
     */
    public void sendProcessFinishedTemplate(final Document document) throws TemplateNameNotFoundException,
        MessageGenerationException {
        final EmailTemplate template = emailTemplateService.findSystemTemplateByName("ProcessFinishedTemplate");
        final TemplateDataContainer container = new TemplateDataContainer();
        container.setDocumentTitle(document.getDocumentTitle());
        container.setLink(document.getLinkToDocumentview() + "/protocol");
        smtpServerHelper.sendTemplatedEmail(document.getOwner(), template, container, Category.PROGRESS, null);
        for (final Signatory signatory : document.getSignatories()) {
            if (!signatory.getEmail().equals(document.getOwner())) {
                smtpServerHelper.sendTemplatedEmail(signatory.getEmail(), template, container, Category.PROGRESS, null);
            }
        }
    }

    /**
     * Sending and configurate the confirmation template.
     * @param user  user to register
     * @param token the confirmation token to verify email
     * @throws TemplateNameNotFoundException If Template is not found
     * @throws MessageGenerationException If message generation fails
     */
    public void sendConfirmationMail(final User user, final String token) throws MessageGenerationException,
        TemplateNameNotFoundException {

        final EmailTemplate template = emailTemplateService.findSystemTemplateByName("ConfirmationTemplate");
        final TemplateDataContainer container = new TemplateDataContainer();
        container.setFirstNameReciever(user.getFirstname());
        container.setLastNameReciever(user.getLastname());
        container.setLink("http://localhost:8080/de/register/confirm/" + token);
        smtpServerHelper.sendTemplatedEmail(user.getEmail(), template, container, Category.SYSTEM, null);

    }


    /**
     * send admin information if ther is a new extern user registrating.
     * @param user the extern user who needs to be validated
     * @throws TemplateNameNotFoundException If Template is not found
     * @throws MessageGenerationException If message generation fails
     */
    public void sendAdminInformation(final User user) throws MessageGenerationException, TemplateNameNotFoundException {
        final List<User> userList = userService.getUsers();
        for (final User admin : userList) {
            if (admin.getRoles().contains(ROLE_ADMIN)) {
                final EmailTemplate template = emailTemplateService.findSystemTemplateByName("AdminValidationTemplate");
                final TemplateDataContainer container = new TemplateDataContainer();
                container.setFirstNameReciever(admin.getFirstname());
                container.setLastNameReciever(admin.getLastname());
                container.setFirstNameOwner(user.getFirstname());
                container.setLastNameOwner(user.getLastname());
                container.setRequestingEmail(user.getEmail());
                container.setLink("http://localhost:8080/de/adminSettings/userManagement");
                smtpServerHelper.sendTemplatedEmail(admin.getEmail(), template, container, Category.TODO, null);

            }
        }
    }


    /**
     * Send Reminder to signatory if time to sign is going to run out.
     * @param document the document to sign
     * @param signatory the signatory who should sign
     * @throws TemplateNameNotFoundException If Template is not found
     * @throws MessageGenerationException If message generation fails
     */
    public void sendReminder(final Document document, final Signatory signatory)
        throws TemplateNameNotFoundException, MessageGenerationException {

        final EmailTemplate template = emailTemplateService.findSystemTemplateByName("ReminderTemplate");
        final TemplateDataContainer container = new TemplateDataContainer();
        container.setEndDate(document.getEndDate().toString());
        container.setDocumentTitle(document.getDocumentTitle());
        container.setLink(document.getLinkToDocumentview());
        smtpServerHelper.sendTemplatedEmail(signatory.getEmail(), template, container, Category.PROGRESS,
            userService.getUser(document.getOwner()));
    }


    /**
     * Send new Comment mail to Owner of document.
     * @param author author of comment
     * @param documentOwner owner of document
     * @param document the document which is commented
     * @throws TemplateNameNotFoundException If Template is not found
     * @throws MessageGenerationException If message generation failed
     */
    public void sendNewCommentEmail(final User author, final User documentOwner, final Document document)
        throws TemplateNameNotFoundException, MessageGenerationException {
        final EmailTemplate template = emailTemplateService.findSystemTemplateByName("NewCommentTemplate");
        final TemplateDataContainer container = new TemplateDataContainer();
        container.setDocumentTitle(document.getDocumentTitle());
        container.setFirstNameOwner(author.getFirstname());
        container.setLastNameOwner(author.getLastname());
        container.setFirstNameReciever(documentOwner.getFirstname());
        container.setLastNameReciever(documentOwner.getLastname());
        container.setLink(document.getLinkToDocumentview());
        smtpServerHelper.sendTemplatedEmail(documentOwner.getEmail(), template, container, Category.SYSTEM, author);
    }


    /**
     * Send Answer on Comment info To Author of Comment.
     * @param author the User who wrote the answer
     * @param reciever the author of the answered comment
     * @param document the document on which the comment are
     * @throws TemplateNameNotFoundException If Template is not found
     * @throws MessageGenerationException If message generation failed
     */
    public void sendAnswerEmail(final User author, final User reciever, final Document document)
        throws TemplateNameNotFoundException, MessageGenerationException {
        final EmailTemplate template = emailTemplateService.findSystemTemplateByName("AnswerCommentTemplate");
        final TemplateDataContainer container = new TemplateDataContainer();
        container.setFirstNameOwner(author.getFirstname());
        container.setLastNameOwner(author.getLastname());
        container.setFirstNameReciever(reciever.getFirstname());
        container.setLastNameReciever(reciever.getLastname());
        container.setLink(document.getLinkToDocumentview());
        smtpServerHelper.sendTemplatedEmail(reciever.getEmail(), template, container, Category.SYSTEM, author);
    }

    /**
     * Sends Reset Password Mail.
     * @param user user who want to reset password
     * @param savedToken the ValidationToken
     * @throws TemplateNameNotFoundException If Template is not found
     * @throws MessageGenerationException If message generation failed
     */
    public void sendResetPassword(final User user, final ResetPasswordToken savedToken)
        throws TemplateNameNotFoundException, MessageGenerationException {
        final TemplateDataContainer emailContainer = new TemplateDataContainer();
        final EmailTemplate template = emailTemplateService.findSystemTemplateByName("ResetPasswordTemplate");
        emailContainer.setFirstNameReciever(user.getFirstname());
        emailContainer.setLastNameReciever(user.getLastname());
        emailContainer.setLink("http://localhost:8080/de/login/reset/" + savedToken.getToken());
        smtpServerHelper.sendTemplatedEmail(user.getEmail(), template,
            emailContainer, Category.SYSTEM, null);
    }


    private void sendUserInvitation(final Document document, final Signatory signatory, final Envelope envelope,
                                    final User owner, final EmailTemplate template, final User signatoryUser)
        throws MessageGenerationException {

        final TemplateDataContainer container = new TemplateDataContainer();
        container.setFirstNameReciever(signatoryUser.getFirstname());
        container.setLastNameReciever(signatoryUser.getLastname());
        container.setFirstNameOwner(owner.getFirstname());
        container.setLastNameOwner(owner.getLastname());
        container.setDocumentTitle(document.getDocumentTitle());
        container.setEnvelopeName(envelope.getName());
        container.setEndDate(document.getEndDate().toString());
        container.setLink(document.getLinkToDocumentview());
        Category category;
        if (signatory.getSignatureType().equals(SignatureType.ADVANCED_SIGNATURE)
            || signatory.getSignatureType().equals(SignatureType.SIMPLE_SIGNATURE)) {
            category = Category.SIGN;
        } else {
            category = Category.READ;
        }
        smtpServerHelper.sendTemplatedEmail(signatory.getEmail(), template, container, category, owner);
    }


    private void sendGuestInvitation(final Document document, final long envelopeID, final Signatory signatory,
                                     final User owner) throws MessageGenerationException {
        EmailTemplate template;
        try {
            template = emailTemplateService.findSystemTemplateByName("GuestInvitationTemplate");
        } catch (TemplateNameNotFoundException e) {
            return;
        }
        final TemplateDataContainer container = new TemplateDataContainer();
        container.setFirstNameOwner(owner.getFirstname());
        container.setLastNameOwner(owner.getLastname());
        container.setDocumentTitle(document.getDocumentTitle());
        final GuestToken token = guestTokenService.saveGuestToken(new GuestToken(signatory.getEmail(),
            document.getId()));
        container.setLink(ENVELOPE_URL + envelopeID + DOCUMENT_URL
            + document.getId() + "/" + token.getToken());
        if (signatory.getSignatureType().equals(SignatureType.REVIEW)) {
            smtpServerHelper.sendTemplatedEmail(signatory.getEmail(), template, container, Category.READ, owner);
        } else if (signatory.getSignatureType().equals(SignatureType.SIMPLE_SIGNATURE)) {
            smtpServerHelper.sendTemplatedEmail(signatory.getEmail(), template, container, Category.SIGN, owner);
        } else {
            container.setLink("http://localhost:8080/de/landing");
            try {
                template = emailTemplateService.findSystemTemplateByName("AdvancedGuestInvitationTemplate");
            } catch (TemplateNameNotFoundException e) {
                return;
            }
            smtpServerHelper.sendTemplatedEmail(signatory.getEmail(), template, container, Category.TODO, owner);
        }
    }
}

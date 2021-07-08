package gpse.example.domain.documents;

import gpse.example.domain.envelopes.Envelope;
import gpse.example.domain.envelopes.EnvelopeService;
import gpse.example.domain.exceptions.DocumentNotFoundException;
import gpse.example.domain.exceptions.MessageGenerationException;
import gpse.example.domain.exceptions.TemplateNameNotFoundException;
import gpse.example.domain.signature.Signatory;
import gpse.example.domain.signature.SignatureType;
import gpse.example.domain.users.User;
import gpse.example.domain.users.UserService;
import gpse.example.domain.email.*;
import gpse.example.web.JSONResponseObject;
import gpse.example.web.documents.GuestToken;
import gpse.example.web.documents.GuestTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The class used to manage all signature and review requests.
 */
@Component
public class SignatureManagement {

    private static final int STATUS_CODE_OK = 200;
    private static final int STATUS_CODE_WRONG_USER = 450;
    private static final int STATUS_CODE_NOT_READER = 451;
    private static final int STATUS_CODE_INVALID_SIGNATURE_TYPE = 453;
    private static final int STATUS_CODE_NOT_READ_YET = 454;
    private static final int STATUS_CODE_NOT_SIGNATORY = 455;
    private static final String DOCUMENT_URL = "/document/";
    private final DocumentService documentService;
    private final UserService userService;
    private final SMTPServerHelper smtpServerHelper;
    private final EmailTemplateService emailTemplateService;
    private final GuestTokenService guestTokenService;
    private final EnvelopeService envelopeService;

    /**
     * constructor of Signature management.
     *
     * @param smtpServerHelper     smtpServerHelper
     * @param givenDocumentService documentservice
     * @param givenUserService     userservice
     * @param emailTemplateService emailTemplateService
     * @param guestTokenService guestTokenService
     * @param envelopeService envelopeService
     */
    @Autowired
    public SignatureManagement(final SMTPServerHelper smtpServerHelper, final DocumentService givenDocumentService,
                               final UserService givenUserService, final EmailTemplateService emailTemplateService,
                               final GuestTokenService guestTokenService, final EnvelopeService envelopeService) {
        this.smtpServerHelper = smtpServerHelper;
        documentService = givenDocumentService;
        userService = givenUserService;
        this.emailTemplateService = emailTemplateService;
        this.guestTokenService = guestTokenService;
        this.envelopeService = envelopeService;
    }

    /**
     * the standard method for managing requests, regarding reviews or signatures.
     *
     * @param userID        the user who stated the request
     * @param document      the document that should be reviewed or signed
     * @param signatureType the type of the signature
     * @param envelopeID the envelope the document refers to.
     * @return a fitting response.
     */
    public JSONResponseObject manageSignatureRequest(final String userID, final Document document,
                                                     final SignatureType signatureType, final long envelopeID)
        throws MessageGenerationException, TemplateNameNotFoundException, DocumentNotFoundException {
        if (document.isOrderRelevant()) {
            return manageSignatureInOrder(userID, document, signatureType, envelopeID);
        } else {
            return manageSignatureWithoutOrder(userID, document, signatureType);
        }
    }

    private JSONResponseObject manageSignatureWithoutOrder(final String userID,
                                                           final Document document, final SignatureType signatureType)
                throws TemplateNameNotFoundException, MessageGenerationException {
        List<Signatory> signatories;
        final JSONResponseObject response = new JSONResponseObject();
        switch (signatureType) {
            case REVIEW:
                signatories = document.getSignatoryManagement().getReaders();
                return manageReadersWithoutOrder(userID, document, signatories, response);
            case SIMPLE_SIGNATURE:
            case ADVANCED_SIGNATURE:
                return manageSignatoriesWithoutOrder(userID, document, response, signatureType);
            default:
                response.setStatus(STATUS_CODE_INVALID_SIGNATURE_TYPE);
                response.setMessage("The given signature type is invalid");
                return response;
        }
    }

    private JSONResponseObject manageSignatoriesWithoutOrder(final String userID, final Document document,
                                                             final JSONResponseObject response,
                                                             final SignatureType signatureType)
                throws TemplateNameNotFoundException, MessageGenerationException {
        if (document.getState().equals(DocumentState.READ)) {
            if (findSignatoryInList(document, userID, signatureType)) {
                if (areSignatoriesFinished(document.getSignatoryManagement().getSignatories())) {
                    sendProcessFinishedTemplate(document);
                    return changeDocumentStateToClosed(document);
                } else {
                    response.setStatus(STATUS_CODE_OK);
                    return response;
                }
            } else {
                response.setStatus(STATUS_CODE_NOT_SIGNATORY);
                response.setMessage("The user is not a signatory for this document.");
                return response;
            }
        } else {
            return changeResponseToNotReadYet(response);
        }
    }

    private void sendProcessFinishedTemplate(final Document document) throws TemplateNameNotFoundException,
                MessageGenerationException {
        final EmailTemplate template = emailTemplateService.findSystemTemplateByName("ProcessFinishedTemplate");
        final TemplateDataContainer container = new TemplateDataContainer();
        container.setDocumentTitle(document.getDocumentTitle());
        container.setLink(document.getLinkToDocumentview() + "/protocol");
        smtpServerHelper.sendTemplatedEmail(document.getOwner(), template, container, Category.PROGRESS, null);
        for (final Signatory signatory : document.getSignatoryManagement().getSignatories()) {
            if (!signatory.getEmail().equals(document.getOwner())) {
                smtpServerHelper.sendTemplatedEmail(signatory.getEmail(), template, container, Category.PROGRESS, null);
            }
        }
    }

    private JSONResponseObject manageReadersWithoutOrder(final String userID, final Document document,
                                                         final List<Signatory> signatories,
                                                         final JSONResponseObject response)
                throws TemplateNameNotFoundException, MessageGenerationException {
        if (findSignatoryInList(document, userID, SignatureType.REVIEW)) {
            if (areSignatoriesFinished(document.getSignatoryManagement().getSignatories())) {
                sendProcessFinishedTemplate(document);
                return changeDocumentStateToClosed(document);
            } else {
                if (areSignatoriesFinished(signatories)) {
                    return changeDocumentStateToRead(document);
                } else {
                    response.setStatus(STATUS_CODE_OK);
                    return response;
                }
            }
        } else {
            response.setStatus(STATUS_CODE_NOT_READER);
            response.setMessage("The user is not a reader for this document.");
            return response;
        }
    }

    private JSONResponseObject changeDocumentStateToRead(final Document document) {
        document.setState(DocumentState.READ);
        documentService.addDocument(document);
        final JSONResponseObject response = new JSONResponseObject();
        response.setStatus(STATUS_CODE_OK);
        response.setMessage("The given document is now read");
        return response;
    }

    private JSONResponseObject changeDocumentStateToClosed(final Document document) {
        final JSONResponseObject response = new JSONResponseObject();
        document.setState(DocumentState.CLOSED);
        documentService.addDocument(document);
        response.setStatus(STATUS_CODE_OK);
        response.setMessage("The given document is now closed");
        return response;
    }

    private boolean findSignatoryInList(final Document document, final String signatoryToFind,
                                        final SignatureType signatureType) {
        List<Signatory> signatories;
        switch (signatureType) {
            case REVIEW:
                signatories = document.getSignatoryManagement().getReaders();
                break;
            case SIMPLE_SIGNATURE:
                signatories = document.getSignatoryManagement().getSimpleSignatories();
                break;
            default:
                signatories = document.getSignatoryManagement().getAdvancedSignatories();
                break;
        }
        boolean foundSignatory = false;
        for (final Signatory currentSignatory : signatories) {
            if (currentSignatory.getEmail().equals(signatoryToFind)) {
                currentSignatory.setStatus(true);
                foundSignatory = true;
                documentService.addDocument(document);
            }
        }
        return foundSignatory;
    }

    private boolean areSignatoriesFinished(final List<Signatory> signatories) {
        boolean finished = true;
        for (final Signatory signatory : signatories) {
            finished &= signatory.isStatus();
        }
        return finished;
    }

    private JSONResponseObject changeResponseToNotReadYet(final JSONResponseObject response) {
        response.setStatus(STATUS_CODE_NOT_READ_YET);
        response.setMessage("The given document is not fully read yet");
        return response;
    }

    private JSONResponseObject manageSignatureInOrder(final String userID, final Document document,
                                                      final SignatureType signatureType, final long envelopeID)
        throws MessageGenerationException, TemplateNameNotFoundException, DocumentNotFoundException {
        final List<Signatory> signatories = document.getSignatoryManagement().getSignatories();
        final JSONResponseObject response = new JSONResponseObject();
        final Signatory currentReader = document.getSignatoryManagement().getCurrentSignatory();
        final Envelope envelope = envelopeService.getEnvelope(envelopeID);
        if (matchesSignatory(userID, currentReader, signatureType)) {
            currentReader.setStatus(true);
            checkIfClosed(document, signatories, response, currentReader);
            final Document savedDocument = documentService.addDocument(document);
            if (savedDocument.getState() == DocumentState.CLOSED) {
                sendProcessFinishedTemplate(document);
            } else {
                final User owner = userService.getUser(savedDocument.getOwner());
                EmailTemplate template = owner.getEmailTemplates().get(0);
                for (final EmailTemplate temp : owner.getEmailTemplates()) {
                    if (temp.getTemplateID() == document.getProcessEmailTemplateId()) {
                        template = temp;
                    }
                }
                sendInvitation(userID, signatureType, envelope, savedDocument, owner, template);
            }

            response.setStatus(STATUS_CODE_OK);
            return response;
        } else {
            response.setStatus(STATUS_CODE_WRONG_USER);
            response.setMessage("The user is either not a signatory for this document,"
                + "or it is currently not his turn");
            return response;
        }
    }

    private void sendInvitation(final String userID, final SignatureType signatureType, final Envelope envelope,
                                final Document savedDocument, final User owner, final EmailTemplate template)
        throws MessageGenerationException, TemplateNameNotFoundException {

        final TemplateDataContainer container = new TemplateDataContainer();
        try {
            container.setFirstNameReciever(
                userService.getUser(
                    savedDocument.getSignatoryManagement().getCurrentSignatory().getEmail()).getFirstname());
            container.setLastNameReciever(
                userService.getUser(
                    savedDocument.getSignatoryManagement().getCurrentSignatory().getEmail()).getLastname());
            container.setFirstNameOwner(owner.getFirstname());
            container.setLastNameOwner(owner.getLastname());
            container.setDocumentTitle(savedDocument.getDocumentTitle());
            container.setEnvelopeName(envelope.getName());
            container.setEndDate(savedDocument.getEndDate().toString());
            container.setLink(savedDocument.getLinkToDocumentview());
            smtpServerHelper.sendTemplatedEmail(
                savedDocument.getSignatoryManagement().getCurrentSignatory().getEmail(), template,
                container, Category.SIGN, owner);
        } catch (UsernameNotFoundException exception) {
            final EmailTemplate guestTemplate = emailTemplateService.findSystemTemplateByName(
                "GuestInvitationTemplate");
            container.setFirstNameOwner(owner.getFirstname());
            container.setLastNameOwner(owner.getLastname());
            container.setDocumentTitle(savedDocument.getDocumentTitle());
            final GuestToken token = guestTokenService.saveGuestToken(new GuestToken(userID, savedDocument.getId()));
            container.setLink("http://localhost:8080/de/" + "envelope/" + envelope.getId() + DOCUMENT_URL
                + savedDocument.getId() + "/" + token.getToken());
            if (signatureType.equals(SignatureType.REVIEW)) {
                smtpServerHelper.sendTemplatedEmail(
                    savedDocument.getSignatoryManagement().getCurrentSignatory().getEmail(), guestTemplate,
                    container, Category.READ, owner);
            } else if (signatureType.equals(SignatureType.SIMPLE_SIGNATURE)) {
                smtpServerHelper.sendTemplatedEmail(
                    savedDocument.getSignatoryManagement().getCurrentSignatory().getEmail(), guestTemplate,
                    container, Category.SIGN, owner);
            } else {
                container.setLink("http://localhost:8080/de/landing");
                final EmailTemplate advancedGuestTemplate = emailTemplateService.findSystemTemplateByName(
                    "AdvancedGuestInvitationTemplate");
                smtpServerHelper.sendTemplatedEmail(
                    savedDocument.getSignatoryManagement().getCurrentSignatory().getEmail(), advancedGuestTemplate,
                    container, Category.TODO, owner);
            }
        }
    }

    private void checkIfClosed(final Document document, final List<Signatory> signatories,
                               final JSONResponseObject response, final Signatory currentReader) {

        if (signatories.get(signatories.size() - 1).equals(currentReader)) {
            document.setState(DocumentState.CLOSED);
            response.setMessage("Document is now closed.");
        }
    }


    private boolean matchesSignatory(final String userID, final Signatory currentReader,
                                     final SignatureType signatureType) {
        return currentReader != null && currentReader.getSignatureType().equals(signatureType)
            && currentReader.getEmail().equals(userID);
    }

}

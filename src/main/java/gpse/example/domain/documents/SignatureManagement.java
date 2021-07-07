package gpse.example.domain.documents;

import gpse.example.domain.signature.Signatory;
import gpse.example.domain.signature.SignatureType;
import gpse.example.domain.users.User;
import gpse.example.domain.users.UserService;
import gpse.example.util.email.*;
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
    private final DocumentService documentService;
    private final UserService userService;
    private final SMTPServerHelper smtpServerHelper;
    private final EmailTemplateService emailTemplateService;
    private final GuestTokenService guestTokenService;

    /**
     * constructor of Signature management.
     *
     * @param smtpServerHelper     smtpServerHelper
     * @param givenDocumentService documentservice
     * @param givenUserService     userservice
     * @param emailTemplateService emailTemplateService
     * @param guestTokenService guestTokenService
     */
    @Autowired
    public SignatureManagement(final SMTPServerHelper smtpServerHelper, final DocumentService givenDocumentService,
                               final UserService givenUserService, final EmailTemplateService emailTemplateService,
                               final GuestTokenService guestTokenService) {
        this.smtpServerHelper = smtpServerHelper;
        documentService = givenDocumentService;
        userService = givenUserService;
        this.emailTemplateService = emailTemplateService;
        this.guestTokenService = guestTokenService;
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
        throws MessageGenerationException, TemplateNameNotFoundException {
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
                signatories = document.getReaders();
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
                if (areSignatoriesFinished(document.getSignatories())) {
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
        EmailTemplate template = emailTemplateService.findSystemTemplateByName("ProcessFinishedTemplate");
        TemplateDataContainer container = new TemplateDataContainer();
        container.setDocumentTitle(document.getDocumentTitle());
        container.setLink("link/to/protocol");
        smtpServerHelper.sendTemplatedEmail(document.getOwner(), template, container, Category.PROGRESS, null);
        for (Signatory signatory : document.getSignatories()) {
            smtpServerHelper.sendTemplatedEmail(signatory.getEmail(), template, container, Category.PROGRESS, null);
        }
    }

    private JSONResponseObject manageReadersWithoutOrder(final String userID, final Document document,
                                                         final List<Signatory> signatories,
                                                         final JSONResponseObject response)
                throws TemplateNameNotFoundException, MessageGenerationException {
        if (findSignatoryInList(document, userID, SignatureType.REVIEW)) {
            if (areSignatoriesFinished(document.getSignatories())) {
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
                signatories = document.getReaders();
                break;
            case SIMPLE_SIGNATURE:
                signatories = document.getSimpleSignatories();
                break;
            default:
                signatories = document.getAdvancedSignatories();
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
        throws MessageGenerationException, TemplateNameNotFoundException {
        final List<Signatory> signatories = document.getSignatories();
        final JSONResponseObject response = new JSONResponseObject();
        final Signatory currentReader = document.getCurrentSignatory();
        if (matchesSignatory(userID, currentReader, signatureType)) {
            currentReader.setStatus(true);
            checkIfClosed(document, signatories, response, currentReader);
            final Document savedDocument = documentService.addDocument(document);
            if (savedDocument.getState() != DocumentState.CLOSED) {
                final User owner = userService.getUser(savedDocument.getOwner());
                EmailTemplate template = owner.getEmailTemplates().get(0);
                for (EmailTemplate temp : owner.getEmailTemplates()) {
                    if (temp.getTemplateID() == document.getProcessEmailTemplateId()) {
                        template = temp;
                    }
                }
                final TemplateDataContainer container = new TemplateDataContainer();
                try {
                    container.setFirstNameReciever(
                        userService.getUser(savedDocument.getCurrentSignatory().getEmail()).getFirstname());
                    container.setLastNameReciever(
                        userService.getUser(savedDocument.getCurrentSignatory().getEmail()).getLastname());
                    container.setFirstNameOwner(owner.getFirstname());
                    container.setLastNameOwner(owner.getLastname());
                    container.setDocumentTitle(document.getDocumentTitle());
                    // TODO find out envelope container.setEnvelopeName(envelope.getName());
                    container.setEndDate(document.getEndDate().toString());
                    //TODO Link to documentview
                    container.setLink("http://localhost:8080/de/link/to/document/view");
                    smtpServerHelper.sendTemplatedEmail(savedDocument.getCurrentSignatory().getEmail(), template,
                        container, Category.SIGN, owner);
                } catch (UsernameNotFoundException exception) {
                    template = emailTemplateService.findSystemTemplateByName("GuestInvitationTemplate");
                    container.setFirstNameOwner(owner.getFirstname());
                    container.setLastNameOwner(owner.getLastname());
                    container.setDocumentTitle(document.getDocumentTitle());
                    final GuestToken token = guestTokenService.saveGuestToken(new GuestToken(userID, document.getId()));
                    container.setLink("http://localhost:8080/de/" + "/document/" + document.getId() + "/"
                        + token.getToken());
                    if (signatureType.equals(SignatureType.REVIEW)) {
                        smtpServerHelper.sendTemplatedEmail(savedDocument.getCurrentSignatory().getEmail(),
                            template, container, Category.READ, owner);
                    } else if (signatureType.equals(SignatureType.SIMPLE_SIGNATURE)) {
                        smtpServerHelper.sendTemplatedEmail(savedDocument.getCurrentSignatory().getEmail(),
                            template, container, Category.SIGN, owner);
                    } else {
                        container.setLink("http://localhost:8080/de/landing");
                        template = emailTemplateService.findSystemTemplateByName("AdvancedGuestInvitationTemplate");
                        smtpServerHelper.sendTemplatedEmail(savedDocument.getCurrentSignatory().getEmail(),
                            template, container, Category.TODO, owner);
                    }
                }
            } else {
                sendProcessFinishedTemplate(document);
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

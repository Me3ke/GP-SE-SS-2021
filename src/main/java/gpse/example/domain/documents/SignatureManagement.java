package gpse.example.domain.documents;

import gpse.example.domain.exceptions.DocumentNotFoundException;
import gpse.example.domain.exceptions.MessageGenerationException;
import gpse.example.domain.exceptions.TemplateNameNotFoundException;
import gpse.example.domain.signature.Signatory;
import gpse.example.domain.signature.SignatureType;
import gpse.example.domain.email.EmailManagement;
import gpse.example.web.JSONResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final EmailManagement emailManagement;

    /**
     * constructor of Signature management.
     *
     * @param givenDocumentService documentservice
     * @param emailManagement the emailManagementClass
     */
    @Autowired
    public SignatureManagement(final DocumentService givenDocumentService, final EmailManagement emailManagement) {
        documentService = givenDocumentService;
        this.emailManagement = emailManagement;
    }

    /**
     * the standard method for managing requests, regarding reviews or signatures.
     *
     * @param userID        the user who stated the request
     * @param document      the document that should be reviewed or signed
     * @param signatureType the type of the signature
     * @param envelopeID    the envelope the document refers to.
     * @return a fitting response.
     */
    public JSONResponseObject manageSignatureRequest(final String userID, final Document document,
                                                     final SignatureType signatureType, final long envelopeID)
        throws MessageGenerationException, TemplateNameNotFoundException, DocumentNotFoundException {
        if (document.getSignatureProcessData().isOrderRelevant()) {
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
            if (document.getSignatureProcessData().getState().equals(DocumentState.SIGN)) {
            if (findSignatoryInList(document, userID, signatureType)) {
                if (areSignatoriesFinished(document.getSignatoryManagement().getSignatories())) {
                    emailManagement.sendProcessFinishedTemplate(document);
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
    private JSONResponseObject manageReadersWithoutOrder(final String userID, final Document document,
                                                         final List<Signatory> signatories,
                                                         final JSONResponseObject response)
                throws TemplateNameNotFoundException, MessageGenerationException {
        if (findSignatoryInList(document, userID, SignatureType.REVIEW)) {
            if (areSignatoriesFinished(document.getSignatoryManagement().getSignatories())) {
                emailManagement.sendProcessFinishedTemplate(document);
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
        document.getSignatureProcessData().setState(DocumentState.SIGN);
        documentService.addDocument(document);
        final JSONResponseObject response = new JSONResponseObject();
        response.setStatus(STATUS_CODE_OK);
        response.setMessage("The given document is now read");
        return response;
    }

    private JSONResponseObject changeDocumentStateToClosed(final Document document) {
        final JSONResponseObject response = new JSONResponseObject();
        document.getSignatureProcessData().setState(DocumentState.ARCHIVED);
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

        if (matchesSignatory(userID, currentReader, signatureType)) {
            currentReader.setStatus(true);
            if (document.getSignatoryManagement().getCurrentSignatory() != null && currentReader.getSignatureType()
                .equals(SignatureType.REVIEW)
                && (document.getSignatoryManagement().getCurrentSignatory().getSignatureType()
                .equals(SignatureType.SIMPLE_SIGNATURE)
                || document.getSignatoryManagement().getCurrentSignatory().getSignatureType()
                .equals(SignatureType.ADVANCED_SIGNATURE))) {
                document.getSignatureProcessData().setState(DocumentState.SIGN);
            }
            checkIfClosed(document, signatories, response, currentReader);
            final Document savedDocument = documentService.addDocument(document);
            if (savedDocument.getSignatureProcessData().getState() == DocumentState.ARCHIVED) {
                emailManagement.sendProcessFinishedTemplate(document);
            } else {
                emailManagement.sendInvitation(savedDocument, envelopeID, currentReader);

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
            document.getSignatureProcessData().setState(DocumentState.ARCHIVED);
            response.setMessage("Document is now closed.");
        }
    }


    private boolean matchesSignatory(final String userID, final Signatory currentReader,
                                     final SignatureType signatureType) {
        return currentReader != null && currentReader.getSignatureType().equals(signatureType)
            && currentReader.getEmail().equals(userID);
    }

}

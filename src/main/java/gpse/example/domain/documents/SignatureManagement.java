package gpse.example.domain.documents;

import gpse.example.domain.signature.Signatory;
import gpse.example.domain.signature.SignatoryService;
import gpse.example.domain.signature.SignatureType;
import gpse.example.domain.users.User;
import gpse.example.web.JSONResponseObject;

import java.util.List;

/**
 * The class used to manage all signature and review requests.
 */

public class SignatureManagement {

    private static final int STATUS_CODE_OK = 200;
    private static final int STATUS_CODE_WRONG_USER = 450;
    private static final int STATUS_CODE_NOT_READER = 451;
    private static final int STATUS_CODE_INVALID_SIGNATURE_TYPE = 453;
    private static final int STATUS_CODE_NOT_READ_YET = 454;
    private static final int STATUS_CODE_NOT_SIGNATORY = 455;
    private final SignatoryService signatoryService;
    private final DocumentService documentService;


    public SignatureManagement(final SignatoryService givenSignatoryService,
                               final DocumentService givenDocumentService) {
        signatoryService = givenSignatoryService;
        documentService = givenDocumentService;
    }


    /**
     * the standard method for managing requests, regarding reviews or signatures.
     *
     * @param reader        the user who stated the request
     * @param document      the document that should be reviewed or signed
     * @param signatureType the type of the signature
     * @return a fitting response.
     */
    public JSONResponseObject manageSignatureRequest(final User reader, final Document document,
                                                     final SignatureType signatureType) {
        if (document.isOrderRelevant()) {
            return manageSignatureInOrder(reader, document, signatureType);
        } else {
            return manageSignatureWithoutOrder(reader, document, signatureType);
        }
    }

    private JSONResponseObject manageSignatureWithoutOrder(final User reader,
                                                           final Document document, final SignatureType signatureType) {
        List<Signatory> signatories;
        final JSONResponseObject response = new JSONResponseObject();
        switch (signatureType) {
            case REVIEW:
                signatories = document.getReaders();
                return manageReadersWithoutOrder(reader, document, signatories, response);
            case SIMPLE_SIGNATURE:
            case ADVANCED_SIGNATURE:
                return manageSignatoriesWithoutOrder(reader, document, response, signatureType);
            default:
                response.setStatus(STATUS_CODE_INVALID_SIGNATURE_TYPE);
                response.setMessage("The given signature type is invalid");
                return response;
        }
    }

    private JSONResponseObject manageSignatoriesWithoutOrder(final User reader, final Document document,
                                                             final JSONResponseObject response,
                                                             final SignatureType signatureType) {
        List<Signatory> signatories;
        signatories = document.getSimpleSignatories();
        if (document.getState().equals(DocumentState.READ)) {
            if (findSignatoryInList(signatories, reader, signatureType)) {
                if (areSignatoriesFinished(document.getSignatories())) {
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

    private JSONResponseObject manageReadersWithoutOrder(final User reader, final Document document,
                                                         final List<Signatory> signatories,
                                                         final JSONResponseObject response) {
        if (findSignatoryInList(signatories, reader, SignatureType.REVIEW)) {
            if (areSignatoriesFinished(document.getSignatories())) {
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

    private boolean findSignatoryInList(final List<Signatory> signatories, final User signatoryToFind,
                                        final SignatureType signatureType) {
        boolean foundSignatory = false;
        for (final Signatory currentSignatory : signatories) {
            if (currentSignatory.getUser().equals(signatoryToFind)
                && currentSignatory.getSignatureType().equals(signatureType)) {
                currentSignatory.setStatus(true);
                foundSignatory = true;
                signatoryService.saveSignatory(currentSignatory);
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

    private JSONResponseObject manageSignatureInOrder(final User reader, final Document document,
                                                      final SignatureType signatureType) {
        final List<Signatory> signatories = document.getSignatories();
        final JSONResponseObject response = new JSONResponseObject();
        final Signatory currentReader = document.getCurrentSignatory();
        if (matchesSignatory(reader, currentReader, signatureType)) {
            currentReader.setStatus(true);
            signatoryService.saveSignatory(currentReader);
            checkIfClosed(document, signatories, response, currentReader);
            documentService.addDocument(document);
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

    private boolean matchesSignatory(final User reader, final Signatory currentReader,
                                     final SignatureType signatureType) {
        return currentReader != null && currentReader.getSignatureType().equals(signatureType)
            && currentReader.getUser().equals(reader);
    }

}

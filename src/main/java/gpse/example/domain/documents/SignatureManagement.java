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

public final class SignatureManagement {

    private static final int STATUS_CODE_OK = 200;
    private static final int STATUS_CODE_WRONG_USER = 450;
    private static final int STATUS_CODE_NOT_READER = 451;
    private SignatoryService signatoryService;
    private DocumentService documentService;


    public SignatureManagement(SignatoryService givenSignatoryService, DocumentService givenDocumentService) {
        signatoryService = givenSignatoryService;
        documentService = givenDocumentService;
    }


    /**
     * the standard method for managing requests, regarding reviews or signatures.
     * @param reader the user who stated the request
     * @param document the document that should be reviewed or signed
     * @return a fitting response.
     */
    public JSONResponseObject manageSignatureRequest(User reader, Document document) {
        if (document.isOrderRelevant()) {
            return manageSignatureInOrder(reader, document);
        } else {
            return manageSignatureWithoutOrder(reader, document);
        }
    }

    private JSONResponseObject manageSignatureWithoutOrder(User reader, Document document) {
        boolean documentFinished = true;
        final List<Signatory> readers = document.getReaders();
        boolean foundReader = false;
        JSONResponseObject response = new JSONResponseObject();
        for (final Signatory currentReader : readers) {
            if (currentReader.getUser().equals(reader)) {
                System.out.println(currentReader.getSignatureType().toString());
                currentReader.setStatus(true);
                foundReader = true;
                signatoryService.saveSignatory(currentReader);
            }
            System.out.println(currentReader.isStatus());
            documentFinished &= currentReader.isStatus();
        }
        if (documentFinished) {
            document.setState(DocumentState.READ);
        }
        documentService.addDocument(document);
        if (foundReader) {
            response.setStatus(STATUS_CODE_OK);
            return response;
        } else {
            response.setStatus(STATUS_CODE_NOT_READER);
            response.setMessage("The user is not a reader for this document.");
            return response;
        }
    }

    private JSONResponseObject manageSignatureInOrder(User reader, Document document) {
        List<Signatory> signatories = document.getSignatories();
        JSONResponseObject response = new JSONResponseObject();
        Signatory currentReader = document.getCurrentSignatory(signatories);
        if (matchesSignatory(reader, currentReader)) {
            currentReader.setStatus(true);
            signatoryService.saveSignatory(currentReader);
            checkIfClosed(document, signatories, response, currentReader);
            documentService.addDocument(document);
            response.setStatus(STATUS_CODE_OK);
            return response;
        } else {
            response.setStatus(STATUS_CODE_WRONG_USER);
            response.setMessage("The user is either not a reader for this document,"
                + "or it is currently not his turn");
            return response;
        }
    }

    private static void checkIfClosed(Document document, List<Signatory> signatories, JSONResponseObject response,
                                      Signatory currentReader) {
        if (signatories.get(signatories.size() - 1).equals(currentReader)) {
            document.setState(DocumentState.CLOSED);
            response.setMessage("Document is now closed.");
        }
    }

    private boolean matchesSignatory(User reader, Signatory currentReader) {
        return currentReader != null && currentReader.getSignatureType().equals(SignatureType.REVIEW)
            && currentReader.getUser().equals(reader);
    }

}

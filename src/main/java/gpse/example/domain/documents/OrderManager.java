package gpse.example.domain.documents;

import gpse.example.domain.signature.Signatory;
import gpse.example.domain.signature.SignatureType;

import java.util.List;

/**
 * The helping class responsible for handling the action order for the signatories/readers.
 */
public class OrderManager {

    /**
     * The method responsible for handling the order.
     * @param reader the user who needs to be checked
     * @param document the document containing the order of and the signatories
     * @param signatureType the type of signature the user needs to provide
     * @return true, if all the conditions are met; false otherwise
     */
    public boolean manageSignatoryTurn(final String reader, final Document document,
                                       final SignatureType signatureType) {
        if (document.isOrderRelevant()) {
            return manageSignatureInOrder(reader, document, signatureType);
        } else {
            return manageSignatureWithoutOrder(reader, document, signatureType);
        }
    }

    private boolean manageSignatureWithoutOrder(final String reader, final Document document,
                                                final SignatureType signatureType) {
        List<Signatory> signatories;
        switch (signatureType) {
            case REVIEW:
                signatories = document.getReaders();
                return findSignatoryInList(signatories, reader, signatureType)
                        && document.getState().equals(DocumentState.REVIEW);
            case SIMPLE_SIGNATURE:
            case ADVANCED_SIGNATURE:
                signatories = document.getSignatories();
                return findSignatoryInList(signatories, reader, signatureType)
                        && document.getState().equals(DocumentState.SIGN);
            default:
                return false;
        }
    }

    private boolean findSignatoryInList(final List<Signatory> signatories, final String signatoryToFind,
                                        final SignatureType signatureType) {
        boolean foundSignatory = false;
        for (final Signatory currentSignatory : signatories) {
            if (currentSignatory.getEmail().equals(signatoryToFind)
                && currentSignatory.getSignatureType().equals(signatureType)) {
                foundSignatory = true;
                break;
            }
        }
        return foundSignatory;
    }

    private boolean manageSignatureInOrder(final String reader, final Document document,
                                           final SignatureType signatureType) {
        final Signatory currentReader = document.getCurrentSignatory();
        return matchesSignatory(reader, currentReader, signatureType);
    }

    private boolean matchesSignatory(final String reader, final Signatory currentReader,
                                     final SignatureType signatureType) {
        return currentReader != null && currentReader.getSignatureType().equals(signatureType)
                && currentReader.getEmail().equals(reader);
    }
}

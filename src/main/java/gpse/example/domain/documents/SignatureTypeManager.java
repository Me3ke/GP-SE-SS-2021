package gpse.example.domain.documents;

import gpse.example.domain.signature.Signatory;
import gpse.example.domain.signature.SignatureType;
import gpse.example.domain.users.User;

import java.util.List;

public class SignatureTypeManager {

    public boolean[] manageCurrentSignatureType(final Document document, final User user) {
        boolean[] signatureTypes = new boolean[2 + 1];
        if (document.isOrderRelevant()) {

        } else {
            if (document.getState().equals(DocumentState.OPEN)) {
                boolean isReader = isSignatoryWithType(document, user, SignatureType.REVIEW);
                signatureTypes[0] = isReader;
                for (int i = 1; i < 3; i++) {
                    signatureTypes[i] = false;
                }
            } else if (document.getState().equals(DocumentState.READ)) {

            }
        }
        return signatureTypes;
    }

    private boolean isSignatoryWithType(final Document document, final User user, final SignatureType signatureType) {
        switch (signatureType) {
            case REVIEW:
                boolean isReader = false;
                List<Signatory> readers = document.getReaders();
                for (Signatory reader : readers) {
                    isReader |= (reader.getUser().equals(user));
                }
                return isReader;
            case SIMPLE_SIGNATURE:
                boolean isSimpleSignatory = false;
                List<Signatory> simpleSignatories = document.getSimpleSignatories();
                for (Signatory simpleSignatory : simpleSignatories) {
                    isSimpleSignatory |= (simpleSignatory.getUser().equals(user));
                }
                return isSimpleSignatory;
            default:
                boolean isAdvancedSignatory = false;
                List<Signatory> advancedSignatories = document.getAdvancedSignatories();
                for (Signatory advancedSignatory : advancedSignatories) {
                    isAdvancedSignatory |= (advancedSignatory.getUser().equals(user));
                }
                return isAdvancedSignatory;
        }
    }
}

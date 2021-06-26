package gpse.example.web.documents;

import gpse.example.domain.documents.Document;
import gpse.example.domain.signature.Signatory;
import gpse.example.domain.signature.SignatureType;
import gpse.example.domain.users.User;
import gpse.example.web.envelopes.DocumentOverviewResponse;

import java.util.Arrays;
import java.util.List;

/**
 * A class which represents a Response for a Document Get request.
 */
public class DocumentGetResponse extends DocumentOverviewResponse {


    private final SignatureType signatureType;
    private final byte[] data;

    /**
     * The default constructor creates the documentGet based on an existing document
     * which is created from the Database beforehand.
     *
     * @param document    the corresponding document for the request.
     * @param owner       the owner of the document.
     * @param currentUser the user doing the request
     */
    public DocumentGetResponse(final Document document, final User owner, final User currentUser) {
        super(document, owner, currentUser);
        this.data = document.getData();
        final List<Signatory> signatories = document.getSignatories();
        SignatureType signatureType = SignatureType.NO_SIGNATURE;
        for (final Signatory currentSignatory : signatories) {
            if (currentSignatory.getUser().equals(currentUser)
                && (currentSignatory.getSignatureType().equals(SignatureType.SIMPLE_SIGNATURE)
                || currentSignatory.getSignatureType().equals(SignatureType.ADVANCED_SIGNATURE))) {
                signatureType = currentSignatory.getSignatureType();
            }
        }
        this.signatureType = signatureType;
    }

    public SignatureType getSignatureType() {
        return signatureType;
    }

    public byte[] getData() {
        return Arrays.copyOf(data, data.length);
    }
}

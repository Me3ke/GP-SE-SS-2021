package gpse.example.domain.documents;

import gpse.example.domain.signature.AdvancedSignature;
import gpse.example.domain.signature.Signatory;
import gpse.example.domain.signature.SignatureType;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The ArchivedDocument class is similar to the Document class only that
 * all of the variables of the archived Document are final and therefore
 * immutable. It extends Document to be treated similar in terms of the database.
 */
public class ArchivedDocument extends Document {

    private final long id;

    private final String title;

    private final DocumentMetaData documentMetaData;

    private final List<Signatory> signatories;

    private final List<AdvancedSignature> advancedSignatures;

    private final String documentType;

    private final SignatureType signatureType;

    private final byte[] data;

    private final boolean orderRelevant;

    private final LocalDateTime endDate;

    private final DocumentState state;

    /**
     * Default constructor for an archived Document.
     * @param document the document from which it descends.
     */
    public ArchivedDocument(final Document document) {
        this.id = document.getId();
        this.title = document.getDocumentTitle();
        this.documentMetaData = document.getDocumentMetaData();
        this.signatories = document.getSignatories();
        this.advancedSignatures = document.getAdvancedSignatures();
        this.documentType = document.getDocumentType();
        this.signatureType = document.getSignatureType();
        this.data = document.getData();
        this.orderRelevant = document.isOrderRelevant();
        this.endDate = document.getEndDate();
        this.state = DocumentState.CLOSED;
    }
}

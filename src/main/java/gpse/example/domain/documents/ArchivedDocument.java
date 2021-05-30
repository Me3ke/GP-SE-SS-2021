package gpse.example.domain.documents;

import gpse.example.domain.signature.AdvancedSignature;
import gpse.example.domain.signature.Signatory;
import gpse.example.domain.signature.SignatureType;

import java.time.LocalDateTime;
import java.util.Arrays;
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

    private final List<Signatory> readers;

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
        this.readers = document.getReaders();
        this.documentType = document.getDocumentType();
        this.signatureType = document.getSignatureType();
        this.data = document.getData();
        this.orderRelevant = document.isOrderRelevant();
        this.endDate = document.getEndDate();
        this.state = DocumentState.CLOSED;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public DocumentMetaData getDocumentMetaData() {
        return documentMetaData;
    }

    @Override
    public List<Signatory> getSignatories() {
        return signatories;
    }

    @Override
    public List<AdvancedSignature> getAdvancedSignatures() {
        return advancedSignatures;
    }

    @Override
    public List<Signatory> getReaders() {
        return readers;
    }

    @Override
    public String getDocumentType() {
        return documentType;
    }

    @Override
    public SignatureType getSignatureType() {
        return signatureType;
    }

    @Override
    public byte[] getData() {
        return Arrays.copyOf(data, data.length);
    }

    @Override
    public boolean isOrderRelevant() {
        return orderRelevant;
    }

    @Override
    public LocalDateTime getEndDate() {
        return endDate;
    }

    @Override
    public DocumentState getState() {
        return state;
    }
}

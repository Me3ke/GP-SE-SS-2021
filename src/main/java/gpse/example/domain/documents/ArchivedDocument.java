package gpse.example.domain.documents;

import gpse.example.domain.signature.AdvancedSignature;
import gpse.example.domain.signature.Signatory;
import gpse.example.domain.signature.SignatureType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The ArchivedDocument class is similar to the Document class only that
 * all of the variables of the archived Document are final and therefore
 * immutable. It extends Document to be treated similar in terms of the database.
 */
@Entity
public class ArchivedDocument extends Document {

    @Column
    @Id
    private long id;

    @Column
    private String title;

    @OneToOne
    private DocumentMetaData documentMetaData;

    @OneToMany
    private List<Signatory> signatories = new ArrayList<>();

    @OneToMany
    private List<AdvancedSignature> advancedSignatures = new ArrayList<>();

    @OneToMany
    private List<Signatory> readers = new ArrayList<>();

    @Column
    private String documentType;

    @Column
    private SignatureType signatureType = SignatureType.NO_SIGNATURE;

    @Lob
    private byte[] data;

    @Column
    private boolean orderRelevant;

    @Column
    private LocalDateTime endDate;

    @Column
    private DocumentState state;

    /**
     * Default constructor for an archived Document.
     * @param document the document from which it descends.
     */
    public ArchivedDocument(final Document document) {
        this.id = document.getId();
        this.title = document.getDocumentTitle();
        this.documentMetaData = new DocumentMetaData(document.getDocumentMetaData());
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

    protected ArchivedDocument() {

    }

    @Override
    public String getDocumentTitle() {
        return documentMetaData.getMetaDocumentTitle();
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
    public String getOwner() {
        return documentMetaData.getMetaUserID();
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

    @Override
    public void setSignatureType(SignatureType signatureType) {
    }

    @Override
    public void setOrderRelevant(boolean orderRelevant) {
    }

    @Override
    public void setEndDate(LocalDateTime endDate) {
    }

    @Override
    public void setState(DocumentState state) {
    }
}

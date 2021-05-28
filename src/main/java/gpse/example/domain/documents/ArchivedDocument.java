package gpse.example.domain.documents;

import gpse.example.domain.signature.AdvancedSignature;
import gpse.example.domain.signature.Signatory;
import gpse.example.domain.signature.SignatureType;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

//TODO maybe extends document, see if attributes can be overwritten by
//TODO set methods from documentclass and what happens.
/**
 *
 */
public class ArchivedDocument {

    private final long id;

    private final String title;

    private final DocumentMetaData documentMetaData;

    private final List<Signatory> signatories;

    private final List<AdvancedSignature> advancedSignatures;

    private final List<Signatory> readers;

    private final File documentFile;

    private final String documentType;

    private final SignatureType signatureType;

    private final byte[] data;

    private final boolean orderRelevant;

    private final LocalDateTime endDate;

    private final DocumentState state;

    public ArchivedDocument(final Document document) {
        this.id = document.getId();
        this.title = document.getDocumentTitle();
        this.documentMetaData = document.getDocumentMetaData();
        this.signatories = document.getSignatories();
        this.advancedSignatures = document.getAdvancedSignatures();
        this.readers = document.getReaders();
        this.documentFile = document.getDocumentFile();
        this.documentType = document.getDocumentType();
        this.signatureType = document.getSignatureType();
        this.data = document.getData();
        this.orderRelevant = document.isOrderRelevant();
        this.endDate = document.getEndDate();
        this.state = DocumentState.CLOSED;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public DocumentMetaData getDocumentMetaData() {
        return documentMetaData;
    }

    public List<Signatory> getSignatories() {
        return signatories;
    }

    public List<AdvancedSignature> getAdvancedSignatures() {
        return advancedSignatures;
    }

    public List<Signatory> getReaders() {
        return readers;
    }

    public File getDocumentFile() {
        return documentFile;
    }

    public String getDocumentType() {
        return documentType;
    }

    public SignatureType getSignatureType() {
        return signatureType;
    }

    public byte[] getData() {
        return Arrays.copyOf(data, data.length);
    }

    public boolean isOrderRelevant() {
        return orderRelevant;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public DocumentState getState() {
        return state;
    }
}

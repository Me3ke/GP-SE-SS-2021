package gpse.example.domain.documents;

import gpse.example.domain.signature.SignatureType;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * The ArchivedDocument class is similar to the Document class only that
 * all of the variables of the archived Document are final and therefore
 * immutable. It extends Document to be treated similar in terms of the database.
 */
@Entity
public class ArchivedDocument extends Document {

    /**
     * Default constructor for an archived Document.
     * @param document the document from which it descends.
     */
    public ArchivedDocument(final Document document) {
        this.id = document.getId();
        this.documentMetaData = new DocumentMetaData(document.getDocumentMetaData());
        this.signatories = document.getSignatories();
        this.documentType = document.getDocumentType();
        this.signatureType = document.getSignatureType();
        this.data = document.getData();
        this.orderRelevant = document.isOrderRelevant();
        this.endDate = document.getEndDate();
        this.state = DocumentState.CLOSED;
        this.previousVersion = document.getPreviousVersion();
    }

    protected ArchivedDocument() {

    }

    @Override
    public void setSignatureType(final SignatureType signatureType) {

    }

    @Override
    public void setOrderRelevant(final boolean orderRelevant) {
    }

    @Override
    public void setEndDate(final LocalDateTime endDate) {
    }

    @Override
    public void setState(final DocumentState state) {
    }

}

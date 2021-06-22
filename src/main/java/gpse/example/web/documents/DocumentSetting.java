package gpse.example.web.documents;

import gpse.example.domain.documents.Document;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The class used to send the current settings of a document to the frontend.
 */
public class DocumentSetting {

    private long documentID;
    private List<SignatorySetting> signatories;
    private boolean orderRelevant;
    private String endDate;

    /**
     * the standard constructor.
     * @param document the relating document.
     */
    public DocumentSetting(final Document document) {
        this.documentID = document.getId();
        signatories = new ArrayList<>();
        for (int i = 0; i < document.getSignatories().size(); i++) {
            signatories.add(new SignatorySetting(document.getSignatories().get(i)));
        }
        this.orderRelevant = document.isOrderRelevant();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        this.endDate = document.getEndDate().format(formatter);
    }

    public long getDocumentID() {
        return documentID;
    }

    public void setDocumentID(final long documentID) {
        this.documentID = documentID;
    }

    public List<SignatorySetting> getSignatories() {
        return signatories;
    }

    public void setSignatories(final List<SignatorySetting> signatories) {
        this.signatories = signatories;
    }

    public boolean isOrderRelevant() {
        return orderRelevant;
    }

    public void setOrderRelevant(final boolean orderRelevant) {
        this.orderRelevant = orderRelevant;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(final String endDate) {
        this.endDate = endDate;
    }
}

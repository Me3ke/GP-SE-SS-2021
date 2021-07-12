package gpse.example.web.documents;

import gpse.example.domain.documents.Document;
import gpse.example.domain.documents.SignatoryManagement;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The class used to send the current settings of a document to the frontend.
 */
public class DocumentSetting extends DocumentSettingsCMD {

    private long documentID;
    private List<SignatorySetting> signatories;
    private boolean orderRelevant;
    private String endDate;
    private boolean showHistory;

    /**
     * the standard constructor.
     * @param document the relating document.
     */
    public DocumentSetting(final Document document) {
        super();
        this.documentID = document.getId();
        final SignatoryManagement signatoryManagement = document.getSignatoryManagement();
        signatories = new ArrayList<>();
        for (int i = 0; i < signatoryManagement.getSignatories().size(); i++) {
            signatories.add(new SignatorySetting(signatoryManagement.getSignatories().get(i)));
        }
        this.orderRelevant = document.isOrderRelevant();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        this.endDate = document.getEndDate().format(formatter);
        this.showHistory = document.isShowHistory();
    }

    public long getDocumentID() {
        return documentID;
    }

    public void setDocumentID(final long documentID) {
        this.documentID = documentID;
    }

    @Override
    public List<SignatorySetting> getSignatories() {
        return signatories;
    }

    @Override
    public void setSignatories(final List<SignatorySetting> signatories) {
        this.signatories = signatories;
    }

    @Override
    public boolean isOrderRelevant() {
        return orderRelevant;
    }

    @Override
    public void setOrderRelevant(final boolean orderRelevant) {
        this.orderRelevant = orderRelevant;
    }

    @Override
    public String getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(final String endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean isShowHistory() {
        return showHistory;
    }

    @Override
    public void setShowHistory(final boolean showHistory) {
        this.showHistory = showHistory;
    }
}

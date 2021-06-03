package gpse.example.domain.documents;

import gpse.example.domain.signature.Signatory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Containerclass for relevant documentsettings.
 */
public class DocumentSettingsCMD {

    private List<Signatory> signatories;
    private boolean orderRelevant;
    private String endDate;

    public LocalDateTime convertEndDate() {
        return LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    public List<Signatory> getSignatories() {
        return signatories;
    }

    public void setSignatories(List<Signatory> signatories) {
        this.signatories = signatories;
    }

    public boolean isOrderRelevant() {
        return orderRelevant;
    }

    public void setOrderRelevant(boolean orderRelevant) {
        this.orderRelevant = orderRelevant;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}

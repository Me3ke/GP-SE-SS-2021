package gpse.example.web.documents;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Containerclass for relevant documentsettings.
 */
public class DocumentSettingsCMD {

    private List<SignatorySetting> signatories;
    private boolean orderRelevant;
    private String endDate;
    private boolean showHistory;

    public LocalDateTime convertEndDate() {
        return LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
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

    public boolean isShowHistory() {
        return showHistory;
    }

    public void setShowHistory(final boolean showHistory) {
        this.showHistory = showHistory;
    }
}

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

    /**
     * the method used to convert the String, that we get from the frontend to LocalDateTime.
     *
     * @return the deadline as LocalDateTime
     */
    public LocalDateTime convertEndDate() {
        if (endDate.equals("")) {
            return null;
        }
        return LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public List<SignatorySetting> getSignatories() {
        return signatories;
    }

    public void setSignatories(List<SignatorySetting> signatories) {
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

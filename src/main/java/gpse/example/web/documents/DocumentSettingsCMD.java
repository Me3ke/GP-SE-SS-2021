package gpse.example.web.documents;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Containerclass for relevant documentsettings.
 */
public class DocumentSettingsCMD {

    private List<SignatorySetting> signatories;
    private boolean orderRelevant;
    private String endDate;
    private boolean showHistory;
    private boolean draft;
    private boolean archiveTask;

    /**
     * the method used to convert the String, that we get from the frontend to LocalDateTime.
     *
     * @return the deadline as LocalDateTime
     */
    public LocalDateTime convertEndDate() {
        if (endDate.equals("")) {
            return null;
        }
        try {
            return LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (DateTimeParseException dtpe) {
            return LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        }
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

    public void setShowHistory(boolean showHistory) {
        this.showHistory = showHistory;
    }

    public boolean isDraft() {
        return draft;
    }

    public void setDraft(boolean draft) {
        this.draft = draft;
    }

    public boolean isArchiveTask() {
        return archiveTask;
    }

    public void setArchiveTask(boolean archiveTask) {
        this.archiveTask = archiveTask;
    }
}

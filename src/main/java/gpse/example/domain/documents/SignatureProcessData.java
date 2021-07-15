package gpse.example.domain.documents;

import gpse.example.web.documents.DocumentPutRequest;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Data of Signature Process.
 */
@Entity
public class SignatureProcessData {

    /**
     * Id of processData.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    protected long id;

    @Column
    private boolean orderRelevant;

    @Column
    private LocalDateTime endDate;

    @Column
    private DocumentState state;

    @Column
    private long processEmailTemplateId;

    @Column
    private boolean showHistory;

    @Column
    private boolean draft;

    /**
     * The container containing the information about the signature process.
     *
     * @param documentPutRequest
     */
    public SignatureProcessData(final DocumentPutRequest documentPutRequest) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.endDate = LocalDateTime.parse(documentPutRequest.getEndDate(), formatter);
        this.draft = documentPutRequest.isDraft();
        if (this.draft) {
            boolean isValidFormat = true;
            try {
                formatter.parse(documentPutRequest.getEndDate());
            } catch (DateTimeParseException exception) {
                isValidFormat = false;
            }
            if (isValidFormat) {
                this.endDate = LocalDateTime.parse(documentPutRequest.getEndDate(), formatter);
            }
        } else {
            this.endDate = LocalDateTime.parse(documentPutRequest.getEndDate(), formatter);
        }
        this.orderRelevant = documentPutRequest.isOrderRelevant();
        this.showHistory = documentPutRequest.isShowHistory();
    }

    public SignatureProcessData() {

    }

    public boolean isOrderRelevant() {
        return orderRelevant;
    }

    /**
     * The method used to change whether the order the signatures and reviews should matter or not.
     * If the document is archived, nothing happens.
     *
     * @param orderRelevant the new boolean for orderRelevant.
     */
    public void setOrderRelevant(final boolean orderRelevant) {
        if (!state.equals(DocumentState.ARCHIVED)) {
            this.orderRelevant = orderRelevant;
        }
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * The method used to change the deadline. If the document is archived, nothing happens.
     *
     * @param endDate the new deadline.
     */
    public void setEndDate(final LocalDateTime endDate) {
        if (!state.equals(DocumentState.ARCHIVED)) {
            this.endDate = endDate;
        }
    }

    public DocumentState getState() {
        return state;
    }

    /**
     * the method used to change the current state of the document.  If the document is archived, nothing happens.
     *
     * @param documentState the new documentState
     */
    public void setState(final DocumentState documentState) {
        if (state == null || !state.equals(DocumentState.ARCHIVED)) {
            this.state = documentState;
        }
    }

    public long getProcessEmailTemplateId() {
        return processEmailTemplateId;
    }

    /**
     * The method used to change the emailTemplate for this document. If the document is archived, nothing happens.
     *
     * @param processEmailTemplateId the id of the template that should be used.
     */
    public void setProcessEmailTemplateId(final long processEmailTemplateId) {
        if (!state.equals(DocumentState.ARCHIVED)) {
            this.processEmailTemplateId = processEmailTemplateId;
        }
    }

    public boolean isShowHistory() {
        return showHistory;
    }

    /**
     * The method used to change whether the history of the document
     * should be shown to signatories who are not the owner. If the document is archived, nothing happens.
     *
     * @param showHistory the boolean that showHistory should be set to.
     */
    public void setShowHistory(final boolean showHistory) {
        if (!state.equals(DocumentState.ARCHIVED)) {
            this.showHistory = showHistory;
        }
    }

    public boolean isDraft() {
        return draft;
    }

    /**
     * The Method used to change the draft state. It can only be changed if the document is
     * currently in the draft state and is not archived yet.
     *
     * @param draft the parameter to which the state should be set.
     */
    public void setDraft(final boolean draft) {
        if (!state.equals(DocumentState.ARCHIVED) && draft) {
            this.draft = draft;
        }
    }
}

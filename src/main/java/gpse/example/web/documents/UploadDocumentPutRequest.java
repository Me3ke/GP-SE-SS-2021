package gpse.example.web.documents;

import gpse.example.domain.signature.Signatory;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * The class responsible for handling the upload of documents.
 */
public class UploadDocumentPutRequest {
    private String title;
    private byte[] data;
    private String type;
    private List<Signatory> signatories;
    private LocalDateTime endDate;
    private boolean orderRelevant;

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(final LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public boolean isOrderRelevant() {
        return orderRelevant;
    }

    public void setOrderRelevant(final boolean orderRelevant) {
        this.orderRelevant = orderRelevant;
    }

    public byte[] getData() {
        return Arrays.copyOf(data, data.length);
    }

    public void setData(final byte[] data) {
        this.data = data.clone();
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public List<Signatory> getSignatories() {
        return signatories;
    }

    public void setSignatories(final List<Signatory> signatories) {
        this.signatories = signatories;
    }

}

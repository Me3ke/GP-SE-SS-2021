package gpse.example.domain.documents;

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

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public boolean isOrderRelevant() {
        return orderRelevant;
    }

    public void setOrderRelevant(boolean orderRelevant) {
        this.orderRelevant = orderRelevant;
    }

    public byte[] getData() {
        return Arrays.copyOf(data, data.length);
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Signatory> getSignatories() {
        return signatories;
    }

    public void setSignatories(List<Signatory> signatories) {
        this.signatories = signatories;
    }

}

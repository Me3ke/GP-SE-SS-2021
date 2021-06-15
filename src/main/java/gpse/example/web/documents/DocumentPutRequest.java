package gpse.example.web.documents;

import gpse.example.domain.signature.ProtoSignatory;

import java.util.Arrays;
import java.util.List;

/**
 * The documentPut class is a CommandPattern class to specify the the Rest-Api.
 */
public class DocumentPutRequest {

    private byte[] data;
    private String title;
    private String dataType;
    private List<ProtoSignatory> signatories;
    private String endDate;
    private boolean orderRelevant;
    private String lastModified;

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(final String lastModified) {
        this.lastModified = lastModified;
    }

    public byte[] getData() {
        return Arrays.copyOf(data, data.length);
    }

    public void setData(final byte[] data) {
        this.data = data.clone();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(final String dataType) {
        this.dataType = dataType;
    }

    public List<ProtoSignatory> getSignatories() {
        return signatories;
    }

    public void setSignatories(final List<ProtoSignatory> signatories) {
        this.signatories = signatories;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(final String endDate) {
        this.endDate = endDate;
    }

    public boolean isOrderRelevant() {
        return orderRelevant;
    }

    public void setOrderRelevant(final boolean orderRelevant) {
        this.orderRelevant = orderRelevant;
    }
}

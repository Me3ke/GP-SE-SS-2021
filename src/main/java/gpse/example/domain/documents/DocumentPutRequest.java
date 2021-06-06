package gpse.example.domain.documents;

import gpse.example.domain.signature.ProtoSignatory;

import java.util.List;

/**
 * The documentPut class is a CommandPattern class to specify the the Rest-Api.
 */
public class DocumentPutRequest {

    private byte[] data;
    private String title;
    private String dataType;
    private List<ProtoSignatory> signatoriesID;
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
        return data;
    }

    public void setData(final byte[] data) {
        this.data = data;
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

    public List<ProtoSignatory> getSignatoriesID() {
        return signatoriesID;
    }

    public void setSignatoriesID(final List<ProtoSignatory> signatoriesID) {
        this.signatoriesID = signatoriesID;
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

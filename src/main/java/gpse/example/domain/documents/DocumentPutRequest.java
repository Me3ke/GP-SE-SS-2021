package gpse.example.domain.documents;

import gpse.example.domain.signature.SignatureType;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * The documentPut class is a CommandPattern class to specify the the Rest-Api.
 */
public class DocumentPutRequest {

    private byte[] data;
    private String title;
    private String dataType;
    private List<String> signatoriesID;
    private List<String> readersID;
    private SignatureType signatureType;
    private LocalDateTime endDate;
    private boolean orderRelevant;
    private DocumentState state;
    private LocalDateTime lastModified;

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(final LocalDateTime lastModified) {
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

    public List<String> getSignatoriesID() {
        return signatoriesID;
    }

    public void setSignatoriesID(final List<String> signatoriesID) {
        this.signatoriesID = signatoriesID;
    }

    public List<String> getReadersID() {
        return readersID;
    }

    public void setReadersID(final List<String> readersID) {
        this.readersID = readersID;
    }

    public SignatureType getSignatureType() {
        return signatureType;
    }

    public void setSignatureType(final SignatureType signatureType) {
        this.signatureType = signatureType;
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

    public DocumentState getState() {
        return state;
    }

    public void setState(final DocumentState state) {
        this.state = state;
    }
}

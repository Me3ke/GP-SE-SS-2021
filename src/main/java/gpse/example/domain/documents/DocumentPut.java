package gpse.example.domain.documents;

import gpse.example.domain.signature.SignatureType;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * The documentPut class is a CommandPattern class to specify the the Rest-Api.
 */
public class DocumentPut {

    private String path;
    private String title;
    private String type;
    private List<String> signatoriesID;
    private List<String> readersID;
    private SignatureType signatureType;
    private LocalDateTime endDate;
    private boolean orderRelevant;
    private byte[] data;

    public String getPath() {
        return path;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
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

    public byte[] getData() {
        return Arrays.copyOf(data, data.length);
    }

    public void setData(final byte[] data) {
        this.data = Arrays.copyOf(data, data.length);
    }
}

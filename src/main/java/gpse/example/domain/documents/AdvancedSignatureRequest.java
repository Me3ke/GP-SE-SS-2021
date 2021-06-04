package gpse.example.domain.documents;

/**
 * Class responsible for containing the advanced Signature
 */
public class AdvancedSignatureRequest {
    private final String hashedDocID;

    public AdvancedSignatureRequest(String hashedDocID) {
        this.hashedDocID = hashedDocID;
    }
    public String getHashedDocID() {
        return hashedDocID;
    }
}

package gpse.example.domain.documents;

/**
 * Class responsible for containing the advanced Signature
 */
public class AdvancedSignatureRequest {
    private final String signature;

    public AdvancedSignatureRequest(String signature) {
        this.signature = signature;
    }

    public String getSignature() {
        return signature;
    }
}

package gpse.example.web;

/**
 * The class used to get the 2FacAuth-code from a request-body.
 */
public class AuthCodeValidationRequest {

    private String qrCodeCode;

    public String getQrCodeCode() {
        return qrCodeCode;
    }

    public void setQrCodeCode(String qrCodeCode) {
        this.qrCodeCode = qrCodeCode;
    }
}

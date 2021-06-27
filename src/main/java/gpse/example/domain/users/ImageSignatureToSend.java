package gpse.example.domain.users;

/**
 * The class responsible for sending the image siganture between frontend and backend.
 */
public class ImageSignatureToSend {

    private byte[] imageSignature;

    private String imageSignatureType;

    public ImageSignatureToSend(byte[] imageSignature, String imageSignatureType) {
        this.imageSignature = imageSignature;
        this.imageSignatureType = imageSignatureType;
    }

    public ImageSignatureToSend() {

    }

    public byte[] getImageSignature() {
        return imageSignature;
    }

    public void setImageSignature(byte[] imageSignature) {
        this.imageSignature = imageSignature;
    }

    public String getImageSignatureType() {
        return imageSignatureType;
    }

    public void setImageSignatureType(String imageSignatureType) {
        this.imageSignatureType = imageSignatureType;
    }
}

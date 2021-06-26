package gpse.example.domain.users;

/**
 * The class responsible for sending the image siganture between frontend and backend.
 */
public class ImageSignatureToSend {

    private byte[] imageSignature;

    public ImageSignatureToSend(byte[] imageSignature) {
        this.imageSignature = imageSignature;
    }

    public ImageSignatureToSend() {

    }

    public byte[] getImageSignature() {
        return imageSignature;
    }

    public void setImageSignature(byte[] imageSignature) {
        this.imageSignature = imageSignature;
    }
}

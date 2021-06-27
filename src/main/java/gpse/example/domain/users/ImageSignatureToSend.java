package gpse.example.domain.users;

import java.util.Arrays;

/**
 * The class responsible for sending the image siganture between frontend and backend.
 */
public class ImageSignatureToSend {

    private byte[] imageSignature;

    private String imageSignatureType;

    public ImageSignatureToSend(byte[] imageSignature, String imageSignatureType) {
        this.imageSignature = imageSignature.clone();
        this.imageSignatureType = imageSignatureType;
    }

    public ImageSignatureToSend() {

    }

    public byte[] getImageSignature() {
        return Arrays.copyOf(imageSignature, imageSignature.length);
    }

    public void setImageSignature(byte[] imageSignature) {
        this.imageSignature = imageSignature.clone();
    }

    public String getImageSignatureType() {
        return imageSignatureType;
    }

    public void setImageSignatureType(String imageSignatureType) {
        this.imageSignatureType = imageSignatureType;
    }
}

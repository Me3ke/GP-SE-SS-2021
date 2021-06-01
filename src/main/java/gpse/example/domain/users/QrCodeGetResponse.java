package gpse.example.domain.users;

/**
 * The class used to contain a byte array representing a qr-code, to produce
 *  a fitting json response.
 */
public class QrCodeGetResponse {
    private byte[] data;

    public QrCodeGetResponse(final byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(final byte[] data) {
        this.data = data;
    }

}

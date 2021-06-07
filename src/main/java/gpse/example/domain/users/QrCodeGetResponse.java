package gpse.example.domain.users;

import java.util.Arrays;

/**
 * The class used to contain a byte array representing a qr-code, to produce
 *  a fitting json response.
 */
public class QrCodeGetResponse {
    private byte[] data;

    public QrCodeGetResponse(final byte[] data) {
        this.data = data.clone();
    }

    public byte[] getData() {
        return Arrays.copyOf(data, data.length);
    }

    public void setData(final byte[] data) {
        this.data = data.clone();
    }

}

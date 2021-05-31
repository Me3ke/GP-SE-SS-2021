package gpse.example.domain.users;

/**
 *
 */
public class QrCodeGetResponse {
    private byte[] data;

    public QrCodeGetResponse(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

}

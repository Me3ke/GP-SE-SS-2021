package gpse.example.web;

/**
 * an response POJO getting the status and an message.
 */
public class JSONResponseObject {

    private int status;
    private String message;

    public JSONResponseObject() {

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int statusCode) {
        this.status = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


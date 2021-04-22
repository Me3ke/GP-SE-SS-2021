package gpse.example.util;

public class XMLTransformationException extends Exception {

    static final long serialVersionUID = 201;

    public XMLTransformationException() {
        super();
    }

    public XMLTransformationException(String message) {
        super(message);
    }

    public XMLTransformationException(Exception exc) {
        super(exc);
    }
}

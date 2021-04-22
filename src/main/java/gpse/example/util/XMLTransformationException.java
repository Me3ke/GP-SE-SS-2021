package gpse.example.util;

/**
 * thrown if an error occures while generating the xml file
 */
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

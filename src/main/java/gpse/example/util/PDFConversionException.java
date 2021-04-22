package gpse.example.util;

public class PDFConversionException extends Exception {

    static final long serialVersionUID = 202;
    public PDFConversionException() {
        super();
    }

    public PDFConversionException(String message) {
        super(message);
    }

    public PDFConversionException(Exception exc) {
        super(exc);
    }
}

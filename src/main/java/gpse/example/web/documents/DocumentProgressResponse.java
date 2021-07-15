package gpse.example.web.documents;

import gpse.example.domain.signature.Signatory;
import gpse.example.domain.signature.SignatureType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A class which represents a Response for a Document progress request.
 */
public class DocumentProgressResponse {

    private final List<Signatory> signatories;
    private final List<Signatory> alreadySigned = new ArrayList<>();
    private final List<Signatory> readers;
    private final List<Signatory> alreadyRead = new ArrayList<>();
    private final LocalDateTime endDate;

    /**
     * The default constructor creates the response based on an data
     * extracted from an existing document.
     * @param signatories a list of signatories from the corresponding document.
     * @param readers a list of readers from the corresponding document.
     * @param endDate the date to which the document has to be signed.
     */
    public DocumentProgressResponse(final List<Signatory> signatories, final List<Signatory> readers,
                                    final LocalDateTime endDate) {
        final List<Signatory> actualSignatories = new ArrayList<>();
        for (final Signatory signatory : signatories) {
            if (signatory.getSignatureType() == SignatureType.ADVANCED_SIGNATURE
                    || signatory.getSignatureType() == SignatureType.SIMPLE_SIGNATURE) {
                actualSignatories.add(signatory);
            }
        }
        for (final Signatory actualSignatory: actualSignatories) {
            if (actualSignatory.isStatus()) {
                alreadySigned.add(actualSignatory);
            }
        }

        for (final Signatory reader : readers) {
            if (reader.isStatus()) {
                alreadyRead.add(reader);
            }
        }
        this.signatories = actualSignatories;
        this.readers = readers;
        this.endDate = endDate;
    }

    public List<Signatory> getSignatories() {
        return signatories;
    }

    public List<Signatory> getAlreadySigned() {
        return alreadySigned;
    }

    public List<Signatory> getReaders() {
        return readers;
    }

    public List<Signatory> getAlreadyRead() {
        return alreadyRead;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
}

package gpse.example.domain.documents;

import gpse.example.domain.signature.Signatory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DocumentProgressResponse {
    
    private List<Signatory> signatories;
    private List<Signatory> alreadySigned = new ArrayList<>();
    private List<Signatory> readers;
    private List<Signatory> alreadyRead = new ArrayList<>();
    private LocalDateTime endDate;

    public DocumentProgressResponse(List<Signatory> signatories, List<Signatory> readers, LocalDateTime endDate) {
        for (final Signatory signatory : signatories) {
            if (signatory.isStatus()) {
                alreadySigned.add(signatory);
            }
        }
        for (final Signatory reader : readers) {
            if (reader.isStatus()) {
                alreadySigned.add(reader);
            }
        }
        this.signatories = signatories;
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

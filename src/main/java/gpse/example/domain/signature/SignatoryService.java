package gpse.example.domain.signature;

import java.util.List;

/**
 * The standard interface for Signatories.
 */
public interface SignatoryService {
    List<Signatory> saveSignatories(List<Signatory> signatories);
    void deleteAll();
    void delete(final List<Signatory> signatories);
    Signatory saveSignatory(Signatory signatory);
}

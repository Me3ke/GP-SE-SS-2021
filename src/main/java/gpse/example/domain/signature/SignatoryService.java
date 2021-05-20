package gpse.example.domain.signature;

import java.util.List;

/**
 * The standard interface for Signatories.
 */
public interface SignatoryService {
    List<Signatory> saveSignatories(List<Signatory> signatories);
    void deleteAll();
}

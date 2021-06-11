package gpse.example.domain.signature;

import java.util.List;

/**
 * The standard interface for Signatories.
 */
public interface SignatoryService {
    void delete(final List<Signatory> signatories);
}

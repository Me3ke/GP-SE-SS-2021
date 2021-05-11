package gpse.example.domain.envelopes;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * the interface from which Spring creates a repository for the Envelopes.
 */
@Repository
public interface EnvelopeRepository extends CrudRepository<Envelope, Long> {
}

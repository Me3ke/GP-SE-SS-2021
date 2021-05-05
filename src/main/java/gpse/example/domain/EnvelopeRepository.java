package gpse.example.domain;

import org.springframework.data.repository.CrudRepository;

/**
 * the interface from which Spring creates a repository for the Envelopes.
 */
public interface EnvelopeRepository extends CrudRepository<Envelope, Long> {
}

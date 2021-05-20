package gpse.example.domain.signature;

import org.springframework.data.repository.CrudRepository;

/**
 * The standard repository for Signatory-objects.
 */
public interface SignatoryRepository extends CrudRepository<Signatory, Long> {
}

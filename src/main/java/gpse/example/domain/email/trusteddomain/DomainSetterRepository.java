package gpse.example.domain.email.trusteddomain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository of DomainSetter.
 */
@Repository
public interface DomainSetterRepository extends CrudRepository<DomainSetter, String> {
}

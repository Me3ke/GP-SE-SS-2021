package gpse.example.util.email.trustedDomain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface DomainSetterRepository extends CrudRepository<DomainSetter, String> {
}

package gpse.example.domain.users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * standard repository.
 */
@Repository
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Long> {
}

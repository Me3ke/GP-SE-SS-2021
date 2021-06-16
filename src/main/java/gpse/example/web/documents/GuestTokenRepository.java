package gpse.example.web.documents;

import gpse.example.domain.users.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * standard repository.
 */
@Repository
public interface GuestTokenRepository extends CrudRepository<GuestToken, Long> {
}


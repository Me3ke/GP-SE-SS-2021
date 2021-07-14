package gpse.example.domain.email;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * the interface from which Spring creates a repository for the Messages.
 */
@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
}

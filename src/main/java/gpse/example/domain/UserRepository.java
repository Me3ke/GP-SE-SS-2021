package gpse.example.domain;

import org.springframework.data.repository.CrudRepository;

/**
 * the interface from which Spring creates a repository for user-objects.
 */
public interface UserRepository extends CrudRepository<User, String> {
}

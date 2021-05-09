package gpse.example.domain;

import java.util.List;
import java.util.Optional;

/**
 * the interface for UserServices.
 */
public interface UserService {
    List<User> getUserList();
    Optional<User> getUser(String email);
}

package gpse.example.domain;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

/**
 * the interface for UserServices.
 */
public interface UserService {
    List<User> getUserList();
    UserDetails loadUserByUsername(String username);
}

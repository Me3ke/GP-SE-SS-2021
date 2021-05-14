package gpse.example.domain.users;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * the interface for UserServices.
 */
public interface UserService {
    UserDetails loadUserByUsername(final String userID);
    List<User> getUserList();
}

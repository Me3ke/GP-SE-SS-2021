package gpse.example.domain;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * the interface for UserServices.
 */
public interface UserService extends UserDetailsService {
    List<User> getUserList();

    @Override
    UserDetails loadUserByUsername(String username);
}

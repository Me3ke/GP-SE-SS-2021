package gpse.example.domain.users;


import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;


/**
 * the interface for UserServices.
 */
public interface UserService extends UserDetailsService {
    User createUser(String username, String password, String firstname, String lastname, String... roles);
    List<User> getUsers();
    User getUser(final String username) throws UsernameNotFoundException;
}

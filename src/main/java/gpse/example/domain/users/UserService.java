package gpse.example.domain.users;


import gpse.example.util.MessageGenerationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;


/**
 * the interface for UserServices.
 */
public interface UserService extends UserDetailsService {
    User createUser(String username, String firstname, String lastname,  String password, String... roles);
    User createUser(String username, String firstname, String lastname, String password,
                    PersonalData personalData, String... roles);
    List<User> getUsers();
    User getUser(final String username) throws UsernameNotFoundException;
    void signUpUser(User user) throws MessageGenerationException;
    void confirmUser(ConfirmationToken confirmationToken);
    void validateUser(User user);
    void infoNewExtUser(User user) throws MessageGenerationException;
    void removeUser(final String username) throws UsernameNotFoundException;
    User saveUser(User user);
}

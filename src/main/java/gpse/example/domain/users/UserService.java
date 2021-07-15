package gpse.example.domain.users;


import gpse.example.domain.email.EmailManagement;
import gpse.example.domain.exceptions.MessageGenerationException;
import gpse.example.domain.exceptions.TemplateNameNotFoundException;
import gpse.example.web.tokens.ConfirmationToken;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.mail.MessagingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;


/**
 * the interface for UserServices.
 */
public interface UserService extends UserDetailsService {
    User createUser(String username, String firstname, String lastname, String password, String... roles);

    User createUser(String username, String firstname, String lastname, String password,
                    PersonalData personalData, String... roles);

    List<User> getUsers();

    User getUser(final String username);

    List<User> getAllUsers();

    void signUpUser(User user, EmailManagement emailManagement) throws MessageGenerationException,
        TemplateNameNotFoundException, MessagingException, InvocationTargetException;

    void confirmUser(ConfirmationToken confirmationToken);

    void validateUser(User user);

    void removeUser(final String username);

    User saveUser(User user);

    boolean checkIfAdmin(final String token);
}

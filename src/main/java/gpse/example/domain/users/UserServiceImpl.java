package gpse.example.domain.users;

import gpse.example.util.SMTPServerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * the class that implements the UserService interface for communication with the database.
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * Standard ConfirmationTokenService.
     */
    private ConfirmationTokenService confirmationTokenService;

    private final UserRepository userRepository;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Lazy
    @Autowired
    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(final String username) throws UsernameNotFoundException {
        return userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " was not found."));
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userRepository.findById(username)
            .orElseThrow(() -> new UsernameNotFoundException("User name " + username + " not found."));
    }

    @Override
    public List<User> getUsers() {
        final List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }
    @Override
    public User createUser(final String username, final String password,
                           final String firstname, final String lastname, final String... roles) {
        final User user = new User(username, firstname, lastname, password);
        for (final String role : roles) {
            user.addRole(role);
        }
        final User saved = userRepository.save(user);
        return saved;
    }

    @Override
    public User createUser(final String username, final String password,
                           final String firstname, final String lastname,
                           final PersonalData personalData, final String... roles) {
        final User user = new User(username, firstname, lastname, password);
        for (final String role : roles) {
            user.addRole(role);
        }
        user.setPersonalData(personalData);
        final User saved = userRepository.save(user);
        return saved;
    }

    @Override
    public void signUpUser(User user) {

        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPassword(user.getPassword());

        final User createdUser = userRepository.save(user);

        final ConfirmationToken token = new ConfirmationToken(user);

        confirmationTokenService.saveConfirmationToken(token);
    }

    @Override
    public void confirmUser(ConfirmationToken confirmationToken) {

        final User user = confirmationToken.getUser();

        user.setEnabled(true);

        userRepository.save(user);

        confirmationTokenService.deleteConfirmationToken(confirmationToken.getId());

    }

    public void sendConfirmationMail(User user, String token) {
        SMTPServerHelper.sendRegistrationEmail(user.getEmail(), user.getUsername(),
            "http://localhost:8080/sign-up/confirm?token="
            + token);
    }
}

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
     * autowired not commited not tested 18.05.21
     */
    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    private final UserRepository userRepository;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * the smtpServerHelper Service for sending emails.
     */
    @Lazy
    @Autowired
    private SMTPServerHelper smtpServerHelper;

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
    public User createUser(final String username, final String firstname,
                           final String lastname, final String password, final String... roles) {
        final User user = new User(username, firstname, lastname, password);
        for (final String role : roles) {
            user.addRole(role);
        }
        return userRepository.save(user);
    }

    @Override
    public User createUser(final String username, final String firstname,
                           final String lastname, final String password,
                           final PersonalData personalData, final String... roles) {
        final User user = new User(username, firstname, lastname, password);
        for (final String role : roles) {
            user.addRole(role);
        }
        user.setPersonalData(personalData);
        return userRepository.save(user);
    }

    @Override
    public void signUpUser(final User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        final User createdUser = userRepository.save(user);
        final ConfirmationToken token = new ConfirmationToken(user);
        final ConfirmationToken savedToken = confirmationTokenService.saveConfirmationToken(token);
        sendConfirmationMail(createdUser, savedToken.getToken());
    }

    @Override
    public void confirmUser(final ConfirmationToken confirmationToken) {

        final User user = confirmationToken.getUser();

        user.setEnabled(true);
        userRepository.save(user);
        confirmationTokenService.deleteConfirmationToken(confirmationToken.getId());
    }

    public void sendConfirmationMail(final User user, final String token) {
        smtpServerHelper.sendRegistrationEmail(user.getEmail(), user.getLastname(),
            "http://localhost:8080/register/confirm/" + token);
    }

    @Override
    public void validateUser(final User user) {
        user.setAdminValidated(true);
        userRepository.save(user);
    }

    @Override
    public void infoNewExtUser(final User user) {
       final List<User> userList = getUsers();
        for (final User value : userList) {
            if (value.getRoles().contains("ROLE_ADMIN")) {
                smtpServerHelper.sendValidationInfo(value.getEmail(), user.getEmail());
                return;
                //optional, without return -> notify all admins.
            }
        }

    }

    @Override
    public void removeUser(final String username) {
        userRepository.deleteById(username);
    }

    @Override
    public User saveUser(final User user) {
        return userRepository.save(user);
    }
}

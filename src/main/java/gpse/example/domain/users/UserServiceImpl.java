package gpse.example.domain.users;

import gpse.example.util.SMTPServerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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

    private final UserRepository repo;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Lazy
    @Autowired
    public UserServiceImpl(final UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public User getUser(final String userID) {
        return repo.findById(userID)
            .orElseThrow(() -> new UsernameNotFoundException("User email: " + userID + " not found."));
    }

    @Override
    public List<User> getUserList() {
        final List<User> userList = new ArrayList<>();
        repo.findAll().forEach(userList::add);
        return userList;
    }

    @Override
    public void signUpUser(User user) {

        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPassword(user.getPassword());
        final User createdUser = repo.save(user);

        final ConfirmationToken token = new ConfirmationToken(user);

        confirmationTokenService.saveConfirmationToken(token);
    }

    @Override
    public void confirmUser(ConfirmationToken confirmationToken) {

        final User user = confirmationToken.getUser();

        user.setEnabled(true);

        repo.save(user);

        confirmationTokenService.deleteConfirmationToken(confirmationToken.getId());

    }

    public void sendConfirmationMail(User user, String token) {
        SMTPServerHelper.sendRegistrationEmail(user.getEmail(), user.getUsername(),
            "http://localhost:8080/sign-up/confirm?token="
            + token);
    }
}

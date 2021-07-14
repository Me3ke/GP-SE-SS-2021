package gpse.example.domain.users;

import gpse.example.domain.email.*;
import gpse.example.domain.exceptions.MessageGenerationException;
import gpse.example.domain.exceptions.TemplateNameNotFoundException;
import gpse.example.domain.security.SecurityConstants;
import gpse.example.web.tokens.ConfirmationToken;
import gpse.example.web.tokens.ConfirmationTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * the class that implements the UserService interface for communication with the database.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    /**
     * Standard ConfirmationTokenService.
     * autowired not commited not tested 18.05.21
     */
    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    private final UserRepository userRepository;
    private final SecuritySettingsRepository securitySettingsRepository;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Lazy
    @Autowired
    private SecurityConstants securityConstants;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository,
                           final SecuritySettingsRepository securitySettingsRepository) {
        this.userRepository = userRepository;
        this.securitySettingsRepository = securitySettingsRepository;
    }

    @Override
    public User getUser(final String username) {
        return userRepository.findById(username)
            .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " was not found."));
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(final String username) {
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

        securitySettingsRepository.save(user.getSecuritySettings());
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
        securitySettingsRepository.save(user.getSecuritySettings());
        return userRepository.save(user);
    }

    @Override
    public void signUpUser(final User user) throws MessageGenerationException, TemplateNameNotFoundException {

        final EmailManagement emailManagement = new EmailManagement();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        securitySettingsRepository.save(user.getSecuritySettings());
        final User createdUser = userRepository.save(user);
        final ConfirmationToken token = new ConfirmationToken(user);
        final ConfirmationToken savedToken = confirmationTokenService.saveConfirmationToken(token);
        emailManagement.sendConfirmationMail(createdUser, savedToken.getToken());
    }

    @Override
    public void confirmUser(final ConfirmationToken confirmationToken) {

        final User user = confirmationToken.getUser();

        user.setEnabled(true);
        securitySettingsRepository.save(user.getSecuritySettings());
        userRepository.save(user);
        confirmationTokenService.deleteConfirmationToken(confirmationToken.getId());
    }


    @Override
    public void validateUser(final User user) {
        user.setAccountNonLocked(true);

        securitySettingsRepository.save(user.getSecuritySettings());
        userRepository.save(user);
    }

    @Override
    public void removeUser(final String username) {
        final Optional<User> user = userRepository.findById(username);
        user.ifPresent(value -> securitySettingsRepository.delete(value.getSecuritySettings()));
        userRepository.deleteById(username);
    }

    @Override
    public User saveUser(final User user) {
        securitySettingsRepository.save(user.getSecuritySettings());
        return userRepository.save(user);
    }

    @Override
    public boolean checkIfAdmin(final String token) {
        final byte[] signingKey = securityConstants.getJwtSecret().getBytes();
        final Jws<Claims> parsedToken = Jwts.parserBuilder()
            .setSigningKey(signingKey).build()
            .parseClaimsJws(token.replace(securityConstants.getTokenPrefix(), "").strip());
        final User user = getUser(parsedToken.getBody().getSubject());
        return user.getRoles().contains(ROLE_ADMIN);
    }
}

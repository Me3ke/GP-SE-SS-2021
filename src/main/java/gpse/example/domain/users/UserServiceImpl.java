package gpse.example.domain.users;

import gpse.example.util.email.*;
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

    @Autowired
    private EmailTemplateService emailTemplateService;

    /**
     * the smtpServerHelper Service for sending emails.
     */
    @Lazy
    @Autowired
    private SMTPServerHelper smtpServerHelper;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository, SecuritySettingsRepository securitySettingsRepository) {
        this.userRepository = userRepository;
        this.securitySettingsRepository = securitySettingsRepository;
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

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        securitySettingsRepository.save(user.getSecuritySettings());
        final User createdUser = userRepository.save(user);
        final ConfirmationToken token = new ConfirmationToken(user);
        final ConfirmationToken savedToken = confirmationTokenService.saveConfirmationToken(token);
        sendConfirmationMail(createdUser, savedToken.getToken());
    }

    @Override
    public void confirmUser(final ConfirmationToken confirmationToken) {

        final User user = confirmationToken.getUser();

        user.setEnabled(true);
        securitySettingsRepository.save(user.getSecuritySettings());
        userRepository.save(user);
        confirmationTokenService.deleteConfirmationToken(confirmationToken.getId());
    }


    /**
     * Sending and configurate the confirmation template.
     * @param user user to register
     * @param token the confirmation token to verify email
     * @throws MessageGenerationException thrown if the email message could not be generated
     * @throws TemplateNameNotFoundException thrown if the email template dont exist.
     */
    public void sendConfirmationMail(final User user, final String token) throws MessageGenerationException,
            TemplateNameNotFoundException {

        EmailTemplate template = emailTemplateService.findSystemTemplateByName("ConfirmationTemplate");
        TemplateDataContainer container = new TemplateDataContainer();
        container.setFirstNameReciever(user.getFirstname());
        container.setLastNameReciever(user.getLastname());
        container.setLink("http://localhost:8080/de/register/confirm/" + token);
        smtpServerHelper.sendTemplatedEmail(user.getEmail(), template, container);

      /*  smtpServerHelper.sendRegistrationEmail(user,
            "http://localhost:8080/de/register/confirm/" + token);*/
    }

    @Override
    public void validateUser(final User user) {
        user.setAdminValidated(true);

        securitySettingsRepository.save(user.getSecuritySettings());
        userRepository.save(user);
    }

    @Override
    public void infoNewExtUser(final User user) throws MessageGenerationException, TemplateNameNotFoundException {
       final List<User> userList = getUsers();
        for (final User admin : userList) {
            if (admin.getRoles().contains("ROLE_ADMIN")) {
                EmailTemplate template = emailTemplateService.findSystemTemplateByName("AdminValidationTemplate");
                TemplateDataContainer container = new TemplateDataContainer();
                container.setFirstNameReciever(admin.getFirstname());
                container.setLastNameReciever(admin.getLastname());
                container.setFirstNameOwner(user.getFirstname());
                container.setLastNameOwner(user.getLastname());
                container.setRequestingEmail(user.getEmail());
                container.setLink("http://localhost:8080/de/admin/settings");
                smtpServerHelper.sendTemplatedEmail(admin.getEmail(), template, container);
                //smtpServerHelper.sendValidationInfo(value, user.getEmail());
                return;
                //optional, without return -> notify all admins.
            }
        }

    }

    @Override
    public void removeUser(final String username) {
        Optional<User> user = userRepository.findById(username);
        user.ifPresent(value -> securitySettingsRepository.delete(value.getSecuritySettings()));
        userRepository.deleteById(username);
    }

    @Override
    public User saveUser(final User user) {
        securitySettingsRepository.save(user.getSecuritySettings());
        return userRepository.save(user);
    }
}

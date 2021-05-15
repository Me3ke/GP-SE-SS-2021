package gpse.example.domain.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * the class that implements the UserService interface for communication with the database.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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
        System.out.println("The right one is used");
        for (User user : this.getUsers()) {
            System.out.println("User in Datenbank:" + user.getUsername());
        }
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
}

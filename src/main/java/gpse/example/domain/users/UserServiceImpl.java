package gpse.example.domain.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * the class that implements the UserService interface for communication with the database.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

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
}

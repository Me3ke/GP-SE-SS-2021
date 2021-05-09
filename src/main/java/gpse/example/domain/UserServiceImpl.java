package gpse.example.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<User> getUserList() {
        final List<User> userList = new ArrayList<>();
        repo.findAll().forEach(userList::add);
        return userList;
    }

    @Override
    public Optional<User> getUser(String email) {
        return repo.findById(email);
    }
}

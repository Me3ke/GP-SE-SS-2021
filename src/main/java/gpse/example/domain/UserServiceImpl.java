package gpse.example.domain;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * the class that implements the UserService interface for communication with the database.
 */
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
}

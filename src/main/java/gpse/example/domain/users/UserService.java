package gpse.example.domain.users;

import java.util.List;

/**
 * the interface for UserServices.
 */
public interface UserService {
    User getUser(final String userID);
    List<User> getUserList();
}

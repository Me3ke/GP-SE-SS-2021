package gpse.example.web.users;

import gpse.example.domain.users.User;

import java.util.ArrayList;
import java.util.List;

/**
 * the response object for the admin-request for all users.
 */
public class UserManagementResponseList {

    private final  List<UserManagementResponse> allUsers;

    /**
     * the standard constructor.
     * @param users the list of all users that are registered in the moment of initializing the object.
     */
    public UserManagementResponseList(final List<User> users) {
        this.allUsers = new ArrayList<>();
        for (final User user: users) {
            this.allUsers.add(new UserManagementResponse(user));
        }
    }

    public List<UserManagementResponse> getAllUsers() {
        return allUsers;
    }
}

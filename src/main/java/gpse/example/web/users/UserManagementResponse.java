package gpse.example.web.users;

import gpse.example.domain.users.User;

/**
 * the class that represents a single user for userManagement.
 */
public class UserManagementResponse extends UserResponseObject {

    private boolean adminValidated;
    private boolean emailConfirmed;

    /**
     * the standard constructor.
     *
     * @param user the relating userObject.
     */
    public UserManagementResponse(final User user) {
        super(user);
        this.adminValidated = user.isAccountNonLocked();
        this.emailConfirmed = user.isEnabled();
    }

    public boolean isAdminValidated() {
        return adminValidated;
    }

    public void setAdminValidated(boolean adminValidated) {
        this.adminValidated = adminValidated;
    }

    public boolean isEmailConfirmed() {
        return emailConfirmed;
    }

    public void setEmailConfirmed(boolean emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }
}

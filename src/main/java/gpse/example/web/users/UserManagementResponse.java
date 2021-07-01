package gpse.example.web.users;

import gpse.example.domain.users.User;

import java.util.List;

/**
 * the class that represents a single user for userManagement.
 */
public class UserManagementResponse extends UserResponseObject {

    private List<String> roles;
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
        this.roles = user.getRoles();
    }

    public boolean isAdminValidated() {
        return adminValidated;
    }

    public void setAdminValidated(final boolean adminValidated) {
        this.adminValidated = adminValidated;
    }

    public boolean isEmailConfirmed() {
        return emailConfirmed;
    }

    public void setEmailConfirmed(final boolean emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(final List<String> roles) {
        this.roles = roles;
    }
}

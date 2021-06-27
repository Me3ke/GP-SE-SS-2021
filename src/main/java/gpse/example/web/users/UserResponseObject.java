package gpse.example.web.users;

import gpse.example.domain.users.User;

/**
 * The Object used to send the most important data regarding a User to the frontend.
 */
public class UserResponseObject {

    private final String email;
    private final String firstname;
    private final String lastname;
    private final boolean enabled;
    private final boolean adminValidated;
    private final boolean firstLogin;
    private final String publicKey;

    /**
     * The standard constructor for userResponseObjects.
     * @param user the user the object should be based on.
     */
    public UserResponseObject(final User user) {
        this.email = user.getEmail();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.enabled = user.isEnabled();
        this.adminValidated = user.isAccountNonLocked();
        this.firstLogin = user.isFirstLogin();
        this.publicKey = user.getPublicKey();
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isAdminValidated() {
        return adminValidated;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public String getPublicKey() {
        return publicKey;
    }
}

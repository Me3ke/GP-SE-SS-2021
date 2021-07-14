package gpse.example.domain.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gpse.example.domain.addressbook.AddressBook;
import gpse.example.domain.addressbook.Entry;
import gpse.example.domain.envelopes.Envelope;
import gpse.example.domain.email.EmailTemplate;
import gpse.example.web.messages.MessageSettingsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * The model for a user, responsible for initializing new Users with all the information given by them.
 *
 * @author Tobias Kröcker & Hannah Schweizer
 * @since 14.04.2021
 */

@Entity
public class User implements UserDetails {

    private static final long serialVersionUID = -8161342821150699353L;

    @Id
    @Column
    private String username;

    @Column
    private String email;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column
    private boolean seenByAdmin;

    @Column
    // false: user has not had a first login yet; true: user has had a first login
    private boolean firstLogin;

    @ElementCollection
    @Column(columnDefinition = "LONGTEXT")
    @JsonIgnore
    private List<String> archivedPublicKeys = new ArrayList<>();

    @Lob
    private String publicKey;

    @Column
    @JsonIgnore
    private String password;

    @OneToOne(
        orphanRemoval = true,
        cascade = CascadeType.ALL
    )
    private PersonalData personalData;

    @OneToOne(
        orphanRemoval = true,
        cascade = CascadeType.ALL
    )
    private SecuritySettings securitySettings;

    @Column
    private boolean enabled;

    @Column
    private boolean accountNonLocked;

    @Lob
    private byte[] imageSignature;

    @Column
    private String imageSignatureType;

    @OneToMany(
        orphanRemoval = true,
        cascade = CascadeType.ALL
    )
    private List<EmailTemplate> emailTemplates;

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    @OneToOne(
        orphanRemoval = true,
        cascade = CascadeType.ALL
    )
    private MessageSettingsContainer messageSettings;

    @OneToOne(
        orphanRemoval = true,
        cascade = CascadeType.ALL
    )
    private AddressBook addressBook = new AddressBook();

    protected User() {

    }

    /**
     * The constructor for a user.
     *
     * @param username  the username equal the email
     * @param firstname the firstname of the user.
     * @param lastname  the lastname of the user.
     * @param password  the password that is used for actions that need security.
     */
    public User(final String username, final String firstname, final String lastname, final String password) {
        this.email = username;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.enabled = false;
        this.accountNonLocked = false;
        this.firstLogin = false;
        this.securitySettings = new SecuritySettings();
        this.imageSignature = new byte[0];
        this.imageSignatureType = "";
        this.emailTemplates = new ArrayList<>();
        this.messageSettings = new MessageSettingsContainer();
        this.messageSettings.setToDo(true);
        this.messageSettings.setProgress(true);
        this.messageSettings.setNewVersion(true);
        this.messageSettings.setSign(true);
        this.messageSettings.setRead(true);
        this.addressBook.addEntry(new Entry(this));
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<String> getArchivedPublicKeys() {
        return archivedPublicKeys;
    }

    public void setArchivedPublicKeys(final List<String> archivedPublicKeys) {
        this.archivedPublicKeys = archivedPublicKeys;
    }

    public void setPersonalData(final PersonalData personalData) {
        this.personalData = personalData;
    }

    /**
     * This method adds Role to an user.
     *
     * @param role e.g. "ROLE_USER" or "ROLE_ADMIN"
     */
    public void addRole(final String role) {
        if (roles == null) {
            this.roles = new ArrayList<>();
        }
        this.roles.add(role);
    }

    /**
     * the method to create a new envelope with the user object, that calls this as the owner.
     *
     * @param name the name of the envelope
     * @return the new envelope.
     */
    public Envelope createNewEnvelope(final String name) {
        return new Envelope(name, this);
    }

    /**
     * This Mehtod allows access to the users ROLES, whom'st define
     * the users authority.
     *
     * @return Collection of objects extending GrantedAuthority containing the users authority roles.
     */
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(roles.toArray(new String[0]));
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return enabled;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(final String publicKey) {
        this.publicKey = publicKey;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    public boolean isSeenByAdmin() {
        return seenByAdmin;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setAccountNonLocked(final boolean adminValidated) {
        this.accountNonLocked = adminValidated;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(final List<String> roles) {
        this.roles = roles;
    }

    public SecuritySettings getSecuritySettings() {
        return securitySettings;
    }

    public void setSecuritySettings(final SecuritySettings securitySettings) {
        this.securitySettings = securitySettings;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(final boolean firstLogin) {
        this.firstLogin = firstLogin;
    }


    public List<EmailTemplate> getEmailTemplates() {
        return emailTemplates;
    }

    public void setEmailTemplates(final List<EmailTemplate> emailTemplates) {
        this.emailTemplates = emailTemplates;
    }

    public void addEmailTemplate(final EmailTemplate emailTemplate) {
        this.emailTemplates.add(emailTemplate);
    }

    public MessageSettingsContainer getMessageSettings() {
        return messageSettings;
    }

    public void setMessageSettings(final MessageSettingsContainer messageSettings) {
        this.messageSettings = messageSettings;
    }

    public byte[] getImageSignature() {
        return Arrays.copyOf(
            imageSignature, imageSignature.length);
    }

    public void setImageSignature(final byte[] imageSignature) {
        this.imageSignature = imageSignature.clone();
    }

    public String getImageSignatureType() {
        return imageSignatureType;
    }

    public void setImageSignatureType(final String imageSignatureType) {
        this.imageSignatureType = imageSignatureType;
    }

    public void setToSeenByAdmin() {
        this.seenByAdmin = true;
    }

    public AddressBook getAddressBook() {
        return addressBook;
    }

    public void setAddressBook(final AddressBook addressBook) {
        this.addressBook = addressBook;
    }
}

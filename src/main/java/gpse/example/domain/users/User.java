package gpse.example.domain.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gpse.example.domain.envelopes.Envelope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
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

    //@OneToOne
    //private Keys activePair;

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

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

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
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /*
     * The Method to add a new keyPair to the list of existing ones.
     *
     * param pathToPrivate a filePath referring to the file of the private key that relates to the public key that
     *                      should be stored
     * param publicKey     the public key that should be stored

    public void addKeyPair(final String pathToPrivate, final PublicKey publicKey) {
        if (publicKey.getAlgorithm().equals("RSA")) {
            keys.add(new Keys(publicKey, pathToPrivate));
            changeActiveKeyPair(keys.size() - 1);
        }
    } */

    /*
     * the Method used to change the active key-pair to an existing one.
     *
     * param index the id of the new active key-pair

    public void changeActiveKeyPair(final int index) {
        //avoid outOfBounds exceptions
        if (index < keys.size()) {
            activePair = keys.get(index);
        }
    }*/

    public List<String> getArchivedPublicKeys() {
        return archivedPublicKeys;
    }

    public void setArchivedPublicKeys(final List<String> archivedPublicKeys) {
        this.archivedPublicKeys = archivedPublicKeys;
    }

    /**
     * the Method used to fill in information that is not necessarily needed.
     *
     * @param street      the street the user lives in.
     * @param houseNumber the house number of the user.
     * @param postCode    the postcode for the hometown of the user
     * @param homeTown    the hometown of the user
     * @param country     the country the user lives in
     * @param birthday    the birthday of the user
     * @param phoneNumber the phoneNumber of the user
     */
    public void setPersonalData(final String street, final int houseNumber, final int postCode,
                                final String homeTown, final String country, final LocalDate birthday,
                                final String phoneNumber) {
        this.personalData = new PersonalData(street, houseNumber, postCode, homeTown,
            country, birthday, phoneNumber);
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

    /*
     * the method used to generate an advanced signature, using the active private key.
     *
     * @return the signature represented by a byte list
     */
    //no private Key in backend -> delete
    /*
    public byte[] advancedSign(final String hash) {
        byte[] signature = null;
        try {
            final Signature sign = Signature.getInstance(SIGNING_ALGORITHM);
            sign.initSign(activePrivate);
            sign.update(hash.getBytes());
            signature = sign.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException exception) {
            System.out.println(exception.getMessage());
        }
        return signature;
    }

     */


    //TODO
    // Methods that are required for using the interface

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
}

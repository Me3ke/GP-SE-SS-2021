package gpse.example.domain;

import java.security.*;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * The model for a user, responsible for initializing new Users with all the information given by them.
 *
 * @author Tobias Kröcker & Hannah Schweizer
 * @since 14.04.2021
 */
public class User implements UserDetails {

    private static final long serialVersionUID = -8161342821150699353L;
    private static final int KEY_SIZE = 2048;
    private static final String SIGNING_ALGORITHM = "SHA256withRSA";
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    private String street;
    private int houseNumber;
    private int postCode;
    private int phoneNumber;
    private String homeTown;
    private String country;
    private LocalDate birthday;
    private KeyPair activeKeyPair;
    private PrivateKey activePrivate;
    private List<KeyPair> keyPairs = new ArrayList<>();

    //Todo: Maybe add a role interface for more specific elements in the list.
    private List<String> roles;

    public User() {

    }

    /**
     * The constructor for a user.
     *
     * @param email     for communication. Is also used as an ID.
     * @param firstname the firstname of the user.
     * @param lastname  the lastname of the user.
     * @param password  the password that is used for actions that need security.
     */
    public User(final String email, final String firstname, final String lastname, final String password) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.roles = new ArrayList<>();
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
    public void addVoluntaryInformation(final String street, final int houseNumber, final int postCode,
                                        final String homeTown, final String country, final LocalDate birthday,
                                        final int phoneNumber) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.postCode = postCode;
        this.homeTown = homeTown;
        this.country = country;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
    }

    /**
     * The method used to give a user another role.
     *
     * @param newRole the role that needs to be added
     */
    public void addRole(final String newRole) {
        if (!this.roles.contains(newRole)) {
            this.roles.add(newRole);
        }
    }

    public void newKeypair() {
        try {
            final KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(KEY_SIZE);
            if (activeKeyPair == null) {
                activeKeyPair = generator.generateKeyPair();
                keyPairs.add(activeKeyPair);
                activePrivate = activeKeyPair.getPrivate();
            } else {
                keyPairs.add(generator.generateKeyPair());
                changeActiveKeyPair(keyPairs.size() - 1);
            }
        } catch (NoSuchAlgorithmException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void changeActiveKeyPair(final int index) {
        //avoid outOfBounds exceptions
        if (index < keyPairs.size()) {
            activeKeyPair = keyPairs.get(index);
            activePrivate = activeKeyPair.getPrivate();
        }
    }

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

    /**
     * The method used to take a role from a user.
     *
     * @param delRole the role that should be taken from the user
     */
    public void deleteRole(final String delRole) {
        if (this.roles.contains(delRole)) {
            this.roles.remove(delRole);
        }
    }

    // Methods that are required for using the interface
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(roles.toArray(new String[0]));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // getter and setter for all the variables that describe the user

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(final String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(final int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(final int postCode) {
        this.postCode = postCode;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(final String homeTown) {
        this.homeTown = homeTown;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(final LocalDate birthday) {
        this.birthday = birthday;
    }

    public List<String> getRoles() {
        return roles;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PublicKey[] getAllPublicKeys() {
        PublicKey[] keys = new PublicKey[keyPairs.size()];
        for (int i = 0; i < keys.length; i++) {
            keys[i] = keyPairs.get(i).getPublic();
        }
        return keys;
    }
}

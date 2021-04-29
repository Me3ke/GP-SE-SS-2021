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
    private PersonalData personalData;
    private String email;
    private String firstname;
    private String lastname;
    private List<KeyPair> keys = new ArrayList<>();
    private String password;
    private KeyPair activePair;
    private boolean admin;

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
                                        final int phoneNumber) {
        final PersonalData personalData = new PersonalData (street, houseNumber, postCode, homeTown,
                                                        country, birthday, phoneNumber);
    }

    /**
     * the Method used to generate a new key-pair, and to change the active keypair and also the active private key to
     * the new ones.
     */
    //TODO
    private void addKeyPair(String pathToPrivate, PublicKey publicKey) {
        /*
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

         */
    }

    /**
     * the Method used to change the active key-pair to an existing one.
     * @param index the id of the new active key-pair
     */
    //TODO
    public void changeActiveKeyPair(final int index) {
        //avoid outOfBounds exceptions
        /*
        if (index < keyPairs.size()) {
            activeKeyPair = keyPairs.get(index);
            activePrivate = activeKeyPair.getPrivate();
        }
         */
    }

    /**
     * the method used to generate an advanced signature, using the active private key.
     * @param hash the id of the document that needs a signature
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
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /*
        return AuthorityUtils.createAuthorityList(roles.toArray(new String[0]));

         */
        return null;
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

}

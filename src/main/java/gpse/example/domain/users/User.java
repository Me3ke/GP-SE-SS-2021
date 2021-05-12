package gpse.example.domain.users;

import java.security.*;
import java.time.LocalDate;
import java.util.ArrayList;

import gpse.example.domain.envelopes.Envelope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
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

    @Column
    private boolean enabled;

    @OneToOne
    private PersonalData personalData;

    @Id
    @Column
    private String email;

    @Column
    private String firstname;

    @Column
    private String lastname;

    //@OneToMany
    //private List<Keys> keys = new ArrayList<>();

    @Column
    private PublicKey publicKey;

    @Column
    private String password;

    //@OneToOne
    //private Keys activePair;

    @Column
    private boolean admin;

    @OneToMany
    private List<Envelope> myEnvelopes = new ArrayList<>();

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
        this.personalData = new PersonalData(street, houseNumber, postCode, homeTown,
            country, birthday, phoneNumber);
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

    /**
     * the method to create a new envelope with the user object, that calls this as the owner.
     * @param name the name of the envelope
     * @return the new envelope.
     */
    public Envelope createNewEnvelope(final String name) {
        final Envelope envelope = new Envelope(name, this);
        myEnvelopes.add(envelope);
        return envelope;
    }

    /**
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


    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalData personalData) {
        this.personalData = personalData;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /*
    public List<Keys> getKeys() {
        return keys;
    }

     */
}

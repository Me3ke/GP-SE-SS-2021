package gpse.example.domain.adressbook;

import javax.persistence.*;

/**
 * A class for an entry in the AddressBook.
 */
@Entity
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private String email;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column
    private boolean favorite;

    @Column
    private String note;

    protected Entry() {

    }

    /**
     * The default constructor for a entry in the address book.
     * @param email the email of the contact.
     * @param firstname the firstname of the contact.
     * @param lastname the lastname of the contact.
     * @param favorite a mark if the contact is the users favorite.
     * @param note a note to the contact from the user.
     */
    public Entry(final String email, final String firstname, final String lastname,
                 final boolean favorite, final String note) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.favorite = favorite;
        this.note = note;
    }

    public long getId() {
        return id;
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

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(final boolean favorite) {
        this.favorite = favorite;
    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }
}

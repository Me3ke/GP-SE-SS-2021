package gpse.example.domain.addressbook;

import gpse.example.domain.users.User;
import gpse.example.web.addressbook.EntryObject;

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

    public Entry() {

    }

    /**
     * The default constructor for a entry in the address book.
     *
     * @param entry the data of the new contact.
     */
    public Entry(final EntryObject entry) {
        this.email = entry.getEmail();
        this.firstname = entry.getFirstname();
        this.lastname = entry.getLastname();
        this.favorite = entry.isFavorite();
        this.note = entry.getNote();
    }

    /**
     * A constructor for an Entry from a user object.
     *
     * @param user the user which will be the contact.
     */
    public Entry(final User user) {
        this.email = user.getEmail();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.favorite = false;
    }

    public void setId(final long id) {
        this.id = id;
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

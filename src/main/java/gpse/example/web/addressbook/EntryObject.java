package gpse.example.web.addressbook;

import gpse.example.domain.addressbook.Entry;

/**
 * A class for request and responses regarding the entries.
 */
public class EntryObject {

    private long id;
    private String email;
    private String firstname;
    private String lastname;
    private boolean favorite;
    private String note;

    /**
     * Default constructor for an entry object from an entry.
     * @param entry the entry.
     */
    public EntryObject(final Entry entry) {
        this.id = entry.getId();
        this.email = entry.getEmail();
        this.firstname = entry.getFirstname();
        this.lastname = entry.getLastname();
        this.favorite = entry.isFavorite();
        this.note = entry.getNote();
    }

    public EntryObject() {

    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
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

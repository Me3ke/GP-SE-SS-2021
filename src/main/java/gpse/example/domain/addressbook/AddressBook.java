package gpse.example.domain.addressbook;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for an address book.
 */
@Entity
public class AddressBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @OneToMany(
        orphanRemoval = true,
        cascade = CascadeType.ALL)
    private List<Entry> entries;

    @Column
    private boolean addAllAutomatically;

    @Column
    private boolean addDomainAutomatically;

    /**
     * Default constructor for an address book.
     * Initially all contacts with the trustedDomain are saved into the address book.
     */
    public AddressBook() {
        entries = new ArrayList<>();
        addAllAutomatically = true;
        addDomainAutomatically = true;
    }

    /**
     * The addEntry method checks if the address book contains the email of the new entry.
     * If this is not the case the new entry is added into the new address book.
     * @param newEntry the new entry.
     * @return the entry.
     */
    public Entry addEntry(final Entry newEntry) {
        if (this.entries.stream().noneMatch(entry -> entry.getEmail().equals(newEntry.getEmail()))) {
            this.entries.add(newEntry);
        }
        return newEntry;
    }

    public Entry removeEntry(final Entry entry) {
        this.entries.remove(entry);
        return entry;
    }

    public long getId() {
        return id;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(final List<Entry> entries) {
        this.entries = entries;
    }

    public boolean isAddAllAutomatically() {
        return addAllAutomatically;
    }

    public void setAddAllAutomatically(final boolean addAllAutomatically) {
        this.addAllAutomatically = addAllAutomatically;
    }

    public boolean isAddDomainAutomatically() {
        return addDomainAutomatically;
    }

    public void setAddDomainAutomatically(final boolean addDomainAutomatically) {
        this.addDomainAutomatically = addDomainAutomatically;
    }
}

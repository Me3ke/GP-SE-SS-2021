package gpse.example.domain.adressbook;

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

    public AddressBook() {
        entries = new ArrayList<>();
        //TODO initialValues
        addAllAutomatically = true;
        addDomainAutomatically = true;
    }

    public Entry addEntry(final Entry entry) {
        this.entries.add(entry);
        return entry;
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

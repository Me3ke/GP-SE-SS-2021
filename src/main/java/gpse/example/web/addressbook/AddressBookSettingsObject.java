package gpse.example.web.addressbook;

/**
 * A class for request and responses regarding the address book settings.
 */
public class AddressBookSettingsObject {

    private boolean addAllAutomatically;
    private boolean addDomainAutomatically;

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

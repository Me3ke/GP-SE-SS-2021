package gpse.example.web.addressbook;

import gpse.example.domain.addressbook.AddressBook;
import gpse.example.domain.addressbook.Entry;
import gpse.example.domain.users.User;
import gpse.example.domain.users.UserService;
import gpse.example.web.JSONResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The AddressBookController class handles the request from the frontend and
 * conducts the corresponding backend actions.
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class AddressBookController {

    private static final int STATUS_CODE_OK = 200;
    private static final int STATUS_ENTRY_NOT_FOUND = 620;
    private final UserService userService;

    @Autowired
    public AddressBookController(final UserService service) {
        this.userService = service;
    }

    /**
     * The getAddressBook method is used for a get request to get address book entries.
     * @param userID the user doing the request.
     * @return a list of entry objects which represent the entries in the address book.
     */
    @GetMapping("user/{userID}/directory")
    public List<EntryObject> getAddressBook(@PathVariable final String userID) {
        final AddressBook addressBook = userService.getUser(userID).getAddressBook();
        final List<EntryObject> response = new ArrayList<>();
        for (final Entry entry: addressBook.getEntries()) {
            response.add(new EntryObject(entry));
        }
        return response;
    }

    /**
     * The addEntry method is used for a put request to create a new entry.
     * @param userID the user doing the request.
     * @param newEntry an entry object representing a new entry.
     * @return a response object to display a status and a message.
     */
    @PutMapping("user/{userID}/directory/newEntry")
    public JSONResponseObject addEntry(@PathVariable final String userID, @RequestBody final EntryObject newEntry) {
        final JSONResponseObject response = new JSONResponseObject();
        final User currentUser = userService.getUser(userID);
        final AddressBook addressBook = currentUser.getAddressBook();
        if (newEntry.getFirstname() == null) {
            try {
                final User user = userService.getUser(newEntry.getEmail());
                newEntry.setFirstname(user.getFirstname());
            } catch (UsernameNotFoundException e) {
                newEntry.setFirstname(null);
            }
        }
        if (newEntry.getLastname() == null) {
            try {
                final User user = userService.getUser(newEntry.getEmail());
                newEntry.setLastname(user.getLastname());
            } catch (UsernameNotFoundException e) {
                newEntry.setLastname(null);
            }
        }
        addressBook.addEntry(new Entry(newEntry));
        userService.saveUser(currentUser);
        response.setStatus(STATUS_CODE_OK);
        response.setMessage("Creating new entry successful");
        return response;
    }

    /**
     * The getSettings method is used for a get request to get the address book settings.
     * @param userID the user doing the request.
     * @return an object representing the settings.
     */
    @GetMapping("user/{userID}/directory/directorySettings")
    public AddressBookSettingsObject getSettings(@PathVariable final String userID) {
        final AddressBookSettingsObject response = new AddressBookSettingsObject();
        final AddressBook addressBook = userService.getUser(userID).getAddressBook();
        response.setAddAllAutomatically(addressBook.isAddAllAutomatically());
        response.setAddDomainAutomatically(addressBook.isAddDomainAutomatically());
        return response;
    }

    /**
     * The updateSettings method is used for a post request to change address book settings.
     * @param userID the user doing the request.
     * @param newSettings an object containing the new settings for the address book.
     * @return a response object to display a status and a message.
     */
    @PostMapping("user/{userID}/directory/changeSettings")
    public JSONResponseObject updateSettings(@PathVariable final String userID,
                                             @RequestBody final AddressBookSettingsObject newSettings) {
        final JSONResponseObject response = new JSONResponseObject();
        final User currentUser = userService.getUser(userID);
        final AddressBook addressBook = currentUser.getAddressBook();
        addressBook.setAddAllAutomatically(newSettings.isAddAllAutomatically());
        addressBook.setAddDomainAutomatically(newSettings.isAddDomainAutomatically());
        userService.saveUser(currentUser);
        response.setStatus(STATUS_CODE_OK);
        response.setMessage("Updating Settings successful");
        return response;
    }

    /**
     * The changeEntry method is used for a post request to change an entry.
     * @param userID the user doing the request.
     * @param entryID the id of the entry to be changed.
     * @param newEntry the new entry data.
     * @return a response object to display a status and a message.
     */
    @PostMapping("user/{userID}/directory/{entryID:\\d+}")
    public JSONResponseObject changeEntry(@PathVariable final String userID,
                                          @PathVariable final long entryID, @RequestBody final EntryObject newEntry) {
        final JSONResponseObject response = new JSONResponseObject();
        final User currentUser = userService.getUser(userID);
        final AddressBook addressBook = currentUser.getAddressBook();
        for (final Entry entry : addressBook.getEntries()) {
            if (entry.getId() == entryID) {
                entry.setEmail(newEntry.getEmail());
                entry.setFirstname(newEntry.getFirstname());
                entry.setLastname(newEntry.getLastname());
                entry.setFavorite(newEntry.isFavorite());
                entry.setNote(newEntry.getNote());
                userService.saveUser(currentUser);
                response.setMessage("changing entry successful");
                response.setStatus(STATUS_CODE_OK);
                return response;
            }
        }
        response.setStatus(STATUS_ENTRY_NOT_FOUND);
        response.setMessage("The given entryID could not be assigned to an existing entry");
        return response;
    }

    /**
     * The deleteEntry method is used for a delete request to delete an entry.
     * @param userID the user doing the request.
     * @param entryID the id of the entry to be deleted.
     * @return a response object to display a status and a message.
     */
    @DeleteMapping("user/{userID}/directory/{entryID:\\d+}")
    public JSONResponseObject deleteEntry(@PathVariable final String userID, @PathVariable final long entryID) {
        final JSONResponseObject response = new JSONResponseObject();
        final User currentUser = userService.getUser(userID);
        final AddressBook addressBook = currentUser.getAddressBook();
        Entry entryToDelete = null;
        for (final Entry entry : addressBook.getEntries()) {
            if (entry.getId() == entryID) {
                entryToDelete = entry;
            }
        }
        if (entryToDelete == null) {
            response.setMessage("entry could not be found");
            response.setStatus(STATUS_ENTRY_NOT_FOUND);
        } else {
            addressBook.removeEntry(entryToDelete);
            userService.saveUser(currentUser);
            response.setStatus(STATUS_CODE_OK);
            response.setMessage("deleting entry successful");
        }
        return response;
    }

}

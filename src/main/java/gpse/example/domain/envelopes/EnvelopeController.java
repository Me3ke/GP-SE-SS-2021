package gpse.example.domain.envelopes;

import gpse.example.domain.documents.DocumentPutRequest;
import gpse.example.domain.exceptions.CreatingFileException;
import gpse.example.domain.exceptions.DocumentNotFoundException;
import gpse.example.domain.users.User;
import gpse.example.domain.users.UserServiceImpl;
import gpse.example.domain.exceptions.UploadFileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The envelopeController class handles the request from the frontend and
 * conducts the corresponding backend actions.
 */
@RestController
@CrossOrigin("http://localhost:8088")
public class EnvelopeController {

    private EnvelopeServiceImpl envelopeService;
    private UserServiceImpl userService;

    @Autowired
    public EnvelopeController(final EnvelopeServiceImpl envelopeService, final UserServiceImpl userService) {
        this.envelopeService = envelopeService;
        this.userService = userService;
    }

    /**
     * The createEnvelope method does a post request to create a new envelope.
     *
     * @param ownerID the email adress of the creator.
     * @param name    the name of the new envelope.
     * @return the new envelope.
     * @throws UploadFileException if the envelope could not be uploaded.
     */
    @PostMapping("api.elsa.de/user/{userID:\\d+}/envelopes/envelopes")
    public Envelope createEnvelope(final @PathVariable("userID") String ownerID,
                                   final @RequestParam("name") String name) throws UploadFileException {
        try {
            final User owner = userService.getUser(ownerID);
            final Envelope envelope = envelopeService.addEnvelope(name, owner);
            return envelope;
        } catch (IOException | UsernameNotFoundException e) {
            throw new UploadFileException(e);
        }
    }

    //TODO User has permission to add Document into envelope?
    /**
     * The fillEnvelope method does a put request to add a Document to an existing envelope.
     *
     * @param envelopeID         the ID of the envelope.
     * @param ownerID            the email of the document creator
     * @param documentPutRequest the command object keeping the information for a document to be created
     * @return the envelope in which the document was added to.
     * @throws UploadFileException if the document could not be uploaded.
     */
    @PutMapping("api.elsa.de/user/{userID:\\d+}/envelopes/{envelopeID:\\d+}")
    public Envelope fillEnvelope(final @PathVariable("envelopeID") long envelopeID,
                                 final @PathVariable("userID") String ownerID,
                                 final @RequestBody DocumentPutRequest documentPutRequest) throws UploadFileException {
        try {
            userService.getUser(ownerID);
            final List<User> signatories = new ArrayList<>();
            final List<String> signatoriesID = documentPutRequest.getSignatoriesID();
            final List<User> readers = new ArrayList<>();
            final List<String> readersID = documentPutRequest.getReadersID();
            for (final String currentID : signatoriesID) {
                signatories.add(userService.getUser(currentID));
            }
            for (final String currentID : readersID) {
                readers.add(userService.getUser(currentID));
            }
            return envelopeService.updateEnvelope(envelopeID, documentPutRequest, ownerID, signatories, readers);
        } catch (CreatingFileException | DocumentNotFoundException | IOException | UsernameNotFoundException e) {
            throw new UploadFileException(e);
        }
    }

    //TODO pagelimit etc?
    //TODO threads for changing state?
    /**
     * The getAllEnvelopes methods gets all envelopes from the database and filters
     * them using the filter method.
     * @param userID the id of the user doing the request.
     * @param request the Request object which keeps the filter data.
     * @return the filtered envelope list.
     */
    @GetMapping("api.elsa.de/user/{userID:\\d+}/envelopes")
    public List<Envelope> getAllEnvelopes(final @PathVariable String userID,
                                          final @RequestBody EnvelopeGetRequest request) {
        userService.getUser(userID);
        final List<Envelope> envelopeList = envelopeService.getEnvelopes();
        return filter(request, envelopeList);
    }

    /**
     * The filter method filters an envelope list on multiple criteria.
     * @param request the Request object which keeps the filter data.
     * @param envelopeList the list containing all envelopes in the database.
     * @return the filtered envelope list.
     */
    private List<Envelope> filter(final EnvelopeGetRequest request, final List<Envelope> envelopeList) {
        return envelopeList;
    }
}



package gpse.example.domain.envelopes;

import gpse.example.domain.documents.Document;
import gpse.example.domain.documents.DocumentCreator;
import gpse.example.domain.documents.UploadDocumentPutRequest;
import gpse.example.domain.exceptions.*;
import gpse.example.domain.users.User;
import gpse.example.domain.users.UserServiceImpl;
import gpse.example.web.JSONResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The envelopeController class handles the request from the frontend and
 * conducts the corresponding backend actions.
 */
@RestController
@CrossOrigin("http://localhost:8088")
@RequestMapping("/api")
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
    @PostMapping("/user/{*userID}/envelopes/envelopes")
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

    /**
     * The fillEnvelope method does a put request to add a Document to an existing envelope.
     *
     * @param envelopeID         the ID of the envelope.
     * @param ownerID            the email of the document creator
     * @param uploadDocumentPutRequest the command object keeping the information for a document to be created
     * @return the envelope in which the document was added to.
     * @throws UploadFileException if the document could not be uploaded.
     */
    @PutMapping("/user/{userID}/envelopes/{envelopeID:\\d+}")
    public JSONResponseObject fillEnvelope(final @PathVariable("envelopeID") long envelopeID,
                                           final @PathVariable("userID") String ownerID,
                                           final @RequestBody UploadDocumentPutRequest uploadDocumentPutRequest)
        throws UploadFileException {
        /*
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
         */
        JSONResponseObject response = new JSONResponseObject();
        response.setStatus(200);
        response.setMessage("Success");
        return response;
    }
    /**
     * The getEnvelope method returns one particular envelope specified by id and may download the file.
     *
     * @param envelopeID the id of the envelope to be downloaded.
     * @param userID     the id of the user.
     * @param download   a boolean deciding if the file should be downloaded
     * @return the response object
     * @throws DocumentNotFoundException if the envelope was not found.
     * @throws DownloadFileException     if the download goes wrong.
     */
    @GetMapping("api.elsa.de/user/{*userID}/envelopes/{envelopeID:\\d+}")
    public EnvelopeGetResponse getEnvelope(final @PathVariable("envelopeID") long envelopeID,
                                           final @PathVariable("userID") String userID,
                                           final @RequestParam("download") boolean download)
        throws DocumentNotFoundException, DownloadFileException {
        try {
            final Envelope envelope = envelopeService.getEnvelope(envelopeID);
            userService.getUser(userID);
            final User owner = userService.getUser(envelope.getOwnerID());
            if (download) {
                final DocumentCreator documentCreator = new DocumentCreator();
                documentCreator.downloadEnvelope(envelope);
            }
            return new EnvelopeGetResponse(envelope, owner);
        } catch (CreatingFileException | IOException | UsernameNotFoundException e) {
            throw new DownloadFileException(e);
        }
    }

    /**
     * The getAllEnvelopes methods gets all envelopes from the database and filters
     * them using the filter method.
     *
     * @param userID  the id of the user doing the request.
     * @param request the Request object which keeps the filter data.
     * @return the filtered envelope list.
     */
    //TODO Change to returning only envelopes that relate to the current User
    @GetMapping("api.elsa.de/user/{*userID}/envelopes")
    public List<EnvelopeGetResponse> getAllEnvelopes(final @PathVariable String userID,
                                                     final @RequestBody EnvelopeGetRequest request) {
        userService.getUser(userID);
        List<Envelope> envelopeList = envelopeService.getEnvelopes();
        envelopeList = filter(request, envelopeList);
        final List<EnvelopeGetResponse> envelopeGetResponseList = new ArrayList<>();
        for (final Envelope envelope : envelopeList) {
            final User owner = userService.getUser(envelope.getOwnerID());
            envelopeGetResponseList.add(new EnvelopeGetResponse(envelope, owner));
        }
        return envelopeGetResponseList;

    }

    /**
     * The filter method filters an envelope list on multiple criteria.
     *
     * @param request      the Request object which keeps the filter data.
     * @param envelopeList the list containing all envelopes in the database.
     * @return the filtered envelope list.
     */
    public List<Envelope> filter(final EnvelopeGetRequest request, final List<Envelope> envelopeList) {
        final List<Envelope> resultList = new ArrayList<>();
        final List<Envelope> filteredEnvelopeList = envelopeList
            .stream()
            .filter(envelope -> envelope.hasName(request.getNameFilter()))
            .filter(envelope -> envelope.hasID(request.getEnvelopeIDFilter()))
            .filter(envelope -> envelope.hasOwnerID(request.getOwnerIDFilter()))
            .filter(envelope -> envelope.hasCreationDate(request.getCreationDateFilterFrom(),
                request.getCreationDateFilterTo()))
            .collect(Collectors.toList());
        for (final Envelope envelope : filteredEnvelopeList) {
            final List<Document> filteredDocumentList = envelope.getDocumentList()
                .stream()
                .filter(document -> document.hasTitle(request.getTitleFilter()))
                .filter(document -> document.hasSignatureType(request.getSignatureTypeFilter()))
                .filter(document -> document.hasState(request.getStateFilter()))
                .filter(document -> document.hasEndDate(request.getEndDateFilterFrom(), request.getEndDateFilterTo()))
                .filter(document -> document.hasDataType(request.getDataType()))
                .filter(document -> document.hasSignatories(request.getSignatoryIDs()))
                .filter(document -> document.hasReaders(request.getReaderIDs()))
                .filter(document -> document.hasSigned(request.isSigned()))
                .filter(document -> document.hasRead(request.isRead()))
                .collect(Collectors.toList());
            final Envelope filteredEnvelope = new Envelope(envelope.getName(), filteredDocumentList,
                envelope.getOwner());
            resultList.add(filteredEnvelope);
        }
        return resultList;
    }
}



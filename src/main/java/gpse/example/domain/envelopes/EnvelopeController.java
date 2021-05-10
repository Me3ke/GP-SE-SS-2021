package gpse.example.domain.envelopes;

import gpse.example.domain.documents.DocumentPut;
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
     * @param ownerID the email adress of the creator.
     * @param name the name of the new envelope.
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

    /**
     * The fillEnvelope method does a put request to add a Document to an existing envelope.
     * @param envelopeID the ID of the envelope.
     * @param ownerID the email of the document creator
     * @param documentPut the command object keeping the information for a document to be created
     * @return the envelope in which the document was added to.
     * @throws UploadFileException if the document could not be uploaded.
     */
    @PutMapping("api.elsa.de/user/{userID:\\d+}/envelopes/{envelopeID:\\d+}")
    public Envelope fillEnvelope(final @PathVariable("envelopeID") long envelopeID,
                                 final @PathVariable("userID") String ownerID,
                                 final @RequestBody DocumentPut documentPut) throws UploadFileException {
        try {
            userService.getUser(ownerID);
            final List<User> signatories = new ArrayList<>();
            final List<String> signatoriesID = documentPut.getSignatoriesID();
            final List<User> readers = new ArrayList<>();
            final List<String> readersID = documentPut.getReadersID();
            for (final String currentID : signatoriesID) {
                signatories.add(userService.getUser(currentID));
            }
            for (final String currentID : readersID) {
                readers.add(userService.getUser(currentID));
            }
            return envelopeService.updateEnvelope(envelopeID, documentPut, ownerID, signatories, readers);
        } catch (CreatingFileException | DocumentNotFoundException | IOException | UsernameNotFoundException e) {
            throw new UploadFileException(e);
        }
    }
/*
        @GetMapping("api.elsa.de/user/{userID:\\d+}/envelopes/{envelopeID:\\d+}/documents/{documentID:\\d+}")
        public Document getDocumentFromEnvelope(final @PathVariable ("envelopeID") long envelopeID,
                                                final @PathVariable ("userID") String ownerID,
                                                final @PathVariable ("documentID") long documentID) {
        userService.getUser(ownerID);
        Optional<Envelope> envelope = envelopeService.getEnvelope(envelopeID);
        if (envelope.isPresent()) {
            List<Document> documentList = envelope.get().getDocumentList();
        }
        DocumentServiceImpl documentService;
        Document = documentService.getDocument(documentID);
            /*
            .stream().map(document -> {
                String documentDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(Long.toString(document.getId()))
                    .toUriString();
                    */
/*
                return new DocumentResponse(
                    document.getDocumentTitle(),
                    documentDownloadUri,
                    document.getDocumentType(),
                    document.getData().length);
            }).collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.OK).body(files);
        }

        @GetMapping("/files/{id}")
        public ResponseEntity<byte[]> getFile(@PathVariable String id) {
            Document document = null;
            try {
                document = storageService.getDocument(Long.parseLong(id));
            } catch (NumberFormatException e) {
                return null;

            }

            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getDocumentTitle() + "\"")
                .body(document.getData());
        }
        */
}

package gpse.example.domain.envelopes;

import gpse.example.domain.documents.Document;
import gpse.example.domain.documents.DocumentCmd;
import gpse.example.domain.documents.DocumentCreator;
import gpse.example.domain.users.User;
import gpse.example.domain.users.UserServiceImpl;
import gpse.example.domain.exceptions.UploadFileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * The envelopeController class handles the request from the frontend and
 * conducts the corresponding backend actions.
 */
@RestController
@CrossOrigin("http://localhost:8088")
public class EnvelopeController {

    @Autowired
    private EnvelopeServiceImpl envelopeService;

    @Autowired
    private UserServiceImpl userService;

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
            final Envelope envelope = envelopeService.store(name, owner);
            System.out.println("Uploaded the envelope successfully: " + envelope.getName());
            return envelope;
        } catch (IOException e) {
            throw new UploadFileException(e);
        }
    }

    /**
     * The fillEnvelope method does a put request to add a Document to an existing envelope.
     * @param envelopeID the ID of the envelope.
     * @param ownerID the email of the document creator
     * @param documentCmd the command object keeping the information for a document to be created
     * @return the envelope in which the document was added to.
     * @throws UploadFileException if the document could not be uploaded.
     */
    //TODO Error if user or envelope not found.
    @PutMapping("api.elsa.de/user/{userID:\\d+}/envelopes/{envelopeID:\\d+}")
    public Envelope fillEnvelope(final @PathVariable("envelopeID") long envelopeID,
                                 final @PathVariable("userID") String ownerID,
                                 final @RequestBody DocumentCmd documentCmd) throws UploadFileException {
        try {
            final DocumentCreator documentCreator = new DocumentCreator();
            final Envelope envelope = envelopeService.getEnvelope(envelopeID);
            final Document document = documentCreator.createDocument(documentCmd, ownerID);
            envelope.addDocument(document);
            System.out.println("Uploaded the file " + documentCmd.getTitle() + " successfully"
                               + " into " + envelope.getName());
            return envelope;
        } catch (IOException e) {
            throw new UploadFileException(e);
        }
    }

        /*
        @GetMapping("/files")
        public ResponseEntity<List<ResponseFile>> getListFiles() {
            List<ResponseFile> files = storageService.getDocuments().stream().map(document -> {
                String documentDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(Long.toString(document.getId()))
                    .toUriString();

                return new ResponseFile(
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

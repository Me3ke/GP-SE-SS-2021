package gpse.example.domain.documents;

import gpse.example.domain.envelopes.Envelope;
import gpse.example.domain.envelopes.EnvelopeServiceImpl;
import gpse.example.domain.exceptions.CreatingFileException;
import gpse.example.domain.exceptions.DocumentNotFoundException;
import gpse.example.domain.exceptions.DownloadFileException;
import gpse.example.domain.users.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * The DocumentController class handles the requests from the frontend and
 * conducts the corresponding backend actions.
 */
@RestController
@CrossOrigin("http://localhost:8088")
public class DocumentController {

    private EnvelopeServiceImpl envelopeService;
    private UserServiceImpl userService;
    private DocumentServiceImpl documentService;

    /**
     * The default constructor which initialises the services by autowiring.
     * @param envelopeService the envelopeService
     * @param userService the userService
     * @param documentService the documentService
     */
    @Autowired
    public DocumentController(final EnvelopeServiceImpl envelopeService, final UserServiceImpl userService,
                              final DocumentServiceImpl documentService) {
        this.envelopeService = envelopeService;
        this.userService = userService;
        this.documentService = documentService;
    }

    //TODO path for downloads?
    //Mark as read if downloaded?
    //TODO User has permission to download
    /**
     * The getDocumentFromEnvelope method gets a get request and creates an appropriate response.
     * @param envelopeID the id of the envelope which contains the document.
     * @param userID the id of the owner doing the request.
     * @param documentID the id of the document asked for.
     * @param download a boolean which indicates if a document should be downloaded.
     * @return the DocumentGet response
     * @throws DocumentNotFoundException if the document was not in this particular envelope, or
     *                                   if there was no document with this id in the database.
     * @throws DownloadFileException if something went wrong while downloading the file.
     */
    @GetMapping("api.elsa.de/user/{userID:\\d+}/envelopes/{envelopeID:\\d+}/documents/{documentID:\\d+}")
    public DocumentGetResponse getDocumentFromEnvelope(final @PathVariable("envelopeID") long envelopeID,
                                                       final @PathVariable("userID") String userID,
                                                       final @PathVariable("documentID") long documentID,
                                                       final @RequestParam("download") boolean download)
                                                throws DocumentNotFoundException, DownloadFileException {
        Document document;
        if (download) {
            try {
                final DocumentCreator documentCreator = new DocumentCreator();
                document = documentService.getDocument(documentID);
                documentCreator.download(document);
            } catch (CreatingFileException | IOException e) {
                throw new DownloadFileException(e);
            }
        }
        final Envelope envelope = envelopeService.getEnvelope(envelopeID);
        final List<Document> documentList = envelope.getDocumentList();
        boolean isInEnvelope = false;
        for (final Document currentDocument : documentList) {
            if (currentDocument.getId() == documentID) {
                isInEnvelope = true;
            }
        }
        if (isInEnvelope) {
            document = documentService.getDocument(documentID);
            return new DocumentGetResponse(document, userService.getUser(userID));
        } else {
            throw new DocumentNotFoundException();
        }
    }
}



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


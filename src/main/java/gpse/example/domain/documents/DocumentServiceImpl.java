package gpse.example.domain.documents;

import gpse.example.domain.envelopes.Envelope;
import gpse.example.domain.exceptions.CreatingFileException;
import gpse.example.domain.exceptions.DocumentNotFoundException;
import gpse.example.domain.signature.ProtoSignatory;
import gpse.example.domain.signature.Signatory;
import gpse.example.domain.signature.SignatoryServiceImpl;
import gpse.example.domain.signature.SignatureType;
import gpse.example.domain.users.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * the class that implements the DocumentService interface for communication with the database.
 */
@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository repo;
    private final DocumentMetaDataService documentMetaDataService;
    private final DocumentCreator documentCreator = new DocumentCreator();

    @Autowired
    public DocumentServiceImpl(final DocumentRepository repo, final DocumentMetaDataService documentMetaDataService) {
        this.documentMetaDataService = documentMetaDataService;
        this.repo = repo;
    }

    @Override
    public Document getDocument(final long id) throws DocumentNotFoundException {
        return repo.findById(id).orElseThrow(DocumentNotFoundException::new);
    }

    @Override
    public List<Document> getDocuments() {
        final List<Document> documents = new ArrayList<>();
        repo.findAll().forEach(documents :: add);
        return documents;
    }

    @Override
    public void remove(final Document document) {
        repo.delete(document);
    }

    @Override
    public Document addDocument(final Document document) {
        return repo.save(document);
    }

    @Override
    public Document creation(final DocumentPutRequest documentPutRequest, final Envelope envelope, final String ownerID,
                              final UserServiceImpl userService, final SignatoryServiceImpl signatoryService)
                                throws CreatingFileException, IOException {
        final List<ProtoSignatory> signatories = new ArrayList<>();
        final List<String> signatoriesID = documentPutRequest.getSignatoriesID();
        final List<String> readersID = documentPutRequest.getReadersID();
        for (final String currentID : readersID) {
            signatories.add(new ProtoSignatory(userService.getUser(currentID), SignatureType.REVIEW));
        }
        for (final String currentID : signatoriesID) {
            signatories.add(new ProtoSignatory(userService.getUser(currentID), SignatureType.SIMPLE_SIGNATURE));
        }
        final Document newDocument = documentCreator.createDocument(documentPutRequest,
            ownerID, signatories);
        for (final Document currentDocument : envelope.getDocumentList()) {
            for (final Signatory signatory : currentDocument.getSignatories()) {
                signatory.setStatus(false);
                signatoryService.saveSignatory(signatory);
            }
        }
        documentMetaDataService.saveDocumentMetaData(newDocument.getDocumentMetaData());

        return addDocument(newDocument);
    }
}

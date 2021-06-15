package gpse.example.domain.documents;

import gpse.example.domain.envelopes.Envelope;
import gpse.example.domain.exceptions.CreatingFileException;
import gpse.example.domain.exceptions.DocumentNotFoundException;
import gpse.example.domain.signature.*;
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
    private final AdvancedSignatureRepository advancedSignatureRepository;
    private final DocumentCreator documentCreator = new DocumentCreator();

    /**
     * the standard constructor for documentServices.
     * @param repo the documentRepository initialized by Spring
     * @param documentMetaDataService the documentMetaDataService initialized by Spring
     * @param advancedSignatureRepository the advancedSignatureRepository initialized by Spring
     */
    @Autowired
    public DocumentServiceImpl(final DocumentRepository repo, final DocumentMetaDataService documentMetaDataService,
                               final AdvancedSignatureRepository advancedSignatureRepository) {
        this.documentMetaDataService = documentMetaDataService;
        this.advancedSignatureRepository = advancedSignatureRepository;
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
    public List<AdvancedSignature> saveSignatures(final Document document) {
        List<AdvancedSignature> saved = new ArrayList<>();
        List<AdvancedSignature> signatures = document.getAdvancedSignatures();
        for (AdvancedSignature signature : signatures) {
            saved.add(advancedSignatureRepository.save(signature));
        }
        return saved;
    }

    @Override
    public Document creation(final DocumentPutRequest documentPutRequest, final Envelope envelope, final String ownerID,
                              final UserServiceImpl userService, final SignatoryServiceImpl signatoryService)
                                throws CreatingFileException, IOException {


        final List<Signatory> alreadyDefinedSignatories = documentPutRequest.getAlreadyDefinedSignatories();
        final List<ProtoSignatory> signatories;

        // TODO Signature Type
        if(alreadyDefinedSignatories != null) {
            signatories = new ArrayList<>();
            for (Signatory signatory : alreadyDefinedSignatories) {
                signatories.add(signatory.toProtoSignatory());
            }
        } else {

            signatories = documentPutRequest.getSignatories();
        }
        final Document newDocument = documentCreator.createDocument(documentPutRequest,
            ownerID, signatories, userService);
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

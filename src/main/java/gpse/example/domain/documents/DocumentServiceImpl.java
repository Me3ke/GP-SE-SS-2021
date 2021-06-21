package gpse.example.domain.documents;

import gpse.example.domain.envelopes.Envelope;
import gpse.example.domain.exceptions.CreatingFileException;
import gpse.example.domain.exceptions.DocumentNotFoundException;
import gpse.example.domain.signature.*;
import gpse.example.domain.users.UserServiceImpl;
import gpse.example.web.documents.DocumentPutRequest;
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


    private final DocumentRepository documentRepository;
    private final DocumentCreator documentCreator;


    /**
     * the standard constructor for documentServices.
     * @param documentRepository the documentRepository initialized by Spring
     * @param documentCreator the documentCreator creates documents.
     */
    @Autowired
    public DocumentServiceImpl(final DocumentRepository documentRepository, final DocumentCreator documentCreator) {
        this.documentRepository = documentRepository;
        this.documentCreator = documentCreator;
    }

    @Override
    public Document getDocument(final long id) throws DocumentNotFoundException {
        return documentRepository.findById(id).orElseThrow(DocumentNotFoundException::new);
    }

    @Override
    public List<Document> getDocuments() {
        final List<Document> documents = new ArrayList<>();
        documentRepository.findAll().forEach(documents :: add);
        return documents;
    }

    @Override
    public void remove(final Document document) {
        documentRepository.delete(document);
    }

    @Override
    public Document addDocument(final Document document) {
        return documentRepository.save(document);
    }

    @Override
    public Document creation(final DocumentPutRequest documentPutRequest, final Envelope envelope, final String ownerID,
                             final UserServiceImpl userService)
                                throws CreatingFileException, IOException {


        final List<Signatory> alreadyDefinedSignatories = documentPutRequest.getAlreadyDefinedSignatories();
        final List<ProtoSignatory> signatories = new ArrayList<>();

        // TODO Signature Type
        if (alreadyDefinedSignatories != null) {
            for (Signatory signatory : alreadyDefinedSignatories) {
                signatories.add(signatory.toProtoSignatory());
            }
        }

        if(documentPutRequest.getSignatories() != null) {
            signatories.addAll(documentPutRequest.getSignatories());
        }

        final Document newDocument = documentCreator.createDocument(documentPutRequest,
            ownerID, signatories, userService, this);

        for (final Document currentDocument : envelope.getDocumentList()) {
            for (final Signatory signatory : currentDocument.getSignatories()) {
                signatory.setStatus(false);
            }
        }
        return addDocument(newDocument);
    }
}

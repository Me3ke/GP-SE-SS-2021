package gpse.example.domain.documents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The standard implementation for DocumentMetadataServices.
 */
@Service
public class DocumentMetaDataServiceImpl implements DocumentMetaDataService {

    private final DocumentMetaDataRepository documentMetaDataRepository;

    @Autowired
    public DocumentMetaDataServiceImpl(final DocumentMetaDataRepository documentMetaDataRepository) {
        this.documentMetaDataRepository = documentMetaDataRepository;
    }

    @Override
    public DocumentMetaData saveDocumentMetaData(final DocumentMetaData documentMetaData) {
        return documentMetaDataRepository.save(documentMetaData);
    }

    @Override
    public void removeAll() {
        documentMetaDataRepository.deleteAll();
    }
}

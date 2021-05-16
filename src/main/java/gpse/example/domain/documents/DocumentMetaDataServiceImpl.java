package gpse.example.domain.documents;

import gpse.example.domain.exceptions.DocumentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DocumentMetaDataServiceImpl implements DocumentMetaDataService{

    private final DocumentMetaDataRepository documentMetaDataRepository;

    @Autowired
    public DocumentMetaDataServiceImpl(DocumentMetaDataRepository documentMetaDataRepository) {
        this.documentMetaDataRepository = documentMetaDataRepository;
    }

    @Override
    public DocumentMetaData saveDocumentMetaData(DocumentMetaData documentMetaData) {
        return documentMetaDataRepository.save(documentMetaData);
    }

    @Override
    public void removeEverything () {
        documentMetaDataRepository.deleteAll();
    }
}

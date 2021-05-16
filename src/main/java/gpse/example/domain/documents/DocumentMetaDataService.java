package gpse.example.domain.documents;

import gpse.example.domain.exceptions.DocumentNotFoundException;

public interface DocumentMetaDataService {
    DocumentMetaData saveDocumentMetaData(DocumentMetaData documentMetaData);
    void removeEverything();
}

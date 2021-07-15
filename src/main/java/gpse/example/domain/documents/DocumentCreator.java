package gpse.example.domain.documents;

import gpse.example.domain.exceptions.CreatingFileException;
import gpse.example.domain.signature.ProtoSignatory;
import org.springframework.stereotype.Component;
import gpse.example.web.documents.DocumentPutRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The DocumentCreator is a factory method responsible for creating documents and envelops.
 */
@SuppressWarnings("PMD.AvoidFileStream")
@Component
public class DocumentCreator {

    /**
     * The createDocFromData creates a document if it has been uploaded, using the put request body.
     *
     * @param documentPutRequest the command object which keeps the information for the document.
     * @param ownerID            the email adress of the User who want to create the document.
     * @param signatories        the list of signatories for this document.
     * @param documentService    the documentService.
     * @return the created document.
     * @throws IOException           if the data is incorrect.
     * @throws CreatingFileException if the path is not specified.
     */
    //does not include directories.
    public Document createDocument(final DocumentPutRequest documentPutRequest, final String ownerID,
                                   final List<ProtoSignatory> signatories,
                                   final DocumentService documentService)
        throws CreatingFileException, IOException {
        if (documentPutRequest.getData().length == 0) {
            throw new CreatingFileException(new IOException());
        }
        final Document document = new Document(documentPutRequest, new ArrayList<>(),
            ownerID);
        setDocumentState(signatories, document);
        setSignatories(signatories, document, documentService);
        return document;
    }

    /**
     * The setSignatories method creates signatory objects and refers them to the documents.
     * A reader uses the same class as Signatory because both processes are similar.
     *
     * @param signatories a list of users containing all the people to sign or read the document.
     * @param document    the document itself.
     */
    private void setSignatories(final List<ProtoSignatory> signatories,
                                final Document document,
                                final DocumentService documentService) {
        if (signatories != null) {
            for (final ProtoSignatory signatory : signatories) {
                document.getSignatoryManagement().addSignatory(signatory.getEmail(), signatory.getSignatureType());
            }
            documentService.addDocument(document);
        }
    }

    /**
     * The setDocumentState method evaluates the readers and signatories and creates an
     * initial state of the document based on that.
     *
     * @param signatories a list of users containing all the people to sign the document.
     * @param document    the document itself.
     */
    private void setDocumentState(final List<ProtoSignatory> signatories, final Document document) {

        if (signatories == null && !document.getSignatureProcessData().isDraft()) {
            document.getSignatureProcessData().setState(DocumentState.ARCHIVED);
        } else if (document.getSignatoryManagement().getReaders().size() == 0) {
            document.getSignatureProcessData().setState(DocumentState.SIGN);
        } else {
            document.getSignatureProcessData().setState(DocumentState.REVIEW);
        }
    }
}

package gpse.example.web;

import gpse.example.domain.documents.DocumentService;
import gpse.example.web.envelopes.DocumentOverviewResponse;
import gpse.example.web.envelopes.EnvelopeGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The class responsible for filtering documents and envelopes.
 */
@Component
public class DocumentFilter {

    private DocumentService documentService;

    @Autowired
    public DocumentFilter(DocumentService documentService) {
        this.documentService = documentService;
    }

    /**
     * The methcod used to filter the EnvelopeGetResponses.
     * @param envelopes the envelopeGetResponses
     * @param userID the user that has send the getRequest
     * @return filtered List of envelopeGetResponses
     */
    public List<EnvelopeGetResponse> filterEnvelopes(List<EnvelopeGetResponse> envelopes, String userID) {
        List<EnvelopeGetResponse> filteredEnvelopes = new ArrayList<>();
        for (EnvelopeGetResponse envelope : envelopes) {
            if (envelope.getOwner().getUsername().equals(userID)) {
                filteredEnvelopes.add(envelope);
            } else if (userIsAssignedToEnvelope(envelope.getDocuments(), userID)) {
                envelope.setDocuments(filterDocuments(envelope.getDocuments(), userID));
                filteredEnvelopes.add(envelope);
            }
        }
        return filteredEnvelopes;
    }

    private List<DocumentOverviewResponse> filterDocuments(List<DocumentOverviewResponse> documents, String userID) {
        List<DocumentOverviewResponse> filteredDocuments = new ArrayList<>();
        for (DocumentOverviewResponse document : documents) {
            if (userIsAssignedToDocument(document, userID)) {
                filteredDocuments.add(document);
            }
        }
        return filteredDocuments;
    }

    private boolean userIsAssignedToEnvelope(List<DocumentOverviewResponse> documents, String userID) {
        boolean isAssigned = false;
        for (DocumentOverviewResponse document : documents) {
            isAssigned |= userIsAssignedToDocument(document, userID);
        }
        return isAssigned;
    }

    private boolean userIsAssignedToDocument(DocumentOverviewResponse document, String userID) {
        if (document.getOwner().getUsername().equals(userID)) {
            return true;
        } else {
            return document.isReader() || document.isSignatory();
        }
    }
}

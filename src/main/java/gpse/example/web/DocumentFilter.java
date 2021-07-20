package gpse.example.web;

import gpse.example.web.envelopes.DocumentOverviewResponse;
import gpse.example.web.envelopes.EnvelopeGetResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The class responsible for filtering documents and envelopes.
 */
@Component
public class DocumentFilter {

    /**
     * The methcod used to filter the EnvelopeGetResponses.
     * @param envelopes the envelopeGetResponses
     * @param userID the user that has send the getRequest
     * @return filtered List of envelopeGetResponses
     */
    public List<EnvelopeGetResponse> filterEnvelopes(final List<EnvelopeGetResponse> envelopes, final String userID) {
        final List<EnvelopeGetResponse> filteredEnvelopes = new ArrayList<>();
        for (final EnvelopeGetResponse envelope : envelopes) {
            if (envelope.getOwner().getUsername().equals(userID)) {
                filteredEnvelopes.add(envelope);
            } else if (userIsAssignedToEnvelope(envelope.getDocuments(), userID)) {
                envelope.setDocuments(filterDocuments(envelope.getDocuments(), userID));
                if (!envelope.getDocuments().isEmpty()) {
                    filteredEnvelopes.add(envelope);
                }
            }
        }
        return filteredEnvelopes;
    }

    private List<DocumentOverviewResponse> filterDocuments(final List<DocumentOverviewResponse> documents,
                                                           final String userID) {
        final List<DocumentOverviewResponse> filteredDocuments = new ArrayList<>();
        for (final DocumentOverviewResponse document : documents) {
            if (userIsAssignedToDocument(document, userID)
                    && !document.isDraft()
                || document.getOwner().getUsername().equals(userID)) {
                filteredDocuments.add(document);
            }
        }
        return filteredDocuments;
    }

    private boolean userIsAssignedToEnvelope(final List<DocumentOverviewResponse> documents, final String userID) {
        boolean isAssigned = false;
        for (final DocumentOverviewResponse document : documents) {
            isAssigned |= userIsAssignedToDocument(document, userID);
        }
        return isAssigned;
    }

    private boolean userIsAssignedToDocument(final DocumentOverviewResponse document, final String userID) {
        if (document.getOwner().getUsername().equals(userID)) {
            return true;
        } else {
            return document.isReader() || document.isSignatory();
        }
    }
}

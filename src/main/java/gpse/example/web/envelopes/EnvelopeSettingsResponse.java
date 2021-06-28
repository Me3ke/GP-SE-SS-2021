package gpse.example.web.envelopes;

import gpse.example.domain.envelopes.Envelope;
import gpse.example.web.documents.DocumentSetting;

import java.util.ArrayList;
import java.util.List;

/**
 * The class used to send the current settings of an Envelope to the frontend.
 */
public class EnvelopeSettingsResponse {

    private List<DocumentSetting> documentSettings;

    /**
     * The standard constructor.
     * @param envelope the relating envelope.
     */
    public EnvelopeSettingsResponse(final Envelope envelope) {
        this.documentSettings = new ArrayList<>();
        for (int i = 0; i < envelope.getDocumentList().size(); i++) {
            this.documentSettings.add(new DocumentSetting(envelope.getDocumentList().get(i)));
        }
    }

    public EnvelopeSettingsResponse() {

    }

    public List<DocumentSetting> getDocumentSettings() {
        return documentSettings;
    }

    public void setDocumentSettings(final List<DocumentSetting> documentSettings) {
        this.documentSettings = documentSettings;
    }
}

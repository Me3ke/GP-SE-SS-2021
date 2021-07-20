package gpse.example.web.envelopes;

import gpse.example.domain.documents.Document;
import gpse.example.domain.documents.DocumentState;
import gpse.example.domain.users.User;

import java.time.format.DateTimeFormatter;

/**
 * The class used to send only the necessary information regarding documents on the overviewpage.
 */
public class DocumentOverviewResponse extends DocumentOverviewResponseBooleanContainer {

    private final String title;
    private final DocumentState state;
    private final User owner;
    private final String creationDate;
    private final String endDate;
    private final String dataType;
    private final String identifier;
    private final long id;

    /**
     * The default constructor creates the documentGet based on an existing document
     * which is created from the Database beforehand.
     *
     * @param document    the corresponding document for the request.
     * @param owner       the owner of the document.
     * @param currentUser the user doing the request
     */
    public DocumentOverviewResponse(final Document document, final User owner, final String currentUser) {
        super(document, currentUser);
        this.title = document.getDocumentTitle();
        this.owner = owner;
        //Replaced with uploadDate
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        this.creationDate = document.getDocumentMetaData().getMetaTimeStampUpload().format(formatter);
        if (document.getSignatureProcessData().getEndDate() == null) {
            this.endDate = "";
        } else {
            this.endDate = document.getSignatureProcessData().getEndDate().format(formatter);
        }
        this.dataType = document.getDocumentType();
        this.state = document.getSignatureProcessData().getState();
        this.identifier = document.getDocumentMetaData().getIdentifier();
        this.id = document.getId();
    }

    public String getTitle() {
        return title;
    }

    public DocumentState getState() {
        return state;
    }

    public User getOwner() {
        return owner;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getDataType() {
        return dataType;
    }

    public String getIdentifier() {
        return identifier;
    }

    public long getId() {
        return id;
    }
}

package gpse.example.util.email;


/**
 * Datacontainer from which the Template get the data to fill.
 */
public class TemplateDataContainer {

    private String firstNameReciever;

    private String lastNameReciever;

    private String firstNameOwner;

    private String lastNameOwner;

    private String endDate;

    private String envelopeName;

    private String documentTitle;

    /**
     * link and requestingEmail are just for internal use.
     * Do not Use in users Template designs!
     */
    private String link;

    private String requestingEmail;

    public TemplateDataContainer() {

    }

    public String getFirstNameReciever() {
        return firstNameReciever;
    }

    public void setFirstNameReciever(String firstNameReciever) {
        this.firstNameReciever = firstNameReciever;
    }

    public String getLastNameReciever() {
        return lastNameReciever;
    }

    public void setLastNameReciever(String lastNameReciever) {
        this.lastNameReciever = lastNameReciever;
    }

    public String getFirstNameOwner() {
        return firstNameOwner;
    }

    public void setFirstNameOwner(String firstNameOwner) {
        this.firstNameOwner = firstNameOwner;
    }

    public String getLastNameOwner() {
        return lastNameOwner;
    }

    public void setLastNameOwner(String lastNameOwner) {
        this.lastNameOwner = lastNameOwner;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEnvelopeName() {
        return envelopeName;
    }

    public void setEnvelopeName(String envelopeName) {
        this.envelopeName = envelopeName;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getRequestingEmail() {
        return requestingEmail;
    }

    public void setRequestingEmail(String requestingEmail) {
        this.requestingEmail = requestingEmail;
    }
}

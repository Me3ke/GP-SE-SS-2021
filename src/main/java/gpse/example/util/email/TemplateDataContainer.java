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

    private String envalopeName;

    private String documentTitle;

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

    public String getEnvalopeName() {
        return envalopeName;
    }

    public void setEnvalopeName(String envalopeName) {
        this.envalopeName = envalopeName;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }
}

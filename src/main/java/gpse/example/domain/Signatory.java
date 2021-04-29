package gpse.example.domain;

public class Signatory {
    private Document document;
    private User user;
    private boolean status;

    public Signatory(Document document, User user) {
        this.document = document;
        this.user = user;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

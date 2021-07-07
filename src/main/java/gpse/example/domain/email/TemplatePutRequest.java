package gpse.example.domain.email;

/**
 * Transport object for templates.
 */
public class TemplatePutRequest {
    private String htmlBody;
    private String subject;
    private String name;

    public String getHtmlBody() {
        return htmlBody;
    }

    public void setHtmlBody(final String htmlBody) {
        this.htmlBody = htmlBody;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}

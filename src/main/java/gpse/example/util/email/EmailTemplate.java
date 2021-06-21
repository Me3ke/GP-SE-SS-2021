package gpse.example.util.email;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class of EmailTemplates that could be defined by user.
 */
@Entity
public class EmailTemplate {

    /**
     * close paramSpace.
     */
    public static final String CLOSE = "]";

    /**
     * open paramSpace.
     */
    public static final String OPEN = "[";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long templateID;

    @Column
    private String htmlTemplateBody;

    @Column
    private String subject;

    public EmailTemplate(String template, String subject) {
        this.htmlTemplateBody = template;
        this.subject = subject;
    }

    public EmailTemplate() {

    }

    /**
     * Computes the params that are used in template.
     * @return an arraylist with the params
     */
    public List<String> neededParams() {
        String temp = this.htmlTemplateBody;
        ArrayList<String> params = new ArrayList<>();
        while (temp.indexOf(CLOSE) > 0) {
            params.add(findFirst(temp));
            temp = temp.substring(temp.indexOf(CLOSE) + 1);
            System.out.println(temp);
        }
        return params;
    }

    static String findFirst(String str) {
        return str.substring(str.indexOf(OPEN) + 1, str.indexOf(CLOSE));
    }

    public long getTemplateID() {
        return templateID;
    }

    public void setTemplateID(long templateID) {
        this.templateID = templateID;
    }

    public String getHtmlTemplateBody() {
        return htmlTemplateBody;
    }

    public void setHtmlTemplateBody(String htmlTemplateBody) {
        this.htmlTemplateBody = htmlTemplateBody;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}

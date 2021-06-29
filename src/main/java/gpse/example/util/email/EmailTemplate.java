package gpse.example.util.email;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Class of EmailTemplates that could be defined by user.
 */
@Entity
public class EmailTemplate implements Serializable {

    /**
     * close paramSpace.
     */
    public static final String CLOSE = "]";

    /**
     * open paramSpace.
     */
    public static final String OPEN = "[";

    private static final long serialVersionUID = -4794520836797714540L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long templateID;

    @Lob
    private String htmlTemplateBody;

    @Column
    private String subject;

    @Column
    private String name;

    @Column
    private boolean system;

    /**
     * Constructor of emailTemplate.
     *
     * @param template String containing the string with the html message.
     * @param subject  the subject of emails with this template
     * @param name     name of template so user can name his/her templates
     * @param system   boolean is true when the template is a systemIntern one
     */
    public EmailTemplate(final String template, final String subject, final String name, final boolean system) {
        this.htmlTemplateBody = template;
        this.subject = subject;
        this.name = name;
        this.system = system;
    }

    public EmailTemplate() {

    }

    /**
     * Computes the params that are used in template.
     *
     * @return an arraylist with the params
     */
    public List<String> neededParams() {
        String temp = this.htmlTemplateBody;
        final ArrayList<String> params = new ArrayList<>();
        while (temp.indexOf(CLOSE) > 0) {
            params.add(findFirst(temp));
            temp = temp.substring(temp.indexOf(CLOSE) + 1);
        }
        return params;
    }

    private String findFirst(final String str) {
        return str.substring(str.indexOf(OPEN) + 1, str.indexOf(CLOSE));
    }

    /**
     * change needed params in Template with the specified Data.
     *
     * @param dataContainer contains the data
     * @return the filled out templatebody missing values are replaced with nothing
     * @throws InvocationTargetException if invocation goes wrong
     */
    public String filledTemplate(final TemplateDataContainer dataContainer)
        throws InvocationTargetException {
        String filledTemplate = this.htmlTemplateBody;
        for (final String placeholder : neededParams()) {
            try {
                if (dataOf(placeholder, dataContainer) != null) {
                    filledTemplate = filledTemplate.replace(OPEN + placeholder + CLOSE,
                        dataOf(placeholder, dataContainer));
                } else {
                    filledTemplate = filledTemplate.replace(OPEN + placeholder + CLOSE, "");
                }
            } catch (NoSuchMethodException | IllegalAccessException exc) {
                filledTemplate = filledTemplate.replace(OPEN + placeholder + CLOSE, "");
            }
        }
        return filledTemplate;
    }


    private String dataOf(final String searchedData, final TemplateDataContainer dataContainer)
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Method method = dataContainer.getClass().getDeclaredMethod("get" + searchedData);

        return (String) method.invoke(dataContainer);
    }

    public long getTemplateID() {
        return templateID;
    }

    public void setTemplateID(final long templateID) {
        this.templateID = templateID;
    }

    public String getHtmlTemplateBody() {
        return htmlTemplateBody;
    }

    public void setHtmlTemplateBody(final String htmlTemplateBody) {
        this.htmlTemplateBody = htmlTemplateBody;
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

    public boolean isSystem() {
        return system;
    }

    public void setSystem(final boolean system) {
        this.system = system;
    }
}

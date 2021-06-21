package gpse.example.util.email;

import javax.persistence.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    @Column
    private String name;

    /**
     * Constructor of emailTemplate.
     * @param template String containing the string with the html message.
     * @param subject the subject of emails with this template
     * @param name name of template so user can name his/her templates
     */
    public EmailTemplate(String template, String subject, String name) {
        this.htmlTemplateBody = template;
        this.subject = subject;
        this.name = name;
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

    private String findFirst(String str) {
        return str.substring(str.indexOf(OPEN) + 1, str.indexOf(CLOSE));
    }

    /**
     * change needed params in Template with the specified Data.
     * @param dataContainer contains the data
     * @return the filled out templatebody missing values are replaced with nothing
     * @throws InvocationTargetException if invocation goes wrong
     * @throws NoSuchMethodException if there is a wrong placeholder so the corresponding getter is not found
     *                                  in dataContainer.
     * @throws IllegalAccessException if invocation of the called method is illegal
     */
    public String filledTemplate(TemplateDataContainer dataContainer)
            throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        String filledTemplate = this.htmlTemplateBody;
        for (String placeholder:neededParams()) {
            if (dataOf(placeholder, dataContainer) != null) {
                filledTemplate = filledTemplate.replace(OPEN + placeholder + CLOSE,
                    dataOf(placeholder, dataContainer));
            } else {
                filledTemplate = filledTemplate.replace(OPEN + placeholder + CLOSE, "");
            }
        }
        return filledTemplate;
    }


    private String dataOf(String searchedData, TemplateDataContainer dataContainer)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = dataContainer.getClass().getDeclaredMethod("get" + searchedData);

        return (String) method.invoke(dataContainer);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package gpse.example.util.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for EmailTemplates.
 */
@Service
public class EmailTemplateService {

    private EmailTemplateRepository templateRepository;

    @Autowired
    public EmailTemplateService(final EmailTemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    /**
     * Find Systemintern Template by its name.
     * @param name name of searched template
     * @return the template opbject
     * @throws TemplateNameNotFoundException if there is no systemtemplate with this name
     */
    public EmailTemplate findSystemTemplateByName(String name) throws TemplateNameNotFoundException {
        final List<EmailTemplate> systemTemplates = (List<EmailTemplate>) templateRepository.findAll();

        for (EmailTemplate temp: systemTemplates) {
            if (temp.isSystem() && temp.getName().equals(name)) {
                return temp;
            }
        }
        throw new TemplateNameNotFoundException();
    }

    public void deleteTemplate(long templateId) {
        templateRepository.deleteById(templateId);
    }
}

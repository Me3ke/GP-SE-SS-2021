package gpse.example.domain.email;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository of emailTemplates.
 */
@Repository
public interface EmailTemplateRepository extends CrudRepository<EmailTemplate, Long> {
}

package gpse.example.domain.email;

import gpse.example.web.messages.MessageSettingsContainer;
import org.springframework.data.repository.CrudRepository;

/**
 * default repository for messages.
 */
public interface MessageSettingsRepository extends CrudRepository<MessageSettingsContainer, Long> {
}

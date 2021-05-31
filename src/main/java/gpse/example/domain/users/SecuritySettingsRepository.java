package gpse.example.domain.users;

import org.springframework.data.repository.CrudRepository;

/**
 * The standard repository for Security-settings-object.
 */
public interface SecuritySettingsRepository extends CrudRepository<SecuritySettings, Long> {

}

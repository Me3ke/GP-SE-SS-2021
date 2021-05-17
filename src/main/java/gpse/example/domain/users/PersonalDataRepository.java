package gpse.example.domain.users;

import org.springframework.data.repository.CrudRepository;

/**
 * the standard repository for PersonalData
 */
public interface PersonalDataRepository extends CrudRepository<PersonalData, Long> {
}

package gpse.example.domain.users;

/**
 * the interface for personalDataservices, that ensures that all PersonalDataServices are able to save PersonalData.
 */
public interface PersonalDataService {

    PersonalData savePersonalData(PersonalData personalData);
}

package gpse.example.domain.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * standard personalDataService used to save PersonalData.
 */
@Service
public class PersonalDataServiceImpl implements PersonalDataService {

    private PersonalDataRepository personalDataRepository;

    @Autowired
    public PersonalDataServiceImpl(PersonalDataRepository personalDataRepository) {
        this.personalDataRepository = personalDataRepository;
    }

    @Override
    public PersonalData savePersonalData(PersonalData personalData) {
        return personalDataRepository.save(personalData);
    }
}

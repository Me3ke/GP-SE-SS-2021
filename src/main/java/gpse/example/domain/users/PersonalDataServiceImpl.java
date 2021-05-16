package gpse.example.domain.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalDataServiceImpl implements PersonalDataService{

    private PersonalDataRepository personalDataRepository;

    @Autowired
    public PersonalDataServiceImpl(PersonalDataRepository personalDataRepository) {
        this.personalDataRepository = personalDataRepository;
    }
    @Override
    public PersonalData savePersonalData(PersonalData personalData) {
        final PersonalData saved = personalDataRepository.save(personalData);
        return personalData;
    }
}

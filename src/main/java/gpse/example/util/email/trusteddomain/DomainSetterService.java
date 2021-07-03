package gpse.example.util.email.trusteddomain;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for DomainSettings in database.
 */
@Service
public class DomainSetterService {

    private final DomainSetterRepository domainSetterRepository;

    public DomainSetterService(final DomainSetterRepository domainSetterRepository) {
        this.domainSetterRepository = domainSetterRepository;
    }

    public boolean isEmpty() {
        final List<DomainSetter> setter = (List<DomainSetter>) domainSetterRepository.findAll();
        return setter.isEmpty();
    }

    public void saveDomainSettings(final DomainSetter domainSetter) {
        domainSetterRepository.save(domainSetter);
    }
    public List<DomainSetter> getDomainSettings() {
        return (List<DomainSetter>) domainSetterRepository.findAll();
    }
}

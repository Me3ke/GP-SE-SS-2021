package gpse.example.util.email.trustedDomain;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DomainSetterService {

    private final DomainSetterRepository domainSetterRepository;

    public DomainSetterService(DomainSetterRepository domainSetterRepository) {
        this.domainSetterRepository = domainSetterRepository;
    }

    public boolean isEmpty() {
        List<DomainSetter> setter = (List<DomainSetter>) domainSetterRepository.findAll();
        return setter.size() == 0;
    }

    public void saveDomainSettings(DomainSetter domainSetter) {
        domainSetterRepository.save(domainSetter);
    }
    public List<DomainSetter> getDomainSettings() {
        return (List<DomainSetter>) domainSetterRepository.findAll();
    }
}

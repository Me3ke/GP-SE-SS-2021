package gpse.example.domain.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * standard securitySettingsService used to save SecuritySettings.
 */
@Service
public class SecuritySettingsServiceImpl implements SecuritySettingsService {

    private final SecuritySettingsRepository securitySettingsRepository;

    @Autowired
    public SecuritySettingsServiceImpl(final SecuritySettingsRepository securitySettingsRepository) {
        this.securitySettingsRepository = securitySettingsRepository;
    }

    @Override
    public SecuritySettings saveSecuritySettings(final SecuritySettings securitySettings) {
        return securitySettingsRepository.save(securitySettings);
    }
}

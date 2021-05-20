package gpse.example;

import gpse.example.domain.users.PersonalData;
import gpse.example.domain.users.PersonalDataService;
import gpse.example.domain.users.User;
import gpse.example.domain.users.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * This class starts the Database on serverstart.
 */
@Service
public class InitializeDatabase implements InitializingBean {

    private final UserService userService;
    private final PersonalDataService personalDataService;


    @Lazy
    @Autowired
    public InitializeDatabase(final UserService userService, final PersonalDataService personalDataService) {
        this.userService = userService;
        this.personalDataService = personalDataService;
    }

    @Override
    public void afterPropertiesSet() {
        final String username = "hans.schneider@mail.de";
        try {
            userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException ex) {
            final PersonalData personalData = new PersonalData("Berliner Straße", 2, 12312,
                "Liebefeld", "Deutschland", LocalDate.now(), "32131245");
            final User user = new User(
                    username,
                    "Hans",
                    "Schneider",
                "{bcrypt}$2y$12$DdtBOd4cDqlvMGXPoNr9L.6YkszYXn364x172BKabx3ucOiYUmTfG"
            );
            user.setEnabled(true);
            personalDataService.savePersonalData(personalData);
            user.setPersonalData(personalData);
            user.addRole("ROLE_USER");
            userService.saveUser(user);
        }
    }
}

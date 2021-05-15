package gpse.example;

import gpse.example.domain.*;
import gpse.example.domain.users.PersonalData;
import gpse.example.domain.users.User;
import gpse.example.domain.users.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * This class starts the Database on serverstart.
 */
@Service
public class InitializeDatabase implements InitializingBean {

    private final UserService userService;


    @Autowired
    public InitializeDatabase(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public void afterPropertiesSet() {
        String username = "hans.schneider@mail.de";
        try {
            userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException ex) {
            final PersonalData personalData = new PersonalData("Berliner Straße", 2, 12312,
                "Liebefeld", "Deutschland", LocalDate.now(), 32131245);
            final User user = userService.createUser(
                    username,
                    "{bcrypt}$2y$12$DdtBOd4cDqlvMGXPoNr9L.6YkszYXn364x172BKabx3ucOiYUmTfG",
                    "Hans",
                    "Schneider",
                    personalData,
                    "ROLE_USER"
            );

        }
    }
}

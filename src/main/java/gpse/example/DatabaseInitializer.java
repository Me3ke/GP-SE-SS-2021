package gpse.example;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * the class that initializes the ELSA-database automatically.
 */
@Service
public class DatabaseInitializer implements InitializingBean {

    @Autowired
    public DatabaseInitializer() {
    }

    @Override
    public void afterPropertiesSet() {
    }
}

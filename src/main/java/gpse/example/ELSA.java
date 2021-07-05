package gpse.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * The ELSA class is a temporary program start class which posses the main method
 * of the program and is responsible for commandline queries.
 */
@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
@EnableScheduling
public class ELSA {

    /**
     * The main method which starts the application.
     * @param args The program arguments
     */
    public static void main(final String... args) {
        SpringApplication.run(ELSA.class, args);
    }
}

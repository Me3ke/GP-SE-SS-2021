package gpse.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * The ELSA class is a temporary program start class which contains the main method
 * of the program and is responsible for commandline queries.
 */
@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class ELSA {

    private static final int DEFAULT_EXIT = 100;
    /**
     * The main method which starts a query to evaluate the command line inputs.
     * Commands:
     * exit
     * import *path*
     * sign *?*
     * help
     * server
     * @param args The program arguments
     */
    public static void main(final String... args) {
        System.out.println("Welcome to ELSA!");
        SpringApplication.run(ELSA.class, args);
        /*
        final QueryHandler queryHandler = new QueryHandler();
        final int exitValue = queryHandler.query(args);
        if (exitValue == DEFAULT_EXIT) {
            System.exit(exitValue);
        }
         */
    }
}

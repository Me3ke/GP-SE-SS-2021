package gpse.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * The ELSA class is a temporary program start class which posses the main method
 * of the program and is responsible for commandline queries.
 */
@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
@SpringBootApplication
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
        System.out.println("Welcome to ELSA! Type help to get help");
        QueryHandler queryHandler = new QueryHandler();
        int exitValue = queryHandler.query(args);
        if (exitValue == DEFAULT_EXIT) {
            System.exit(exitValue);
        }

    }
}

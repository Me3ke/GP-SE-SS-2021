package gpse.example;

import gpse.example.domain.Document;
import org.apache.tomcat.util.net.jsse.JSSEUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Scanner;

@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
@SpringBootApplication
public class ELSA {

    /**
     * The main method which evaluates the  inputs on the commandline.
     * Commands:
     * end
     * import <path>
     * sign <?>
     * help
     * server
     * @param args The program arguments
     */
    public static void main(final String... args) {
        System.out.println("Welcome to ELSA! Type help to get help");
        while (true) {
            System.out.print("Input: ");
            Document document;
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            String[] input = line.split(" ");
            switch (input[0]) {
                case "help":
                    System.out.println("end               -ends the programm");
                    System.out.println("import <path>     -imports a document or an envelop");
                    System.out.println("sign <?>          -signs a document");
                    System.out.println("server            -starts the server");
                    break;
                case "end":
                    System.exit(0);
                    break;
                case "import":
                    if (input.length > 1) {
                        File file = new File(input[1]);
                        if (file.exists() && file.isFile()) {
                            try {
                                document = new Document(input[1]);
                                System.out.println("import of " + document.getDocumentMetaData().getMetaDocumentTitle() + " successful.");
                            } catch (IOException e) {
                                System.out.println("invalid path");
                            }
                        } else if (file.exists() && file.isDirectory()) {
                            //Enevelop
                        } else {
                            System.out.println("document not found");
                        }
                    } else {
                        System.out.println("no path specified. Use import <path>");
                    }
                    break;
                case "sign":
                    if (input.length > 1) {
                        //Sign the document
                    }
                    break;
                case "server":
                    SpringApplication.run(ELSA.class, args);
                    break;
                default:
                    System.out.println("Unknown command");
            }
        }
    }
}

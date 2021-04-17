package gpse.example;

import gpse.example.domain.Document;
import gpse.example.domain.Envelop;
import gpse.example.domain.SignatureType;
import gpse.example.domain.exceptions.SignatureTypeFromIntegerException;
import org.springframework.boot.SpringApplication;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * The QueryHandler class is responsible for reading the command line arguments and
 * getting the responding actions done.
 */
public class QueryHandler {
    //TODO: !!! NICHT HARDCODED !!! guckt euch mal Apache !CommonsCLI!
    // oder picocli oder args4j an bitte, das hier wird schnell zu komplex
    //TODO: !Unit Tests!


    private static final int DEFAULT_EXIT = 100;

    /**
     * The documentList contains all imported documents.
     * The envelopList contains all imported envelops.
     */
    private final List<Document> documentList;
    private final List<Envelop> envelopList;
    private Scanner scanner;

    public QueryHandler() {
        documentList = new ArrayList<>();
        envelopList = new ArrayList<>();
    }

    /**
     * The query method gets the input from the command line and evaluates it.
     *
     * @param args the programm arguments
     * @return returns the exit value if the query stops.
     */

    public int query(final String... args) {
        scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Input: ");
            String line = getInput();
            String[] input = line.split(" ");
            switch (input[0]) {
                case "help":
                    help();
                    break;
                case "exit":
                    scanner.close();
                    return DEFAULT_EXIT;
                case "import":
                    importDocEnv(input);
                    break;
                case "sign":
                    sign(input);
                    break;
                case "server":
                    SpringApplication.run(ELSA.class, args);
                    break;
                case "list":
                    list();
                    break;
                case "envelop":
                    listEnvelop(input);
                    break;
                case "setSignatureType":
                    setSignatureType(input);
                    break;
                default:
                    System.out.println("Unknown command");
                    break;
            }
        }
    }

    /**
     * Sign the document.
     *
     * @param input the input.
     */
    private void sign(final String[] input) {
        //Sign the document
    }

    /**
     * The importDocEnv method decides whether a single document or
     * an envelop should be imported and calls the responding import methods.
     *
     * @param input the the path(s) of the file(s) to be imported.
     */
    private void importDocEnv(final String[] input) {
        if (input.length > 1) {
            File file = new File(input[1]);
            if (file.exists() && file.isFile() && input.length == 2) {
                importDoc(input);
            } else if (file.isDirectory() || input.length > 2) {
                importEnv(input);
            } else {
                System.out.println("document not found");
            }
        } else {
            System.out.println("no path specified. Use import <path>");
        }

    }

    /**
     * The importEnv method is responsible for creating an envelop
     * from a given path and adding it to the envelop list.
     *
     * @param input the the path(s) of the file(s) to be imported.
     */
    private void importEnv(final String[] input) {
        Envelop envelop;
        try {
            if (input.length > 2) {
                System.out.println("Type in the name of the envelop: ");
                String name = getInput();
                envelop = new Envelop(name, Arrays.asList(input).subList(1, input.length));
                envelopList.add(envelop);
            } else {
                envelop = new Envelop(input[1]);
                envelopList.add(envelop);
            }
        } catch (IOException e) {
            System.out.println("Some paths were invalid.");
        }
    }

    /**
     * The importDoc method is responsible for creating a document
     * from a given path and adding it to the document list.
     *
     * @param input the path(s) of the file(s) to be imported.
     */
    private void importDoc(final String[] input) {
        Document document;
        try {
            document = new Document(input[1], null);
            String title = document.getDocumentMetaData().getMetaDocumentTitle();
            System.out.println("import of " + title + " successful.");
            documentList.add(document);
        } catch (IOException e) {
            System.out.println("invalid path");
        }
    }

    /**
     * The help method prints out the necessary information about
     * how the command line inputs work.
     */
    private void help() {
        System.out.println("exit              -terminates the programm");
        System.out.println("import <path>     -imports a document or an envelop");
        System.out.println("sign <?>          -signs a document");
        System.out.println("server            -starts the server");
        System.out.println("list              -lists all imported documents and envelops");
        System.out.println("envelop <name>    -lists all documents of the specified envelop");
        System.out.println("setSignatureType <name> <value>    -sets the Signature type, value: 0: simple 1: advanced");
    }

    /**
     * The getInput method reads a line from the command line and returns it.
     *
     * @return The String from the command line.
     */
    private String getInput() {
        String line = scanner.nextLine();
        return line;
    }

    private void list() {
        System.out.println("Imported Documents: ");
        for (Document document : documentList) {
            System.out.print(document.getDocumentMetaData().getMetaDocumentTitle() + ".");
            System.out.println(document.getDocumentType());
        }
        System.out.println("Imported Envelops: ");
        for (Envelop envelop : envelopList) {
            System.out.println(envelop.getName());
        }
    }

    /**
     * The listEnvelop method lists all documents of an envelop in order.
     *
     * @param input the inputs which contains the name of the envelop to be listed.
     */
    private void listEnvelop(final String[] input) {
        System.out.println("listing documents of envelop " + input[1]);
        for (Envelop envelop : envelopList) {
            if (envelop.getName().equals(input[1])) {
                for (Document document : envelop) {
                    System.out.println(document.getDocumentMetaData().getMetaDocumentTitle());
                }
            }
        }
    }

    private void setSignatureType(final String[] input) {
        boolean seenDocument = false;
        final int validInputLength = 3;
        if (input.length != validInputLength) {
            System.out.println("Wrong use of: setSignatureType");
            System.out.println("Use: setSignatureType exampleTitle.txt 1");
            return;
        }

        for (Document document : documentList) {
            if ((document.getDocumentTitle() + "." + document.getDocumentType()).equals(input[1])) {
                try {
                    document.setSignatureType(SignatureType.fromInteger(Integer.parseInt(input[2])));
                    seenDocument = true;
                    System.out.printf("Successfully changed SignatureType of %s to %s%n",
                        input[1], document.getSignatureType().toString());
                } catch (SignatureTypeFromIntegerException | NumberFormatException e) {
                    System.out.println("Not a valid SignatureType");
                    System.out.println("Valid: 0 for simple, 1 for advanced");
                    return;
                }
            }
        }
        if (!seenDocument) {
            System.out.printf("The Document %s wasn't found.%n", input[1]);
        }
    }


    public List<Document> getDocumentList() {
        return documentList;
    }

    public List<Envelop> getEnvelopList() {
        return envelopList;
    }
}

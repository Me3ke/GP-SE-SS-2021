package gpse.example;

import gpse.example.domain.*;
import gpse.example.domain.exceptions.SignatureTypeFromIntegerException;
import org.springframework.boot.SpringApplication;

import java.util.*;

/**
 * The QueryHandler class is responsible for reading the command line arguments and
 * getting the responding actions done.
 */
public class QueryHandler {


    private static final int DEFAULT_EXIT = 100;
    private static final String DOCUMENT_NOT_FOUND = "The Document %s wasn't found.%n";
    private static final int INPUT_THREE = 3;
    private static final String NOT_EXISTS = " does not exist.";

    /**
     * The documentList contains all imported documents.
     * The envelopList contains all imported envelops.
     */
    private final List<Document> documentList;
    private final List<Envelope> envelopeList;
    private Scanner scanner;
    private final User hans;
    private final DocumentCreator documentCreator;
    private final List<Signatory> signatories;

    /**
     * the standard constructor for the QueryHandler.
     */
    public QueryHandler() {
        documentList = new ArrayList<>();
        envelopeList = new ArrayList<>();
        signatories = new ArrayList<>();
        documentCreator = new DocumentCreator();
        hans = new User("emailadresse@email.de", "Hans", "Schneider", "1234567898765");
        signatories.add(new Signatory(null, hans));
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
            final String line = getInput();
            final String[] input = line.split("  ");
            switch (input[0]) {
                case "help":
                    help();
                    break;
                case "exit":
                    scanner.close();
                    return DEFAULT_EXIT;
                case "import":
                    importDoc(input);
                    break;
                case "sign":
                    sign(input);
                    break;
                case "server":
                    SpringApplication.run(ELSA.class, args);
                    break;
                case "add":
                    add(input);
                    break;
                case "remove":
                    remove(input);
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
     * Adds one or more documents into an envelop.
     *
     * @param input the command line input containing a name and the paths of the documents.
     */
    private void add(final String... input) {
        if (input.length >= INPUT_THREE) {
            for (final Envelope envelope : envelopeList) {
                if (envelope.getName().equals(input[1])) {
                    final Map<String, List<Signatory>> map = new HashMap<>();
                    final List<String> inputList = Arrays.asList(input).subList(2, input.length);
                    for (final String currentInput : inputList) {
                        map.put(currentInput, signatories);
                    }
                    final Envelope temporaryEnvelope = hans.createNewEnvelope(map, " ");
                    for (final Document document : temporaryEnvelope.getDocumentList()) {
                        envelope.addDocument(document);
                        System.out.println("added " + document.getDocumentTitle() + " to " + envelope.getName());
                    }
                } else {
                    System.out.println(input[1] + NOT_EXISTS);
                }
            }
        } else {
            System.out.println("parameters invalid. Try add <envelop name> <paths>.");
        }
    }

    /**
     * Removes one or more documents from an envelop.
     *
     * @param input the command line input containing a name and the paths of the documents.
     */
    private void remove(final String... input) {
        if (input.length >= INPUT_THREE) {
            for (final Envelope envelope : envelopeList) {
                if (envelope.getName().equals(input[1])) {
                    final List<String> inputList = Arrays.asList(input).subList(2, input.length);
                    for (final String currentInput : inputList) {
                        final List<Document> documentList = new ArrayList<>(envelope.getDocumentList());
                        for (final Document document : documentList) {
                            if (currentInput.equals(document.getDocumentTitle())
                                || currentInput.equals(document.getDocumentTitle()
                                .concat(document.getDocumentType()))) {
                                envelope.removeDocument(envelope.getDocumentList().indexOf(document));
                                System.out.println("removed " + document.getDocumentTitle()
                                    + " from " + envelope.getName());
                            }
                        }
                    }
                } else {
                    System.out.println(input[1] + NOT_EXISTS);
                }
            }
        } else {
            System.out.println("parameters invalid. Try remove <envelop name> <names>.");
        }
    }

    /**
     * Sign the document.
     *
     * @param input the input.
     */
    private void sign(final String... input) {
        /*
        boolean seenDocument = false;
        final int validInputLength = 2;
        if (input.length != validInputLength) {
            System.out.println("Wrong use of: sign");
            System.out.println("Use: sign exampleTitle.txt ");
            return;
        }

        for (final Document document : documentList) {
            if ((document.getDocumentTitle() + "." + document.getDocumentType()).equals(input[1])) {
                seenDocument = true;
                if (document.getSignatureType().equals(SignatureType.SIMPLE_SIGNATURE)) {
                    document.addSignedSignatory(hans.getEmail());
                } else if (document.getSignatureType().equals(SignatureType.ADVANCED_SIGNATURE)) {
                    document.advancedSignature(hans);
                } else {
                    System.out.println("The Document couldn't be signed, because it doesn't have a signature type");
                }
                break;
            }
        }
        if (!seenDocument) {
            System.out.printf(DOCUMENT_NOT_FOUND, input[1]);
        }

         */
    }

    /**
     * The importDoc method creates an envelop from the specified paths.
     *
     * @param input the the path(s) of the file(s) to be imported.
     */
    private void importDoc(final String... input) {
        if (input.length > 1) {
            System.out.print("Type in the name of the envelop: ");
            final String name = getInput();
            final Map<String, List<Signatory>> map = new HashMap<>();
            final List<String> inputList = Arrays.asList(input).subList(1, input.length);
            for (final String currentInput : inputList) {
                map.put(currentInput, signatories);

            }
            final Envelope envelope = hans.createNewEnvelope(map, name);
            envelopeList.add(envelope);
        } else {
            System.out.println("no path specified. Use import <path>");
        }

    }

    /**
     * The help method prints out the necessary information about
     * how the command line inputs work.
     */
    private void help() {
        System.out.println("exit                               -terminates the programm");
        System.out.println("import <path>                      -imports a document or an envelop");
        System.out.println("sign <name>                        -signs a document");
        System.out.println("server                             -starts the server");
        System.out.println("envelop <name>                     -lists all documents of the specified envelop");
        System.out.println("setSignatureType <name> <value>    -sets the Signature type, value: 0: simple 1: advanced");
        System.out.println("add <envelop name> <paths>         -adds documents in an existing envelop.");
        System.out.println("remove <envelop name> <names>      -removes documents from an existing envelop.");
    }

    /**
     * The getInput method reads a line from the command line and returns it.
     *
     * @return The String from the command line.
     */
    private String getInput() {
        final String line = scanner.nextLine();
        return line;
    }

    /**
     * The listEnvelop method lists all documents of an envelop in order.
     *
     * @param input the inputs which contains the name of the envelop to be listed.
     */
    private void listEnvelop(final String... input) {
        if (input.length > 1) {
            System.out.println("listing documents of envelop " + input[1]);
            for (final Envelope envelope : envelopeList) {
                if (envelope.getName().equals(input[1])) {
                    int counter = 0;
                    for (final Document document : envelope) {
                        System.out.println(counter + ". " + document.getDocumentTitle());
                        counter++;
                    }
                }
            }
        } else {
            System.out.println("These envelops exist:");
            for (final Envelope envelope : envelopeList) {
                System.out.println(envelope.getName());
            }
        }
    }

    private void setSignatureType(final String... input) {
        boolean seenDocument = false;
        final int validInputLength = 3;
        if (input.length != validInputLength) {
            System.out.println("Wrong use of: setSignatureType");
            System.out.println("Use: setSignatureType exampleTitle.txt 1");
            return;
        }

        for (final Document document : documentList) {
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
            System.out.printf(DOCUMENT_NOT_FOUND, input[1]);
        }
    }

    public List<Document> getDocumentList() {
        return documentList;
    }

    public List<Envelope> getEnvelopList() {
        return envelopeList;
    }
}

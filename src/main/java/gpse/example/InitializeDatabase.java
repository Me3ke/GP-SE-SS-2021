package gpse.example;


import gpse.example.domain.documents.*;
import gpse.example.domain.envelopes.Envelope;
import gpse.example.domain.envelopes.EnvelopeService;
import gpse.example.domain.exceptions.CreatingFileException;
import gpse.example.domain.exceptions.DocumentNotFoundException;
import gpse.example.domain.signature.SignatoryService;
import gpse.example.domain.users.PersonalData;
import gpse.example.domain.users.PersonalDataService;
import gpse.example.domain.users.User;
import gpse.example.domain.users.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This class initializes the Database with some example data on serverstart.
 */
@Service
public class InitializeDatabase implements InitializingBean {

    private static final String PROGRAM_PATH = "./src/main/resources/Programme.pdf";
    private static final String PLAN_PATH = "./src/main/resources/Essensplan.txt";
    private static final String USERNAME = "hans.schneider@mail.de";
    private static final long ID_THREE = 3L;
    private static final long ID_FOUR = 4L;
    private static final long ID_FIVE = 5L;
    private static final long ID_SIX = 6L;
    private static final long ID_SEVEN = 7L;
    private final UserService userService;
    private final PersonalDataService personalDataService;
    private final DocumentService documentService;
    private final DocumentMetaDataService documentMetaDataService;
    private final EnvelopeService envelopeService;
    private final SignatoryService signatoryService;


    /**
     * The standard constructor for the class initializing the database.
     *
     * @param userService             used for saving user-objects in the database.
     * @param personalDataService     used for saving personalData-objects in the database.
     * @param documentService         used for saving document-objects in the database.
     * @param envelopeService         used for saving envelope-objects in the database.
     * @param documentMetaDataService used for saving documentMetaData-objects in the database.
     * @param signatoryService        used for saving signatory-objects in the database.
     */
    @Autowired
    public InitializeDatabase(final UserService userService, final PersonalDataService personalDataService,
                              final DocumentService documentService, final EnvelopeService envelopeService,
                              final DocumentMetaDataService documentMetaDataService,
                              final SignatoryService signatoryService) {
        this.userService = userService;
        this.personalDataService = personalDataService;
        this.documentService = documentService;
        this.documentMetaDataService = documentMetaDataService;
        this.envelopeService = envelopeService;
        this.signatoryService = signatoryService;
    }

    @Override
    public void afterPropertiesSet() {
        try {
            userService.loadUserByUsername(USERNAME);
        } catch (UsernameNotFoundException ex) {
            final PersonalData personalData = new PersonalData("Berliner Straße", 2, 12312,
                "Liebefeld", "Deutschland", LocalDate.now(), "3213145");
            final User user = userService.createUser(
                USERNAME,
                "{bcrypt}$2y$12$DdtBOd4cDqlvMGXPoNr9L.6YkszYXn364x172BKabx3ucOiYUmTfG",
                "Hans",
                "Schneider",
                personalDataService.savePersonalData(personalData),
                "ROLE_USER"
            );
        }
        final List<Long> documentIDs = new ArrayList<>();
        final List<String> documentPaths = new ArrayList<>();
        documentIDs.add(1L);
        documentPaths.add(PROGRAM_PATH);
        createExampleEnvelope(1, "international congress 2021", documentIDs, documentPaths,
            DocumentState.READ, true, false);
        documentIDs.clear();
        documentPaths.clear();
        documentIDs.add(2L);
        documentIDs.add(ID_THREE);
        documentIDs.add(ID_FOUR);
        documentPaths.add(PROGRAM_PATH);
        documentPaths.add("./src/main/resources/Handout_Kundengespraech.pdf");
        documentPaths.add(PLAN_PATH);
        createExampleEnvelope(2, "Wichtige änderungen am Essensplan", documentIDs,
            documentPaths, DocumentState.READ_AND_SIGNED, true, true);
        documentIDs.clear();
        documentPaths.clear();
        documentIDs.add(ID_FIVE);
        documentPaths.add(PLAN_PATH);
        createExampleEnvelope(ID_THREE, "Pläne für die Weltherrschaft", documentIDs,
            documentPaths, DocumentState.SIGNED, false, true);
        documentIDs.clear();
        documentPaths.clear();
        documentIDs.add(ID_SIX);
        documentIDs.add(ID_SEVEN);
        documentPaths.add(PROGRAM_PATH);
        documentPaths.add("./src/main/resources/Dropbox.pdf");
        createExampleEnvelope(ID_FOUR, "Tutorialpläne", documentIDs,
            documentPaths, DocumentState.READ_AND_SIGNED, true, true);
    }

    private void createExampleEnvelope(final long id, final String name, final List<Long> documentIDs,
                                       final List<String> documentPaths, final DocumentState documentState,
                                       final boolean docsRead, final boolean docsSigned) {
        final User owner = userService.getUser(USERNAME);
        try {
            final Envelope envelope = envelopeService.getEnvelope(id);
            for (int i = 0; i < documentIDs.size(); i++) {
                createExampleDocument(owner, envelope, documentIDs.get(i), documentPaths.get(i),
                    documentState, docsRead, docsSigned);
            }
        } catch (DocumentNotFoundException exception) {
            final Envelope envelope = owner.createNewEnvelope(name);
            for (int i = 0; i < documentIDs.size(); i++) {
                createExampleDocument(owner, envelope, documentIDs.get(i), documentPaths.get(i),
                    documentState, docsRead, docsSigned);
            }
        }
    }

    private void createExampleDocument(final User owner, final Envelope envelope, final long id,
                                       final String path, final DocumentState documentState,
                                       final boolean read, final boolean signed) {
        try {
            documentService.getDocument(id);
        } catch (DocumentNotFoundException exception) {
            final DocumentCreator creator = new DocumentCreator();
            final DocumentPutRequest documentPutRequestRequest = new DocumentPutRequest();
            documentPutRequestRequest.setPath(path);
            try {
                final List<User> signatories = new ArrayList<>();
                final List<User> readers = new ArrayList<>();
                if (signed) {
                    signatories.add(owner);
                }
                if (read) {
                    readers.add(owner);
                }
                final Document document = creator.createDocument(documentPutRequestRequest, USERNAME,
                    signatories, readers);
                if (read) {
                    for (int i = 0; i < document.getReaders().size(); i++) {
                        document.getReaders().get(i).setStatus(true);
                    }
                }
                if (signed) {
                    for (int i = 0; i < document.getSignatories().size(); i++) {
                        document.getSignatories().get(i).setStatus(true);
                    }
                }
                try {
                    document.setState(documentState);
                } catch (IllegalStateException stateException) {
                    document.setState(DocumentState.OPEN);
                }
                signatoryService.saveSignatories(document.getSignatories());
                signatoryService.saveSignatories(document.getReaders());
                documentMetaDataService.saveDocumentMetaData(document.getDocumentMetaData());
                documentService.addDocument(document);
                envelope.addDocument(document);
                envelopeService.saveEnvelope(envelope);
            } catch (CreatingFileException e) {
                envelopeService.saveEnvelope(envelope);
                e.printStackTrace();
            } catch (IOException e) {
                envelopeService.saveEnvelope(envelope);
                e.printStackTrace();
            }
        }
    }

}

package gpse.example;

import gpse.example.domain.*;
import gpse.example.domain.documents.*;
import gpse.example.domain.envelopes.EnvelopeService;
import gpse.example.domain.exceptions.CreatingFileException;
import gpse.example.domain.exceptions.DocumentNotFoundException;
import gpse.example.domain.users.PersonalData;
import gpse.example.domain.users.PersonalDataService;
import gpse.example.domain.users.User;
import gpse.example.domain.users.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;

/**
 * This class starts the Database on serverstart.
 */
@Service
public class InitializeDatabase implements InitializingBean {

    private final UserService userService;
    private final PersonalDataService personalDataService;
    private final DocumentService documentService;
    private final DocumentMetaDataService documentMetaDataService;
    private final EnvelopeService envelopeService;


    @Autowired
    public InitializeDatabase(final UserService userService, final PersonalDataService personalDataService,
                              final DocumentService documentService, final EnvelopeService envelopeService,
                              final DocumentMetaDataService documentMetaDataService) {
        this.userService = userService;
        this.personalDataService = personalDataService;
        this.documentService = documentService;
        this.documentMetaDataService = documentMetaDataService;
        this.envelopeService = envelopeService;
    }

    @Override
    public void afterPropertiesSet() throws DocumentNotFoundException {
        String username = "hans.schneider@mail.de";
        try {
            userService.loadUserByUsername(username);
            /*
            if (document == null) {
                DocumentCreator creator = new DocumentCreator();
                DocumentPutRequest documentPutRequestRequest = new DocumentPutRequest();
                documentPutRequestRequest.setPath("/home/tobias/gpse-Projekt/Dokument.pdf");
                try {
                    document = creator.createDocument(documentPutRequestRequest, username, null, null);
                    documentMetaDataService.saveDocumentMetaData(document.getDocumentMetaData());
                    documentService.addDocument(document);
                } catch (CreatingFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
             */
        } catch (UsernameNotFoundException ex) {
            final PersonalData personalData = new PersonalData("Berliner Straße", 2, 12312,
                "Liebefeld", "Deutschland", LocalDate.now(), 32131245);
            final User user = userService.createUser(
                    username,
                    "{bcrypt}$2y$12$DdtBOd4cDqlvMGXPoNr9L.6YkszYXn364x172BKabx3ucOiYUmTfG",
                    "Hans",
                    "Schneider",
                    personalDataService.savePersonalData(personalData),
                    "ROLE_USER"
            );

        }
    }
}

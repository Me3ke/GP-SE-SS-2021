package gpse.example.domain.email;

/**
 * Container of the basic systemintern EmailTemplates.
 */
public final class BasicHtmlTemplates {


    private static final String CLOSE_BODY_TAG = "  </body>";

    private static final String P_LINK_URL_P = "\t\t<p>Link-URL: [Link]</p>\n";

    private static final String P_TO_SIGN_THE_DOCUMENT_DOCUMENT_TITLE_P =
        "\t\t<p>to sign the document [DocumentTitle].</p>\n";

    private static final String P_YOU_CAN_FIND_THE_DOCUMENT_A_HREF_LINK_HERE_A_P =
        "\t\t<p>You can find the document <a href=\"[Link]\">here</a>.</p>\n";

    private static final String GREETINGS_PERSONAL_ENGLISH =
        "\t\t<p>Hello [FirstNameReciever] [LastNameReciever],</p>\n";

    private static final String P_DEADLINE_P = "\t\t<p>von 24 Stunden.</p>\n";

    private static final String GREETINGS_PERSONAL_GERMAN =
        "    <p>Guten Tag [FirstNameReciever] [LastNameReciever],</p>\n";

    private static final String P_YOU_ARE_INVITED_BY_FIRST_NAME_OWNER_LAST_NAME_OWNER_P =
        "\t\t<p>you are invited by [FirstNameOwner] [LastNameOwner]</p>\n";

    private static final String GREETINGS_ENGLISH = "\t\t<p>Hello,</p>\n";

    private static final String P_SIE_FINDEN_DAS_DOKUMENT_A_HREF_LINK_HIER_A_P =
        "\t\t<p>Sie finden das Dokument <a href=\"[Link]\">hier</a>.</p>\n";

    private static final String NEWLINE_FROM_TEXTFILE = "\n";

    private static final String HTML_HEAD = "<!DOCTYPE html>\n"
        + "<html lang = \"de\">\n"
        + NEWLINE_FROM_TEXTFILE
        + "\t<head>\n"
        + NEWLINE_FROM_TEXTFILE
        + "\t\t<meta charset = \"utf8\">\n"
        + "\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" >\n"
        + "  </head>\n"
        + NEWLINE_FROM_TEXTFILE
        + "  <body>\n";

    private static final String P_DOKUMENTS_DOCUMENT_TITLE_EINGETRAGEN_P =
        "\t\t<p>Dokuments [DocumentTitle] eingetragen.</p>\n";

    private static final String P_SIE_WURDEN_VON_FIRST_NAME_OWNER_LAST_NAME_OWNER_ZUM_SIGNIEREN_DES_P =
        "\t\t<p>Sie wurden von [FirstNameOwner] [LastNameOwner] zum Signieren des</p>\n";

    private static final String GREETINGS_GERMAN = "<p>Guten Tag,</p>\n";

    private static final String NEWLINE = "\t\t<br>\n";

    private static final String P_I_ENGLISH_VERSION_BELOW_I_P = "\t\t<p> <i>English version below</i> </p>\n";

    /**
     * Template for sending invitationEmail to Guest Signatories.
     * needs: FirstNameOwner, LastNameOwner, DocumentTitle, Link
     * Link -> to Documentview
     */
    public static final String GUEST_INVITATION_TEMPLATE = HTML_HEAD
        + P_I_ENGLISH_VERSION_BELOW_I_P
        + NEWLINE
        + GREETINGS_GERMAN
        + P_SIE_WURDEN_VON_FIRST_NAME_OWNER_LAST_NAME_OWNER_ZUM_SIGNIEREN_DES_P
        + P_DOKUMENTS_DOCUMENT_TITLE_EINGETRAGEN_P
        + P_SIE_FINDEN_DAS_DOKUMENT_A_HREF_LINK_HIER_A_P
        + NEWLINE
        + NEWLINE
        + GREETINGS_ENGLISH
        + P_YOU_ARE_INVITED_BY_FIRST_NAME_OWNER_LAST_NAME_OWNER_P
        + P_TO_SIGN_THE_DOCUMENT_DOCUMENT_TITLE_P
        + P_YOU_CAN_FIND_THE_DOCUMENT_A_HREF_LINK_HERE_A_P
        + NEWLINE
        + NEWLINE
        + P_LINK_URL_P
        + CLOSE_BODY_TAG;

    /**
     * Template for sending AdvancedGuestInvitation, because Guest cant sign advance they can register.
     * needs: FirstNameOwner, LastNameOwner, DocumentTitle, Link
     * Link to register page
     */
    public static final String ADVANCED_GUEST_INVITATION_TEMPLATE = HTML_HEAD
        + P_I_ENGLISH_VERSION_BELOW_I_P
        + NEWLINE
        + GREETINGS_GERMAN
        + "\t\t<p>Sie wurden von [FirstNameOwner] [LastNameOwner] f&uumlr eine erweiterte</p>\n"
        + "\t\t<p>Signatur des Dokuments [DocumentTitle] eingetragen.</p>\n"
        + "\t\t<p>F&uumlr eine erweiterte Signatur ist eine Registrierung erforderlich.</p>\n"
        + "\t\t<p>Sie k&oumlnnen sich <a href=\"[Link]\">hier</a> registrieren.</p>\n"
        + NEWLINE
        + NEWLINE
        + GREETINGS_ENGLISH
        + P_YOU_ARE_INVITED_BY_FIRST_NAME_OWNER_LAST_NAME_OWNER_P
        + "\t\t<p>to sign the document [DocumentTitle] with an advanced signature.</p>\n"
        + "\t\t<p>For an advanced signature you need to register.</p>\n"
        + "\t\t<p>You can register <a href=\"[Link]\">here</a>.</p>\n"
        + NEWLINE
        + NEWLINE
        + P_LINK_URL_P
        + CLOSE_BODY_TAG;

    /**
     * Template for sending SignatureReminder.
     * needs: DocumentTitle, EndDate, Link
     * Link to DocumentView
     */
    public static final String REMINDER_TEMPLATE = HTML_HEAD
        + P_I_ENGLISH_VERSION_BELOW_I_P
        + NEWLINE
        + GREETINGS_GERMAN
        + "\t\t<p>der Signaturprozess zu Dokument [DocumentTitle] endet am [EndDate].</p>\n"
        + "\t\t<p>Bitte denken Sie daran das Dokument rechtzeitig zu Signieren.</p>\n"
        + NEWLINE
        + P_SIE_FINDEN_DAS_DOKUMENT_A_HREF_LINK_HIER_A_P
        + NEWLINE
        + NEWLINE
        + GREETINGS_ENGLISH
        + "\t\t<p>the signature process of the document [DocumentTitle] ends at [EndDate].</p>\n"
        + "\t\t<p>Don't forget to sign befor the process ends.</p>\n"
        + NEWLINE
        + "\t\t<p>You find the document <a href=\"[Link]\">here</a>.</p>\n"
        + NEWLINE
        + NEWLINE
        + P_LINK_URL_P
        + CLOSE_BODY_TAG;

    /**
     * Template for sending ConfirmationEMail.
     * needs: FirstNameReciever, LastNameReciever, Link
     * Link with registration token
     */
    public static final String CONFIRMATION_TEMPLATE = HTML_HEAD
        + P_I_ENGLISH_VERSION_BELOW_I_P
        + NEWLINE
        + GREETINGS_PERSONAL_GERMAN
        + "\t\t<p>Sie k&oumlnnen Ihre Email-Adresse <a href=\"[Link]\">hier</a> best&aumltigen.</p>\n"
        + "\t\t<p>Beachten Sie bitte die Eingeschr&aumlnkte G&uumlltigkeit ihres Best&aumltigungslinks</p>\n"
        + P_DEADLINE_P
        + NEWLINE
        + NEWLINE
        + GREETINGS_PERSONAL_ENGLISH
        + "\t\t<p>please confirm your email address <a href=\"[Link]\">here</a>.</p>\n"
        + "\t\t<p>Please keep in mind that the validity of the link is limited to 24 hours.</p>\n"
        + P_DEADLINE_P
        + NEWLINE
        + NEWLINE
        + P_LINK_URL_P
        + CLOSE_BODY_TAG;

    /**
     * Template for sending An information about registration of untrusted email.
     * needs:   FirstNameReciever, LastNameReciever -> Admin
     *          FirstNameOwner, LastNameOwner, RequestingEmail -> newUser
     *          Link -> to Admin Settings validate user
     *          RequestingEmail, Link -> fields for internal use only
     */
    public static final String ADMIN_VALIDATION_TEMPLATE = HTML_HEAD
        + P_I_ENGLISH_VERSION_BELOW_I_P
        + NEWLINE
        + GREETINGS_PERSONAL_GERMAN
        + "\t\t<p>[FirstNameOwner] [LastNameOwner] m&oumlchte sich mit der Email-Adresse </p>\n"
        + "\t\t<p>[RequestingEmail] Registrieren.</p>\n"
        + "\t\t<p>Sie k&oumlnnen die Anfrage <a href=\"[Link]\">hier</a> best&aumltigen.</p>\n"
        + NEWLINE
        + NEWLINE
        + GREETINGS_PERSONAL_ENGLISH
        + "\t\t<p>[FirstNameOwner] [LastNameOwner] wants to register with the </p>\n"
        + "\t\t<p>email address [RequestingEmail].</p>\n"
        + "\t\t<p>You can confirm the registration <a href=\"[Link]\">here</a>.</p>\n"
        + NEWLINE
        + NEWLINE
        + P_LINK_URL_P
        + CLOSE_BODY_TAG;

    /**
     * Template for sending an new Version Information.
     * needs: DocumentTitle, Link
     * Link to documentView
     */
    public static final String NEW_VERSION_TEMPLATE = HTML_HEAD
        + P_I_ENGLISH_VERSION_BELOW_I_P
        + NEWLINE
        + GREETINGS_GERMAN
        + "\t\t<p>es wurde eine neue Version des Dokuments [DocumentTitle] Hochgeladen.</p>\n"
        + "\t\t<p>Alle Signaturen wurden invalidiert!!</p>\n"
        + NEWLINE
        + "\t\t<p>Sie finden die neue Version <a href=\"[Link]\">hier</a>.</p>\n"
        + NEWLINE
        + NEWLINE
        + GREETINGS_ENGLISH
        + "\t\t<p>a new version of the document [DocumentTitle] was uploaded.</p>\n"
        + "\t\t<p>All signatures are invalidated.</p>\n"
        + NEWLINE
        + "\t\t<p>You find the new version <a href=\"[Link]\">here</a>.</p>\n"
        + NEWLINE
        + NEWLINE
        + P_LINK_URL_P
        + CLOSE_BODY_TAG;

    /**
     * Template for sending an Information to Owner when process is finished.
     * needs: FirstNameReciever, LastNameReciever, DocumentTitle, Link
     * Link to protcolView
     */
    public static final String PROCESS_FINISHED_TEMPLATE = HTML_HEAD
        + "    <p> <i>English version below</i> </p>\n"
        + "    <br>\n"
        + "\t\t<p>Guten Tag [FirstNameReciever] [LastNameReciever],</p>\n"
        + "\t\t<p>der Signaturprozess des Dokuments [DocumentTitle] ist abgeschlossen.</p>\n"
        + "\t\t<p>Sie k&oumlnnen das Protokoll <a href=\"[Link]\">hier</a> Herunterladen.</p>\n"
        + NEWLINE
        + NEWLINE
        + GREETINGS_PERSONAL_ENGLISH
        + "\t\t<p>the Signaturprozess of the document [DocumentTitle] is finished.</p>\n"
        + "\t\t<p>You can download the protocol <a href=\"[Link]\">here</a>.</p>\n"
        + NEWLINE
        + NEWLINE
        + P_LINK_URL_P
        + CLOSE_BODY_TAG;

    /**
     * Template for sending a Signature Invitation.
     * needs: FirstNameReciever, LastNameReciever, FirstNameOwner, LastNameOwner, DocumentTitle, Link
     * Link to documentView
     */
    public static final String SIGNATURE_INVITATION_TEMPLATE = HTML_HEAD
        + P_I_ENGLISH_VERSION_BELOW_I_P
        + NEWLINE
        + GREETINGS_PERSONAL_GERMAN
        + P_SIE_WURDEN_VON_FIRST_NAME_OWNER_LAST_NAME_OWNER_ZUM_SIGNIEREN_DES_P
        + P_DOKUMENTS_DOCUMENT_TITLE_EINGETRAGEN_P
        + P_SIE_FINDEN_DAS_DOKUMENT_A_HREF_LINK_HIER_A_P
        + NEWLINE
        + NEWLINE
        + GREETINGS_PERSONAL_ENGLISH
        + P_YOU_ARE_INVITED_BY_FIRST_NAME_OWNER_LAST_NAME_OWNER_P
        + P_TO_SIGN_THE_DOCUMENT_DOCUMENT_TITLE_P
        + P_YOU_CAN_FIND_THE_DOCUMENT_A_HREF_LINK_HERE_A_P
        + NEWLINE
        + NEWLINE
        + P_LINK_URL_P
        + CLOSE_BODY_TAG;

    /**
     * Template for ResetPasswordEmails.
     * needs: FirstNameReciever, LastNameReciever, Link
     * Link for password Reset
     */
    public static final String RESET_PASSWORD_TEMPLATE = HTML_HEAD
        + P_I_ENGLISH_VERSION_BELOW_I_P
        + NEWLINE
        + GREETINGS_PERSONAL_GERMAN
        + "\t\t<p>Um Ihr Password zur&uumlck zu setzen klicken sie <a href=\"[Link]\">hier</a>.</p>\n"
        + NEWLINE
        + "\t\t<p>Falls Sie gerade nicht versucht haben Ihr Passwort zur&uumlck zusetzen,</p>\n"
        + "\t\t<p>ist Ihr ELSA-Account oder Ihr Email-Konto m&oumlglicherweise gehackt.</p>\n"
        + "\t\t<p>In diesem Fall kontaktieren sie Ihren Systemadministrator.</p>\n"
        + NEWLINE
        + NEWLINE
        + "\t\t<p>Hello [RecieverFirstName] [RecieverLastName],</p>\n"
        + "\t\t<p>you can reset your password <a href=\"[Link]\">here</a>.</p>\n"
        + NEWLINE
        + "\t\t<p>If you did not try to reset your password, your ELSA-account or </p>\n"
        + "\t\t<p>your email-account is possibly hacked.</p>\n"
        + "\t\t<p>In this case contact your system administrator</p>\n"
        + NEWLINE
        + NEWLINE
        + P_LINK_URL_P
        + CLOSE_BODY_TAG;

    private BasicHtmlTemplates() {

    }
}

package gpse.example.domain.email.trusteddomain;

/**
 * The handler responsible for sending the current settings of the GET request to
 * the frontend.
 */
public class DomainSettingsGetResponse {
    private final String host;
    private final int port;
    private final String username;
    private final boolean mailSMTPAuth;
    private final boolean mailSMTPStartTLSEnable;

    /**
     * Constructor of DomainSettingsGetResponse.
     * @param host the host server
     * @param port the port
     * @param username the username
     * @param mailSMTPAuth activate SMTPAuth
     * @param mailSMTPStartTLSEnable activate StartTls
     */
    public DomainSettingsGetResponse(final String host, final int port, final String username,
                                     final boolean mailSMTPAuth, final boolean mailSMTPStartTLSEnable) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.mailSMTPAuth = mailSMTPAuth;
        this.mailSMTPStartTLSEnable = mailSMTPStartTLSEnable;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public boolean isMailSMTPAuth() {
        return mailSMTPAuth;
    }

    public boolean isMailSMTPStartTLSEnable() {
        return mailSMTPStartTLSEnable;
    }
}

package gpse.example.util.email.trustedDomain;

/**
 * The handler responsible for sending the current settings of the GET request to
 * the frontend.
 */
public class DomainSettingsGetResponse {
    private final String host;
    private final int port;
    private final String username;
    private final boolean mailSMPTAuth;
    private final boolean mailSMTPStartTLSEnable;


    public DomainSettingsGetResponse(String host, int port, String username, boolean mailSMPTAuth, boolean mailSMTPStartTLSEnable) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.mailSMPTAuth = mailSMPTAuth;
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

    public boolean isMailSMPTAuth() {
        return mailSMPTAuth;
    }

    public boolean isMailSMTPStartTLSEnable() {
        return mailSMTPStartTLSEnable;
    }
}

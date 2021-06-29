package gpse.example.util.email.trustedDomain;

/**
 * The handler responsible for sending the current settings of the GET request to
 * the frontend.
 */
public class DomainSettingsGetResponse {
    private final String host;
    private final int port;
    private final String username;
    private final boolean mailSMPTOut;
    private final boolean mailSMTPStartTLSEnable;


    public DomainSettingsGetResponse(String host, int port, String username, boolean mailSMPTOut, boolean mailSMTPStartTLSEnable) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.mailSMPTOut = mailSMPTOut;
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

    public boolean isMailSMPTOut() {
        return mailSMPTOut;
    }

    public boolean isMailSMTPStartTLSEnable() {
        return mailSMTPStartTLSEnable;
    }
}

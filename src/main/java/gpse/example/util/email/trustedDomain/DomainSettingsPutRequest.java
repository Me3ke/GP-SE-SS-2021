package gpse.example.util.email.trustedDomain;

/**
 * The handler responsible for sending the settings of the PUT request to
 * the backend.
 */
public class DomainSettingsPutRequest extends DomainSettingsGetResponse {

    private final String password;

    public DomainSettingsPutRequest(String host, int port, String username, boolean mailSMPTOut,
                                    boolean mailSMTPStartTLSEnable, String password) {
        super(host, port, username, mailSMPTOut, mailSMTPStartTLSEnable);
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
}

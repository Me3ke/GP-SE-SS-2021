package gpse.example.domain.email.trusteddomain;

/**
 * The handler responsible for sending the settings of the PUT request to
 * the backend.
 */
public class DomainSettingsPutRequest extends DomainSettingsGetResponse {

    private final String password;

    public DomainSettingsPutRequest(final String host, final int port, final String username,
                                    final boolean mailSMTPAuth, final boolean mailSMTPStartTLSEnable,
                                    final String password) {
        super(host, port, username, mailSMTPAuth, mailSMTPStartTLSEnable);
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
}

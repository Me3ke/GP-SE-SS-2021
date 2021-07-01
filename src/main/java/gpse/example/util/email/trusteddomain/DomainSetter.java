package gpse.example.util.email.trusteddomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Domain Setter Entity stores Domain Settings.
 */
@Entity
public class DomainSetter {
    @Column
    private String host;
    @Column
    private int port;
    @Id
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private boolean mailSMTPAuth;
    @Column
    private boolean mailSMTPStartTLSEnable;
    @Column
    private String trustedMailDomain;

    /**
     * Standard constructor.
     * @param host the host
     * @param port the port
     * @param username the username
     * @param password the password
     * @param mailSMTPAuth activate SMTPAuth
     * @param mailSMTPStartTLSEnable activate StartTLS
     * @param trustedMailDomain the trusted mail Domain
     */
    public DomainSetter(String host, int port, String username, String password,
                        boolean mailSMTPAuth, boolean mailSMTPStartTLSEnable, String trustedMailDomain) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.mailSMTPAuth = mailSMTPAuth;
        this.mailSMTPStartTLSEnable = mailSMTPStartTLSEnable;
        this.trustedMailDomain = trustedMailDomain;
    }

    public DomainSetter() {

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

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMailSMTPAuth(boolean mailSMPTOut) {
        this.mailSMTPAuth = mailSMPTOut;
    }

    public void setMailSMTPStartTLSEnable(boolean mailSMTPStartTLSEnable) {
        this.mailSMTPStartTLSEnable = mailSMTPStartTLSEnable;
    }

    public String getPassword() {
        return password;
    }

    public String getTrustedMailDomain() {
        return trustedMailDomain;
    }

    public void setTrustedMailDomain(String trustedMailDomain) {
        this.trustedMailDomain = trustedMailDomain;
    }
}

package gpse.example.domain.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.samstevens.totp.code.*;
import dev.samstevens.totp.exceptions.CodeGenerationException;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;

import javax.persistence.*;
import java.io.Serializable;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * The class responsible for storing the security settings.
 */
@Entity
public class SecuritySettings implements Serializable {

    private static final long serialVersionUID = -8161342821150699358L;
    private static final int SECRET_GENERATOR_NUMBER = 64;
    private static final int CODE_DIGIT_NUMBER = 6;
    private static final int TIME_UNTIL_EXPIRED = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private boolean seenByAdmin;

    @Column
    // false: user has not had a first login yet; true: user has had a first login
    private boolean firstLogin;

    @ElementCollection
    @Column(columnDefinition = "LONGTEXT")
    @JsonIgnore
    private List<String> archivedPublicKeys = new ArrayList<>();

    @Lob
    private String publicKey;

    @Column
    private String secret;

    @Column
    private boolean twoFactorLogin;

    public SecuritySettings() {
        this.firstLogin = false;
        this.twoFactorLogin = false;
    }

    public void generateSecret() {
        final SecretGenerator secretGenerator = new DefaultSecretGenerator(SECRET_GENERATOR_NUMBER);
        this.secret = secretGenerator.generate();
    }

    /**
     * The method used to generate a QR-code with the username and the secret.
     *
     * @param username is a part of the QR-code generating process
     * @return the QR-Code in form of a byte array
     * @throws QrGenerationException Gets thrown, if the username is incorrect
     */
    public byte[] generateQRCode(final String username) throws QrGenerationException {
        final QrData data = new QrData.Builder()
            .label(username)
            .secret(secret)
            .issuer("ELSA")
            .algorithm(HashingAlgorithm.SHA1)
            .digits(CODE_DIGIT_NUMBER)
            .period(TIME_UNTIL_EXPIRED)
            .build();
        final QrGenerator generator = new ZxingPngQrGenerator();
        return generator.generate(data);
    }

    /**
     * the Method used to verify a given 2-Fac-Auth code.
     *
     * @param code the given code
     * @return true if code is valid, else false.
     */
    public boolean verifyCode(final String code) throws CodeGenerationException {
        final TimeProvider timeProvider = new SystemTimeProvider();
        final CodeGenerator codeGenerator = new DefaultCodeGenerator();
        final DefaultCodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);

        return verifier.isValidCode(this.secret, code);
    }

    public long getId() {
        return id;
    }

    public String getSecret() {
        return secret;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public boolean isTwoFactorLogin() {
        return twoFactorLogin;
    }

    public void setTwoFactorLogin(final boolean twoFactorLogin) {
        this.twoFactorLogin = twoFactorLogin;
    }

    public boolean isSeenByAdmin() {
        return seenByAdmin;
    }

    public void setToSeenByAdmin() {
        this.seenByAdmin = true;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(final boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public List<String> getArchivedPublicKeys() {
        return archivedPublicKeys;
    }

    public void setArchivedPublicKeys(final List<String> archivedPublicKeys) {
        this.archivedPublicKeys = archivedPublicKeys;
    }

    public void setPublicKey(final String publicKey) {
        this.publicKey = publicKey;
    }
}

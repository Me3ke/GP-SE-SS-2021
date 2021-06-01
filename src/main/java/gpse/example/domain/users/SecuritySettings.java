package gpse.example.domain.users;

import dev.samstevens.totp.code.*;
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
    private PublicKey publicKey;

    @Column
    private String secret;

    public void generateSecret() {
        final SecretGenerator secretGenerator = new DefaultSecretGenerator(SECRET_GENERATOR_NUMBER);
        this.secret = secretGenerator.generate();
    }

    /**
     * The method used to generate a QR-code with the username and the secret.
     * @param username is a part of the QR-code generating process
     * @return the QR-Code in form of a byte array
     * @throws QrGenerationException Gets thrown, if the username is incorrect
     */
    public byte[] generateQRCode(final String username) throws QrGenerationException {
        final QrData data = new QrData.Builder()
                .label(username)
                .secret(secret)
                .issuer("ELSA")
                .algorithm(HashingAlgorithm.SHA256)
                .digits(CODE_DIGIT_NUMBER)
                .period(TIME_UNTIL_EXPIRED)
                .build();
        final QrGenerator generator = new ZxingPngQrGenerator();
        return generator.generate(data);
    }

    /**
     * the Method used to verify a given 2-Fac-Auth code.
     * @param code the given code
     * @return true if code is valid, else false.
     */
    public boolean verifyCode(final String code) {
        final TimeProvider timeProvider = new SystemTimeProvider();
        final CodeGenerator codeGenerator = new DefaultCodeGenerator();
        final CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
        return verifier.isValidCode(secret, code);
    }

    public long getId() {
        return id;
    }
    public String getSecret() {
        return secret;
    }
    public PublicKey getPublicKey() {
        return publicKey;
    }
}

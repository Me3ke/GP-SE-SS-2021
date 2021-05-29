package gpse.example.domain.users;

import dev.samstevens.totp.code.HashingAlgorithm;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;

import javax.persistence.*;
import java.security.PublicKey;

/**
 * The class responsible for storing the security settings
 */
@Entity
public class SecuritySettings {

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
        SecretGenerator secretGenerator = new DefaultSecretGenerator(SECRET_GENERATOR_NUMBER);
        this.secret = secretGenerator.generate();
    }

    /**
     * The method used to generate a QR-code with the username and the secret
     * @param username is a part of the QR-code generating process
     * @return the QR-Code in form of a byte array
     * @throws QrGenerationException Gets thrown, if the username is incorrect
     */
    public byte[] generateQRCode(String username) throws QrGenerationException {
        QrData data = new QrData.Builder()
                .label(username)
                .secret(secret)
                .issuer("ELSA")
                .algorithm(HashingAlgorithm.SHA256)
                .digits(CODE_DIGIT_NUMBER)
                .period(TIME_UNTIL_EXPIRED)
                .build();
        QrGenerator generator = new ZxingPngQrGenerator();
        return generator.generate(data);
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

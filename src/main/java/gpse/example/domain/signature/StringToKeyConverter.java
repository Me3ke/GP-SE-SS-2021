package gpse.example.domain.signature;

import org.apache.tomcat.util.codec.binary.Base64;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * The class responsible for converting a public key from String to PublicKey format.
 */
public class StringToKeyConverter {

    public StringToKeyConverter() {
    }

    /**
     * Method, which converts a public key from a String format into a PublicKey format.
     * @param keyToConvert
     * @return the converted public key in the PublicKey format
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public PublicKey convertString(String keyToConvert) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String publicKeyPemStr = keyToConvert;
        publicKeyPemStr = publicKeyPemStr.replace("publicKey", "");
        publicKeyPemStr = publicKeyPemStr.replace(":", "");
        publicKeyPemStr = publicKeyPemStr.replace("{", "");
        publicKeyPemStr = publicKeyPemStr.replace("}", "");
        publicKeyPemStr = publicKeyPemStr.replace("-----BEGIN PUBLIC KEY-----", "");
        publicKeyPemStr = publicKeyPemStr.replace("-----END PUBLIC KEY-----", "");
        byte[] encoded = Base64.decodeBase64(publicKeyPemStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }
}

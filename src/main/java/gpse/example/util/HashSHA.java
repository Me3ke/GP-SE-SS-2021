package gpse.example.util;


import gpse.example.domain.security.JwtAuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This is class executes the SHA algorithm for a given String.
 *
 * @author Jan Kronsbein & Alexander Heide
 * @since 04-13-2021
 */
public class HashSHA implements HashFunction {
    private static final int BASE_SIXTEEN = 16;
    private static final int HASH_LENGTH = 32;
    private static final String SHA_VERSION = "SHA-512";
    private static final Logger LOG = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    @Override
    public String computeHash(final String input) {
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance(SHA_VERSION);
            final byte[] byteCode = messageDigest.digest(input.getBytes());
            final BigInteger bigInteger = new BigInteger(1, byteCode);
            String hashText = bigInteger.toString(BASE_SIXTEEN);
            while (hashText.length() < HASH_LENGTH) {
                final StringBuilder sb = new StringBuilder();
                sb.append(0);
                sb.append(hashText);
                hashText = sb.toString();
            }
            return hashText;
        } catch (NoSuchAlgorithmException e) {
            LOG.debug("error with the SHA Algorithm", e);
            return null;
        }
    }
}

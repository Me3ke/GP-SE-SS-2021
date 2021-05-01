package gpse.example.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {



    @Nested
    public class KeyPairTests {

        @Test
        public void testAddedKeyPair() throws NoSuchAlgorithmException {
            User hans = new User("aa", "jd", " ", "jko");
            String path = "hehehe";
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair keys = generator.generateKeyPair();
            PublicKey key = keys.getPublic();
            hans.addKeyPair(path, key);
            Assertions.assertTrue(hans.getKeys().get(0).getPublicKey().equals(key)
                && hans.getKeys().get(0).getPrivateKey().equals(path));
        }

        @Test
        public void testchangedKeyPair() throws NoSuchAlgorithmException {
            User hans = new User("aaa", "jda", " a", "jkoa");
            String path1 = "hehehea";
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair keys = generator.generateKeyPair();
            PublicKey key1 = keys.getPublic();
            hans.addKeyPair(path1, key1);
            keys = generator.generateKeyPair();
            PublicKey key2 = keys.getPublic();
            String path2 = "bhkas";
            hans.addKeyPair(path2, key2);
            hans.changeActiveKeyPair(0);
            Assertions.assertTrue(hans.getKeys().get(0).getPublicKey().equals(key1)
                && hans.getKeys().get(0).getPrivateKey().equals(path1));
        }
    }
}

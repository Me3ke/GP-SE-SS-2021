package gpse.example.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class HashSHATest {
    HashSHA hashSHA = new HashSHA();

    @Test
    public void testSameHash() {
        String testHash = hashSHA.computeHash("alex");
        Assertions.assertThat
                (hashSHA.computeHash("alex")).
                isEqualTo(testHash);
    }
    @Test
    public void testDifferentHash() {
        String testHash = hashSHA.computeHash("alex");
        Assertions.assertThat
                (hashSHA.computeHash("jan")).isNotEqualTo(testHash);
    }
    @Test
    public void testWithExternalHash() {
        // Source for external hash: https://emn178.github.io/online-tools/sha512.html
        String external = "35f319ca1dfc9689f5a33631c8f93ed7c3120ee7afa05b1672c7df7b71f63a6753def5fd3ac9db2eaf90ccab6bff31a486b51c7095ff958d228102b84efd7736";
        Assertions.assertThat(hashSHA.computeHash("alex")).isEqualTo(external);
    }
}
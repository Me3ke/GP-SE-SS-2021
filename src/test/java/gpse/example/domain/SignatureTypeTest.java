package gpse.example.domain;

import gpse.example.domain.exceptions.SignatureTypeFromIntegerException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SignatureTypeTest {

    SignatureType noSignature;
    SignatureType simpleSignature;
    SignatureType advancedSignature;

    @Test
    void testToInteger() {
        assertEquals(-1, (int) noSignature.toInteger());
        assertEquals((int) simpleSignature.toInteger(), 0);
        assertEquals((int) advancedSignature.toInteger(), 1);
    }

    @Test
    void testFromInteger() {
        try {
            assertSame(SignatureType.fromInteger(-1), noSignature);
            assertNotSame(SignatureType.fromInteger(-1), simpleSignature);
            assertNotSame(SignatureType.fromInteger(-1), advancedSignature);
            assertSame(SignatureType.fromInteger(0), simpleSignature);
            assertSame(SignatureType.fromInteger(1), advancedSignature);
        } catch (SignatureTypeFromIntegerException e) {
            Assertions.fail();
        }
            assertThrows(SignatureTypeFromIntegerException.class, () ->
                SignatureType.fromInteger(2));
    }

    @Test
    void testValueOf() {
        assertSame(SignatureType.valueOf("NO_SIGNATURE"), noSignature);
        assertSame(SignatureType.valueOf("SIMPLE_SIGNATURE"), simpleSignature);
        assertSame(SignatureType.valueOf("ADVANCED_SIGNATURE"), advancedSignature);
    }

    @BeforeEach
    void setUp() {
        noSignature = SignatureType.NO_SIGNATURE;
        simpleSignature = SignatureType.SIMPLE_SIGNATURE;
        advancedSignature = SignatureType.ADVANCED_SIGNATURE;
    }

    @AfterEach
    void tearDown() {
        noSignature = null;
        simpleSignature = null;
        advancedSignature = null;
    }

}
package by.nhorushko.distancecalculator;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DistanceCalculatorImplBytecodeTest {

    private static final String RELEASE_7_7_MD5 = "4641f97feedbb51dd3a3ce4a3cfb3ae5";

    @Test
    void bytecodeMatchesRelease7_7() throws IOException, NoSuchAlgorithmException {
        try (InputStream bytecode = DistanceCalculatorImpl.class.getResourceAsStream("DistanceCalculatorImpl.class")) {
            assertNotNull(bytecode, "DistanceCalculatorImpl.class is missing from the classpath");
            byte[] digest = MessageDigest.getInstance("MD5").digest(bytecode.readAllBytes());
            assertEquals(RELEASE_7_7_MD5, String.format("%032x", new BigInteger(1, digest)),
                    "DistanceCalculatorImpl.class is part of a metrological checksum in Aurora. "
                            + "Changing its source or compiler requires a deliberate decision by the owner. "
                            + "Do not update the expected checksum to make this test pass.");
        }
    }
}

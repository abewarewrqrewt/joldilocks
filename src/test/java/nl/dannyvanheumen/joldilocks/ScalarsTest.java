package nl.dannyvanheumen.joldilocks;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.security.SecureRandom;

import static java.math.BigInteger.*;
import static nl.dannyvanheumen.joldilocks.Ed448.MODULUS;
import static nl.dannyvanheumen.joldilocks.Scalars.requireNotZero;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"ConstantConditions", "WeakerAccess"})
public class ScalarsTest {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final BigInteger TWO_POWER_447 = TWO.pow(447);
    private static final BigInteger FOUR_TIMES_TWO_POWER_445_MINUS_ONE = BigInteger.valueOf(4L).multiply(TWO.pow(445).subtract(ONE));

    @Test
    public void testRequireNotZeroForNullFails() {
        assertThrows(NullPointerException.class, () -> requireNotZero(null));
    }

    @Test
    public void testRequireNotZeroActuallyCorrect() {
        assertNotNull(requireNotZero(ONE));
    }

    @Test
    public void testRequireNotZeroActuallyZero() {
        assertThrows(IllegalArgumentException.class, () -> requireNotZero(ZERO));
    }

    @Test
    public void testRequireNotZeroReturnsSameInstance() {
        final BigInteger value = TEN;
        assertSame(value, requireNotZero(value));
    }

    @Test
    public void testDeserializationOfRandomScalar() {
        final byte[] random = new byte[Ed448.LENGTH_SCALAR_BYTES];
        // "Scalars are assumed to be randomly generated bytes. For X448, set the two least significant bits of the
        // first byte to 0, and the most significant bit of the last byte to 1. This means that the resulting integer is
        // of the form 2^447 + 4 * z, with z value in range [0, 2^445-1]." -- RFC 7748
        for (int i = 0; i < 100000; i++) {
            RANDOM.nextBytes(random);
            final BigInteger scalar = Scalars.deserialize(random).mod(MODULUS);
            assertTrue(scalar.compareTo(TWO_POWER_447) >= 0, "Lower bound failed for random scalar: " + scalar);
            assertTrue(scalar.subtract(TWO_POWER_447).compareTo(FOUR_TIMES_TWO_POWER_445_MINUS_ONE) <= 0, "Upper bound failed for random scalar: " + scalar);
        }
    }
}
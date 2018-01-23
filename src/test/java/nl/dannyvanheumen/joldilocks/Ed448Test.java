package nl.dannyvanheumen.joldilocks;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static nl.dannyvanheumen.joldilocks.Ed448.MODULUS;
import static nl.dannyvanheumen.joldilocks.Ed448.P;
import static nl.dannyvanheumen.joldilocks.Ed448.PRIME_ORDER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("WeakerAccess")
public class Ed448Test {

    @Test
    public void testVerifyPrimeOrderSmallerThanModulus() {
        assertTrue(PRIME_ORDER.compareTo(MODULUS) < 0);
    }

    @Disabled
    @Test
    public void testVerifyPrimeOrderMultBasePointEqualsIdentity() {
        final Point identity = Points.identity();
        final Point result = P.multiply(PRIME_ORDER);
        assertEquals(identity.x(), result.x());
        assertEquals(identity.y(), result.y());
    }
}

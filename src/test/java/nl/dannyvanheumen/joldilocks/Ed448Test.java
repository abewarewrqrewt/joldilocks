package nl.dannyvanheumen.joldilocks;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
import static nl.dannyvanheumen.joldilocks.Ed448.MODULUS;
import static nl.dannyvanheumen.joldilocks.Ed448.P;
import static nl.dannyvanheumen.joldilocks.Ed448.PRIME_ORDER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings({"WeakerAccess", "ConstantConditions"})
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

    @Test
    public void testContainsNullFails() {
        assertThrows(NullPointerException.class, () -> Ed448.contains(null));
    }

    @Test
    public void testContainsZero() {
        assertFalse(Ed448.contains(ExtendedPoint.fromEdwards(ZERO, ZERO)));
    }

    // FIXME Not completely sure if this point is supposed to be on the curve ...
    @Test
    public void testContainsPoint1() {
        assertTrue(Ed448.contains(ExtendedPoint.fromEdwards(ONE, ZERO)));
    }

    @Test
    public void testContainsPoint2() {
        assertTrue(Ed448.contains(ExtendedPoint.fromEdwards(ONE.negate(), ZERO)));
    }

    @Test
    public void testContainsNeutralPoint() {
        assertTrue(Ed448.contains(ExtendedPoint.fromEdwards(ZERO, ONE)));
    }

    @Test
    public void testArbitraryPoint() {
        assertFalse(Ed448.contains(ExtendedPoint.fromEdwards(BigInteger.valueOf(1234), BigInteger.valueOf(-856382))));
    }

    @Test
    public void testBasePointDeserialization() {
        assertEquals(new BigInteger("224580040295924300187604334099896036246789641632564134246125461686950415467406032909029192869357953282578032075146446173674602635247710"), P.x());
        assertEquals(new BigInteger("298819210078481492676017930443930673437544040154080242095928241372331506189835876003536878655418784733982303233503462500531545062832660"), P.y());
    }

    @Test
    public void testBasePointIsOnCurve() {
        assertTrue(Ed448.contains(P), "Something is wrong as the base point is not considered to be on the curve. Not sure what is wrong yet.");
    }
}

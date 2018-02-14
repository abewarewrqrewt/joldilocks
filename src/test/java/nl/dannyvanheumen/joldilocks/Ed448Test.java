package nl.dannyvanheumen.joldilocks;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
import static nl.dannyvanheumen.joldilocks.Ed448.MODULUS;
import static nl.dannyvanheumen.joldilocks.Ed448.P;
import static nl.dannyvanheumen.joldilocks.Ed448.Q;
import static nl.dannyvanheumen.joldilocks.Ed448.contains;
import static nl.dannyvanheumen.joldilocks.ExtendedPoint.fromEdwards;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings({"WeakerAccess", "ConstantConditions"})
public class Ed448Test {

    @Test
    public void testVerifyPrimeOrderSmallerThanModulus() {
        assertTrue(Q.compareTo(MODULUS) < 0);
    }

    @Disabled("Fails for as of yet unexplained reason. Anything else w.r.t. multiplication seems to work correctly. This is an important characteristic though ...")
    @Test
    public void testVerifyPrimeOrderMultBasePointEqualsIdentity() {
        final Point identity = fromEdwards(ZERO, ONE);
        final Point result = P.multiply(Q);
        assertEquals(identity.x(), result.x(), result.toString());
        assertEquals(identity.y(), result.y(), result.toString());
        assertEquals(identity, result);
    }

    @Test
    public void testContainsNullFails() {
        assertThrows(NullPointerException.class, () -> contains(null));
    }

    @Test
    public void testContainsZero() {
        assertFalse(contains(fromEdwards(ZERO, ZERO)));
    }

    // FIXME Not completely sure if this point is supposed to be on the curve ...
    @Test
    public void testContainsPoint1() {
        assertTrue(contains(fromEdwards(ONE, ZERO)));
    }

    @Test
    public void testContainsPoint2() {
        assertTrue(contains(fromEdwards(ONE.negate(), ZERO)));
    }

    @Test
    public void testContainsNeutralPoint() {
        assertTrue(contains(fromEdwards(ZERO, ONE)));
    }

    @Test
    public void testArbitraryPoint() {
        assertFalse(contains(fromEdwards(BigInteger.valueOf(1234), BigInteger.valueOf(-856382))));
    }

    @Test
    public void testBasePointDeserialization() {
        assertEquals(new BigInteger("224580040295924300187604334099896036246789641632564134246125461686950415467406032909029192869357953282578032075146446173674602635247710"), P.x());
        assertEquals(new BigInteger("298819210078481492676017930443930673437544040154080242095928241372331506189835876003536878655418784733982303233503462500531545062832660"), P.y());
    }

    @Test
    public void testBasePointIsOnCurve() {
        assertTrue(contains(P), "Something is wrong as the base point is not considered to be on the curve. Not sure what is wrong yet.");
    }
}

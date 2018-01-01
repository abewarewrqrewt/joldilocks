package nl.dannyvanheumen.joldilocks;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
import static nl.dannyvanheumen.joldilocks.Ed448.P;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"ConstantConditions", "WeakerAccess"})
public class CurveTest {

    @Test
    public void testContainsNullFails() {
        assertThrows(NullPointerException.class, () -> {
            Curve.contains(null);
        });
    }

    @Test
    public void testContainsZero() {
        assertFalse(Curve.contains(ExtendedPoint.fromEdwards(ZERO, ZERO)));
    }

    // FIXME Not completely sure if this point is supposed to be on the curve ...
    @Test
    public void testContainsPoint1() {
        assertTrue(Curve.contains(ExtendedPoint.fromEdwards(ONE, ZERO)));
    }

    @Test
    public void testContainsPoint2() {
        assertTrue(Curve.contains(ExtendedPoint.fromEdwards(ONE.negate(), ZERO)));
    }

    @Test
    public void testContainsNeutralPoint() {
        assertTrue(Curve.contains(ExtendedPoint.fromEdwards(ZERO, ONE)));
    }

    @Test
    public void testArbitraryPoint() {
        assertFalse(Curve.contains(ExtendedPoint.fromEdwards(BigInteger.valueOf(1234), BigInteger.valueOf(-856382))));
    }

    @Test
    public void testBasePointIsOnCurve() {
        assertTrue(Curve.contains(P), "Something is wrong as the base point is not considered to be on the curve. Not sure what is wrong yet.");
    }
}

package nl.dannyvanheumen.joldilocks;

import org.junit.jupiter.api.Test;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
import static nl.dannyvanheumen.joldilocks.Ed448.P;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings({"ConstantConditions", "WeakerAccess"})
public class ExtendedPointTest {

    @Test
    public void testPointConversionWithNullX() {
        assertThrows(NullPointerException.class, () -> ExtendedPoint.fromEdwards(null, ZERO));
    }

    @Test
    public void testPointConversionWithNullY() {
        assertThrows(NullPointerException.class, () -> ExtendedPoint.fromEdwards(ONE, null));
    }

    @Test
    public void testPointConversion() {
        assertNotNull(ExtendedPoint.fromEdwards(ONE, ZERO));
    }

    @Test
    public void testPointIdentityAddingItself() {
        final ExtendedPoint i = ExtendedPoint.fromEdwards(ZERO, ONE);
        final ExtendedPoint i2 = i.add(i);
        assertEquals(i, i2);
        assertEquals(i.x(), i2.x());
        assertEquals(i.y(), i2.y());
    }

    @Test
    public void testPointIdentityAddition() {
        final Point r = P.add(new AffinePoint(ZERO, ONE));
        assertEquals(P.x(), r.x());
        assertEquals(P.y(), r.y());
    }

    @Test
    public void testPointDoublingIdentity() {
        final ExtendedPoint i = ExtendedPoint.fromEdwards(ZERO, ONE);
        final ExtendedPoint idoubled = i.doubling();
        assertEquals(i.x(), idoubled.x());
        assertEquals(i.y(), idoubled.y());
    }

    @Test
    public void testPointDoublingBase() {
        final Point doubled = P.doubling();
        final Point added = P.add(P);
        assertEquals(added.x(), doubled.x());
        assertEquals(added.y(), doubled.y());
    }

    @Test
    public void testPointNegation() {
        final Point negativeP = P.negate();
        assertNotNull(negativeP);
        assertEquals(P.x().negate(), negativeP.x());
        assertEquals(P.y(), negativeP.y());
    }
}

package nl.dannyvanheumen.joldilocks;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TEN;
import static java.math.BigInteger.TWO;
import static java.math.BigInteger.ZERO;
import static nl.dannyvanheumen.joldilocks.BigIntegers.EIGHT;
import static nl.dannyvanheumen.joldilocks.BigIntegers.FIVE;
import static nl.dannyvanheumen.joldilocks.BigIntegers.FIVEHUNDREDTWELVE;
import static nl.dannyvanheumen.joldilocks.BigIntegers.FOUR;
import static nl.dannyvanheumen.joldilocks.BigIntegers.NINE;
import static nl.dannyvanheumen.joldilocks.BigIntegers.ONEHUNDREDTWENTYEIGHT;
import static nl.dannyvanheumen.joldilocks.BigIntegers.ONETHOUSANDTWENTYFOUR;
import static nl.dannyvanheumen.joldilocks.BigIntegers.SEVEN;
import static nl.dannyvanheumen.joldilocks.BigIntegers.SIX;
import static nl.dannyvanheumen.joldilocks.BigIntegers.SIXTEEN;
import static nl.dannyvanheumen.joldilocks.BigIntegers.SIXTYFOUR;
import static nl.dannyvanheumen.joldilocks.BigIntegers.THIRTYTWO;
import static nl.dannyvanheumen.joldilocks.BigIntegers.THREE;
import static nl.dannyvanheumen.joldilocks.BigIntegers.TWOHUNDREDFIFTYSIX;
import static nl.dannyvanheumen.joldilocks.BigIntegers.TWOTHOUSANDFORTYEIGHT;
import static nl.dannyvanheumen.joldilocks.Ed448.MODULUS;
import static nl.dannyvanheumen.joldilocks.Ed448.P;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings({"WeakerAccess", "ConstantConditions"})
public class PointTest {

    @Test
    public void testPointEncodingDecoding() throws Points.InvalidDataException {
        final AffinePoint p = P;
        final Point newP = Points.decode(p.encode());
        assertEquals(p.x(), newP.x());
        assertEquals(p.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationNull() {
        assertThrows(NullPointerException.class, () -> P.multiply(null));
    }

    @Test
    public void testPointMultiplicationScalar1() {
        final Point expected = P;
        final Point newP = P.multiply(ONE);
        assertEquals(expected, newP);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar2() {
        final Point expected = P.add(P);
        final Point newP = P.multiply(TWO);
        assertEquals(expected, newP);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar3() {
        final Point expected = P.doubling().add(P);
        final Point newP = P.multiply(THREE);
        assertEquals(expected, newP);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar4() {
        final Point expected = P.doubling().doubling();
        final Point newP = P.multiply(FOUR);
        assertEquals(expected, newP);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar5() {
        final Point expected = P.doubling().doubling().add(P);
        final Point newP = P.multiply(FIVE);
        assertEquals(expected, newP);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar6() {
        final AffinePoint expected = P.add(P).add(P).add(P).add(P).add(P);
        final Point newP = P.multiply(SIX);
        assertEquals(expected, newP);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar7() {
        final AffinePoint expected = P.add(P).add(P).add(P).add(P).add(P).add(P);
        final Point newP = P.multiply(SEVEN);
        assertEquals(expected, newP);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar8() {
        final AffinePoint expected = P.add(P).add(P).add(P).add(P).add(P).add(P).add(P);
        final Point newP = P.multiply(EIGHT);
        assertEquals(expected, newP);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar9() {
        final AffinePoint expected = P.add(P).add(P).add(P).add(P).add(P).add(P).add(P).add(P);
        final Point newP = P.multiply(NINE);
        assertEquals(expected, newP);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar10() {
        final AffinePoint expected = P.add(P).add(P).add(P).add(P).add(P).add(P).add(P).add(P).add(P);
        final Point newP = P.multiply(TEN);
        assertEquals(expected, newP);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar16() {
        final AffinePoint expected = P.doubling().doubling().doubling().doubling();
        final Point newP = P.multiply(SIXTEEN);
        assertEquals(expected, newP);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar32() {
        final AffinePoint expected = P.doubling().doubling().doubling().doubling().doubling();
        final Point newP = P.multiply(THIRTYTWO);
        assertEquals(expected, newP);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar64() {
        final AffinePoint expected = P.doubling().doubling().doubling().doubling().doubling().doubling();
        final Point newP = P.multiply(SIXTYFOUR);
        assertEquals(expected, newP);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar128() {
        final AffinePoint expected = P.doubling().doubling().doubling().doubling().doubling().doubling().doubling();
        final Point newP = P.multiply(ONEHUNDREDTWENTYEIGHT);
        assertEquals(expected, newP);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar256() {
        final AffinePoint expected = P.doubling().doubling().doubling().doubling().doubling().doubling().doubling().doubling();
        final Point newP = P.multiply(TWOHUNDREDFIFTYSIX);
        assertEquals(expected, newP);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar512() {
        final AffinePoint expected = P.doubling().doubling().doubling().doubling().doubling().doubling().doubling().doubling().doubling();
        final Point newP = P.multiply(FIVEHUNDREDTWELVE);
        assertEquals(expected, newP);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar1024() {
        final AffinePoint expected = P.doubling().doubling().doubling().doubling().doubling().doubling().doubling().doubling().doubling().doubling();
        final Point newP = P.multiply(ONETHOUSANDTWENTYFOUR);
        assertEquals(expected, newP);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar2048() {
        final AffinePoint expected = P.doubling().doubling().doubling().doubling().doubling().doubling().doubling().doubling().doubling().doubling().doubling();
        final Point newP = P.multiply(TWOTHOUSANDFORTYEIGHT);
        assertEquals(expected, newP);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationZero() {
        final ExtendedPoint expected = ExtendedPoint.fromEdwards(ZERO, ONE);
        final Point newP = P.multiply(ZERO);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Disabled("BUG: zero will not be successfully encoded due to zero-length byte array.")
    @Test
    public void testPointEncodeLowerBoundXY() {
        final AffinePoint p = new AffinePoint(ZERO, ONE);
        assertNotNull(p.encode());
    }

    @Test
    public void testPointEncodeNegativeX() {
        final AffinePoint p = new AffinePoint(BigInteger.valueOf(-1L), ZERO);
        assertThrows(IllegalArgumentException.class, p::encode);
    }

    @Test
    public void testPointEncodeNegativeY() {
        final AffinePoint p = new AffinePoint(ZERO, BigInteger.valueOf(-1L));
        assertThrows(IllegalArgumentException.class, p::encode);
    }

    @Test
    public void testPointEncodeUpperBoundXY() {
        final BigInteger modulusMinusOne = MODULUS.subtract(ONE);
        final AffinePoint p = new AffinePoint(modulusMinusOne, modulusMinusOne);
        assertNotNull(p.encode());
    }

    @Test
    public void testPointEncodeMaxX() {
        final AffinePoint p = new AffinePoint(MODULUS, ZERO);
        assertThrows(IllegalArgumentException.class, p::encode);
    }

    @Test
    public void testPointEncodeMaxY() {
        final AffinePoint p = new AffinePoint(ZERO, MODULUS);
        assertThrows(IllegalArgumentException.class, p::encode);
    }
}

package nl.dannyvanheumen.joldilocks;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TEN;
import static java.math.BigInteger.TWO;
import static java.math.BigInteger.ZERO;
import static nl.dannyvanheumen.joldilocks.Ed448.P;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings({"WeakerAccess", "ConstantConditions"})
public class PointTest {

    private static final BigInteger THREE = BigInteger.valueOf(3L);
    private static final BigInteger FOUR = BigInteger.valueOf(4L);
    private static final BigInteger FIVE = BigInteger.valueOf(5L);
    private static final BigInteger SIX = BigInteger.valueOf(6L);
    private static final BigInteger SEVEN = BigInteger.valueOf(7L);
    private static final BigInteger EIGHT = BigInteger.valueOf(8L);
    private static final BigInteger NINE = BigInteger.valueOf(9L);

    private static final BigInteger SIXTEEN = BigInteger.valueOf(16L);
    private static final BigInteger THIRTYTWO = BigInteger.valueOf(32L);
    private static final BigInteger SIXTYFOUR = BigInteger.valueOf(64L);
    private static final BigInteger ONEHUNDREDTWENTYEIGHT = BigInteger.valueOf(128L);
    private static final BigInteger TWOHUNDREDFIFTYSIX = BigInteger.valueOf(256L);
    private static final BigInteger FIVEHUNDREDTWELVE = BigInteger.valueOf(512L);
    private static final BigInteger ONETHOUSANDTWENTYFOUR = BigInteger.valueOf(1024L);
    private static final BigInteger TWOTHOUSANDFORTYEIGHT = BigInteger.valueOf(2048L);

    @Test
    public void testPointEncodingDecoding() {
        final ExtendedPoint p = P;
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
        final ExtendedPoint expected = P.add(P).add(P).add(P).add(P).add(P);
        final Point newP = P.multiply(SIX);
        assertEquals(expected, newP);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar7() {
        final ExtendedPoint expected = P.add(P).add(P).add(P).add(P).add(P).add(P);
        final Point newP = P.multiply(SEVEN);
        assertEquals(expected, newP);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar8() {
        final ExtendedPoint expected = P.add(P).add(P).add(P).add(P).add(P).add(P).add(P);
        final Point newP = P.multiply(EIGHT);
        assertEquals(expected, newP);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar9() {
        final ExtendedPoint expected = P.add(P).add(P).add(P).add(P).add(P).add(P).add(P).add(P);
        final Point newP = P.multiply(NINE);
        assertEquals(expected, newP);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar10() {
        final ExtendedPoint expected = P.add(P).add(P).add(P).add(P).add(P).add(P).add(P).add(P).add(P);
        final Point newP = P.multiply(TEN);
        assertEquals(expected, newP);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar16() {
        final ExtendedPoint expected = P.doubling().doubling().doubling().doubling();
        final Point newP = P.multiply(SIXTEEN);
        assertEquals(expected, newP);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar32() {
        final ExtendedPoint expected = P.doubling().doubling().doubling().doubling().doubling();
        final Point newP = P.multiply(THIRTYTWO);
        assertEquals(expected, newP);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar64() {
        final ExtendedPoint expected = P.doubling().doubling().doubling().doubling().doubling().doubling();
        final Point newP = P.multiply(SIXTYFOUR);
        assertEquals(expected, newP);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar128() {
        final ExtendedPoint expected = P.doubling().doubling().doubling().doubling().doubling().doubling().doubling();
        final Point newP = P.multiply(ONEHUNDREDTWENTYEIGHT);
        assertEquals(expected, newP);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar256() {
        final ExtendedPoint expected = P.doubling().doubling().doubling().doubling().doubling().doubling().doubling().doubling();
        final Point newP = P.multiply(TWOHUNDREDFIFTYSIX);
        assertEquals(expected, newP);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar512() {
        final ExtendedPoint expected = P.doubling().doubling().doubling().doubling().doubling().doubling().doubling().doubling().doubling();
        final Point newP = P.multiply(FIVEHUNDREDTWELVE);
        assertEquals(expected, newP);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar1024() {
        final ExtendedPoint expected = P.doubling().doubling().doubling().doubling().doubling().doubling().doubling().doubling().doubling().doubling();
        final Point newP = P.multiply(ONETHOUSANDTWENTYFOUR);
        assertEquals(expected, newP);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar2048() {
        final ExtendedPoint expected = P.doubling().doubling().doubling().doubling().doubling().doubling().doubling().doubling().doubling().doubling().doubling();
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
}

package nl.dannyvanheumen.joldilocks;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TWO;
import static nl.dannyvanheumen.joldilocks.Ed448.P;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("WeakerAccess")
public class PointTest {

    private static final BigInteger THREE = BigInteger.valueOf(3L);
    private static final BigInteger FOUR = BigInteger.valueOf(4L);
    private static final BigInteger FIVE = BigInteger.valueOf(5L);
    private static final BigInteger SIX = BigInteger.valueOf(6L);

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
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar2() {
        final Point expected = P.add(P);
        final Point newP = P.multiply(TWO);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar3() {
        final Point expected = P.doubling().add(P);
        final Point newP = P.multiply(THREE);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar4() {
        final Point expected = P.doubling().doubling();
        final Point newP = P.multiply(FOUR);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar5() {
        final Point expected = P.doubling().doubling().add(P);
        final Point newP = P.multiply(FIVE);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }

    @Test
    public void testPointMultiplicationScalar6() {
        final Point expected = P.doubling().add(P).doubling();
        final Point newP = P.multiply(SIX);
        assertEquals(expected.x(), newP.x());
        assertEquals(expected.y(), newP.y());
    }
}

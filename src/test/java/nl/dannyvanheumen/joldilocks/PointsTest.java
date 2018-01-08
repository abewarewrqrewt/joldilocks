package nl.dannyvanheumen.joldilocks;

import org.junit.jupiter.api.Test;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
import static nl.dannyvanheumen.joldilocks.Ed448.P;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ConstantConditions")
public class PointsTest {

    @Test
    public void testNullConversion() {
        assertThrows(NullPointerException.class, () -> Points.toExtended(null));
    }

    @Test
    public void testBasePointConversion() {
        assertSame(P, Points.toExtended(P));
    }

    @Test
    public void testIdentityPoint() {
        final Point id = Points.identity();
        assertNotNull(id);
        assertEquals(ZERO, id.x());
        assertEquals(ONE, id.y());
        assertEquals(P, id.add(P));
        assertEquals(id, id.doubling());
        assertEquals(id, id.negate());
    }
}
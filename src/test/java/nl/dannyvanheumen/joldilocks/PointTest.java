package nl.dannyvanheumen.joldilocks;

import org.junit.jupiter.api.Test;

import static nl.dannyvanheumen.joldilocks.Ed448.P;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PointTest {

    @Test
    public void testPointEncodingDecoding() {
        final ExtendedPoint p = P;
        final Point newP = Points.decode(p.encode());
        assertEquals(p.x(), newP.x());
        assertEquals(p.y(), newP.y());
    }
}

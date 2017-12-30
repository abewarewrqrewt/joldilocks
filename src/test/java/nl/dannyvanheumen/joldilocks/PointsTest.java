package nl.dannyvanheumen.joldilocks;

import org.junit.jupiter.api.Test;

import static nl.dannyvanheumen.joldilocks.Ed448.P;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PointsTest {

    @Test
    public void testNullConversion() {
        assertThrows(NullPointerException.class, () -> Points.toExtended(null));
    }

    @Test
    public void testIdentityPointConversion() {
        assertSame(P, Points.toExtended(P));
    }
}
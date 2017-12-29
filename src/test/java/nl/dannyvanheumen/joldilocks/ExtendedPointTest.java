package nl.dannyvanheumen.joldilocks;

import org.junit.jupiter.api.Test;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"ConstantConditions", "WeakerAccess"})
public class ExtendedPointTest {

    @Test
    public void testPointConversionWithNullX() {
        assertThrows(NullPointerException.class, () -> {
            ExtendedPoint.fromEdwards(null, ZERO);
        });
    }

    @Test
    public void testPointConversionWithNullY() {
        assertThrows(NullPointerException.class, () -> {
            ExtendedPoint.fromEdwards(ONE, null);
        });
    }

    @Test
    public void testPointConversion() {
        assertNotNull(ExtendedPoint.fromEdwards(ONE, ZERO));
    }
}

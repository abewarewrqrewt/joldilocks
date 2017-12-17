package nl.dannyvanheumen.joldilocks;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings({"ConstantConditions", "WeakerAccess"})
public class ExtendedPointTest {

    @Test
    public void testPointConversionWithNullX() {
        assertThrows(NullPointerException.class, () -> {
            ExtendedPoint.fromEdwards(null, BigInteger.ZERO);
        });
    }

    @Test
    public void testPointConversionWithNullY() {
        assertThrows(NullPointerException.class, () -> {
            ExtendedPoint.fromEdwards(BigInteger.ONE, null);
        });
    }

    @Test
    public void testPointConversion() {
        assertNotNull(ExtendedPoint.fromEdwards(BigInteger.ONE, BigInteger.ZERO));
    }
}

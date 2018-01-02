package nl.dannyvanheumen.joldilocks;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TEN;
import static java.math.BigInteger.ZERO;
import static nl.dannyvanheumen.joldilocks.Scalars.requireNotZero;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"ConstantConditions", "WeakerAccess"})
public class ScalarsTest {

    @Test
    public void testRequireNotZeroForNullFails() {
        assertThrows(NullPointerException.class, () -> {
            requireNotZero(null);
        });
    }

    @Test
    public void testRequireNotZeroActuallyCorrect() {
        assertNotNull(requireNotZero(ONE));
    }

    @Test
    public void testRequireNotZeroActuallyZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            requireNotZero(ZERO);
        });
    }

    @Test
    public void testRequireNotZeroReturnsSameInstance() {
        final BigInteger value = TEN;
        assertSame(value, requireNotZero(value));
    }
}
package nl.dannyvanheumen.joldilocks;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TEN;
import static java.math.BigInteger.ZERO;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"ConstantConditions", "WeakerAccess"})
public class BigIntegersTest {

    @Test
    public void testRequireNotZeroForNullFails() {
        assertThrows(NullPointerException.class, () -> {
            BigIntegers.requireNotZero(null);
        });
    }

    @Test
    public void testRequireNotZeroActuallyCorrect() {
        BigIntegers.requireNotZero(ONE);
    }

    @Test
    public void testRequireNotZeroActuallyZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            BigIntegers.requireNotZero(ZERO);
        });
    }

    @Test
    public void testRequireNotZeroReturnsSameInstance() {
        final BigInteger value = TEN;
        assertSame(value, BigIntegers.requireNotZero(value));
    }
}
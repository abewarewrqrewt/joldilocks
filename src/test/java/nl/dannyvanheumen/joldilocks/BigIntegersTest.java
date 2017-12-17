package nl.dannyvanheumen.joldilocks;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

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
        BigIntegers.requireNotZero(BigInteger.ONE);
    }

    @Test
    public void testRequireNotZeroActuallyZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            BigIntegers.requireNotZero(BigInteger.ZERO);
        });
    }

    @Test
    public void testRequireNotZeroReturnsSameInstance() {
        final BigInteger value = BigInteger.TEN;
        assertSame(value, BigIntegers.requireNotZero(value));
    }
}
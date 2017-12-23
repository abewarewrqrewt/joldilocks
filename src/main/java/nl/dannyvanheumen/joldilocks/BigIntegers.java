package nl.dannyvanheumen.joldilocks;

import java.math.BigInteger;

/**
 * Utilities for big integers.
 */
final class BigIntegers {

    private BigIntegers() {
        // No need to instantiate utility class.
    }

    /**
     * Verify that BigInteger value is not zero.
     *
     * @param value Value to be verified for zero. (null is not allowed)
     * @return Returns same value iff not zero.
     */
    static BigInteger requireNotZero(final BigInteger value) {
        if (value.equals(BigInteger.ZERO)) {
            throw new IllegalArgumentException("Value is zero.");
        }
        return value;
    }
}

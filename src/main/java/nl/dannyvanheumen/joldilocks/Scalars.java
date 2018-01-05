package nl.dannyvanheumen.joldilocks;

import javax.annotation.Nonnull;
import java.math.BigInteger;

import static java.math.BigInteger.ZERO;
import static nl.dannyvanheumen.joldilocks.ByteArrays.requireLengthExactly;
import static nl.dannyvanheumen.joldilocks.Ed448.LENGTH_SCALAR_BYTES;

/**
 * Utility methods for scalar values.
 */
final class Scalars {

    private Scalars() {
        // No need to instantiate utility class.
    }

    /**
     * Deserialize scalar value for Ed448 curve.
     *
     * @param value The serialized value of the scalar.
     * @return Returns scalar.
     */
    @Nonnull
    static BigInteger deserialize(final byte[] value) {
        requireLengthExactly(value, LENGTH_SCALAR_BYTES);
        value[0] &= 0xfc;
        value[55] |= 0x80;
        return decodeLittleEndian(value);
    }

    /**
     * Decode little endian format for scalar values.
     *
     * @param value Little-endian encoded scalar value.
     * @return Returns scalar.
     */
    @Nonnull
    private static BigInteger decodeLittleEndian(final byte[] value) {
        BigInteger sum = ZERO;
        for (int i = 0; i < value.length; i++) {
            sum = sum.add(new BigInteger(1, value, i, 1).shiftLeft(8 * i));
        }
        return sum;
    }

    /**
     * Verify that BigInteger value is not zero.
     *
     * @param value Value to be verified for zero. (null is not allowed)
     * @return Returns same value iff not zero.
     */
    @Nonnull
    static BigInteger requireNotZero(final BigInteger value) {
        if (value.equals(BigInteger.ZERO)) {
            throw new IllegalArgumentException("Value is zero.");
        }
        return value;
    }

}

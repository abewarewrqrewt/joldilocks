package nl.dannyvanheumen.joldilocks;

import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.BigIntegers;

import javax.annotation.Nonnull;
import java.math.BigInteger;

import static java.math.BigInteger.ZERO;
import static nl.dannyvanheumen.joldilocks.ByteArrays.requireLengthExactly;

/**
 * Utility methods for scalar values.
 */
public final class Scalars {

    private static final int LENGTH_SCALAR_BITS = 448;

    static final int LENGTH_SCALAR_BYTES = LENGTH_SCALAR_BITS / 8;

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
     * Encode scalar value as little-endian.
     *
     * @param value scalar value
     * @return Returns scalar as little-endian byte array.
     */
    @Nonnull
    public static byte[] encodeLittleEndian(final BigInteger value) {
        return Arrays.reverse(BigIntegers.asUnsignedByteArray(value));
    }

    /**
     * Encode scalar value as little-endian to provided destination byte array.
     *
     * @param dst    Destination byte array.
     * @param offset offset in array
     * @param value  Value to encode.
     */
    public static void encodeLittleEndianTo(final byte[] dst, final int offset, final BigInteger value) {
        final byte[] bigEndianBytes = BigIntegers.asUnsignedByteArray(value);
        for (int si = bigEndianBytes.length - 1, di = offset; si >= 0; si--) {
            dst[di++] = bigEndianBytes[si];
        }
    }

    /**
     * Decode little endian format for scalar values.
     *
     * @param value Little-endian encoded scalar value.
     * @return Returns scalar.
     */
    @Nonnull
    public static BigInteger decodeLittleEndian(final byte[] value) {
        return BigIntegers.fromUnsignedByteArray(Arrays.reverse(value));
    }

    /**
     * Verify that BigInteger value is not zero.
     *
     * @param value Value to be verified for zero. (null is not allowed)
     * @return Returns same value iff not zero.
     */
    @Nonnull
    public static BigInteger requireNotZero(final BigInteger value) {
        if (value.equals(ZERO)) {
            throw new IllegalArgumentException("Value is zero.");
        }
        return value;
    }
}

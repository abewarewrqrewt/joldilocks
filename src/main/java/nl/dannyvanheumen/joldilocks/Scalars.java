package nl.dannyvanheumen.joldilocks;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;

import static java.math.BigInteger.ZERO;
import static nl.dannyvanheumen.joldilocks.ByteArrays.requireLengthExactly;
import static org.bouncycastle.util.Arrays.clear;
import static org.bouncycastle.util.Arrays.reverse;
import static org.bouncycastle.util.BigIntegers.asUnsignedByteArray;
import static org.bouncycastle.util.BigIntegers.fromUnsignedByteArray;

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
     * Pruning of private key source data.
     * <p>
     * The procedure is described in RFC 8032, section 5.2.5. "Key Generation", step 2.
     * <pre>
     * 2.  Prune the buffer: The two least significant bits of the first
     *     octet are cleared, all eight bits the last octet are cleared, and
     *     the highest bit of the second to last octet is set.
     * </pre>
     *
     * @param privateKeySourceData Public key source data.
     * @throws IllegalArgumentException In case of invalid length of source data.
     */
    public static void prune(final byte[] privateKeySourceData) {
        requireLengthExactly(57, privateKeySourceData);
        privateKeySourceData[0] &= 0b11111100;
        privateKeySourceData[56] = 0;
        privateKeySourceData[55] |= 0b10000000;
    }

    /**
     * Test that source data is valid for use as EdDSA private key.
     *
     * @param privateKeySourceData The private key source data.
     * @return Original source data iff verified.
     */
    @Nonnull
    public static BigInteger requireValidSourceData(final BigInteger privateKeySourceData) {
        if (privateKeySourceData.testBit(0) || privateKeySourceData.testBit(1) || !privateKeySourceData.testBit(447)
            || privateKeySourceData.testBit(448) || privateKeySourceData.testBit(449)
            || privateKeySourceData.testBit(450) || privateKeySourceData.testBit(451)
            || privateKeySourceData.testBit(452) || privateKeySourceData.testBit(453)
            || privateKeySourceData.testBit(454) || privateKeySourceData.testBit(455)) {
            throw new IllegalArgumentException("Invalid source data.");
        }
        return privateKeySourceData;
    }

    /**
     * Deserialize scalar value for Ed448 curve.
     *
     * @param value The serialized value of the scalar.
     * @return Returns scalar.
     */
    @Nonnull
    static BigInteger deserialize(final byte[] value) {
        requireLengthExactly(LENGTH_SCALAR_BYTES, value);
        value[0] &= 0xfc;
        value[55] |= 0x80;
        return decodeLittleEndian(value);
    }

    /**
     * Write encoded scalar value to provided output stream.
     *
     * @param out    The destination output stream.
     * @param scalar The scalar value to be written.
     * @throws IOException Failure to write to output stream.
     */
    public static void encodeLittleEndianTo(final OutputStream out, final BigInteger scalar) throws IOException {
        final byte[] dst = encodeLittleEndian(scalar);
        out.write(dst);
        clear(dst);
    }

    /**
     * Encode scalar value as little-endian.
     *
     * @param value scalar value
     * @return Returns scalar as little-endian byte array.
     */
    @Nonnull
    public static byte[] encodeLittleEndian(final BigInteger value) {
        return reverse(asUnsignedByteArray(value));
    }

    /**
     * Encode scalar value as little-endian to provided destination byte array.
     *
     * @param dst    Destination byte array.
     * @param offset offset in array
     * @param value  Value to encode.
     */
    public static void encodeLittleEndianTo(final byte[] dst, final int offset, final BigInteger value) {
        final byte[] littleEndianBytes = encodeLittleEndian(value);
        System.arraycopy(littleEndianBytes, 0, dst, offset, littleEndianBytes.length);
    }

    /**
     * Decode little endian format for scalar values.
     *
     * @param value Little-endian encoded scalar value.
     * @return Returns scalar.
     */
    @Nonnull
    public static BigInteger decodeLittleEndian(final byte[] value) {
        return fromUnsignedByteArray(reverse(value));
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

package nl.dannyvanheumen.joldilocks;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TEN;
import static java.math.BigInteger.ZERO;
import static nl.dannyvanheumen.joldilocks.BigIntegers.FOUR_TIMES_TWO_POWER_445_MINUS_ONE;
import static nl.dannyvanheumen.joldilocks.BigIntegers.TWO_POWER_447;
import static nl.dannyvanheumen.joldilocks.Ed448.MODULUS;
import static nl.dannyvanheumen.joldilocks.Scalars.decodeLittleEndian;
import static nl.dannyvanheumen.joldilocks.Scalars.encodeLittleEndian;
import static nl.dannyvanheumen.joldilocks.Scalars.encodeLittleEndianTo;
import static nl.dannyvanheumen.joldilocks.Scalars.prune;
import static nl.dannyvanheumen.joldilocks.Scalars.requireNotZero;
import static nl.dannyvanheumen.joldilocks.Scalars.requireValidSourceData;
import static org.bouncycastle.util.Arrays.fill;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings({"ConstantConditions", "WeakerAccess"})
public class ScalarsTest {

    private static final SecureRandom RANDOM = new SecureRandom();

    @Test
    public void testRequireNotZeroForNullFails() {
        assertThrows(NullPointerException.class, () -> requireNotZero(null));
    }

    @Test
    public void testRequireNotZeroActuallyCorrect() {
        assertNotNull(requireNotZero(ONE));
    }

    @Test
    public void testRequireNotZeroActuallyZero() {
        assertThrows(IllegalArgumentException.class, () -> requireNotZero(ZERO));
    }

    @Test
    public void testRequireNotZeroReturnsSameInstance() {
        final BigInteger value = TEN;
        assertSame(value, requireNotZero(value));
    }

    @Test
    public void testDeserializationOfRandomScalar() {
        final byte[] random = new byte[Scalars.LENGTH_SCALAR_BYTES];
        // "Scalars are assumed to be randomly generated bytes. For X448, set the two least significant bits of the
        // first byte to 0, and the most significant bit of the last byte to 1. This means that the resulting integer is
        // of the form 2^447 + 4 * z, with z value in range [0, 2^445-1]." -- RFC 7748
        for (int i = 0; i < 100000; i++) {
            RANDOM.nextBytes(random);
            final BigInteger scalar = Scalars.deserialize(random).mod(MODULUS);
            assertTrue(scalar.compareTo(TWO_POWER_447) >= 0, "Lower bound failed for random scalar: " + scalar);
            assertTrue(scalar.subtract(TWO_POWER_447).compareTo(FOUR_TIMES_TWO_POWER_445_MINUS_ONE) <= 0, "Upper bound failed for random scalar: " + scalar);
        }
    }

    @Test
    public void testEncodeAndDecodeLittleEndian() {
        final BigInteger expected = BigInteger.valueOf(4278322184L);
        final BigInteger decoded = decodeLittleEndian(encodeLittleEndian(expected));
        assertEquals(expected, decoded);
    }

    @Test
    public void testEncodeLittleEndian() {
        final byte[] expected = new byte[]{0x8, 0x4, 0x2, (byte) 0xff};
        assertArrayEquals(expected, encodeLittleEndian(BigInteger.valueOf(4278322184L)));
    }

    @Test
    public void testEncodeLittleEndianNullFails() {
        assertThrows(NullPointerException.class, () -> encodeLittleEndian(null));
    }

    @Test
    public void testEncodeLittleEndianZero() {
        assertArrayEquals(new byte[0], encodeLittleEndian(ZERO));
    }

    @Test
    public void testDecodeLittleEndian() {
        final BigInteger expected = BigInteger.valueOf(4278322184L);
        assertEquals(expected, decodeLittleEndian(new byte[]{(byte) 0x8, 0x4, 0x2, (byte) 0xff}));
    }

    @Test
    public void testDecodeLittleEndianEmptyArray() {
        assertEquals(ZERO, decodeLittleEndian(new byte[0]));
    }

    @Test
    public void testDecodeLittleEndianNullFails() {
        assertThrows(NullPointerException.class, () -> decodeLittleEndian(null));
    }

    @Test
    public void testEncodeLittleEndianTo() {
        final byte[] expected = new byte[]{0x8, 0x4, 0x2, (byte) 0xff};
        final byte[] dst = new byte[4];
        encodeLittleEndianTo(dst, 0, BigInteger.valueOf(4278322184L));
        assertArrayEquals(expected, dst);
    }

    @Test
    public void testEncodeLittleEndianToOffset() {
        final byte[] expected = new byte[]{0, 0, 0x8, 0x4, 0x2, (byte) 0xff};
        final byte[] dst = new byte[6];
        encodeLittleEndianTo(dst, 2, BigInteger.valueOf(4278322184L));
        assertArrayEquals(expected, dst);
    }

    @Test
    public void testEncodeLittleEndianToNullValueFails() {
        final byte[] dst = new byte[0];
        assertThrows(NullPointerException.class, () -> encodeLittleEndianTo(dst, 0, null));
    }

    @Test
    public void testEncodeLittleEndianToZero() {
        final byte[] dst = new byte[0];
        encodeLittleEndianTo(dst, 0, ZERO);
        assertArrayEquals(new byte[0], dst);
    }

    @Test
    public void testEncodeLittleEndianToNullDstFails() {
        assertThrows(NullPointerException.class, () -> encodeLittleEndianTo(null, 0, ONE));
    }

    @Test
    public void testEncodeLittleEndianToIllegalOffsetFails() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> encodeLittleEndianTo(new byte[0], -1, ONE));
    }

    @Test
    public void testEncodeLittleEndianToNullStreamFails() {
        assertThrows(NullPointerException.class, () -> encodeLittleEndianTo(null, ONE));
    }

    @Test
    public void testEncodeLittleEndianToByteArrayOutputStream() throws IOException {
        final BigInteger scalar = BigInteger.valueOf(4278322184L);
        final byte[] expected = Scalars.encodeLittleEndian(scalar);
        try (final ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            encodeLittleEndianTo(out, scalar);
            assertArrayEquals(expected, out.toByteArray());
        }
    }

    @Test
    public void testPruneNull() {
        assertThrows(NullPointerException.class, () -> prune(null));
    }

    @Test
    public void testPruneEmptyArray() {
        assertThrows(IllegalArgumentException.class, () -> prune(new byte[0]));
    }

    @Test
    public void tesPruneTooSmallArray() {
        assertThrows(IllegalArgumentException.class, () -> prune(new byte[56]));
    }

    @Test
    public void testPruneZeroArray() {
        final byte[] expected = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, (byte) 0b10000000, 0};
        final byte[] data = new byte[57];
        prune(data);
        assertArrayEquals(expected, data);
    }

    @Test
    public void testPruneFullArray() {
        final byte[] expected = new byte[]{(byte) 0b11111100, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, 0};
        final byte[] data = new byte[57];
        fill(data, (byte) 0xff);
        prune(data);
        assertArrayEquals(expected, data);
    }

    @Test
    public void testRequireValidSourceDataNull() {
        assertThrows(NullPointerException.class, () -> requireValidSourceData(null));
    }

    @Test
    public void testRequireValidSourceDataZeroValue() {
        assertThrows(IllegalArgumentException.class, () -> requireValidSourceData(ZERO));
    }

    @Test
    public void testRequireValidSourceDataMinimalValue() {
        final byte[] data = new byte[57];
        prune(data);
        final BigInteger value = decodeLittleEndian(data);
        assertSame(value, requireValidSourceData(value));
    }

    @Test
    public void testRequireValidSourceDataMaximumValue() {
        final byte[] data = new byte[57];
        fill(data, (byte) 0xff);
        prune(data);
        final BigInteger value = decodeLittleEndian(data);
        assertSame(value, requireValidSourceData(value));
    }

    @Test
    public void testRequireValidSourceDataIndividualBits() {
        final byte[] firstBitSet = new byte[]{0b000001, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, (byte) 0b10000000, 0};
        assertThrows(IllegalArgumentException.class, () -> requireValidSourceData(decodeLittleEndian(firstBitSet)));
        final byte[] secondBitSet = new byte[]{0b000010, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, (byte) 0b10000000, 0};
        assertThrows(IllegalArgumentException.class, () -> requireValidSourceData(decodeLittleEndian(secondBitSet)));
        final byte[] almostSignificantByte = new byte[57];
        prune(almostSignificantByte);
        almostSignificantByte[55] &= 0b01111111;
        assertThrows(IllegalArgumentException.class, () -> requireValidSourceData(decodeLittleEndian(almostSignificantByte)));
        final byte[] lastByte = new byte[57];
        lastByte[56] = 0b00000001;
        assertThrows(IllegalArgumentException.class, () -> requireValidSourceData(decodeLittleEndian(lastByte)));
        lastByte[56] = 0b00000010;
        assertThrows(IllegalArgumentException.class, () -> requireValidSourceData(decodeLittleEndian(lastByte)));
        lastByte[56] = 0b00000100;
        assertThrows(IllegalArgumentException.class, () -> requireValidSourceData(decodeLittleEndian(lastByte)));
        lastByte[56] = 0b00001000;
        assertThrows(IllegalArgumentException.class, () -> requireValidSourceData(decodeLittleEndian(lastByte)));
        lastByte[56] = 0b00010000;
        assertThrows(IllegalArgumentException.class, () -> requireValidSourceData(decodeLittleEndian(lastByte)));
        lastByte[56] = 0b00100000;
        assertThrows(IllegalArgumentException.class, () -> requireValidSourceData(decodeLittleEndian(lastByte)));
        lastByte[56] = 0b01000000;
        assertThrows(IllegalArgumentException.class, () -> requireValidSourceData(decodeLittleEndian(lastByte)));
        lastByte[56] = (byte) 0b10000000;
        assertThrows(IllegalArgumentException.class, () -> requireValidSourceData(decodeLittleEndian(lastByte)));
    }
}

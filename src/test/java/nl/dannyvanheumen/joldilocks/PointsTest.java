package nl.dannyvanheumen.joldilocks;

import org.junit.jupiter.api.Test;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
import static nl.dannyvanheumen.joldilocks.Ed448.P;
import static nl.dannyvanheumen.joldilocks.Point.ENCODED_LENGTH_BYTES;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("ConstantConditions")
public class PointsTest {

    private static final byte[] RFC8032_ED448_PUBLIC_KEY_1023_BYTES_MESSAGE = new byte[]{(byte) 0xa8, 0x1b, 0x2e, (byte) 0x8a, 0x70, (byte) 0xa5, (byte) 0xac, (byte) 0x94, (byte) 0xff, (byte) 0xdb, (byte) 0xcc, (byte) 0x9b, (byte) 0xad, (byte) 0xfc, 0x3f, (byte) 0xeb, 0x08, 0x01, (byte) 0xf2, 0x58, 0x57, (byte) 0x8b, (byte) 0xb1, 0x14, (byte) 0xad, 0x44, (byte) 0xec, (byte) 0xe1, (byte) 0xec, 0x0e, 0x79, (byte) 0x9d, (byte) 0xa0, (byte) 0x8e, (byte) 0xff, (byte) 0xb8, 0x1c, 0x5d, 0x68, 0x5c, 0x0c, 0x56, (byte) 0xf6, 0x4e, (byte) 0xec, (byte) 0xae, (byte) 0xf8, (byte) 0xcd, (byte) 0xf1, 0x1c, (byte) 0xc3, (byte) 0x87, 0x37, (byte) 0x83, (byte) 0x8c, (byte) 0xf4, 0x00};

    @Test
    public void testNullConversion() {
        assertThrows(NullPointerException.class, () -> Points.toExtended(null));
    }

    @Test
    public void testBasePointConversion() {
        assertSame(P, Points.toExtended(P));
    }

    @Test
    public void testIdentityPoint() {
        final Point id = Points.identity();
        assertNotNull(id);
        assertEquals(ZERO, id.x());
        assertEquals(ONE, id.y());
        assertEquals(P, id.add(P));
        assertEquals(id, id.doubling());
        assertEquals(id, id.negate());
    }

    @Test
    public void testIdentityPointToExtended() {
        final Point id = Points.identity();
        final ExtendedPoint extendedId = Points.toExtended(id);
        assertEquals(id.x(), extendedId.x());
        assertEquals(id.y(), extendedId.y());
    }

    @Test
    public void testDecodeRFC8032TestPublicKey() {
        assertNotNull(Points.decode(RFC8032_ED448_PUBLIC_KEY_1023_BYTES_MESSAGE));
    }

    @Test
    public void testEncodeDecodeRFC8032TestPublicKey() {
        assertArrayEquals(RFC8032_ED448_PUBLIC_KEY_1023_BYTES_MESSAGE, Points.decode(RFC8032_ED448_PUBLIC_KEY_1023_BYTES_MESSAGE).encode());
    }

    @Test
    public void testDecodeNull() {
        assertThrows(NullPointerException.class, () -> Points.decode(null));
    }

    @Test
    public void testDecodeEmptyArray() {
        assertThrows(IllegalArgumentException.class, () -> Points.decode(new byte[0]));
    }

    @Test
    public void testDecodeArrayTooSmall() {
        assertThrows(IllegalArgumentException.class, () -> Points.decode(new byte[ENCODED_LENGTH_BYTES-1]));
    }

    @Test
    public void testDecodeArrayTooLarge() {
        assertThrows(IllegalArgumentException.class, () -> Points.decode(new byte[ENCODED_LENGTH_BYTES+1]));
    }
}

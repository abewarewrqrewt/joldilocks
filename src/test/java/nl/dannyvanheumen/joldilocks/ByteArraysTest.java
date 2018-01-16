package nl.dannyvanheumen.joldilocks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"ResultOfMethodCallIgnored", "ConstantConditions", "WeakerAccess"})
public class ByteArraysTest {

    @Test
    public void testRequireLengthExactlyNull() {
        assertThrows(NullPointerException.class, () -> ByteArrays.requireLengthExactly(null, 10));
    }

    @Test
    public void testRequireLengthExactlyActuallyLength() {
        ByteArrays.requireLengthExactly(new byte[10], 10);
    }

    @Test
    public void testRequireLengthExactlyBadLength() {
        assertThrows(IllegalArgumentException.class, () -> ByteArrays.requireLengthExactly(new byte[12], 11));
    }

    @Test
    public void testRequireLengthExactlyReturnsSameInstance() {
        final byte[] value = new byte[10];
        assertSame(value, ByteArrays.requireLengthExactly(value, 10));
    }

    @Test
    public void testConstantTimeEqualsCorrectlyReturnsTrue() {
        final byte[] value = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        assertTrue(ByteArrays.equalsConstantTime(value, value));
    }

    @Test
    public void testConstantTimeEqualsCorrectlyReturnsFalse() {
        final byte[] value = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        final byte[] falseValue = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14};
        assertFalse(ByteArrays.equalsConstantTime(value, falseValue));
    }

    @Test
    public void testConstantTimeEqualsNull() {
        assertFalse(ByteArrays.equalsConstantTime(null, new byte[0]));
        assertFalse(ByteArrays.equalsConstantTime(new byte[0], null));
    }

    @Test
    public void testConstantTimeEqualsEmpty() {
        assertTrue(ByteArrays.equalsConstantTime(new byte[0], new byte[0]));
    }
}
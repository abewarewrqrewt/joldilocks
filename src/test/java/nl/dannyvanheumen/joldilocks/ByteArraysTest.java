package nl.dannyvanheumen.joldilocks;

import org.junit.jupiter.api.Test;

import static nl.dannyvanheumen.joldilocks.ByteArrays.requireLengthAtMost;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings({"ResultOfMethodCallIgnored", "ConstantConditions", "WeakerAccess"})
public class ByteArraysTest {

    @Test
    public void testRequireLengthExactlyNull() {
        assertThrows(NullPointerException.class, () -> ByteArrays.requireLengthExactly(10, null));
    }

    @Test
    public void testRequireLengthExactlyActuallyLength() {
        ByteArrays.requireLengthExactly(10, new byte[10]);
    }

    @Test
    public void testRequireLengthExactlyBadLength() {
        assertThrows(IllegalArgumentException.class, () -> ByteArrays.requireLengthExactly(11, new byte[12]));
    }

    @Test
    public void testRequireLengthExactlyReturnsSameInstance() {
        final byte[] value = new byte[10];
        assertSame(value, ByteArrays.requireLengthExactly(10, value));
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

    @Test
    public void testRequireLengthAtMostNullArray() {
        assertThrows(NullPointerException.class, () -> requireLengthAtMost(10, null));
    }

    @Test
    public void testRequireLengthAtMostExact() {
        final byte[] d = new byte[10];
        assertSame(d, requireLengthAtMost(10, d));
    }

    @Test
    public void testRequireLengthAtMostValueIsOver() {
        assertThrows(IllegalArgumentException.class, () -> requireLengthAtMost(9, new byte[10]));
    }

    @Test
    public void testRequireLengthAtMostValueIsLower() {
        final byte[] d = new byte[10];
        assertSame(d, requireLengthAtMost(11, d));
    }
}

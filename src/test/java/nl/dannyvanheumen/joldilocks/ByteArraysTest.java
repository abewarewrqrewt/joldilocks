package nl.dannyvanheumen.joldilocks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"ResultOfMethodCallIgnored", "ConstantConditions", "WeakerAccess"})
public class ByteArraysTest {

    @Test
    public void testRequireLengthExactlyNull() {
        assertThrows(NullPointerException.class, () -> {
            ByteArrays.requireLengthExactly(null, 10);
        });
    }

    @Test
    public void testRequireLengthExactlyActuallyLength() {
        ByteArrays.requireLengthExactly(new byte[10], 10);
    }

    @Test
    public void testRequireLengthExactlyBadLength() {
        assertThrows(IllegalArgumentException.class, () -> {
            ByteArrays.requireLengthExactly(new byte[12], 11);
        });
    }

    @Test
    public void testRequireLengthExactlyReturnsSameInstance() {
        final byte[] value = new byte[10];
        assertSame(value, ByteArrays.requireLengthExactly(value, 10));
    }
}
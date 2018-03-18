package nl.dannyvanheumen.joldilocks;

import javax.annotation.Nonnull;
import java.security.MessageDigest;

final class ByteArrays {

    private ByteArrays() {
        // No need to instantiate utility class.
    }

    /**
     * Require length to be exactly as specified. Throws an IllegalArgumentException whenever this does not hold.
     *
     * @param length The expected exact length of the byte array.
     * @param data   The data to be verified.
     * @return Returns same data as provided only if length exactly as expected.
     */
    @Nonnull
    static byte[] requireLengthExactly(final int length, final byte[] data) {
        if (data.length != length) {
            throw new IllegalArgumentException("Length of array is expected to be exactly " + length + ", but is in fact " + data.length);
        }
        return data;
    }

    /**
     * Require length to be at most as specified. Throws an IllegalArgumentException whenever this does not hold.
     *
     * @param length The maximum length that is acceptable.
     * @param data   The array to be verified.
     * @return Returns same data as provided only if length is at most as specified.
     */
    @Nonnull
    static byte[] requireLengthAtMost(final int length, final byte[] data) {
        if (data.length > length) {
            throw new IllegalArgumentException("Length of array is expected to be at most " + length + ", but is in fact " + data.length);
        }
        return data;
    }

    /**
     * Constant-time equality test for byte-arrays.
     *
     * @param a1 Array 1 in comparison
     * @param a2 Array 2 in comparison
     * @return Returns true if arrays are equal (either both null or both have equal contents), or false otherwise.
     */
    // FIXME add unit tests, can we reasonably test constant-time-ness?
    static boolean equalsConstantTime(final byte[] a1, final byte[] a2) {
        return MessageDigest.isEqual(a1, a2);
    }
}

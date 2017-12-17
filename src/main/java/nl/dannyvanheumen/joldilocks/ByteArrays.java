package nl.dannyvanheumen.joldilocks;

import javax.annotation.Nonnull;

final class ByteArrays {

    private ByteArrays() {
        // No need to instantiate utility class.
    }

    /**
     * Require length to be exactly as specified. Throws an IllegalArgumentException whenever this does not hold.
     *
     * @param data   The data to be verified.
     * @param length The expected exact length of the byte array.
     * @return Returns same data as provided only if length exactly as expected.
     */
    @Nonnull
    static byte[] requireLengthExactly(final byte[] data, final int length) {
        if (data.length != length) {
            throw new IllegalArgumentException("Length of array is expected to be exactly " + length + ", but is in fact " + data.length);
        }
        return data;
    }
}

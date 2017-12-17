package nl.dannyvanheumen.joldilocks;

import org.bouncycastle.crypto.digests.SHA512Digest;

import javax.annotation.Nonnull;

final class Crypto {

    private static final int LENGTH_SHA512_HASH_BYTES = 64;

    private static final byte[] PREFIX_DERIVEPK = new byte[] { 'd', 'e', 'r', 'i', 'v', 'e', 'p', 'k'};

    private Crypto() {
        // No need to instantiate utility class.
    }

    @Nonnull
    static byte[] pseudoRandomFunction(final byte[] input) {
        final byte[] result = new byte[LENGTH_SHA512_HASH_BYTES];
        final SHA512Digest digest = new SHA512Digest();
        digest.update(PREFIX_DERIVEPK, 0, PREFIX_DERIVEPK.length);
        digest.update(input, 0, input.length);
        digest.doFinal(result, 0);
        return result;
    }
}

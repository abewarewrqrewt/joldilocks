package nl.dannyvanheumen.joldilocks;

import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.digests.SHAKEDigest;

import javax.annotation.Nonnull;

/**
 * Cryptographically oriented utility functions.
 */
final class Crypto {

    private static final int LENGTH_SHA512_HASH_BYTES = 64;

    private static final int LENGTH_SHAKE_256_BITS = 256;

    private static final byte[] PREFIX_DERIVEPK = new byte[]{'d', 'e', 'r', 'i', 'v', 'e', 'p', 'k'};

    private Crypto() {
        // No need to instantiate utility class.
    }

    // TODO Consider renaming this as it is used to derive the secret key from input bytes.
    @Nonnull
    static byte[] pseudoRandomFunction(final byte[] input) {
        final byte[] result = new byte[LENGTH_SHA512_HASH_BYTES];
        final SHA512Digest digest = new SHA512Digest();
        digest.update(PREFIX_DERIVEPK, 0, PREFIX_DERIVEPK.length);
        digest.update(input, 0, input.length);
        digest.doFinal(result, 0);
        return result;
    }

    @Nonnull
    static byte[] shake256(final byte[] data, final int outputSize) {
        final SHAKEDigest shake256 = new SHAKEDigest(LENGTH_SHAKE_256_BITS);
        shake256.update(data, 0, data.length);
        final byte[] digest = new byte[outputSize];
        shake256.doFinal(digest, 0, outputSize);
        return digest;
    }
}

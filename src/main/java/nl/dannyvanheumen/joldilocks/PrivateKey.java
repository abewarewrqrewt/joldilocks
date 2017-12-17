package nl.dannyvanheumen.joldilocks;

import javax.annotation.Nonnull;

import static nl.dannyvanheumen.joldilocks.ByteArrays.requireLengthExactly;
import static nl.dannyvanheumen.joldilocks.Crypto.pseudoRandomFunction;

/**
 * Container for the private key data.
 */
// TODO Consider if we want to pass on the original byte array, or create copies such that the original container cannot be modified.
final class PrivateKey {

    // FIXME assign correct lengths for secret, public and symmetric parts.
    private static final int LENGTH_SECRET_KEY_BYTES = 0;
    private static final int LENGTH_PUBLIC_KEY_BYTES = 0;
    private static final int LENGTH_SYMMETRIC_KEY_BYTES = 0;

    private final byte[] secretKey;
    private final byte[] publicKey;
    private final byte[] symmetricKey;

    private PrivateKey(final byte[] secretKey, final byte[] publicKey, final byte[] symmetricKey) {
        this.secretKey = requireLengthExactly(secretKey, LENGTH_SECRET_KEY_BYTES);
        this.publicKey = requireLengthExactly(publicKey, LENGTH_PUBLIC_KEY_BYTES);
        this.symmetricKey = requireLengthExactly(symmetricKey, LENGTH_SYMMETRIC_KEY_BYTES);
    }

    @Nonnull
    static PrivateKey deriveFromSymmetricKey(final byte[] symmetricKey) {
        requireLengthExactly(symmetricKey, LENGTH_SYMMETRIC_KEY_BYTES);
        final byte[] result = pseudoRandomFunction(symmetricKey);
        // FIXME derive secret part
        // FIXME derive public part
        return new PrivateKey(null, null, symmetricKey);
    }

    @Nonnull
    byte[] secretKey() {
        return this.secretKey.clone();
    }

    @Nonnull
    byte[] publicKey() {
        return this.publicKey.clone();
    }

    @Nonnull
    byte[] symmetricKey() {
        return this.symmetricKey.clone();
    }
}

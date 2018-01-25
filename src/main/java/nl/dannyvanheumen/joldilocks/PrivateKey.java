package nl.dannyvanheumen.joldilocks;

import javax.annotation.Nonnull;

import java.math.BigInteger;

import static java.util.Objects.requireNonNull;
import static nl.dannyvanheumen.joldilocks.ByteArrays.requireLengthExactly;
import static nl.dannyvanheumen.joldilocks.Crypto.pseudoRandomFunction;

/**
 * Container for the private key data.
 */
// TODO Consider if we want to pass on the original byte array, or create copies such that the original container cannot be modified.
final class PrivateKey {

    // FIXME assign correct lengths for secret, public and symmetric parts.
    private static final int LENGTH_SECRET_KEY_BYTES = 0;
    private static final int LENGTH_SYMMETRIC_KEY_BYTES = 0;

    private final BigInteger secretKey;
    private final Point publicKey;
    private final BigInteger symmetricKey;

    private PrivateKey(final BigInteger secretKey, final Point publicKey, final BigInteger symmetricKey) {
        this.secretKey = requireNonNull(secretKey);
        this.publicKey = requireNonNull(publicKey);
        this.symmetricKey = requireNonNull(symmetricKey);
    }

    @Nonnull
    static PrivateKey deriveFromSymmetricKey(final byte[] symmetricKeyBytes) {
        final BigInteger symmetricKey = new BigInteger(requireLengthExactly(symmetricKeyBytes, LENGTH_SYMMETRIC_KEY_BYTES));
        final byte[] secretKeyBytes = requireLengthExactly(pseudoRandomFunction(symmetricKeyBytes), LENGTH_SECRET_KEY_BYTES);
        final BigInteger secretKey = new BigInteger(secretKeyBytes).mod(Ed448.MODULUS);
        final Point publicKey = Ed448.multiplyByBase(secretKey);
        return new PrivateKey(secretKey, publicKey, symmetricKey);
    }

    @Nonnull
    BigInteger secretKey() {
        return this.secretKey;
    }

    @Nonnull
    Point publicKey() {
        return this.publicKey;
    }

    @Nonnull
    BigInteger symmetricKey() {
        return this.symmetricKey;
    }
}

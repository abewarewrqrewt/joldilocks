package nl.dannyvanheumen.joldilocks;

import javax.annotation.Nonnull;
import java.math.BigInteger;

import static java.util.Objects.requireNonNull;
import static nl.dannyvanheumen.joldilocks.ByteArrays.requireLengthExactly;
import static nl.dannyvanheumen.joldilocks.Ed448.SYMMETRIC_KEY_LENGTH_BYTES;
import static nl.dannyvanheumen.joldilocks.Ed448.generateSecretScalar;
import static nl.dannyvanheumen.joldilocks.Ed448.multiplyByBase;

/**
 * Ed448 key pair based on EdDSA.
 */
public final class Ed448KeyPair {

    private final byte[] symmetricKey;
    private final BigInteger secretKey;
    private final Point publicKey;

    private Ed448KeyPair(final byte[] symmetricKey, final BigInteger secretKey, final Point publicKey) {
        this.symmetricKey = requireLengthExactly(SYMMETRIC_KEY_LENGTH_BYTES, symmetricKey);
        this.secretKey = requireNonNull(secretKey);
        this.publicKey = requireNonNull(publicKey);
    }

    @Nonnull
    public static Ed448KeyPair create(final byte[] symmetricKey) {
        final BigInteger secretKey = generateSecretScalar(symmetricKey);
        final Point publicKey = multiplyByBase(secretKey);
        return new Ed448KeyPair(symmetricKey, secretKey, publicKey);
    }

    @Nonnull
    byte[] getSymmetricKey() {
        return symmetricKey;
    }

    @Nonnull
    BigInteger getSecretKey() {
        return secretKey;
    }

    @Nonnull
    public Point getPublicKey() {
        return publicKey;
    }

    @Nonnull
    public byte[] sign(final byte[] context, final byte[] message) {
        return Ed448.sign(this.symmetricKey, context, message);
    }
}

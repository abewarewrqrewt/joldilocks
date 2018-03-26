package nl.dannyvanheumen.joldilocks;

import javax.annotation.Nonnull;
import java.math.BigInteger;

import static nl.dannyvanheumen.joldilocks.Ed448.multiplyByBase;
import static nl.dannyvanheumen.joldilocks.Scalars.requireValidSourceData;

/**
 * In-memory representation of Ed448 key pair.
 *
 * This class represents a generated Ed448 key pair. For this reason we do not take the final step of converting both
 * public and private keys into byte arrays.
 */
// FIXME Should we include the symmetric key that is used during generation?
// FIXME what extra verification should we add for verifying the private key during construction.
public final class KeyPair {

    private final BigInteger privateKey;
    private final Point publicKey;

    public KeyPair(final BigInteger privateKey) {
        this.privateKey = requireValidSourceData(privateKey);
        this.publicKey = multiplyByBase(privateKey);
    }

    @Nonnull
    public Point getPublicKey() {
        return this.publicKey;
    }

    @Nonnull
    public byte[] sign(final byte[] context, final byte[] message) {
        return Ed448.sign(this.privateKey, context, message);
    }
}

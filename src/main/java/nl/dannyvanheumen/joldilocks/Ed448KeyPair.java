package nl.dannyvanheumen.joldilocks;

import java.math.BigInteger;

import static java.util.Objects.requireNonNull;

/**
 * In-memory representation of Ed448 key pair.
 *
 * This class represents a generated Ed448 key pair. For this reason we do not take the final step of converting both
 * public and private keys into byte arrays.
 */
public final class Ed448KeyPair {

    private final BigInteger secretKey;
    private final Point publicKey;

    Ed448KeyPair(final BigInteger secretKey, final Point publicKey) {
        this.secretKey = requireNonNull(secretKey);
        this.publicKey = requireNonNull(publicKey);
    }
}

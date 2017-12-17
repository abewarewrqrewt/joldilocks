package nl.dannyvanheumen.joldilocks;

import javax.annotation.Nonnull;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Ed448-Goldilocks implementation.
 */
public final class Ed448 {

    // FIXME Is this correct? For Extended representation we need to define an 'a'. We assume 1 for now. (This is probably wrong, somewhere in the Ed448-Goldilocks paper it is described that -1 is used.)
    static final BigInteger A = BigInteger.ONE;

    static final int LENGTH_SCALAR_BITS = 448;
    static final int LENGTH_SCALAR_BYTES = LENGTH_SCALAR_BITS / 8;

    /**
     * Cofactor of Ed448-Goldilocks curve.
     */
    static final int COFACTOR = 4;

    /**
     * Prime p of the Ed448-Goldilocks curve. (Used as modulus.)
     */
    static final BigInteger MODULUS = new BigInteger("fffffffffffffffffffffffffffffffffffffffffffffffffffffffeffffffffffffffffffffffffffffffffffffffffffffffffffffffff", 16);

    /**
     * Prime order q.
     * NOTE: 4q is the order of the Ed448-Goldilocks curve.
     * <p>
     * The order of its twist is: 4 * (2**446 - 0x335dc163bb124b65129c96fde933d8d723a70aadc873d6d54a7bb0d). Not sure if that is actually used yet.
     */
    static final BigInteger PRIME_ORDER = new BigInteger("3fffffffffffffffffffffffffffffffffffffffffffffffffffffff7cca23e9c44edb49aed63690216cc2728dc58f552378c292ab5844f3", 16);

    /**
     * Edwards constant d.
     */
    static final BigInteger D = BigInteger.valueOf(-39081);

    /**
     * Generate symmetric key.
     *
     * @param random Instance of {@link SecureRandom}.
     * @param dst The destination for the random bytes.
     */
    public static void generateSymmetricKey(final SecureRandom random, final byte[] dst) {
        random.nextBytes(dst);
    }
}

package nl.dannyvanheumen.joldilocks;

import java.math.BigInteger;

/**
 * Ed448-Goldilocks implementation.
 */
public final class Ed448 {

    static final int LENGTH_SCALAR_BITS = 448;
    static final int LENGTH_SCALAR_BYTES = LENGTH_SCALAR_BITS / 8;

    /**
     * Cofactor of Ed448-Goldilocks curve.
     */
    static final int COFACTOR = 4;

    /**
     * Base point for Ed448-Goldilocks.
     */
    static final Point P = ExtendedPoint.fromEdwards(
            new BigInteger("224580040295924300187604334099896036246789641632564134246125461686950415467406032909029192869357953282578032075146446173674602635247710"),
            new BigInteger("298819210078481492676017930443930673437544040154080242095928241372331506189835876003536878655418784733982303233503462500531545062832660")
    );

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
     * a parameter of the untwisted Edwards curve formula.
     * <p>
     * NOTE: Page 5 of Decaf paper describes how they take "Untwisted" (Edwards Curve [sic]) to mean a = 1.
     */
    static final BigInteger A = BigInteger.ONE;

    /**
     * Edwards constant d.
     */
    static final BigInteger D = BigInteger.valueOf(-39081);
}

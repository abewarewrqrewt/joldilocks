package nl.dannyvanheumen.joldilocks;

import javax.annotation.Nonnull;
import java.math.BigInteger;

import static java.math.BigInteger.ONE;

/**
 * Ed448-Goldilocks implementation.
 *
 * Ed448 is an untwisted Edwards curve (form: y^2 + x^2 = 1 + d * x^2 * y^2)
 */
public final class Ed448 {

    /**
     * Cofactor of Ed448-Goldilocks curve.
     */
    static final BigInteger COFACTOR = BigInteger.valueOf(4L);

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
    public static final BigInteger Q = new BigInteger("3fffffffffffffffffffffffffffffffffffffffffffffffffffffff7cca23e9c44edb49aed63690216cc2728dc58f552378c292ab5844f3", 16);

    /**
     * a parameter of the untwisted Edwards curve formula.
     * <p>
     * NOTE: Page 5 of Decaf paper describes how they take "Untwisted" (Edwards Curve [sic]) to mean a = 1.
     */
    static final BigInteger A = BigInteger.ONE;

    /**
     * Edwards constant d.
     */
    static final BigInteger D = BigInteger.valueOf(-39081L);

    /**
     * Base point for Ed448-Goldilocks.
     *
     * NOTE: this is the base point in 4E.
     */
    public static final ExtendedPoint P = ExtendedPoint.fromEdwards(
//     X(P): 224580040295924300187604334099896036246789641632564134246125461686950415467406032909029192869357953282578032075146446173674602635247710
            new BigInteger("224580040295924300187604334099896036246789641632564134246125461686950415467406032909029192869357953282578032075146446173674602635247710", 10),
//     Y(P): 298819210078481492676017930443930673437544040154080242095928241372331506189835876003536878655418784733982303233503462500531545062832660
            new BigInteger("298819210078481492676017930443930673437544040154080242095928241372331506189835876003536878655418784733982303233503462500531545062832660", 10)
    );

    /**
     * Verify that given point is contained in the curve.
     *
     * @param p The point to verify.
     * @return Returns true if it is contained in the curve.
     */
    // TODO: According to otrv4 spec, we can verify point is on the curve with: Given point X = (x,y), check X != Identity & x in range [0, q-1] & y in range [0, q-1] & q & X = Identity.
    // (https://github.com/otrv4/otrv4/blob/master/otrv4.md#verifying-that-a-point-is-on-the-curve)
    static boolean contains(final Point p) {
        // TODO: Check if we really need so many mod-operations
        final BigInteger x = p.x();
        final BigInteger xx = x.multiply(x).mod(MODULUS);
        final BigInteger y = p.y();
        final BigInteger yy = y.multiply(y).mod(MODULUS);

        final BigInteger dxx = D.multiply(xx).mod(MODULUS);
        final BigInteger dxxyy = dxx.multiply(yy).mod(MODULUS);

        final BigInteger left =  xx.add(yy).mod(MODULUS);
        final BigInteger right= ONE.add(dxxyy).mod(MODULUS);
        return left.compareTo(right) == 0;
    }

    /**
     * Multiply scalar (e.g. secret key) by (pre-computed) base in order to derive a new point on the curve.
     *
     * @param scalar The scalar value, e.g. secret key.
     * @return Returns a point that is the result of the multiplication with the base.
     */
    // FIXME Use (and implemented) pre-computed base for ECDH calculations. (Is this correct?)
    @Nonnull
    static Point multiplyByBase(final BigInteger scalar) {
        return null;
    }
}

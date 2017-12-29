package nl.dannyvanheumen.joldilocks;

import javax.annotation.Nonnull;
import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static nl.dannyvanheumen.joldilocks.Ed448.D;

/**
 * Curve is an untwisted Edwards curve (form: y^2 + x^2 = 1 + d * x^2 * y^2)
 */
final class Curve {

    /**
     * Verify that given point is contained in the curve.
     *
     * @param p The point to verify.
     * @return Returns true if it is contained in the curve.
     */
    static boolean contains(final Point p) {
        final BigInteger x = p.x();
        final BigInteger xx = x.multiply(x);
        final BigInteger y = p.y();
        final BigInteger yy = y.multiply(y);

        // (1 + d*x^2*y^2) - x^2 - y^2 = 0
        final BigInteger result = ONE.add(D.multiply(xx).multiply(yy)).subtract(xx).subtract(yy);
        return BigInteger.ZERO.equals(result);
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

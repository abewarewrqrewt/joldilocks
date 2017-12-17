package nl.dannyvanheumen.joldilocks;

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
}

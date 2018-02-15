package nl.dannyvanheumen.joldilocks;

import javax.annotation.Nonnull;
import java.math.BigInteger;
import java.util.Objects;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TWO;
import static java.math.BigInteger.ZERO;
import static java.util.Objects.requireNonNull;
import static nl.dannyvanheumen.joldilocks.Ed448.A;
import static nl.dannyvanheumen.joldilocks.Ed448.D;
import static nl.dannyvanheumen.joldilocks.Ed448.MODULUS;
import static nl.dannyvanheumen.joldilocks.Scalars.requireNotZero;

/**
 * ExtendedPoint is a point in Extended Homogeneous Projective representation.
 */
// TODO: Use of BigInteger coords, NOT CONSTANT TIME.
final class ExtendedPoint implements Point {

    /**
     * Identity point in Extended Homogeneous Projective representation.
     */
    private static final ExtendedPoint IDENTITY = new ExtendedPoint(ZERO, ONE, ONE, ZERO);

    private static final BigInteger MINUS_TWO = BigInteger.valueOf(-2);
    private static final BigInteger FOUR = BigInteger.valueOf(4);

    /**
     * Extended Homogeneous representation parameter X.
     */
    private final BigInteger x;

    /**
     * Extended Homogeneous representation parameter Y.
     */
    private final BigInteger y;

    /**
     * Extended Homogeneous representation parameter Z.
     */
    private final BigInteger z;

    /**
     * Extended Homogeneous representation parameter T.
     */
    private final BigInteger t;

    /**
     * Convert normal (Edwards form) point coordinates into Extended coordinate representation.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @return Returns the Extended point representation.
     */
    @Nonnull
    static ExtendedPoint fromEdwards(final BigInteger x, final BigInteger y) {
        if (ZERO.equals(x) && ONE.equals(y)) {
            return IDENTITY;
        }
        return new ExtendedPoint(x, y, ONE, x.multiply(y));
    }

    /**
     * Decaf- decode a point.
     * <p>
     * Decoding formula implemented from reference in "Decaf - Eliminating cofactors through point compression",
     * Appendix A.2.
     *
     * @param s Compressed-point scalar value.
     * @return Returns the point in Extended Homogeneous Coordinates representation.
     */
    // FIXME verify that these are correct. It seems that this algorithm needs to be applied on Jacobi Quartic-type values.
    @Nonnull
    static ExtendedPoint decodeDecafCompressedPoint(final BigInteger s) {
        if (!s.equals(s.abs())) {
            throw new IllegalArgumentException("Illegal value for Decaf compressed point.");
        }
        final BigInteger x = TWO.multiply(s);
        final BigInteger ss = s.multiply(s);
        final BigInteger z = ONE.add(A.multiply(ss));
        final BigInteger u = z.multiply(z).subtract(FOUR.multiply(D).multiply(ss));
        final BigInteger testV = u.multiply(ss);
        final BigInteger prelimV;
        if (testV.compareTo(ZERO) == 0) {
            prelimV = ZERO;
        } else {
            final BigInteger[] sqrtV = testV.sqrtAndRemainder();
            if (!sqrtV[1].equals(ZERO)) {
                throw new IllegalArgumentException("Illegal value for Decaf compressed point.");
            }
            prelimV = sqrtV[0];
        }
        final BigInteger v = u.multiply(prelimV).compareTo(ZERO) < 0 ? prelimV.negate() : prelimV;
        final BigInteger prelimW = v.multiply(s).multiply(TWO.subtract(z));
        final BigInteger w = s.equals(ZERO) ? prelimW.add(ONE) : prelimW;
        final BigInteger y = w.multiply(z);
        final BigInteger t = w.multiply(x);
        return new ExtendedPoint(x, y, z, t);
    }

    /**
     * Private constructor for instantiating Extended Homogeneous Projective representation of point with raw values.
     *
     * @param x parameter X
     * @param y parameter Y
     * @param z parameter Z
     * @param t parameter T
     */
    private ExtendedPoint(final BigInteger x, final BigInteger y, final BigInteger z, final BigInteger t) {
        if (!t.equals(x.multiply(y).divide(z))) {
            throw new IllegalArgumentException("Incorrect composition of scalars for ExtendedPoint state.");
        }
        this.x = requireNonNull(x);
        this.y = requireNonNull(y);
        this.z = requireNotZero(z);
        this.t = requireNonNull(t);
    }

    // TODO Equals implementation is NOT CONSTANT TIME
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ExtendedPoint other = (ExtendedPoint) o;
        //     # x1 / z1 == x2 / z2  <==>  x1 * z2 == x2 * z1
        //    if (P[0] * Q[2] - Q[0] * P[2]) % p != 0:
        //        return False
        //    if (P[1] * Q[2] - Q[1] * P[2]) % p != 0:
        //        return False
        //    return True
        return ZERO.equals(this.x.multiply(other.z).subtract(other.x.multiply(this.z)).mod(MODULUS))
            & ZERO.equals(this.y.multiply(other.z).subtract(other.y.multiply(this.z)).mod(MODULUS));
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, t);
    }

    @Override
    @Nonnull
    public BigInteger x() {
        return this.x.divide(this.z);
    }

    @Override
    @Nonnull
    public BigInteger y() {
        return this.y.divide(this.z);
    }

    @Override
    @Nonnull
    public ExtendedPoint negate() {
        return new ExtendedPoint(this.x.negate(), this.y, this.z, this.t.negate());
    }

    @Override
    @Nonnull
    public ExtendedPoint add(final Point other) {
        final ExtendedPoint p2 = Points.toExtended(other);
        final BigInteger a = this.x.multiply(p2.x);
        final BigInteger b = this.y.multiply(p2.y);
        final BigInteger c = this.t.multiply(D).multiply(p2.t);
        final BigInteger d = this.z.multiply(p2.z);
        final BigInteger e = this.x.add(this.y).multiply(p2.x.add(p2.y)).subtract(a).subtract(b);
        final BigInteger f = d.subtract(c);
        final BigInteger g = d.add(c);
        final BigInteger h = b.subtract(A.multiply(a));
        final BigInteger resultX = e.multiply(f);
        final BigInteger resultY = g.multiply(h);
        //final BigInteger resultT = e.multiply(h);
        final BigInteger resultZ = f.multiply(g);
        // After having finished the calculation, reduce the values.
        final BigInteger reducedX = resultX.divide(resultZ);
        final BigInteger reducedY = resultY.divide(resultZ);
        return new ExtendedPoint(reducedX, reducedY, ONE, reducedX.multiply(reducedY));
    }

    /**
     * Double current point (add point to itself).
     *
     * @return Returns new point.
     */
    @Override
    @Nonnull
    public ExtendedPoint doubling() {
        return this.add(this);
    }

    /**
     * Decaf-encode a point.
     * <p>
     * Encoding formula implemented from reference in "Decaf - Eliminating cofactors through point compression",
     * Appendix A.1.
     *
     * @return Returns compressed-point scalar value.
     */
    // FIXME verify that these are correct. It seems that this algorithm needs to be applied on Jacobi Quartic-type values.
    @Nonnull
    BigInteger encodeDecaf() {
        final BigInteger aSubtractD = A.subtract(D);
        final BigInteger prelimR = ONE.divide((aSubtractD.multiply(this.z.add(this.y)).multiply(this.z.subtract(this.y))));
        final BigInteger u = aSubtractD.multiply(prelimR);
        final BigInteger r = MINUS_TWO.multiply(u).multiply(this.z).compareTo(ZERO) < 0 ? prelimR.negate() : prelimR;
        // Part 's' of the computation is split up in its nested components: s1, s2, s3. 's3' being the innermost part
        // of the computation. This is done for readability of the code.
        final BigInteger s3 = A.multiply(this.z).multiply(this.x).subtract(D.multiply(this.y).multiply(this.t));
        final BigInteger s2 = r.multiply(s3).add(this.y);
        final BigInteger s1 = u.multiply(s2).divide(A);
        return s1.abs();
    }

    @Override
    public String toString() {
        return String.format("(%s, %s, %s, %s) = (%s, %s)", this.x, this.y, this.z, this.t, x(), y());
    }
}

package nl.dannyvanheumen.joldilocks;

import javax.annotation.Nonnull;
import java.math.BigInteger;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static nl.dannyvanheumen.joldilocks.BigIntegers.requireNotZero;

/**
 * ExtendedPoint is a point in Extended Homogenous Projective representation.
 */
// TODO Consider replacing with custom bigint class for computation speed.
// TODO Use of BigInteger coords, NOT CONSTANT TIME.
// TODO Add base point when necessary.
final class ExtendedPoint {

    /**
     * Identity point.
     */
    // FIXME double-check if identity point is correct
    private static final ExtendedPoint IDENTITY = new ExtendedPoint(BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO);

    private final BigInteger x;
    private final BigInteger y;
    private final BigInteger z;
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
        return new ExtendedPoint(x, y, BigInteger.ONE, x.multiply(y));
    }

    private ExtendedPoint(final BigInteger x, final BigInteger y, final BigInteger z, final BigInteger t) {
        if (!t.equals(x.multiply(y))) {
            throw new IllegalArgumentException("Incorrect composition of ExtendedPoint state.");
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
        final ExtendedPoint extendedPoint = (ExtendedPoint) o;
        return Objects.equals(x, extendedPoint.x) &&
                Objects.equals(y, extendedPoint.y) &&
                Objects.equals(z, extendedPoint.z) &&
                Objects.equals(t, extendedPoint.t);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, t);
    }

    // TODO Is this needed? (Right now at least, ExtendedPoint is immutable.)
    @Nonnull
    ExtendedPoint copy() {
        return new ExtendedPoint(this.x, this.y, this.z, this.t);
    }

    /**
     * Get X coordinate of this point.
     *
     * @return Returns X coordinate.
     */
    @Nonnull
    BigInteger x() {
        return this.x.divide(this.z);
    }

    /**
     * Get Y coordinate of this point.
     *
     * @return Returns Y coordinate.
     */
    @Nonnull
    BigInteger y() {
        return this.y.divide(this.z);
    }

    /**
     * Invert the point by negating the first coordinate.
     *
     * @return Returns the inverted point.
     */
    @Nonnull
    ExtendedPoint negate() {
        return new ExtendedPoint(this.x.negate(), this.y, this.z, this.t);
    }

    /**
     * Add two poins together.
     *
     * @param p2 The second point.
     * @return Returns addition of this and the provided point.
     */
    @Nonnull
    ExtendedPoint add(final ExtendedPoint p2) {
        final BigInteger a = this.x.multiply(p2.x);
        final BigInteger b = this.y.multiply(p2.y);
        final BigInteger c = this.t.multiply(Ed448.D).multiply(p2.t);
        final BigInteger d = this.z.multiply(p2.z);
        final BigInteger e = this.x.add(this.y).multiply(p2.x.add(p2.y)).subtract(a).subtract(b);
        final BigInteger f = d.subtract(c);
        final BigInteger g = d.add(c);
        final BigInteger h = b.subtract(Ed448.A.multiply(a));
        final BigInteger resultX = e.multiply(f);
        final BigInteger resultY = g.multiply(h);
        final BigInteger resultT = e.multiply(h);
        final BigInteger resultZ = f.multiply(g);
        return new ExtendedPoint(resultX, resultY, resultZ, resultT);
    }
}

package nl.dannyvanheumen.joldilocks;

import javax.annotation.Nonnull;
import java.math.BigInteger;
import java.util.Objects;

import static java.math.BigInteger.ONE;
import static java.util.Objects.requireNonNull;
import static nl.dannyvanheumen.joldilocks.Ed448.D;
import static nl.dannyvanheumen.joldilocks.Ed448.MODULUS;

/**
 * AffinePoint is the pure representation of an Edwards point. This implementation does not contain any optimizations
 * for point arithmetic.
 */
final class AffinePoint implements Point {

    private final BigInteger x;
    private final BigInteger y;

    AffinePoint(final BigInteger x, final BigInteger y) {
        this.x = requireNonNull(x);
        this.y = requireNonNull(y);
    }

    @Nonnull
    @Override
    public BigInteger x() {
        return this.x;
    }

    @Nonnull
    @Override
    public BigInteger y() {
        return this.y;
    }

    @Nonnull
    @Override
    public AffinePoint negate() {
        return new AffinePoint(this.x.negate(), this.y);
    }

    @Nonnull
    @Override
    public AffinePoint add(final Point p) {
        if (p instanceof AffinePoint) {
            return add((AffinePoint) p);
        } else {
            return add(new AffinePoint(p.x(), p.y()));
        }
    }

    @Nonnull
    AffinePoint add(final AffinePoint other) {
        //    x3 = (x1*y2+y1*x2)/(c*(1+d*x1*x2*y1*y2))
        final BigInteger resultX = this.x.multiply(other.y).add(this.y.multiply(other.x)).multiply(
            ONE.add(D.multiply(this.x).multiply(other.x).multiply(this.y).multiply(other.y)).modInverse(MODULUS)
        ).mod(MODULUS);
        //    y3 = (y1*y2-x1*x2)/(c*(1-d*x1*x2*y1*y2))
        final BigInteger resultY = this.y.multiply(other.y).subtract(this.x.multiply(other.x)).multiply(
            ONE.subtract(D.multiply(this.x).multiply(other.x).multiply(this.y).multiply(other.y)).modInverse(MODULUS)
        ).mod(MODULUS);
        return new AffinePoint(resultX, resultY);
    }

    @Nonnull
    @Override
    public AffinePoint doubling() {
        return this.add(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AffinePoint that = (AffinePoint) o;
        return Objects.equals(x, that.x) &&
            Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ')';
    }
}

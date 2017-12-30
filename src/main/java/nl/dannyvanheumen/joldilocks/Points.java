package nl.dannyvanheumen.joldilocks;

import javax.annotation.Nonnull;
import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

/**
 * (Internal) utility methods for managing points and point representation conversions.
 */
final class Points {

    /**
     * Point representing the identity of the curve.
     */
    private static final Point IDENTITY = new IdentityPoint();

    private Points() {
        // No need to instantiate utility class.
    }

    /**
     * Acquire Identity point.
     *
     * @return Returns identity point.
     */
    @Nonnull
    static Point identity() {
        return IDENTITY;
    }

    /**
     * Convert arbitrary Edwards point type to Extended Homogeneous Projective point representation.
     *
     * @param other Some other Edwards point.
     * @return Returns ExtendedPoint with same Edwards coordinates as input point.
     */
    @Nonnull
    static ExtendedPoint toExtended(final Point other) {
        if (other instanceof ExtendedPoint) {
            return (ExtendedPoint) other;
        }
        return ExtendedPoint.fromEdwards(other.x(), other.y());
    }

    /**
     * Class that implements an identity point.
     */
    private static final class IdentityPoint implements Point {

        @Nonnull
        @Override
        public BigInteger x() {
            return ZERO;
        }

        @Nonnull
        @Override
        public BigInteger y() {
            return ONE;
        }

        @Nonnull
        @Override
        public Point negate() {
            return this;
        }

        @Nonnull
        @Override
        public Point add(final Point p) {
            return p;
        }

        @Nonnull
        @Override
        public Point doubling() {
            return this;
        }
    }
}

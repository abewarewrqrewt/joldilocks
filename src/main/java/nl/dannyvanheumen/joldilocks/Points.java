package nl.dannyvanheumen.joldilocks;

import javax.annotation.Nonnull;

/**
 * (Internal) utility methods for managing points and point representation conversions.
 */
final class Points {

    private Points() {
        // No need to instantiate utility class.
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
}

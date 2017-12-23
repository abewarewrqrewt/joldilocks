package nl.dannyvanheumen.joldilocks;

import javax.annotation.Nonnull;
import java.math.BigInteger;

/**
 * The Point interface represents the generic Point on the Ed448-Goldilocks curve.
 * <p>
 * Several implementations of the Point interface exist for efficiency reasons.
 *
 * @param <P> The specific type of point implementation.
 */
// TODO consider making public
interface Point<P extends Point> {

    /**
     * The X-coordinate of the point.
     * <p>
     * Note that this need not be a simple memory retrieval (constant-time), depending on the type of representation of a point.
     *
     * @return Returns the scalar value representing the X-coordinate.
     */
    @Nonnull
    BigInteger x();

    /**
     * The Y-coordinate of the point.
     * <p>
     * Note that this need not be a simple memory retrieval (constant-time), depending on the type of representation of a point.
     *
     * @return Returns the scalar value representing the Y-coordinate.
     */
    @Nonnull
    BigInteger y();

    /**
     * Invert the point (by negating the first coordinate).
     *
     * @return Returns the inverted point.
     */
    @Nonnull
    P negate();

    /**
     * Add two points together.
     *
     * @param p The second point.
     * @return Returns addition of this and the provided point.
     */
    @Nonnull
    P add(P p);

    /**
     * Double the point (by multiplying it with itself).
     *
     * @return Returns the doubled point.
     */
    @Nonnull
    P doubling();
}

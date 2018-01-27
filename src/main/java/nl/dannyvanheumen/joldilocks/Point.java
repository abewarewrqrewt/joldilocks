package nl.dannyvanheumen.joldilocks;

import javax.annotation.Nonnull;
import java.math.BigInteger;

import static java.math.BigInteger.ZERO;
import static nl.dannyvanheumen.joldilocks.Ed448.MODULUS;
import static nl.dannyvanheumen.joldilocks.Points.LEAST_SIGNIFICANT_BIT_OF_BYTE;

/**
 * The Point interface represents the generic Point on the Ed448-Goldilocks curve.
 * <p>
 * Implementations MUST be immutable.
 * <p>
 * Several implementations of the Point interface exist for efficiency reasons.
 */
public interface Point {

    /**
     * Length of encoded point in bytes.
     */
    int ENCODED_LENGTH_BYTES = 57;

    /**
     * The Edwards X-coordinate of the point.
     * <p>
     * Note that this need not be a simple memory retrieval (constant-time), depending on the type of representation of a point.
     *
     * @return Returns the scalar value representing the Edwards X-coordinate.
     */
    @Nonnull
    BigInteger x();

    /**
     * The Edwards Y-coordinate of the point.
     * <p>
     * Note that this need not be a simple memory retrieval (constant-time), depending on the type of representation of a point.
     *
     * @return Returns the scalar value representing the Edwards Y-coordinate.
     */
    @Nonnull
    BigInteger y();

    /**
     * Invert the point (by negating the first coordinate).
     *
     * @return Returns the inverted point.
     */
    @Nonnull
    Point negate();

    /**
     * Add two points together.
     *
     * @param p The second point.
     * @return Returns addition of this and the provided point.
     */
    @Nonnull
    Point add(Point p);

    /**
     * Double the point (naively, by adding it to itself).
     *
     * @return Returns the doubled point.
     */
    @Nonnull
    Point doubling();

    /**
     * Multiply the point with given scalar value.
     *
     * @param scalar the scalar value
     * @return Returns new point that is result of multiplication.
     */
    // TODO Consider replacing with Montgomery Ladder or some other safer multiplication algorithm.
    @Nonnull
    default Point multiply(final BigInteger scalar) {
        //
        // Current implementation is based on Double-and-Add, as described in Wikipedia.
        // https://en.wikipedia.org/wiki/Elliptic_curve_point_multiplication#Double-and-add
        //
        //  N ← P
        //  Q ← 0
        //  for i from 0 to m do
        //     if di = 1 then
        //         Q ← point_add(Q, N)
        //     N ← point_double(N)
        //  return Q
        Point p = this;
        Point q = Points.identity();
        for (int i = 0; i < scalar.bitLength(); i++) {
            if (scalar.testBit(i)) {
                q = q.add(p);
            }
            p = p.add(p);
        }
        return q;
    }

    /**
     * Encode Point according to RFC8032.
     *
     * @return Returns encoded point.
     */
    // FIXME Write test for encoding of Edwards point.
    @Nonnull
    default byte[] encode() {
        final BigInteger x = this.x();
        final BigInteger y = this.y();
        if (x.compareTo(ZERO) < 0 || x.compareTo(MODULUS) >= 0 || y.compareTo(ZERO) < 0 || y.compareTo(MODULUS) >= 0) {
            throw new IllegalArgumentException("Illegal point. Point cannot be encoded.");
        }
        // "A curve point (x,y), with coordinates in the range 0 <= x,y < p, is
        // coded as follows.  First, encode the y-coordinate as a little-endian
        // string of 57 octets.  The final octet is always zero.  To form the
        // encoding of the point, copy the least significant bit of the
        // x-coordinate to the most significant bit of the final octet."
        // -- RFC 8032
        final byte[] encoded = new byte[ENCODED_LENGTH_BYTES];
        Scalars.encodeLittleEndianTo(encoded, 0, y);
        final int leastSignificantBit = Scalars.encodeLittleEndian(x)[0] & LEAST_SIGNIFICANT_BIT_OF_BYTE;
        encoded[ENCODED_LENGTH_BYTES-1] |= (leastSignificantBit << 7);
        return encoded;
    }
}

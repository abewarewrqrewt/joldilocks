package nl.dannyvanheumen.joldilocks;

import javax.annotation.Nonnull;
import java.math.BigInteger;

import static java.math.BigInteger.ZERO;
import static nl.dannyvanheumen.joldilocks.Ed448.MODULUS;

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
     * Double the point (by multiplying it with itself).
     *
     * @return Returns the doubled point.
     */
    @Nonnull
    Point doubling();

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
        // FIXME Should we do a test for byte-array length to be exact? (Expected is 56 or 57 depending on type of scalar ...)
        final byte[] encoded = Scalars.encodeLittleEndian(y);
        final int leastSignificantBit = x.toByteArray()[0] & 0x1;
        encoded[ENCODED_LENGTH_BYTES-1] |= (leastSignificantBit << 7);
        return encoded;
    }

    /**
     * Decode encoded Point according to RFC8032.
     *
     * @param encodedPoint Encoded Edwards point
     * @return Returns point instance.
     */
    @Nonnull
    static Point decode(final byte[] encodedPoint) {
        // FIXME Implement point decoding. (Is this the right place for a construction method?)
        throw new UnsupportedOperationException("To be implementd");
    }
}

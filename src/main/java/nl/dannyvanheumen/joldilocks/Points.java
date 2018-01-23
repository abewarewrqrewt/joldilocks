package nl.dannyvanheumen.joldilocks;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TWO;
import static java.math.BigInteger.ZERO;
import static nl.dannyvanheumen.joldilocks.ByteArrays.requireLengthExactly;
import static nl.dannyvanheumen.joldilocks.Ed448.D;
import static nl.dannyvanheumen.joldilocks.Ed448.MODULUS;
import static nl.dannyvanheumen.joldilocks.Point.ENCODED_LENGTH_BYTES;

/**
 * Utility methods for managing points and point representation conversions.
 */
public final class Points {

    /**
     * Point representing the identity of the curve.
     */
    private static final Point IDENTITY = new IdentityPoint();

    private static final BigInteger FOUR = BigInteger.valueOf(4L);

    static final int LEAST_SIGNIFICANT_BIT_OF_BYTE = 0x01;

    private static final int MOST_SIGNIFICANT_BIT_OF_BYTE = 0x80;

    private Points() {
        // No need to instantiate utility class.
    }

    /**
     * Acquire Identity point.
     *
     * @return Returns identity point.
     */
    @Nonnull
    public static Point identity() {
        return IDENTITY;
    }

    /**
     * Require that point p is not the identity point.
     *
     * @param p point
     * @return Returns p iff not the identity point.
     * @throws IllegalArgumentException In case p is identity point.
     */
    public static Point requireNotIdentity(final Point p) {
        if (checkIdentity(p)) {
            throw new IllegalArgumentException("Point is identity.");
        }
        return p;
    }

    /**
     * Method for testing if a point is the identity point.
     *
     * @param p point
     * @return Returns true if p is identity, or false otherwise.
     */
    @CheckReturnValue
    public static boolean checkIdentity(final Point p) {
        return ZERO.equals(p.x()) && ONE.equals(p.y());
    }

    /**
     * Convert arbitrary Edwards point type to Extended Homogeneous Projective point representation.
     *
     * @param other Some other Edwards point.
     * @return Returns ExtendedPoint with same Edwards coordinates as input point.
     */
    @Nonnull
    public static ExtendedPoint toExtended(final Point other) {
        if (other instanceof ExtendedPoint) {
            return (ExtendedPoint) other;
        }
        return ExtendedPoint.fromEdwards(other.x(), other.y());
    }

    /**
     * Decode encoded Point according to RFC8032.
     *
     * @param encodedPoint Encoded Edwards point
     * @return Returns point instance.
     */
    @Nonnull
    public static Point decode(final byte[] encodedPoint) {
        requireLengthExactly(encodedPoint, ENCODED_LENGTH_BYTES);
        final int xBit = (encodedPoint[ENCODED_LENGTH_BYTES - 1] & MOST_SIGNIFICANT_BIT_OF_BYTE) >> 7;
        encodedPoint[ENCODED_LENGTH_BYTES - 1] ^= (encodedPoint[ENCODED_LENGTH_BYTES - 1] & MOST_SIGNIFICANT_BIT_OF_BYTE);
        final BigInteger y = Scalars.decodeLittleEndian(encodedPoint);
        if (y.compareTo(MODULUS) >= 0) {
            throw new IllegalArgumentException("Encoded point is illegal.");
        }
        // "Let
        //       num = y^2 - 1
        //     denom = d * y^2 - 1
        //
        // To compute the square root of (num/denom), compute the candidate root x = (num/denom)^((p+1)/4).
        // This can be done using a single modular powering for both the inversion of denom and the square root:
        //
        //     x = ((num ^ 3) * denom * (num^5 * denom^3) ^ ((p-3)/4)) (mod p)
        //
        // If
        //     denom * x^2 = num
        //
        // then the recovered x-coordinate is x. Otherwise, no square root exists, and the decoding fails."
        final BigInteger num = y.multiply(y).subtract(ONE);
        final BigInteger denom = D.multiply(y.multiply(y)).subtract(ONE);
        final BigInteger x2 = num.multiply(denom.modInverse(MODULUS));
        final BigInteger prelimX = x2.modPow(MODULUS.add(ONE).divide(FOUR), MODULUS);
        if (num.mod(MODULUS).compareTo(prelimX.multiply(prelimX).multiply(denom).mod(MODULUS)) != 0) {
            throw new IllegalArgumentException("Encoded point is illegal.");
        }
        if (prelimX.equals(ZERO) && xBit != 0) {
            throw new IllegalArgumentException("Encoded point is illegal.");
        }
        final BigInteger x = xBit == prelimX.mod(TWO).intValue() ? prelimX : MODULUS.subtract(prelimX);
        return ExtendedPoint.fromEdwards(x, y);
    }

    /**
     * Class that implements an identity point.
     */
    // TODO Should we even use this or throw it away in favor of consistent behavior? (safety)
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
        public Point multiply(final BigInteger scalar) {
            return this;
        }

        @Nonnull
        @Override
        public Point doubling() {
            return this;
        }
    }
}

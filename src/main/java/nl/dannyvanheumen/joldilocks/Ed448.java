package nl.dannyvanheumen.joldilocks;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
import static nl.dannyvanheumen.joldilocks.ByteArrays.requireLengthAtMost;
import static nl.dannyvanheumen.joldilocks.ByteArrays.requireLengthExactly;
import static nl.dannyvanheumen.joldilocks.Crypto.shake256;
import static nl.dannyvanheumen.joldilocks.Point.ENCODED_LENGTH_BYTES;
import static nl.dannyvanheumen.joldilocks.Points.decode;
import static nl.dannyvanheumen.joldilocks.Points.prune;
import static nl.dannyvanheumen.joldilocks.Scalars.decodeLittleEndian;
import static nl.dannyvanheumen.joldilocks.Scalars.encodeLittleEndian;
import static org.bouncycastle.util.Arrays.clear;
import static org.bouncycastle.util.Arrays.concatenate;
import static org.bouncycastle.util.Arrays.copyOf;
import static org.bouncycastle.util.Arrays.copyOfRange;

/**
 * Ed448-Goldilocks implementation.
 *
 * Ed448 is an untwisted Edwards curve (form: y^2 + x^2 = 1 + d * x^2 * y^2)
 */
// TODO: Need to implement precomputed base multiples to speed up computation?
public final class Ed448 {

    /**
     * Length of private key in bytes.
     */
    private static final int PRIVATE_KEY_LENGTH_BYTES = 57;

    /**
     * Digest length in bytes, applies when producing digest of the private key.
     */
    private static final int SIGNING_DIGEST_LENGTH_BYTES = 114;

    /**
     * Cofactor of Ed448-Goldilocks curve.
     */
    static final BigInteger COFACTOR = BigInteger.valueOf(4L);

    /**
     * Prime p of the Ed448-Goldilocks curve. (Used as modulus.)
     */
    static final BigInteger MODULUS = new BigInteger("fffffffffffffffffffffffffffffffffffffffffffffffffffffffeffffffffffffffffffffffffffffffffffffffffffffffffffffffff", 16);

    /**
     * Prime order q.
     * NOTE: 4q is the order of the Ed448-Goldilocks curve.
     * <p>
     * The order of its twist is: 4 * (2**446 - 0x8335dc163bb124b65129c96fde933d8d723a70aadc873d6d54a7bb0d).
     */
    static final BigInteger Q = new BigInteger("3fffffffffffffffffffffffffffffffffffffffffffffffffffffff7cca23e9c44edb49aed63690216cc2728dc58f552378c292ab5844f3", 16);

    /**
     * a parameter of the untwisted Edwards curve formula.
     * <p>
     * NOTE: Page 5 of Decaf paper describes how they take "Untwisted" (Edwards Curve [sic]) to mean a = 1.
     */
    static final BigInteger A = BigInteger.ONE;

    /**
     * Edwards constant d.
     */
    static final BigInteger D = BigInteger.valueOf(-39081L);

    /**
     * Base point for Ed448-Goldilocks.
     *
     * NOTE: this is the base point in 4E.
     */
    static final AffinePoint P = new AffinePoint(
//     X(P): 224580040295924300187604334099896036246789641632564134246125461686950415467406032909029192869357953282578032075146446173674602635247710
            new BigInteger("224580040295924300187604334099896036246789641632564134246125461686950415467406032909029192869357953282578032075146446173674602635247710", 10),
//     Y(P): 298819210078481492676017930443930673437544040154080242095928241372331506189835876003536878655418784733982303233503462500531545062832660
            new BigInteger("298819210078481492676017930443930673437544040154080242095928241372331506189835876003536878655418784733982303233503462500531545062832660", 10)
    );

    private static final byte[] PREFIX_SIGED448_BYTES = "SigEd448".getBytes(StandardCharsets.US_ASCII);

    /**
     * Verify that given point is contained in the curve.
     *
     * @param p The point to verify.
     * @return Returns true if it is contained in the curve.
     */
    // TODO: According to otrv4 spec, we can verify point is on the curve with: Given point X = (x,y), check X != Identity & x in range [0, q-1] & y in range [0, q-1] & q * X = Identity.
    // (https://github.com/otrv4/otrv4/blob/master/otrv4.md#verifying-that-a-point-is-on-the-curve)
    @CheckReturnValue
    static boolean contains(final Point p) {
        // TODO: Check if we really need so many mod-operations
        final BigInteger x = p.x();
        final BigInteger xx = x.multiply(x).mod(MODULUS);
        final BigInteger y = p.y();
        final BigInteger yy = y.multiply(y).mod(MODULUS);

        final BigInteger dxx = D.multiply(xx).mod(MODULUS);
        final BigInteger dxxyy = dxx.multiply(yy).mod(MODULUS);

        final BigInteger left =  xx.add(yy).mod(MODULUS);
        final BigInteger right= ONE.add(dxxyy).mod(MODULUS);
        return left.compareTo(right) == 0;
    }

    /**
     * Access base point of Ed448-Goldilocks curve.
     *
     * @return Returns base point.
     */
    @Nonnull
    public static Point basePoint() {
        return P;
    }

    /**
     * Access modulus of Ed448-Goldilocks curve.
     *
     * @return Returns modulus.
     */
    @Nonnull
    public static BigInteger modulus() {
        return MODULUS;
    }

    /**
     * Access prime order of Ed448-Goldilocks curve.
     *
     * @return Returns prime order.
     */
    @Nonnull
    public static BigInteger primeOrder() {
        return Q;
    }

    /**
     * Generate Ed448 key pair according to OTRv4 spec. Which corresponds to RFC 8032 key generation section with the
     * exception of the final conversions to byte arrays.
     * <p>
     * NOTE: if there is no additional value for <code>symmetricKey</code>, caller should ensure that this variable is
     * cleared.
     *
     * @param symmetricKey The secret bytes used as input to key generation. This symmetric key data should be generated
     *                     from a cryptographically secure random source.
     * @return Returns an Ed448 key pair based on the private key input.
     */
    @Nonnull
    public static Ed448KeyPair generate(final byte[] symmetricKey) {
        final byte[] h = shake256(requireLengthExactly(PRIVATE_KEY_LENGTH_BYTES, symmetricKey),
            SIGNING_DIGEST_LENGTH_BYTES);
        final byte[] publicKeySourceData = copyOf(h, ENCODED_LENGTH_BYTES);
        clear(h);
        prune(publicKeySourceData);
        final BigInteger sk = decodeLittleEndian(publicKeySourceData);
        final Point a = multiplyByBase(sk);
        return new Ed448KeyPair(sk, a);
    }

    /**
     * Sign an arbitrary length message.
     *
     * @param sk      The secret key.
     * @param context Context C, max 255 bytes.
     * @param message Message, arbitrary length.
     */
    @Nonnull
    public static byte[] sign(final BigInteger sk, final byte[] context, final byte[] message) {
        requireLengthAtMost(255, context);
        // "1. Hash the private key, 57 octets, using SHAKE256(x, 114).  Let h denote the resulting digest. Construct the
        //     secret scalar s from the first half of the digest, and the corresponding public key A, as described in the
        //     previous section.  Let prefix denote the second half of the hash digest, h[57],...,h[113]."
        final byte[] skbytes = encodeLittleEndian(sk);
        final byte[] h = shake256(skbytes, SIGNING_DIGEST_LENGTH_BYTES);
        final byte[] sbytes = copyOf(h, 57);
        final byte[] prefix = copyOfRange(h, 57, 114);
        clear(h);
        prune(sbytes);
        final BigInteger s = decodeLittleEndian(sbytes);
        final byte[] encodedPointA = multiplyByBase(s).encode();
        // "2. Compute SHAKE256(dom4(F, C) || prefix || PH(M), 114), where M is the message to be signed, F is 1 for
        //     Ed448ph, 0 for Ed448, and C is the context to use.  Interpret the 114-octet digest as a little-endian
        //     integer r."
        final byte[] bufferR = concatenate(dom4(context), prefix, ph(message));
        final BigInteger r = decodeLittleEndian(shake256(bufferR, 114));
        // "3. Compute point [r]B. For efficiency, do this by first reducing r modulo L, the group order of B. Let the
        //     string R be the encoding of this point."
        final byte[] encodedPointR = P.multiply(r.mod(Q)).encode();
        // "4. Compute SHAKE256(dom4(F, C) || R || A || PH(M), 114), and interpret the 114-octet digest as a
        //     little-endian integer k."
        final byte[] bufferK = concatenate(dom4(context), encodedPointR, encodedPointA, ph(message));
        final BigInteger k = decodeLittleEndian(shake256(bufferK, 114));
        // "5. Compute S = (r + k * s) mod L. For efficiency, again reduce k modulo L first."
        final byte[] encodedPointS = encodeLittleEndian(r.add(k.mod(Q).multiply(s)).mod(Q));
        // "6. Form the signature of the concatenation of R (57 octets) and the little-endian encoding of S (57 octets;
        //    the ten most significant bits of the final octets are always zero)."
        // Given that the top ten most significant bits are always zero, add single byte to get to total of 114 bytes.
        return concatenate(encodedPointR, encodedPointS, new byte[1]);
    }

    /**
     * Verify a signature for an arbitrary length message.
     */
    public static void verify(final byte[] context, final Point publicKey, final byte[] message, final byte[] signature)
        throws SignatureVerificationFailedException {
        // 1. To verify a signature on a message M using context C and public key A, with F being 0 for Ed448 and 1 for
        //    Ed448ph, first split the signature into two 57-octet halves.  Decode the first half as a point R, and the
        //    second half as an integer S, in the range 0 <= s < L. Decode the public key A as point A'. If any of the
        //    decodings fail (including S being out of range), the signature is invalid.
        final Point r;
        try {
            r = decode(copyOf(signature, 57));
        } catch (final Points.InvalidDataException e) {
            throw new SignatureVerificationFailedException("Data for point R is invalid.", e);
        }
        final BigInteger s = decodeLittleEndian(copyOfRange(signature, 57, 114));
        if (ZERO.compareTo(s) > 0 || s.compareTo(Q) >= 0) {
            throw new SignatureVerificationFailedException("Signature verification failed: scalar s is illegal.");
        }
        // 2. Compute SHAKE256(dom4(F, C) || R || A || PH(M), 114), and interpret the 114-octet digest as a
        //    little-endian integer k.
        final byte[] digest = shake256(concatenate(dom4(context), r.encode(), publicKey.encode(), ph(message)), 114);
        final BigInteger k = decodeLittleEndian(digest);
        // 3. Check the group equation [4][S]B = [4]R + [4][k]A'.  It's sufficient, but not required, to instead check
        //    [S]B = R + [k]A'.
        final Point lhs = P.multiply(s);
        final Point rhs = r.add(publicKey.multiply(k));
        // FIXME debugging code
        System.err.println("LHS: " + Arrays.toString(lhs.encode()));
        System.err.println("RHS: " + Arrays.toString(rhs.encode()));
        if (!lhs.equals(rhs)) {
            throw new SignatureVerificationFailedException("Failed to verify components.");
        }
    }

    /**
     * Multiply scalar (e.g. secret key) by (pre-computed) base in order to derive a new point on the curve.
     *
     * @param scalar The scalar value, e.g. secret key.
     * @return Returns a point that is the result of the multiplication with the base.
     */
    @Nonnull
    public static Point multiplyByBase(final BigInteger scalar) {
        return P.multiply(scalar);
    }

    @Nonnull
    private static byte[] dom4(final byte[] y) {
        requireLengthAtMost(255, y);
        try {
            final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            buffer.write(PREFIX_SIGED448_BYTES);
            buffer.write(0);
            buffer.write(y.length);
            buffer.write(y);
            return buffer.toByteArray();
        } catch (final IOException e) {
            throw new IllegalStateException("Failed to compose byte array.", e);
        }
    }

    /**
     * PreHash function. For (plain) Ed448, PH(x) is the identity function, i.e. returning x.
     *
     * @param x the data
     * @return Returns the prehash for x.
     */
    @Nonnull
    private static byte[] ph(final byte[] x) {
        return x;
    }

    /**
     * Exception indicating a failure during signature verification.
     * <p>
     * There are multiple failures possible. The message indicates what part of the verification actually failed.
     */
    public static final class SignatureVerificationFailedException extends Exception {

        private SignatureVerificationFailedException(final String message) {
            super(message);
        }

        private SignatureVerificationFailedException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }
}

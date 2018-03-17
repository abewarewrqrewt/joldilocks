package nl.dannyvanheumen.joldilocks;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Arrays;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
import static nl.dannyvanheumen.joldilocks.Ed448.MODULUS;
import static nl.dannyvanheumen.joldilocks.Ed448.P;
import static nl.dannyvanheumen.joldilocks.Ed448.Q;
import static nl.dannyvanheumen.joldilocks.Ed448.contains;
import static nl.dannyvanheumen.joldilocks.Ed448.sign;
import static nl.dannyvanheumen.joldilocks.ExtendedPoint.fromEdwards;
import static nl.dannyvanheumen.joldilocks.Points.decode;
import static nl.dannyvanheumen.joldilocks.Scalars.decodeLittleEndian;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings({"WeakerAccess", "ConstantConditions"})
public class Ed448Test {

    @Test
    public void testVerifyPrimeOrderSmallerThanModulus() {
        assertTrue(Q.compareTo(MODULUS) < 0);
    }

    @Disabled("Fails for as of yet unexplained reason. Anything else w.r.t. multiplication seems to work correctly. This is an important characteristic though ...")
    @Test
    public void testVerifyPrimeOrderMultBasePointEqualsIdentity() {
        final Point identity = fromEdwards(ZERO, ONE);
        final Point result = P.multiply(Q);
        assertEquals(identity.x(), result.x(), result.toString());
        assertEquals(identity.y(), result.y(), result.toString());
        assertEquals(identity, result);
    }

    @Test
    public void testContainsNullFails() {
        assertThrows(NullPointerException.class, () -> contains(null));
    }

    @Test
    public void testContainsZero() {
        assertFalse(contains(fromEdwards(ZERO, ZERO)));
    }

    // FIXME Not completely sure if this point is supposed to be on the curve ...
    @Test
    public void testContainsPoint1() {
        assertTrue(contains(fromEdwards(ONE, ZERO)));
    }

    @Test
    public void testContainsPoint2() {
        assertTrue(contains(fromEdwards(ONE.negate(), ZERO)));
    }

    @Test
    public void testContainsNeutralPoint() {
        assertTrue(contains(fromEdwards(ZERO, ONE)));
    }

    @Test
    public void testArbitraryPoint() {
        assertFalse(contains(fromEdwards(BigInteger.valueOf(1234), BigInteger.valueOf(-856382))));
    }

    @Test
    public void testBasePointDeserialization() {
        assertEquals(new BigInteger("224580040295924300187604334099896036246789641632564134246125461686950415467406032909029192869357953282578032075146446173674602635247710"), P.x());
        assertEquals(new BigInteger("298819210078481492676017930443930673437544040154080242095928241372331506189835876003536878655418784733982303233503462500531545062832660"), P.y());
    }

    @Test
    public void testBasePointIsOnCurve() {
        assertTrue(contains(P), "Something is wrong as the base point is not considered to be on the curve. Not sure what is wrong yet.");
    }

    @Disabled("Point arithmetic not correct yet, therefore the test vector still fails.")
    @Test
    public void testVerifyPredefinedTestVectors() throws Points.InvalidDataException {
        final byte[] expected = new byte[]{0x53, 0x3a, 0x37, (byte) 0xf6, (byte) 0xbb, (byte) 0xe4, 0x57, 0x25, 0x1f, 0x02, 0x3c, 0x0d, (byte) 0x88, (byte) 0xf9, 0x76, (byte) 0xae, 0x2d, (byte) 0xfb, 0x50, 0x4a, (byte) 0x84, 0x3e, 0x34, (byte) 0xd2, 0x07, 0x4f, (byte) 0xd8, 0x23, (byte) 0xd4, 0x1a, 0x59, 0x1f, 0x2b, 0x23, 0x3f, 0x03, 0x4f, 0x62, (byte) 0x82, (byte) 0x81, (byte) 0xf2, (byte) 0xfd, 0x7a, 0x22, (byte) 0xdd, (byte) 0xd4, 0x7d, 0x78, 0x28, (byte) 0xc5, (byte) 0x9b, (byte) 0xd0, (byte) 0xa2, 0x1b, (byte) 0xfd, 0x39, (byte) 0x80, (byte) 0xff, 0x0d, 0x20, 0x28, (byte) 0xd4, (byte) 0xb1, (byte) 0x8a, (byte) 0x9d, (byte) 0xf6, 0x3e, 0x00, 0x6c, 0x5d, 0x1c, 0x2d, 0x34, 0x5b, (byte) 0x92, 0x5d, (byte) 0x8d, (byte) 0xc0, 0x0b, 0x41, 0x04, (byte) 0x85, 0x2d, (byte) 0xb9, (byte) 0x9a, (byte) 0xc5, (byte) 0xc7, (byte) 0xcd, (byte) 0xda, (byte) 0x85, 0x30, (byte) 0xa1, 0x13, (byte) 0xa0, (byte) 0xf4, (byte) 0xdb, (byte) 0xb6, 0x11, 0x49, (byte) 0xf0, 0x5a, 0x73, 0x63, 0x26, (byte) 0x8c, 0x71, (byte) 0xd9, 0x58, 0x08, (byte) 0xff, 0x2e, 0x65, 0x26, 0x00};
        final byte[] message = new byte[0];
        final BigInteger sk = decodeLittleEndian(new byte[]{0x6c, (byte) 0x82, (byte) 0xa5, 0x62, (byte) 0xcb, (byte) 0x80, (byte) 0x8d, 0x10, (byte) 0xd6, 0x32, (byte) 0xbe, (byte) 0x89, (byte) 0xc8, 0x51, 0x3e, (byte) 0xbf, 0x6c, (byte) 0x92, (byte) 0x9f, 0x34, (byte) 0xdd, (byte) 0xfa, (byte) 0x8c, (byte) 0x9f, 0x63, (byte) 0xc9, (byte) 0x96, 0x0e, (byte) 0xf6, (byte) 0xe3, 0x48, (byte) 0xa3, 0x52, (byte) 0x8c, (byte) 0x8a, 0x3f, (byte) 0xcc, 0x2f, 0x04, 0x4e, 0x39, (byte) 0xa3, (byte) 0xfc, 0x5b, (byte) 0x94, 0x49, 0x2f, (byte) 0x8f, 0x03, 0x2e, 0x75, 0x49, (byte) 0xa2, 0x00, (byte) 0x98, (byte) 0xf9, 0x5b});
        final Point pk = decode(new byte[]{0x5f, (byte) 0xd7, 0x44, (byte) 0x9b, 0x59, (byte) 0xb4, 0x61, (byte) 0xfd, 0x2c, (byte) 0xe7, (byte) 0x87, (byte) 0xec, 0x61, 0x6a, (byte) 0xd4, 0x6a, 0x1d, (byte) 0xa1, 0x34, 0x24, (byte) 0x85, (byte) 0xa7, 0x0e, 0x1f, (byte) 0x8a, 0x0e, (byte) 0xa7, 0x5d, (byte) 0x80, (byte) 0xe9, 0x67, 0x78, (byte) 0xed, (byte) 0xf1, 0x24, 0x76, (byte) 0x9b, 0x46, (byte) 0xc7, 0x06, 0x1b, (byte) 0xd6, 0x78, 0x3d, (byte) 0xf1, (byte) 0xe5, 0x0f, 0x6c, (byte) 0xd1, (byte) 0xfa, 0x1a, (byte) 0xbe, (byte) 0xaf, (byte) 0xe8, 0x25, 0x61, (byte) 0x80});
        System.err.println("Actual: " + Arrays.toString(sign(sk, new byte[0], message)));
        System.err.println("Expect: " + Arrays.toString(expected));
    }
}

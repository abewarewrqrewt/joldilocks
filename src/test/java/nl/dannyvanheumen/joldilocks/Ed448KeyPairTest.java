package nl.dannyvanheumen.joldilocks;

import nl.dannyvanheumen.joldilocks.Points.InvalidDataException;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static nl.dannyvanheumen.joldilocks.Ed448.multiplyByBase;
import static nl.dannyvanheumen.joldilocks.Points.decode;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings({"WeakerAccess", "ConstantConditions"})
public final class Ed448KeyPairTest {

    @Test
    public void testSignBadArguments() {
        final Ed448KeyPair keypair = Ed448KeyPair.create(new byte[]{0x6c, (byte) 0x82, (byte) 0xa5, 0x62, (byte) 0xcb, (byte) 0x80, (byte) 0x8d, 0x10, (byte) 0xd6, 0x32, (byte) 0xbe, (byte) 0x89, (byte) 0xc8, 0x51, 0x3e, (byte) 0xbf, 0x6c, (byte) 0x92, (byte) 0x9f, 0x34, (byte) 0xdd, (byte) 0xfa, (byte) 0x8c, (byte) 0x9f, 0x63, (byte) 0xc9, (byte) 0x96, 0x0e, (byte) 0xf6, (byte) 0xe3, 0x48, (byte) 0xa3, 0x52, (byte) 0x8c, (byte) 0x8a, 0x3f, (byte) 0xcc, 0x2f, 0x04, 0x4e, 0x39, (byte) 0xa3, (byte) 0xfc, 0x5b, (byte) 0x94, 0x49, 0x2f, (byte) 0x8f, 0x03, 0x2e, 0x75, 0x49, (byte) 0xa2, 0x00, (byte) 0x98, (byte) 0xf9, 0x5b});
        final byte[] context = new byte[0];
        final byte[] message = new byte[0];
        assertThrows(NullPointerException.class, () -> keypair.sign(null, message));
        assertThrows(NullPointerException.class, () -> keypair.sign(context, null));
    }

    @Test
    public void testSignPredefinedTestVectorBlank() {
        final byte[] expected = new byte[]{0x53, 0x3a, 0x37, (byte) 0xf6, (byte) 0xbb, (byte) 0xe4, 0x57, 0x25, 0x1f, 0x02, 0x3c, 0x0d, (byte) 0x88, (byte) 0xf9, 0x76, (byte) 0xae, 0x2d, (byte) 0xfb, 0x50, 0x4a, (byte) 0x84, 0x3e, 0x34, (byte) 0xd2, 0x07, 0x4f, (byte) 0xd8, 0x23, (byte) 0xd4, 0x1a, 0x59, 0x1f, 0x2b, 0x23, 0x3f, 0x03, 0x4f, 0x62, (byte) 0x82, (byte) 0x81, (byte) 0xf2, (byte) 0xfd, 0x7a, 0x22, (byte) 0xdd, (byte) 0xd4, 0x7d, 0x78, 0x28, (byte) 0xc5, (byte) 0x9b, (byte) 0xd0, (byte) 0xa2, 0x1b, (byte) 0xfd, 0x39, (byte) 0x80, (byte) 0xff, 0x0d, 0x20, 0x28, (byte) 0xd4, (byte) 0xb1, (byte) 0x8a, (byte) 0x9d, (byte) 0xf6, 0x3e, 0x00, 0x6c, 0x5d, 0x1c, 0x2d, 0x34, 0x5b, (byte) 0x92, 0x5d, (byte) 0x8d, (byte) 0xc0, 0x0b, 0x41, 0x04, (byte) 0x85, 0x2d, (byte) 0xb9, (byte) 0x9a, (byte) 0xc5, (byte) 0xc7, (byte) 0xcd, (byte) 0xda, (byte) 0x85, 0x30, (byte) 0xa1, 0x13, (byte) 0xa0, (byte) 0xf4, (byte) 0xdb, (byte) 0xb6, 0x11, 0x49, (byte) 0xf0, 0x5a, 0x73, 0x63, 0x26, (byte) 0x8c, 0x71, (byte) 0xd9, 0x58, 0x08, (byte) 0xff, 0x2e, 0x65, 0x26, 0x00};
        final Ed448KeyPair keypair = Ed448KeyPair.create(new byte[]{0x6c, (byte) 0x82, (byte) 0xa5, 0x62, (byte) 0xcb, (byte) 0x80, (byte) 0x8d, 0x10, (byte) 0xd6, 0x32, (byte) 0xbe, (byte) 0x89, (byte) 0xc8, 0x51, 0x3e, (byte) 0xbf, 0x6c, (byte) 0x92, (byte) 0x9f, 0x34, (byte) 0xdd, (byte) 0xfa, (byte) 0x8c, (byte) 0x9f, 0x63, (byte) 0xc9, (byte) 0x96, 0x0e, (byte) 0xf6, (byte) 0xe3, 0x48, (byte) 0xa3, 0x52, (byte) 0x8c, (byte) 0x8a, 0x3f, (byte) 0xcc, 0x2f, 0x04, 0x4e, 0x39, (byte) 0xa3, (byte) 0xfc, 0x5b, (byte) 0x94, 0x49, 0x2f, (byte) 0x8f, 0x03, 0x2e, 0x75, 0x49, (byte) 0xa2, 0x00, (byte) 0x98, (byte) 0xf9, 0x5b});
        final byte[] context = new byte[0];
        final byte[] message = new byte[0];
        assertArrayEquals(expected, keypair.sign(context, message));
    }

    @Test
    public void testSignPredefinedTestVector1Octet() {
        final byte[] expected = new byte[]{0x26, (byte) 0xb8, (byte) 0xf9, 0x17, 0x27, (byte) 0xbd, 0x62, (byte) 0x89, 0x7a, (byte) 0xf1, 0x5e, 0x41, (byte) 0xeb, 0x43, (byte) 0xc3, 0x77, (byte) 0xef, (byte) 0xb9, (byte) 0xc6, 0x10, (byte) 0xd4, (byte) 0x8f, 0x23, 0x35, (byte) 0xcb, 0x0b, (byte) 0xd0, 0x08, 0x78, 0x10, (byte) 0xf4, 0x35, 0x25, 0x41, (byte) 0xb1, 0x43, (byte) 0xc4, (byte) 0xb9, (byte) 0x81, (byte) 0xb7, (byte) 0xe1, (byte) 0x8f, 0x62, (byte) 0xde, (byte) 0x8c, (byte) 0xcd, (byte) 0xf6, 0x33, (byte) 0xfc, 0x1b, (byte) 0xf0, 0x37, (byte) 0xab, 0x7c, (byte) 0xd7, 0x79, (byte) 0x80, 0x5e, 0x0d, (byte) 0xbc, (byte) 0xc0, (byte) 0xaa, (byte) 0xe1, (byte) 0xcb, (byte) 0xce, (byte) 0xe1, (byte) 0xaf, (byte) 0xb2, (byte) 0xe0, 0x27, (byte) 0xdf, 0x36, (byte) 0xbc, 0x04, (byte) 0xdc, (byte) 0xec, (byte) 0xbf, 0x15, 0x43, 0x36, (byte) 0xc1, (byte) 0x9f, 0x0a, (byte) 0xf7, (byte) 0xe0, (byte) 0xa6, 0x47, 0x29, 0x05, (byte) 0xe7, (byte) 0x99, (byte) 0xf1, (byte) 0x95, 0x3d, 0x2a, 0x0f, (byte) 0xf3, 0x34, (byte) 0x8a, (byte) 0xb2, 0x1a, (byte) 0xa4, (byte) 0xad, (byte) 0xaf, (byte) 0xd1, (byte) 0xd2, 0x34, 0x44, 0x1c, (byte) 0xf8, 0x07, (byte) 0xc0, 0x3a, 0x00};
        final Ed448KeyPair keypair = Ed448KeyPair.create(new byte[]{(byte) 0xc4, (byte) 0xea, (byte) 0xb0, 0x5d, 0x35, 0x70, 0x07, (byte) 0xc6, 0x32, (byte) 0xf3, (byte) 0xdb, (byte) 0xb4, (byte) 0x84, (byte) 0x89, (byte) 0x92, 0x4d, 0x55, 0x2b, 0x08, (byte) 0xfe, 0x0c, 0x35, 0x3a, 0x0d, 0x4a, 0x1f, 0x00, (byte) 0xac, (byte) 0xda, 0x2c, 0x46, 0x3a, (byte) 0xfb, (byte) 0xea, 0x67, (byte) 0xc5, (byte) 0xe8, (byte) 0xd2, (byte) 0x87, 0x7c, 0x5e, 0x3b, (byte) 0xc3, (byte) 0x97, (byte) 0xa6, 0x59, (byte) 0x94, (byte) 0x9e, (byte) 0xf8, 0x02, 0x1e, (byte) 0x95, 0x4e, 0x0a, 0x12, 0x27, 0x4e});
        final byte[] context = new byte[0];
        final byte[] message = new byte[]{0x03};
        assertArrayEquals(expected, keypair.sign(context, message));
    }

    @Test
    public void testSignPredefinedTestVector1OctetWithContext() {
        final byte[] expected = new byte[]{(byte) 0xd4, (byte) 0xf8, (byte) 0xf6, 0x13, 0x17, 0x70, (byte) 0xdd, 0x46, (byte) 0xf4, 0x08, 0x67, (byte) 0xd6, (byte) 0xfd, 0x5d, 0x50, 0x55, (byte) 0xde, 0x43, 0x54, 0x1f, (byte) 0x8c, 0x5e, 0x35, (byte) 0xab, (byte) 0xbc, (byte) 0xd0, 0x01, (byte) 0xb3, 0x2a, (byte) 0x89, (byte) 0xf7, (byte) 0xd2, 0x15, 0x1f, 0x76, 0x47, (byte) 0xf1, 0x1d, (byte) 0x8c, (byte) 0xa2, (byte) 0xae, 0x27, (byte) 0x9f, (byte) 0xb8, 0x42, (byte) 0xd6, 0x07, 0x21, 0x7f, (byte) 0xce, 0x6e, 0x04, 0x2f, 0x68, 0x15, (byte) 0xea, 0x00, 0x0c, (byte) 0x85, 0x74, 0x1d, (byte) 0xe5, (byte) 0xc8, (byte) 0xda, 0x11, 0x44, (byte) 0xa6, (byte) 0xa1, (byte) 0xab, (byte) 0xa7, (byte) 0xf9, 0x6d, (byte) 0xe4, 0x25, 0x05, (byte) 0xd7, (byte) 0xa7, 0x29, (byte) 0x85, 0x24, (byte) 0xfd, (byte) 0xa5, 0x38, (byte) 0xfc, (byte) 0xcb, (byte) 0xbb, 0x75, 0x4f, 0x57, (byte) 0x8c, 0x1c, (byte) 0xad, 0x10, (byte) 0xd5, 0x4d, 0x0d, 0x54, 0x28, 0x40, 0x7e, (byte) 0x85, (byte) 0xdc, (byte) 0xbc, (byte) 0x98, (byte) 0xa4, (byte) 0x91, 0x55, (byte) 0xc1, 0x37, 0x64, (byte) 0xe6, 0x6c, 0x3c, 0x00};
        final Ed448KeyPair keypair = Ed448KeyPair.create(new byte[]{(byte) 0xc4, (byte) 0xea, (byte) 0xb0, 0x5d, 0x35, 0x70, 0x07, (byte) 0xc6, 0x32, (byte) 0xf3, (byte) 0xdb, (byte) 0xb4, (byte) 0x84, (byte) 0x89, (byte) 0x92, 0x4d, 0x55, 0x2b, 0x08, (byte) 0xfe, 0x0c, 0x35, 0x3a, 0x0d, 0x4a, 0x1f, 0x00, (byte) 0xac, (byte) 0xda, 0x2c, 0x46, 0x3a, (byte) 0xfb, (byte) 0xea, 0x67, (byte) 0xc5, (byte) 0xe8, (byte) 0xd2, (byte) 0x87, 0x7c, 0x5e, 0x3b, (byte) 0xc3, (byte) 0x97, (byte) 0xa6, 0x59, (byte) 0x94, (byte) 0x9e, (byte) 0xf8, 0x02, 0x1e, (byte) 0x95, 0x4e, 0x0a, 0x12, 0x27, 0x4e});
        final byte[] context = new byte[]{0x66, 0x6f, 0x6f};
        final byte[] message = new byte[]{0x03};
        assertArrayEquals(expected, keypair.sign(context, message));
    }

    @Test
    public void testSignPredefinedTestVector11Octets() {
        final byte[] expected = new byte[]{0x1f, 0x0a, (byte) 0x88, (byte) 0x88, (byte) 0xce, 0x25, (byte) 0xe8, (byte) 0xd4, 0x58, (byte) 0xa2, 0x11, 0x30, (byte) 0x87, (byte) 0x9b, (byte) 0x84, 0x0a, (byte) 0x90, (byte) 0x89, (byte) 0xd9, (byte) 0x99, (byte) 0xaa, (byte) 0xba, 0x03, (byte) 0x9e, (byte) 0xaf, 0x3e, 0x3a, (byte) 0xfa, 0x09, 0x0a, 0x09, (byte) 0xd3, (byte) 0x89, (byte) 0xdb, (byte) 0xa8, 0x2c, 0x4f, (byte) 0xf2, (byte) 0xae, (byte) 0x8a, (byte) 0xc5, (byte) 0xcd, (byte) 0xfb, 0x7c, 0x55, (byte) 0xe9, 0x4d, 0x5d, (byte) 0x96, 0x1a, 0x29, (byte) 0xfe, 0x01, 0x09, (byte) 0x94, 0x1e, 0x00, (byte) 0xb8, (byte) 0xdb, (byte) 0xde, (byte) 0xea, 0x6d, 0x3b, 0x05, 0x10, 0x68, (byte) 0xdf, 0x72, 0x54, (byte) 0xc0, (byte) 0xcd, (byte) 0xc1, 0x29, (byte) 0xcb, (byte) 0xe6, 0x2d, (byte) 0xb2, (byte) 0xdc, (byte) 0x95, 0x7d, (byte) 0xbb, 0x47, (byte) 0xb5, 0x1f, (byte) 0xd3, (byte) 0xf2, 0x13, (byte) 0xfb, (byte) 0x86, (byte) 0x98, (byte) 0xf0, 0x64, 0x77, 0x42, 0x50, (byte) 0xa5, 0x02, (byte) 0x89, 0x61, (byte) 0xc9, (byte) 0xbf, (byte) 0x8f, (byte) 0xfd, (byte) 0x97, 0x3f, (byte) 0xe5, (byte) 0xd5, (byte) 0xc2, 0x06, 0x49, 0x2b, 0x14, 0x0e, 0x00};
        final Ed448KeyPair keypair = Ed448KeyPair.create(new byte[]{(byte) 0xcd, 0x23, (byte) 0xd2, 0x4f, 0x71, 0x42, 0x74, (byte) 0xe7, 0x44, 0x34, 0x32, 0x37, (byte) 0xb9, 0x32, (byte) 0x90, (byte) 0xf5, 0x11, (byte) 0xf6, 0x42, 0x5f, (byte) 0x98, (byte) 0xe6, 0x44, 0x59, (byte) 0xff, 0x20, 0x3e, (byte) 0x89, (byte) 0x85, 0x08, 0x3f, (byte) 0xfd, (byte) 0xf6, 0x05, 0x00, 0x55, 0x3a, (byte) 0xbc, 0x0e, 0x05, (byte) 0xcd, 0x02, 0x18, 0x4b, (byte) 0xdb, (byte) 0x89, (byte) 0xc4, (byte) 0xcc, (byte) 0xd6, 0x7e, 0x18, 0x79, 0x51, 0x26, 0x7e, (byte) 0xb3, 0x28});
        final byte[] context = new byte[0];
        final byte[] message = new byte[]{0x0c, 0x3e, 0x54, 0x40, 0x74, (byte) 0xec, 0x63, (byte) 0xb0, 0x26, 0x5e, 0x0c};
        assertArrayEquals(expected, keypair.sign(context, message));
    }

    @Test
    public void generate11OctetsPublicKey() throws InvalidDataException {
        final Point expected = decode(new byte[]{(byte) 0xdc, (byte) 0xea, (byte) 0x9e, 0x78, (byte) 0xf3, 0x5a, 0x1b, (byte) 0xf3, 0x49, (byte) 0x9a, (byte) 0x83, 0x1b, 0x10, (byte) 0xb8, 0x6c, (byte) 0x90, (byte) 0xaa, (byte) 0xc0, 0x1c, (byte) 0xd8, 0x4b, 0x67, (byte) 0xa0, 0x10, (byte) 0x9b, 0x55, (byte) 0xa3, 0x6e, (byte) 0x93, 0x28, (byte) 0xb1, (byte) 0xe3, 0x65, (byte) 0xfc, (byte) 0xe1, 0x61, (byte) 0xd7, 0x1c, (byte) 0xe7, 0x13, 0x1a, 0x54, 0x3e, (byte) 0xa4, (byte) 0xcb, 0x5f, 0x7e, (byte) 0x9f, 0x1d, (byte) 0x8b, 0x00, 0x69, 0x64, 0x47, 0x00, 0x14, 0x00});
        final byte[] sk = new byte[]{(byte) 0xcd, 0x23, (byte) 0xd2, 0x4f, 0x71, 0x42, 0x74, (byte) 0xe7, 0x44, 0x34, 0x32, 0x37, (byte) 0xb9, 0x32, (byte) 0x90, (byte) 0xf5, 0x11, (byte) 0xf6, 0x42, 0x5f, (byte) 0x98, (byte) 0xe6, 0x44, 0x59, (byte) 0xff, 0x20, 0x3e, (byte) 0x89, (byte) 0x85, 0x08, 0x3f, (byte) 0xfd, (byte) 0xf6, 0x05, 0x00, 0x55, 0x3a, (byte) 0xbc, 0x0e, 0x05, (byte) 0xcd, 0x02, 0x18, 0x4b, (byte) 0xdb, (byte) 0x89, (byte) 0xc4, (byte) 0xcc, (byte) 0xd6, 0x7e, 0x18, 0x79, 0x51, 0x26, 0x7e, (byte) 0xb3, 0x28};
        final Ed448KeyPair keypair = Ed448KeyPair.create(sk);
        final Point generated = multiplyByBase(keypair.getSecretKey());
        assertEquals(expected.x(), generated.x());
        assertEquals(expected.y(), generated.y());
    }

    @Test
    public void testSignPredefinedTestVector12Octets() {
        final byte[] expected = new byte[]{0x7e, (byte) 0xee, (byte) 0xab, 0x7c, 0x4e, 0x50, (byte) 0xfb, 0x79, (byte) 0x9b, 0x41, (byte) 0x8e, (byte) 0xe5, (byte) 0xe3, 0x19, 0x7f, (byte) 0xf6, (byte) 0xbf, 0x15, (byte) 0xd4, 0x3a, 0x14, (byte) 0xc3, 0x43, (byte) 0x89, (byte) 0xb5, (byte) 0x9d, (byte) 0xd1, (byte) 0xa7, (byte) 0xb1, (byte) 0xb8, 0x5b, 0x4a, (byte) 0xe9, 0x04, 0x38, (byte) 0xac, (byte) 0xa6, 0x34, (byte) 0xbe, (byte) 0xa4, 0x5e, 0x3a, 0x26, (byte) 0x95, (byte) 0xf1, 0x27, 0x0f, 0x07, (byte) 0xfd, (byte) 0xcd, (byte) 0xf7, (byte) 0xc6, 0x2b, (byte) 0x8e, (byte) 0xfe, (byte) 0xaf, 0x00, (byte) 0xb4, 0x5c, 0x2c, (byte) 0x96, (byte) 0xba, 0x45, 0x7e, (byte) 0xb1, (byte) 0xa8, (byte) 0xbf, 0x07, 0x5a, 0x3d, (byte) 0xb2, (byte) 0x8e, 0x5c, 0x24, (byte) 0xf6, (byte) 0xb9, 0x23, (byte) 0xed, 0x4a, (byte) 0xd7, 0x47, (byte) 0xc3, (byte) 0xc9, (byte) 0xe0, 0x3c, 0x70, 0x79, (byte) 0xef, (byte) 0xb8, 0x7c, (byte) 0xb1, 0x10, (byte) 0xd3, (byte) 0xa9, (byte) 0x98, 0x61, (byte) 0xe7, 0x20, 0x03, (byte) 0xcb, (byte) 0xae, 0x6d, 0x6b, (byte) 0x8b, (byte) 0x82, 0x7e, 0x4e, 0x6c, 0x14, 0x30, 0x64, (byte) 0xff, 0x3c, 0x00};
        final Ed448KeyPair keypair = Ed448KeyPair.create(new byte[]{0x25, (byte) 0x8c, (byte) 0xdd, 0x4a, (byte) 0xda, 0x32, (byte) 0xed, (byte) 0x9c, (byte) 0x9f, (byte) 0xf5, 0x4e, 0x63, 0x75, 0x6a, (byte) 0xe5, (byte) 0x82, (byte) 0xfb, (byte) 0x8f, (byte) 0xab, 0x2a, (byte) 0xc7, 0x21, (byte) 0xf2, (byte) 0xc8, (byte) 0xe6, 0x76, (byte) 0xa7, 0x27, 0x68, 0x51, 0x3d, (byte) 0x93, (byte) 0x9f, 0x63, (byte) 0xdd, (byte) 0xdb, 0x55, 0x60, (byte) 0x91, 0x33, (byte) 0xf2, (byte) 0x9a, (byte) 0xdf, (byte) 0x86, (byte) 0xec, (byte) 0x99, 0x29, (byte) 0xdc, (byte) 0xcb, 0x52, (byte) 0xc1, (byte) 0xc5, (byte) 0xfd, 0x2f, (byte) 0xf7, (byte) 0xe2, 0x1b});
        final byte[] context = new byte[0];
        final byte[] message = new byte[]{0x64, (byte) 0xa6, 0x5f, 0x3c, (byte) 0xde, (byte) 0xdc, (byte) 0xdd, 0x66, (byte) 0x81, 0x1e, 0x29, 0x15};
        assertArrayEquals(expected, keypair.sign(context, message));
    }

    @Test
    public void testSignPredefinedTestVector13Octets() {
        final byte[] expected = new byte[]{0x6a, 0x12, 0x06, 0x6f, 0x55, 0x33, 0x1b, 0x6c, 0x22, (byte) 0xac, (byte) 0xd5, (byte) 0xd5, (byte) 0xbf, (byte) 0xc5, (byte) 0xd7, 0x12, 0x28, (byte) 0xfb, (byte) 0xda, (byte) 0x80, (byte) 0xae, (byte) 0x8d, (byte) 0xec, 0x26, (byte) 0xbd, (byte) 0xd3, 0x06, 0x74, 0x3c, 0x50, 0x27, (byte) 0xcb, 0x48, (byte) 0x90, (byte) 0x81, 0x0c, 0x16, 0x2c, 0x02, 0x74, 0x68, 0x67, 0x5e, (byte) 0xcf, 0x64, 0x5a, (byte) 0x83, 0x17, 0x6c, 0x0d, 0x73, 0x23, (byte) 0xa2, (byte) 0xcc, (byte) 0xde, 0x2d, (byte) 0x80, (byte) 0xef, (byte) 0xe5, (byte) 0xa1, 0x26, (byte) 0x8e, (byte) 0x8a, (byte) 0xca, 0x1d, 0x6f, (byte) 0xbc, 0x19, 0x4d, 0x3f, 0x77, (byte) 0xc4, 0x49, (byte) 0x86, (byte) 0xeb, 0x4a, (byte) 0xb4, 0x17, 0x79, 0x19, (byte) 0xad, (byte) 0x8b, (byte) 0xec, 0x33, (byte) 0xeb, 0x47, (byte) 0xbb, (byte) 0xb5, (byte) 0xfc, 0x6e, 0x28, 0x19, 0x6f, (byte) 0xd1, (byte) 0xca, (byte) 0xf5, 0x6b, 0x4e, 0x7e, 0x0b, (byte) 0xa5, 0x51, (byte) 0x92, 0x34, (byte) 0xd0, 0x47, 0x15, 0x5a, (byte) 0xc7, 0x27, (byte) 0xa1, 0x05, 0x31, 0x00};
        final Ed448KeyPair keypair = Ed448KeyPair.create(new byte[]{0x7e, (byte) 0xf4, (byte) 0xe8, 0x45, 0x44, 0x23, 0x67, 0x52, (byte) 0xfb, (byte) 0xb5, 0x6b, (byte) 0x8f, 0x31, (byte) 0xa2, 0x3a, 0x10, (byte) 0xe4, 0x28, 0x14, (byte) 0xf5, (byte) 0xf5, 0x5c, (byte) 0xa0, 0x37, (byte) 0xcd, (byte) 0xcc, 0x11, (byte) 0xc6, 0x4c, (byte) 0x9a, 0x3b, 0x29, 0x49, (byte) 0xc1, (byte) 0xbb, 0x60, 0x70, 0x03, 0x14, 0x61, 0x17, 0x32, (byte) 0xa6, (byte) 0xc2, (byte) 0xfe, (byte) 0xa9, (byte) 0x8e, (byte) 0xeb, (byte) 0xc0, 0x26, 0x6a, 0x11, (byte) 0xa9, 0x39, 0x70, 0x10, 0x0e});
        final byte[] context = new byte[0];
        final byte[] message = new byte[]{0x64, (byte) 0xa6, 0x5f, 0x3c, (byte) 0xde, (byte) 0xdc, (byte) 0xdd, 0x66, (byte) 0x81, 0x1e, 0x29, 0x15, (byte) 0xe7};
        assertArrayEquals(expected, keypair.sign(context, message));
    }

    @Test
    public void testSignPredefinedTestVector1023Octets() {
        final byte[] expected = new byte[]{(byte) 0xe3, 0x01, 0x34, 0x5a, 0x41, (byte) 0xa3, (byte) 0x9a, 0x4d, 0x72, (byte) 0xff, (byte) 0xf8, (byte) 0xdf, 0x69, (byte) 0xc9, (byte) 0x80, 0x75, (byte) 0xa0, (byte) 0xcc, 0x08, 0x2b, (byte) 0x80, 0x2f, (byte) 0xc9, (byte) 0xb2, (byte) 0xb6, (byte) 0xbc, 0x50, 0x3f, (byte) 0x92, 0x6b, 0x65, (byte) 0xbd, (byte) 0xdf, 0x7f, 0x4c, (byte) 0x8f, 0x1c, (byte) 0xb4, (byte) 0x9f, 0x63, (byte) 0x96, (byte) 0xaf, (byte) 0xc8, (byte) 0xa7, 0x0a, (byte) 0xbe, 0x6d, (byte) 0x8a, (byte) 0xef, 0x0d, (byte) 0xb4, 0x78, (byte) 0xd4, (byte) 0xc6, (byte) 0xb2, (byte) 0x97, 0x00, 0x76, (byte) 0xc6, (byte) 0xa0, 0x48, 0x4f, (byte) 0xe7, 0x6d, 0x76, (byte) 0xb3, (byte) 0xa9, 0x76, 0x25, (byte) 0xd7, (byte) 0x9f, 0x1c, (byte) 0xe2, 0x40, (byte) 0xe7, (byte) 0xc5, 0x76, 0x75, 0x0d, 0x29, 0x55, 0x28, 0x28, 0x6f, 0x71, (byte) 0x9b, 0x41, 0x3d, (byte) 0xe9, (byte) 0xad, (byte) 0xa3, (byte) 0xe8, (byte) 0xeb, 0x78, (byte) 0xed, 0x57, 0x36, 0x03, (byte) 0xce, 0x30, (byte) 0xd8, (byte) 0xbb, 0x76, 0x17, (byte) 0x85, (byte) 0xdc, 0x30, (byte) 0xdb, (byte) 0xc3, 0x20, (byte) 0x86, (byte) 0x9e, 0x1a, 0x00};
        final Ed448KeyPair keypair = Ed448KeyPair.create(new byte[]{(byte) 0x87, 0x2d, 0x09, 0x37, (byte) 0x80, (byte) 0xf5, (byte) 0xd3, 0x73, 0x0d, (byte) 0xf7, (byte) 0xc2, 0x12, 0x66, 0x4b, 0x37, (byte) 0xb8, (byte) 0xa0, (byte) 0xf2, 0x4f, 0x56, (byte) 0x81, 0x0d, (byte) 0xaa, (byte) 0x83, (byte) 0x82, (byte) 0xcd, 0x4f, (byte) 0xa3, (byte) 0xf7, 0x76, 0x34, (byte) 0xec, 0x44, (byte) 0xdc, 0x54, (byte) 0xf1, (byte) 0xc2, (byte) 0xed, (byte) 0x9b, (byte) 0xea, (byte) 0x86, (byte) 0xfa, (byte) 0xfb, 0x76, 0x32, (byte) 0xd8, (byte) 0xbe, 0x19, (byte) 0x9e, (byte) 0xa1, 0x65, (byte) 0xf5, (byte) 0xad, 0x55, (byte) 0xdd, (byte) 0x9c, (byte) 0xe8});
        final byte[] context = new byte[0];
        final byte[] message = new byte[]{0x6d, (byte) 0xdf, (byte) 0x80, 0x2e, 0x1a, (byte) 0xae, 0x49, (byte) 0x86, (byte) 0x93, 0x5f, 0x7f, (byte) 0x98, 0x1b, (byte) 0xa3, (byte) 0xf0, 0x35, 0x1d, 0x62, 0x73, (byte) 0xc0, (byte) 0xa0, (byte) 0xc2, 0x2c, (byte) 0x9c, 0x0e, (byte) 0x83, 0x39, 0x16, (byte) 0x8e, 0x67, 0x54, 0x12, (byte) 0xa3, (byte) 0xde, (byte) 0xbf, (byte) 0xaf, 0x43, 0x5e, (byte) 0xd6, 0x51, 0x55, (byte) 0x80, 0x07, (byte) 0xdb, 0x43, (byte) 0x84, (byte) 0xb6, 0x50, (byte) 0xfc, (byte) 0xc0, 0x7e, 0x3b, 0x58, 0x6a, 0x27, (byte) 0xa4, (byte) 0xf7, (byte) 0xa0, 0x0a, (byte) 0xc8, (byte) 0xa6, (byte) 0xfe, (byte) 0xc2, (byte) 0xcd, (byte) 0x86, (byte) 0xae, 0x4b, (byte) 0xf1, 0x57, 0x0c, 0x41, (byte) 0xe6, (byte) 0xa4, 0x0c, (byte) 0x93, 0x1d, (byte) 0xb2, 0x7b, 0x2f, (byte) 0xaa, 0x15, (byte) 0xa8, (byte) 0xce, (byte) 0xdd, 0x52, (byte) 0xcf, (byte) 0xf7, 0x36, 0x2c, 0x4e, 0x6e, 0x23, (byte) 0xda, (byte) 0xec, 0x0f, (byte) 0xbc, 0x3a, 0x79, (byte) 0xb6, (byte) 0x80, 0x6e, 0x31, 0x6e, (byte) 0xfc, (byte) 0xc7, (byte) 0xb6, (byte) 0x81, 0x19, (byte) 0xbf, 0x46, (byte) 0xbc, 0x76, (byte) 0xa2, 0x60, 0x67, (byte) 0xa5, 0x3f, 0x29, 0x6d, (byte) 0xaf, (byte) 0xdb, (byte) 0xdc, 0x11, (byte) 0xc7, 0x7f, 0x77, 0x77, (byte) 0xe9, 0x72, 0x66, 0x0c, (byte) 0xf4, (byte) 0xb6, (byte) 0xa9, (byte) 0xb3, 0x69, (byte) 0xa6, 0x66, 0x5f, 0x02, (byte) 0xe0, (byte) 0xcc, (byte) 0x9b, 0x6e, (byte) 0xdf, (byte) 0xad, 0x13, 0x6b, 0x4f, (byte) 0xab, (byte) 0xe7, 0x23, (byte) 0xd2, (byte) 0x81, 0x3d, (byte) 0xb3, 0x13, 0x6c, (byte) 0xfd, (byte) 0xe9, (byte) 0xb6, (byte) 0xd0, 0x44, 0x32, 0x2f, (byte) 0xee, 0x29, 0x47, (byte) 0x95, 0x2e, 0x03, 0x1b, 0x73, (byte) 0xab, 0x5c, 0x60, 0x33, 0x49, (byte) 0xb3, 0x07, (byte) 0xbd, (byte) 0xc2, 0x7b, (byte) 0xc6, (byte) 0xcb, (byte) 0x8b, (byte) 0x8b, (byte) 0xbd, 0x7b, (byte) 0xd3, 0x23, 0x21, (byte) 0x9b, (byte) 0x80, 0x33, (byte) 0xa5, (byte) 0x81, (byte) 0xb5, (byte) 0x9e, (byte) 0xad, (byte) 0xeb, (byte) 0xb0, (byte) 0x9b, 0x3c, 0x4f, 0x3d, 0x22, 0x77, (byte) 0xd4, (byte) 0xf0, 0x34, 0x36, 0x24, (byte) 0xac, (byte) 0xc8, 0x17, (byte) 0x80, 0x47, 0x28, (byte) 0xb2, 0x5a, (byte) 0xb7, (byte) 0x97, 0x17, 0x2b, 0x4c, 0x5c, 0x21, (byte) 0xa2, 0x2f, (byte) 0x9c, 0x78, 0x39, (byte) 0xd6, 0x43, 0x00, 0x23, 0x2e, (byte) 0xb6, 0x6e, 0x53, (byte) 0xf3, 0x1c, 0x72, 0x3f, (byte) 0xa3, 0x7f, (byte) 0xe3, (byte) 0x87, (byte) 0xc7, (byte) 0xd3, (byte) 0xe5, 0x0b, (byte) 0xdf, (byte) 0x98, 0x13, (byte) 0xa3, 0x0e, 0x5b, (byte) 0xb1, 0x2c, (byte) 0xf4, (byte) 0xcd, (byte) 0x93, 0x0c, 0x40, (byte) 0xcf, (byte) 0xb4, (byte) 0xe1, (byte) 0xfc, 0x62, 0x25, (byte) 0x92, (byte) 0xa4, (byte) 0x95, (byte) 0x88, 0x79, 0x44, (byte) 0x94, (byte) 0xd5, 0x6d, 0x24, (byte) 0xea, 0x4b, 0x40, (byte) 0xc8, (byte) 0x9f, (byte) 0xc0, 0x59, 0x6c, (byte) 0xc9, (byte) 0xeb, (byte) 0xb9, 0x61, (byte) 0xc8, (byte) 0xcb, 0x10, (byte) 0xad, (byte) 0xde, (byte) 0x97, 0x6a, 0x5d, 0x60, 0x2b, 0x1c, 0x3f, (byte) 0x85, (byte) 0xb9, (byte) 0xb9, (byte) 0xa0, 0x01, (byte) 0xed, 0x3c, 0x6a, 0x4d, 0x3b, 0x14, 0x37, (byte) 0xf5, 0x20, (byte) 0x96, (byte) 0xcd, 0x19, 0x56, (byte) 0xd0, 0x42, (byte) 0xa5, (byte) 0x97, (byte) 0xd5, 0x61, (byte) 0xa5, (byte) 0x96, (byte) 0xec, (byte) 0xd3, (byte) 0xd1, 0x73, 0x5a, (byte) 0x8d, 0x57, 0x0e, (byte) 0xa0, (byte) 0xec, 0x27, 0x22, 0x5a, 0x2c, 0x4a, (byte) 0xaf, (byte) 0xf2, 0x63, 0x06, (byte) 0xd1, 0x52, 0x6c, 0x1a, (byte) 0xf3, (byte) 0xca, 0x6d, (byte) 0x9c, (byte) 0xf5, (byte) 0xa2, (byte) 0xc9, (byte) 0x8f, 0x47, (byte) 0xe1, (byte) 0xc4, 0x6d, (byte) 0xb9, (byte) 0xa3, 0x32, 0x34, (byte) 0xcf, (byte) 0xd4, (byte) 0xd8, 0x1f, 0x2c, (byte) 0x98, 0x53, (byte) 0x8a, 0x09, (byte) 0xeb, (byte) 0xe7, 0x69, (byte) 0x98, (byte) 0xd0, (byte) 0xd8, (byte) 0xfd, 0x25, (byte) 0x99, 0x7c, 0x7d, 0x25, 0x5c, 0x6d, 0x66, (byte) 0xec, (byte) 0xe6, (byte) 0xfa, 0x56, (byte) 0xf1, 0x11, 0x44, (byte) 0x95, 0x0f, 0x02, 0x77, (byte) 0x95, (byte) 0xe6, 0x53, 0x00, (byte) 0x8f, 0x4b, (byte) 0xd7, (byte) 0xca, 0x2d, (byte) 0xee, (byte) 0x85, (byte) 0xd8, (byte) 0xe9, 0x0f, 0x3d, (byte) 0xc3, 0x15, 0x13, 0x0c, (byte) 0xe2, (byte) 0xa0, 0x03, 0x75, (byte) 0xa3, 0x18, (byte) 0xc7, (byte) 0xc3, (byte) 0xd9, 0x7b, (byte) 0xe2, (byte) 0xc8, (byte) 0xce, 0x5b, 0x6d, (byte) 0xb4, 0x1a, 0x62, 0x54, (byte) 0xff, 0x26, 0x4f, (byte) 0xa6, 0x15, 0x5b, (byte) 0xae, (byte) 0xe3, (byte) 0xb0, 0x77, 0x3c, 0x0f, 0x49, 0x7c, 0x57, 0x3f, 0x19, (byte) 0xbb, 0x4f, 0x42, 0x40, 0x28, 0x1f, 0x0b, 0x1f, 0x4f, 0x7b, (byte) 0xe8, 0x57, (byte) 0xa4, (byte) 0xe5, (byte) 0x9d, 0x41, 0x6c, 0x06, (byte) 0xb4, (byte) 0xc5, 0x0f, (byte) 0xa0, (byte) 0x9e, 0x18, 0x10, (byte) 0xdd, (byte) 0xc6, (byte) 0xb1, 0x46, 0x7b, (byte) 0xae, (byte) 0xac, 0x5a, 0x36, 0x68, (byte) 0xd1, 0x1b, 0x6e, (byte) 0xca, (byte) 0xa9, 0x01, 0x44, 0x00, 0x16, (byte) 0xf3, (byte) 0x89, (byte) 0xf8, 0x0a, (byte) 0xcc, 0x4d, (byte) 0xb9, 0x77, 0x02, 0x5e, 0x7f, 0x59, 0x24, 0x38, (byte) 0x8c, 0x7e, 0x34, 0x0a, 0x73, 0x2e, 0x55, 0x44, 0x40, (byte) 0xe7, 0x65, 0x70, (byte) 0xf8, (byte) 0xdd, 0x71, (byte) 0xb7, (byte) 0xd6, 0x40, (byte) 0xb3, 0x45, 0x0d, 0x1f, (byte) 0xd5, (byte) 0xf0, 0x41, 0x0a, 0x18, (byte) 0xf9, (byte) 0xa3, 0x49, 0x4f, 0x70, 0x7c, 0x71, 0x7b, 0x79, (byte) 0xb4, (byte) 0xbf, 0x75, (byte) 0xc9, (byte) 0x84, 0x00, (byte) 0xb0, (byte) 0x96, (byte) 0xb2, 0x16, 0x53, (byte) 0xb5, (byte) 0xd2, 0x17, (byte) 0xcf, 0x35, 0x65, (byte) 0xc9, 0x59, 0x74, 0x56, (byte) 0xf7, 0x07, 0x03, 0x49, 0x7a, 0x07, (byte) 0x87, 0x63, (byte) 0x82, (byte) 0x9b, (byte) 0xc0, 0x1b, (byte) 0xb1, (byte) 0xcb, (byte) 0xc8, (byte) 0xfa, 0x04, (byte) 0xea, (byte) 0xdc, (byte) 0x9a, 0x6e, 0x3f, 0x66, (byte) 0x99, 0x58, 0x7a, (byte) 0x9e, 0x75, (byte) 0xc9, 0x4e, 0x5b, (byte) 0xab, 0x00, 0x36, (byte) 0xe0, (byte) 0xb2, (byte) 0xe7, 0x11, 0x39, 0x2c, (byte) 0xff, 0x00, 0x47, (byte) 0xd0, (byte) 0xd6, (byte) 0xb0, 0x5b, (byte) 0xd2, (byte) 0xa5, (byte) 0x88, (byte) 0xbc, 0x10, (byte) 0x97, 0x18, (byte) 0x95, 0x42, 0x59, (byte) 0xf1, (byte) 0xd8, 0x66, 0x78, (byte) 0xa5, 0x79, (byte) 0xa3, 0x12, 0x0f, 0x19, (byte) 0xcf, (byte) 0xb2, (byte) 0x96, 0x3f, 0x17, 0x7a, (byte) 0xeb, 0x70, (byte) 0xf2, (byte) 0xd4, (byte) 0x84, 0x48, 0x26, 0x26, 0x2e, 0x51, (byte) 0xb8, 0x02, 0x71, 0x27, 0x20, 0x68, (byte) 0xef, 0x5b, 0x38, 0x56, (byte) 0xfa, (byte) 0x85, 0x35, (byte) 0xaa, 0x2a, (byte) 0x88, (byte) 0xb2, (byte) 0xd4, 0x1f, 0x2a, 0x0e, 0x2f, (byte) 0xda, 0x76, 0x24, (byte) 0xc2, (byte) 0x85, 0x02, 0x72, (byte) 0xac, 0x4a, 0x2f, 0x56, 0x1f, (byte) 0x8f, 0x2f, 0x7a, 0x31, (byte) 0x8b, (byte) 0xfd, 0x5c, (byte) 0xaf, (byte) 0x96, (byte) 0x96, 0x14, (byte) 0x9e, 0x4a, (byte) 0xc8, 0x24, (byte) 0xad, 0x34, 0x60, 0x53, (byte) 0x8f, (byte) 0xdc, 0x25, 0x42, 0x1b, (byte) 0xee, (byte) 0xc2, (byte) 0xcc, 0x68, 0x18, 0x16, 0x2d, 0x06, (byte) 0xbb, (byte) 0xed, 0x0c, 0x40, (byte) 0xa3, (byte) 0x87, 0x19, 0x23, 0x49, (byte) 0xdb, 0x67, (byte) 0xa1, 0x18, (byte) 0xba, (byte) 0xda, 0x6c, (byte) 0xd5, (byte) 0xab, 0x01, 0x40, (byte) 0xee, 0x27, 0x32, 0x04, (byte) 0xf6, 0x28, (byte) 0xaa, (byte) 0xd1, (byte) 0xc1, 0x35, (byte) 0xf7, 0x70, 0x27, (byte) 0x9a, 0x65, 0x1e, 0x24, (byte) 0xd8, (byte) 0xc1, 0x4d, 0x75, (byte) 0xa6, 0x05, (byte) 0x9d, 0x76, (byte) 0xb9, 0x6a, 0x6f, (byte) 0xd8, 0x57, (byte) 0xde, (byte) 0xf5, (byte) 0xe0, (byte) 0xb3, 0x54, (byte) 0xb2, 0x7a, (byte) 0xb9, 0x37, (byte) 0xa5, (byte) 0x81, 0x5d, 0x16, (byte) 0xb5, (byte) 0xfa, (byte) 0xe4, 0x07, (byte) 0xff, 0x18, 0x22, 0x2c, 0x6d, 0x1e, (byte) 0xd2, 0x63, (byte) 0xbe, 0x68, (byte) 0xc9, 0x5f, 0x32, (byte) 0xd9, 0x08, (byte) 0xbd, (byte) 0x89, 0x5c, (byte) 0xd7, 0x62, 0x07, (byte) 0xae, 0x72, 0x64, (byte) 0x87, 0x56, 0x7f, (byte) 0x9a, 0x67, (byte) 0xda, (byte) 0xd7, (byte) 0x9a, (byte) 0xbe, (byte) 0xc3, 0x16, (byte) 0xf6, (byte) 0x83, (byte) 0xb1, 0x7f, 0x2d, 0x02, (byte) 0xbf, 0x07, (byte) 0xe0, (byte) 0xac, (byte) 0x8b, 0x5b, (byte) 0xc6, 0x16, 0x2c, (byte) 0xf9, 0x46, (byte) 0x97, (byte) 0xb3, (byte) 0xc2, 0x7c, (byte) 0xd1, (byte) 0xfe, (byte) 0xa4, (byte) 0x9b, 0x27, (byte) 0xf2, 0x3b, (byte) 0xa2, (byte) 0x90, 0x18, 0x71, (byte) 0x96, 0x25, 0x06, 0x52, 0x0c, 0x39, 0x2d, (byte) 0xa8, (byte) 0xb6, (byte) 0xad, 0x0d, (byte) 0x99, (byte) 0xf7, 0x01, 0x3f, (byte) 0xbc, 0x06, (byte) 0xc2, (byte) 0xc1, 0x7a, 0x56, (byte) 0x95, 0x00, (byte) 0xc8, (byte) 0xa7, 0x69, 0x64, (byte) 0x81, (byte) 0xc1, (byte) 0xcd, 0x33, (byte) 0xe9, (byte) 0xb1, 0x4e, 0x40, (byte) 0xb8, 0x2e, 0x79, (byte) 0xa5, (byte) 0xf5, (byte) 0xdb, (byte) 0x82, 0x57, 0x1b, (byte) 0xa9, 0x7b, (byte) 0xae, 0x3a, (byte) 0xd3, (byte) 0xe0, 0x47, (byte) 0x95, 0x15, (byte) 0xbb, 0x0e, 0x2b, 0x0f, 0x3b, (byte) 0xfc, (byte) 0xd1, (byte) 0xfd, 0x33, 0x03, 0x4e, (byte) 0xfc, 0x62, 0x45, (byte) 0xed, (byte) 0xdd, 0x7e, (byte) 0xe2, 0x08, 0x6d, (byte) 0xda, (byte) 0xe2, 0x60, 0x0d, (byte) 0x8c, (byte) 0xa7, 0x3e, 0x21, 0x4e, (byte) 0x8c, 0x2b, 0x0b, (byte) 0xdb, 0x2b, 0x04, 0x7c, 0x6a, 0x46, 0x4a, 0x56, 0x2e, (byte) 0xd7, 0x7b, 0x73, (byte) 0xd2, (byte) 0xd8, 0x41, (byte) 0xc4, (byte) 0xb3, 0x49, 0x73, 0x55, 0x12, 0x57, 0x71, 0x3b, 0x75, 0x36, 0x32, (byte) 0xef, (byte) 0xba, 0x34, (byte) 0x81, 0x69, (byte) 0xab, (byte) 0xc9, 0x0a, 0x68, (byte) 0xf4, 0x26, 0x11, (byte) 0xa4, 0x01, 0x26, (byte) 0xd7, (byte) 0xcb, 0x21, (byte) 0xb5, (byte) 0x86, (byte) 0x95, 0x56, (byte) 0x81, (byte) 0x86, (byte) 0xf7, (byte) 0xe5, 0x69, (byte) 0xd2, (byte) 0xff, 0x0f, (byte) 0x9e, 0x74, 0x5d, 0x04, (byte) 0x87, (byte) 0xdd, 0x2e, (byte) 0xb9, (byte) 0x97, (byte) 0xca, (byte) 0xfc, 0x5a, (byte) 0xbf, (byte) 0x9d, (byte) 0xd1, 0x02, (byte) 0xe6, 0x2f, (byte) 0xf6, 0x6c, (byte) 0xba, (byte) 0x87};
        assertArrayEquals(expected, keypair.sign(context, message));
    }

    @Test
    public void generate1023OctetsPublicKey() throws InvalidDataException {
        final Point expected = decode(new byte[]{(byte) 0xa8, 0x1b, 0x2e, (byte) 0x8a, 0x70, (byte) 0xa5, (byte) 0xac, (byte) 0x94, (byte) 0xff, (byte) 0xdb, (byte) 0xcc, (byte) 0x9b, (byte) 0xad, (byte) 0xfc, 0x3f, (byte) 0xeb, 0x08, 0x01, (byte) 0xf2, 0x58, 0x57, (byte) 0x8b, (byte) 0xb1, 0x14, (byte) 0xad, 0x44, (byte) 0xec, (byte) 0xe1, (byte) 0xec, 0x0e, 0x79, (byte) 0x9d, (byte) 0xa0, (byte) 0x8e, (byte) 0xff, (byte) 0xb8, 0x1c, 0x5d, 0x68, 0x5c, 0x0c, 0x56, (byte) 0xf6, 0x4e, (byte) 0xec, (byte) 0xae, (byte) 0xf8, (byte) 0xcd, (byte) 0xf1, 0x1c, (byte) 0xc3, (byte) 0x87, 0x37, (byte) 0x83, (byte) 0x8c, (byte) 0xf4, 0x00});
        final Ed448KeyPair keypair = Ed448KeyPair.create(new byte[]{(byte) 0x87, 0x2d, 0x09, 0x37, (byte) 0x80, (byte) 0xf5, (byte) 0xd3, 0x73, 0x0d, (byte) 0xf7, (byte) 0xc2, 0x12, 0x66, 0x4b, 0x37, (byte) 0xb8, (byte) 0xa0, (byte) 0xf2, 0x4f, 0x56, (byte) 0x81, 0x0d, (byte) 0xaa, (byte) 0x83, (byte) 0x82, (byte) 0xcd, 0x4f, (byte) 0xa3, (byte) 0xf7, 0x76, 0x34, (byte) 0xec, 0x44, (byte) 0xdc, 0x54, (byte) 0xf1, (byte) 0xc2, (byte) 0xed, (byte) 0x9b, (byte) 0xea, (byte) 0x86, (byte) 0xfa, (byte) 0xfb, 0x76, 0x32, (byte) 0xd8, (byte) 0xbe, 0x19, (byte) 0x9e, (byte) 0xa1, 0x65, (byte) 0xf5, (byte) 0xad, 0x55, (byte) 0xdd, (byte) 0x9c, (byte) 0xe8});
        final BigInteger secretKey = keypair.getSecretKey();
        final Point generated = multiplyByBase(secretKey);
        assertEquals(expected.x(), generated.x());
        assertEquals(expected.y(), generated.y());
    }
}

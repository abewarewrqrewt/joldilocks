package nl.dannyvanheumen.joldilocks;

import java.math.BigInteger;

import static nl.dannyvanheumen.joldilocks.Scalars.deserialize;

/**
 * Ed448-Goldilocks implementation.
 */
public final class Ed448 {

    private static final int LENGTH_SCALAR_BITS = 448;
    static final int LENGTH_SCALAR_BYTES = LENGTH_SCALAR_BITS / 8;

    /**
     * Cofactor of Ed448-Goldilocks curve.
     */
    static final BigInteger COFACTOR = BigInteger.valueOf(4);

    /**
     * Prime p of the Ed448-Goldilocks curve. (Used as modulus.)
     */
    static final BigInteger MODULUS = new BigInteger("fffffffffffffffffffffffffffffffffffffffffffffffffffffffeffffffffffffffffffffffffffffffffffffffffffffffffffffffff", 16);

    /**
     * Prime order q.
     * NOTE: 4q is the order of the Ed448-Goldilocks curve.
     * <p>
     * The order of its twist is: 4 * (2**446 - 0x335dc163bb124b65129c96fde933d8d723a70aadc873d6d54a7bb0d). Not sure if that is actually used yet.
     */
    static final BigInteger PRIME_ORDER = new BigInteger("3fffffffffffffffffffffffffffffffffffffffffffffffffffffff7cca23e9c44edb49aed63690216cc2728dc58f552378c292ab5844f3", 16);

    /**
     * a parameter of the untwisted Edwards curve formula.
     * <p>
     * NOTE: Page 5 of Decaf paper describes how they take "Untwisted" (Edwards Curve [sic]) to mean a = 1.
     */
    static final BigInteger A = BigInteger.ONE;

    /**
     * Edwards constant d.
     */
    static final BigInteger D = BigInteger.valueOf(-39081);

    /**
     * Base point for Ed448-Goldilocks.
     *
     * NOTE: this is the base point in 4E.
     */
    //FIXME: Dezerialization of the byte array fails, find out why
//    static final Point P = ExtendedPoint.fromEdwards(
//            // X(P): 224580040295924300187604334099896036246789641632564134246125461686950415467406032909029192869357953282578032075146446173674602635247710
//            deserialize(new byte[]{
//                    (byte) 0x4f, (byte) 0x19, (byte) 0x70, (byte) 0xc6, (byte) 0x6b, (byte) 0xed, (byte) 0x0d,
//                    (byte) 0xed, (byte) 0x22, (byte) 0x1d, (byte) 0x15, (byte) 0xa6, (byte) 0x22, (byte) 0xbf,
//                    (byte) 0x36, (byte) 0xda, (byte) 0x9e, (byte) 0x14, (byte) 0x65, (byte) 0x70, (byte) 0x47,
//                    (byte) 0x0f, (byte) 0x17, (byte) 0x67, (byte) 0xea, (byte) 0x6d, (byte) 0xe3, (byte) 0x24,
//                    (byte) 0xa3, (byte) 0xd3, (byte) 0xa4, (byte) 0x64, (byte) 0x12, (byte) 0xae, (byte) 0x1a,
//                    (byte) 0xf7, (byte) 0x2a, (byte) 0xb6, (byte) 0x65, (byte) 0x11, (byte) 0x43, (byte) 0x3b,
//                    (byte) 0x80, (byte) 0xe1, (byte) 0x8b, (byte) 0x00, (byte) 0x93, (byte) 0x8e, (byte) 0x26,
//                    (byte) 0x26, (byte) 0xa8, (byte) 0x2b, (byte) 0xc7, (byte) 0x0c, (byte) 0xc0, (byte) 0x5e}),
//            // Y(P): 298819210078481492676017930443930673437544040154080242095928241372331506189835876003536878655418784733982303233503462500531545062832660
//            deserialize(new byte[]{
//                    (byte) 0x69, (byte) 0x3f, (byte) 0x46, (byte) 0x71, (byte) 0x6e, (byte) 0xb6, (byte) 0xbc,
//                    (byte) 0x24, (byte) 0x88, (byte) 0x76, (byte) 0x20, (byte) 0x37, (byte) 0x56, (byte) 0xc9,
//                    (byte) 0xc7, (byte) 0x62, (byte) 0x4b, (byte) 0xea, (byte) 0x73, (byte) 0x73, (byte) 0x6c,
//                    (byte) 0xa3, (byte) 0x98, (byte) 0x40, (byte) 0x87, (byte) 0x78, (byte) 0x9c, (byte) 0x1e,
//                    (byte) 0x05, (byte) 0xa0, (byte) 0xc2, (byte) 0xd7, (byte) 0x3a, (byte) 0xd3, (byte) 0xff,
//                    (byte) 0x1c, (byte) 0xe6, (byte) 0x7c, (byte) 0x39, (byte) 0xc4, (byte) 0xfd, (byte) 0xbd,
//                    (byte) 0x13, (byte) 0x2c, (byte) 0x4e, (byte) 0xd7, (byte) 0xc8, (byte) 0xad, (byte) 0x98,
//                    (byte) 0x08, (byte) 0x79, (byte) 0x5b, (byte) 0xf2, (byte) 0x30, (byte) 0xfa, (byte) 0x14}));

    static final Point P = ExtendedPoint.fromEdwards(
            new BigInteger("224580040295924300187604334099896036246789641632564134246125461686950415467406032909029192869357953282578032075146446173674602635247710"),
            new BigInteger("298819210078481492676017930443930673437544040154080242095928241372331506189835876003536878655418784733982303233503462500531545062832660")
    );
}

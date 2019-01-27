package nl.dannyvanheumen.joldilocks;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;

final class BigIntegers {

    static final BigInteger TWO = BigInteger.valueOf(2L);
    static final BigInteger MINUS_TWO = BigInteger.valueOf(-2L);

    static final BigInteger THREE = BigInteger.valueOf(3L);
    static final BigInteger FOUR = BigInteger.valueOf(4L);
    static final BigInteger FIVE = BigInteger.valueOf(5L);
    static final BigInteger SIX = BigInteger.valueOf(6L);
    static final BigInteger SEVEN = BigInteger.valueOf(7L);
    static final BigInteger EIGHT = BigInteger.valueOf(8L);
    static final BigInteger NINE = BigInteger.valueOf(9L);

    static final BigInteger SIXTEEN = BigInteger.valueOf(16L);
    static final BigInteger THIRTYTWO = BigInteger.valueOf(32L);
    static final BigInteger SIXTYFOUR = BigInteger.valueOf(64L);
    static final BigInteger ONEHUNDREDTWENTYEIGHT = BigInteger.valueOf(128L);
    static final BigInteger TWOHUNDREDFIFTYSIX = BigInteger.valueOf(256L);
    static final BigInteger FIVEHUNDREDTWELVE = BigInteger.valueOf(512L);
    static final BigInteger ONETHOUSANDTWENTYFOUR = BigInteger.valueOf(1024L);
    static final BigInteger TWOTHOUSANDFORTYEIGHT = BigInteger.valueOf(2048L);

    static final BigInteger TWO_POWER_447 = TWO.pow(447);
    static final BigInteger FOUR_TIMES_TWO_POWER_445_MINUS_ONE = FOUR.multiply(TWO.pow(445).subtract(ONE));

    private BigIntegers() {
        // No need to instantiate utility class.
    }
}

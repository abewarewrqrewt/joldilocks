package nl.dannyvanheumen.joldilocks;

import javax.annotation.Nonnull;
import java.math.BigInteger;

// TODO consider making public
interface Point<P extends Point> {

    @Nonnull
    BigInteger x();

    @Nonnull
    BigInteger y();

    @Nonnull
    P negate();

    @Nonnull
    P add(P p);
}

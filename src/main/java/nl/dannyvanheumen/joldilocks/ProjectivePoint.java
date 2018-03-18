package nl.dannyvanheumen.joldilocks;

import javax.annotation.Nonnull;
import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TWO;
import static java.util.Objects.requireNonNull;
import static nl.dannyvanheumen.joldilocks.Ed448.D;
import static nl.dannyvanheumen.joldilocks.Ed448.MODULUS;

/**
 * Edwards point in Projective Homogenous representation.
 */
final class ProjectivePoint implements Point {

    private final BigInteger x;
    private final BigInteger y;
    private final BigInteger z;

    ProjectivePoint(final BigInteger x, final BigInteger y, final BigInteger z) {
        this.x = requireNonNull(x);
        this.y = requireNonNull(y);
        this.z = requireNonNull(z);
    }

    static ProjectivePoint fromEdwards(final BigInteger edwardsX, final BigInteger edwardsY) {
        return new ProjectivePoint(edwardsX, edwardsY, ONE);
    }

    @Nonnull
    @Override
    public BigInteger x() {
        return this.x.divide(this.z).mod(MODULUS);
    }

    @Nonnull
    @Override
    public BigInteger y() {
        return this.y.divide(this.z).mod(MODULUS);
    }

    @Nonnull
    @Override
    public Point negate() {
        throw new UnsupportedOperationException("To be implemented");
    }

    @Nonnull
    @Override
    public ProjectivePoint add(final Point p) {
        final ProjectivePoint other = (ProjectivePoint) p;
        //                 A = Z1*Z2
        //                 B = A^2
        //                 C = X1*X2
        //                 D = Y1*Y2
        //                 E = d*C*D
        //                 F = B-E
        //                 G = B+E
        //                 H = (X1+Y1)*(X2+Y2)
        //                 X3 = A*F*(H-C-D)
        //                 Y3 = A*G*(D-C)
        //                 Z3 = F*G
        final BigInteger a = this.z.multiply(other.z).mod(MODULUS);
        final BigInteger b = a.multiply(a).mod(MODULUS);
        final BigInteger c = this.x.multiply(other.x).mod(MODULUS);
        final BigInteger d = this.y.multiply(other.y).mod(MODULUS);
        final BigInteger e = D.multiply(c).mod(MODULUS).multiply(d).mod(MODULUS);
        final BigInteger f = b.subtract(e).mod(MODULUS);
        final BigInteger g = b.add(e).mod(MODULUS);
        final BigInteger h = this.x.add(this.y).mod(MODULUS).multiply(other.x.add(other.y).mod(MODULUS)).mod(MODULUS);
        final BigInteger resultX = a.multiply(f).mod(MODULUS).multiply(h.subtract(c).mod(MODULUS).subtract(d).mod(MODULUS)).mod(MODULUS);
        final BigInteger resultY = a.multiply(g).mod(MODULUS).multiply(d.subtract(c).mod(MODULUS)).mod(MODULUS);
        final BigInteger resultZ = f.multiply(g).mod(MODULUS);
//        return ProjectivePoint.fromEdwards(resultX.divide(resultZ), resultY.divide(resultZ));
        return new ProjectivePoint(resultX, resultY, resultZ);
    }

    @Nonnull
    @Override
    public ProjectivePoint doubling() {
        //                 B = (X1+Y1)^2
        //                 C = X1^2
        //                 D = Y1^2
        //                 E = C+D
        //                 H = Z1^2
        //                 J = E-2*H
        //                 X3 = (B-E)*J
        //                 Y3 = E*(C-D)
        //                 Z3 = E*J
        final BigInteger xPlusY = this.x.add(this.y);
        final BigInteger b = xPlusY.multiply(xPlusY).mod(MODULUS);
        final BigInteger c = this.x.multiply(this.x).mod(MODULUS);
        final BigInteger d = this.y.multiply(this.y).mod(MODULUS);
        final BigInteger e = c.add(d);
        final BigInteger h = this.z.multiply(this.z);
        final BigInteger j = e.subtract(h.multiply(TWO));
        final BigInteger resultX = b.subtract(e).multiply(j);
        final BigInteger resultY = e.multiply(c.subtract(d));
        final BigInteger resultZ = e.multiply(j);
        return new ProjectivePoint(resultX, resultY, resultZ);
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ") = (" + this.x() + ", " + this.y() + ")";
    }
}

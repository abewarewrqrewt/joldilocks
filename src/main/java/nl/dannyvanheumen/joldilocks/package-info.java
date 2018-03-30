/**
 * An implementation of Ed448-Goldilocks elliptic curve for Java.
 * <p>
 * This implementation is geared towards use in an OTR version 4 implementation. Therefore it might not be complete and
 * it does not provide every function defined in Ed448-Goldilocks.
 */
// TODO Modify the code such that public keys are persisted as byte arrays. (Is this necessary for security, otherwise in-memory objects are more convenient.)
// TODO Consider temporary instances creating during calculations that may contain sensitive data. Can we reduce allocations/securely clear this data?
@ParametersAreNonnullByDefault
package nl.dannyvanheumen.joldilocks;

import javax.annotation.ParametersAreNonnullByDefault;

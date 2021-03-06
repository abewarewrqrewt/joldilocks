# Ed448-Goldilocks for Java

This is an implementation of Ed448-Goldilocks for Java. At this level, the implementation is very basic and not ready for use in production.

Note that this implementation has, so far, only focused on minimal required functionality for use in `otr4j`.

__Status__: _Critical security requirements missing._

# Features and limitations

A short summary of features and limitations, intended to give an impression of the current state of the project in more detail.

## Functional requirements

* Ed448-Goldilocks
  * Point addition: ✓
  * Point subtraction: ❌
  * Point multiplication: ✓
  * ...

* RFC-8032: EdDSA
  * Ed448: ✓
  * Ed448ph: ❌
  * Key generation: ✓
  * Encoding and decoding: ✓
  * Signature generation: ✓
  * Signature verification: ✓

## Operational requirements

* Computational safety
  * Decaf: ❌
* Optimized for performance
  * Precompiled multiplication base table: ❌
  * Byte-arrays for internal coordinate representation: ❌
  * Extended Homogeneous point representation: ❌
  * Karatsuba multiplication: ✓  
  _Applied internally in Java, however may not be optimal._
* Effort taken to mitigate potential side-channels
  * Constant-time addition: ❌
  * Constant-time multiplication: ❌  
  _E.g.: Montgomery ladder_
  * Constant-time equality: ❌
  * In-memory representation of values (Elligator)

## Developmental requirements

* Stable API: ❌  
  _Backward compatibility not guaranteed._
* Trade-off for following spec exactly vs. Java GC behavior: ❌  
  _Ed448-Goldilocks and RFC 8032 define storing certain data as byte-arrays. However, given a high-level language like Java, we should evaluate how this way of working interacts with the language's GC. If we produce many intermediate instances that needs to be garbage-collected due to the nature of the language, then it may be more appropriate to use a different strategy. (For example, keep different types of objects in memory.)_

# References

* [Ed448-Goldilocks, a new elliptic curve](http://eprint.iacr.org/2015/625.pdf)
* HyperElliptic: Explicit Formulas Database
  * [Extended coordinates for twisted Edwards curves](https://hyperelliptic.org/EFD/g1p/auto-twisted-extended.html)
* [RFC 7748: Elliptic Curves for Security](https://tools.ietf.org/html/rfc7748)
* [RFC 8032: Edwards-Curve Digital Signature Algorithm (EdDSA)](https://tools.ietf.org/html/rfc8032)

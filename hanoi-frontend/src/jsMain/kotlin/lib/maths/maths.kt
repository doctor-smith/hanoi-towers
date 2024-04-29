package lib.maths

@DslMarker annotation class Maths

@Maths
infix fun Int.toThe(exponent: Int): Int = toThe(exponent, 1)

@Maths
tailrec fun Int.toThe(exponent: Int, store: Int): Int = when (exponent) {
    0 -> store
    else -> toThe(exponent - 1, this * store)
}

/**
 * Hintereinanderausführung von Funktionen
 */
@Maths
infix fun <A, B, C> ((B) -> C).o(after: (A) -> B): (A) -> C = { a: A -> this(after(a)) }

@Maths
infix fun <A, B, C, D> ((A) -> B).x(other: (C) -> D): (Pair<A, C>) -> Pair<B, D> = {
    aXc ->
    this(aXc.first) x other(aXc.second)
}

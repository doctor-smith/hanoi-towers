package lib.maths

@DslMarker annotation class Maths

@Maths
infix fun Int.toThe(exponent: Int): Int = toThe(exponent, 1)

@Maths
tailrec fun Int.toThe(exponent: Int, store: Int): Int = when(exponent) {
    0 -> store
    else -> toThe(exponent -1, this * store)
}


/**
 * Hintereinanderausführung von Funktionen
 */
@Maths
infix fun <A, B, C> ((B)->C).o(after: (A)->B) : (A)->C= { a:A  -> this(after(a))}

/**
 * Bilde Produkte
 */
@Maths
infix fun <F, S> F.x(other: S): Pair<F, S> = Pair(this, other)



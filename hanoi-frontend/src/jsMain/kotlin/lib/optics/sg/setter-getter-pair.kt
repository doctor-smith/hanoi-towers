package lib.optics.sg

import lib.maths.o

data class SGPair<W, P>(
    val get: (W) -> P,
    val set: (P) -> W
)

fun <W, P> get(getter: (W) -> P): ((P) -> W) -> SGPair<W, P> = {
    SGPair(getter, it)
}

infix fun <W, P> (((P) -> W) -> SGPair<W, P>).set(setter: (P) -> W): SGPair<W, P> = this(setter)

fun <W, P, D> SGPair<W, P>.o(sgPair: SGPair<P, D>): SGPair<W, D> = SGPair(
    sgPair.get o get,
    set o sgPair.set
)

package lib.optics.lens

import lib.maths.Maths
import lib.maths.o
import lib.maths.x as X

@Maths
data class Lens<W, P> (
    val get: (W) -> P,
    val set: (P) -> (W) -> W
)

@Maths
operator fun <W, P, D> Lens<W, P>.times(other: Lens<P, D>): Lens<W, D> = Lens(
    other.get o get) {
    with(other.set(it)) {
        {w:W ->   (set o this o get) (w) (w) }
    }
}


@Maths
fun <V, W, P, Q> Lens<V, P>.x(other: Lens<W, Q>): Lens<Pair<V, W>, Pair<P, Q>> = Lens(
    get X other.get
) {
    pXq -> set(pXq.first) X other.set(pXq.second)
}
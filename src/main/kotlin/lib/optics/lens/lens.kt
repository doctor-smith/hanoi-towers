package lib.optics.lens

import lib.maths.Maths
import lib.maths.o

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
fun <T> Lens<Unit, T>.storage(): Storage<T> = Storage(
    {get(Unit)},
    {t: T -> set(t)(Unit)}
)

@Maths
fun <T> Storage<T>.lens(): Lens<Unit, T> = Lens(
    {read()},
    {t:T  -> {write(t)}}
)

@Maths
operator fun <W, P> Storage<W>.times(lens: Lens<W, P>): Storage<P> = (lens() * lens).storage()
package lib.optics.transform

import lib.maths.Maths
import lib.optics.lens.Lens
import lib.optics.lens.times
import lib.optics.storage.Storage


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

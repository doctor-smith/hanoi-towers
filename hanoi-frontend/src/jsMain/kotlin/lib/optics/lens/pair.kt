package lib.optics.lens

import lib.maths.Maths

@Maths
@Suppress("FunctionName")
fun <F, S> First(): Lens<Pair<F, S>, F> = Lens(
    {pair -> pair.first},
    {f -> {p -> p.copy(first = f)}}
)

@Maths
@Suppress("FunctionName")
fun <F, S> Second(): Lens<Pair<F, S>, S> = Lens(
    {pair -> pair.second},
    {s -> {p -> p.copy(second = s)}}
)

package lib.maths


@Maths
infix fun <F, S> F.x(s: S): Pair<F, S> = Pair(this, s)

@Maths
suspend infix fun <F1, F2, S> Pair<F1, S>.mapFirst(f: suspend (F1) -> F2): Pair<F2, S> = f(first) x second

@Maths
suspend infix fun <F, S1, S2> Pair<F, S1>.mapSecond(f: suspend (S1) -> S2): Pair<F, S2> = first x f(second)

@Maths
suspend fun <S, T> Pair<suspend (S)->T, S>.evaluate(): T = first(second)
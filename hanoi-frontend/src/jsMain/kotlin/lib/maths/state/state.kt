package lib.maths.state

import lib.maths.*

typealias State<S, T> = suspend (S) -> Pair<T, S>
typealias KlState<St, S, T> = suspend (S) -> State<St, T>

@Maths
fun <S, T> State(f: suspend (S) -> Pair<T, S>): State<S, T> = f

@Maths
fun <S, T> State(t: T): State<S, T> = State { s -> t x s }
@Maths
fun <St, S, T> KlState(kleisli: suspend (S) -> State<St, T>): KlState<St, S, T> = kleisli
@Maths
@Suppress("FunctionName")
fun <St, S, T> KlStateF(kleisli: suspend (S) -> suspend (St) -> Pair<T, St>): KlState<St, S, T> = KlState {
    s: S ->
    State { st: St -> kleisli(s)(st) }
}

@Maths
suspend infix fun <R, S, T> State<R, S>.map(f: suspend (S) -> T): State<R, T> = State {
    r ->
    this (r) mapFirst f
}
@Maths
suspend fun <S, T> State<S, State<S, T>>.multiply(): State<S, T> = State {
    s ->
    this(s).evaluate()
}
@Maths
suspend infix fun <St, S, T> State<St, S>.bind(kleisli: KlState<St, S, T>): State<St, T> = (this map kleisli).multiply()
@Maths
suspend operator fun <St, S, T> State<St, S>.times(kleisli: KlState<St, S, T>): State<St, T> = this bind kleisli

@Maths
val <St, S, T> State<St, suspend (S) -> T>.applyTo: suspend (State<St, S>) -> suspend (St) -> Pair<T, St>
    get() = {
        stateS ->
        this@applyTo bind { f -> stateS map f }
    }
@Maths
suspend infix fun <S, T> State<S, T>.runOn(state: S): Pair<T, S> = this(state)

package org.evoleq.math.state

import org.evoleq.math.MathDsl
import org.evoleq.math.evaluate
import org.evoleq.math.mapFirst
import org.evoleq.math.x


typealias State<S, T> = suspend (S)->Pair<T, S>
typealias KlState<St, S, T> = suspend (S)-> State<St, T>

@MathDsl
@Suppress("FunctionName")
fun <S,  T> State(f: suspend (S) -> Pair<T, S>): State<S, T> = f

@MathDsl
@Suppress("FunctionName")
fun <S, T> State(t: T): State<S, T> = State{ s -> t x s }
@MathDsl
@Suppress("FunctionName")
fun <St, S, T> KlState(kleisli: suspend (S)-> State<St, T>): KlState<St, S, T> = kleisli
@MathDsl
@Suppress("FunctionName")
fun <St, S, T> KlStateF(kleisli: suspend (S)-> suspend (St) ->Pair<T, St>): KlState<St, S, T> = KlState{
   s: S -> State { st: St -> kleisli(s)(st)}
}

@MathDsl
suspend infix fun <R, S, T> State<R, S>.map(f: suspend (S) -> T): State<R, T> = State{
    r -> this (r) mapFirst f
}
@MathDsl
suspend fun <S, T> State<S, State<S, T>>.multiply(): State<S, T> = State {
    s -> this(s).evaluate()
}
@MathDsl
suspend infix fun <St, S, T> State<St, S>.bind(kleisli: KlState<St, S, T>): State<St, T> = (this map kleisli).multiply()
@MathDsl
suspend operator fun <St, S, T> State<St, S>.times(kleisli: KlState<St, S, T>): State<St, T> = this bind kleisli

@MathDsl
val <St, S, T> State<St, suspend (S) -> T>.applyTo: suspend (State<St, S>) -> suspend (St) -> Pair<T, St>
    get() = {
        stateS -> this@applyTo bind { f -> stateS map f }
    }
@MathDsl
suspend infix fun <S, T> State<S, T>.runOn(state: S): Pair<T, S> = this(state)
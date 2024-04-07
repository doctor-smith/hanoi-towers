package org.evoleq.util

import hanoi.towers.application.environment.Environment
import hanoi.towers.application.environment.connectToDatabase
import io.ktor.server.application.*
import org.evoleq.math.MathDsl
import org.evoleq.math.mapSecond
import org.evoleq.math.state.KlState
import org.evoleq.math.state.KlStateF
import org.evoleq.math.state.State
import org.jetbrains.exposed.sql.Database

typealias Base = BaseState
typealias Action<T> = State<Base, T>
typealias KlAction<S, T> = KlState<Base, S, T>

data class BaseState(
    val call: ApplicationCall,
    val database: Database
)

fun Base(call: ApplicationCall, environment: Environment): BaseState = Base(call, environment.connectToDatabase())
@MathDsl
fun <T> Action(state: suspend (b: Base) -> Pair<T, Base>): Action<T> = State{ b -> state(b)}
@MathDsl
@Suppress("FunctionName")
fun <T> DbAction(state: suspend (Database) -> Pair<T, Database>): Action<T> = Action{
    base: Base -> state(base.database) mapSecond {database -> base.copy(database = database) }
}
@MathDsl
@Suppress("FunctionName")
fun <T> ApiAction(state: suspend (ApplicationCall) -> Pair<T, ApplicationCall>): Action<T> = Action{
        base: Base -> state(base.call) mapSecond {call -> base.copy(call = call) }
}
@MathDsl
fun <S, T> KlAction(kleisli: suspend (S)-> Action< T>): KlAction<S, T> = KlState(kleisli)
@MathDsl
@Suppress("FunctionName")
fun <S, T> KlActionF(kleisli: suspend (S)-> suspend (b: Base) -> Pair<T, Base>): KlAction<S, T> = KlStateF(kleisli)
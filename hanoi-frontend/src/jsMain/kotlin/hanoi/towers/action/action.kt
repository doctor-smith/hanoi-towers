package hanoi.towers.action

import hanoi.towers.data.environment.Environment
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json
import lib.ktor.JsClient
import lib.maths.state.KlState
import lib.maths.state.State
import lib.maths.x

typealias Action<T> = State<Base, T>
typealias KlAction<S, T> = KlState<Base, S, T>
data class Base(
    val environment:  Environment,
    val client: HttpClient = JsClient()
)

@Suppress("FunctionName")
suspend inline fun <reified T> Get(
    url: String,
    crossinline block: HttpRequestBuilder.(Environment)->Unit = {port  = it.hanoiBackendPort}
): Action<T> = State{
    base -> base.client.get<T>(url) {
         this.block(base.environment)
    } x base
}

@Suppress("FunctionName")
suspend inline fun <reified T> DecodeTo(): KlAction<String, T> = KlState<Base, String, T> {
    input -> State{ base -> Json.decodeFromString<T>(input) x base }
}


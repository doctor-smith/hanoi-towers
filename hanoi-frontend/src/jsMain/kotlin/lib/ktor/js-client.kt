package lib.ktor

import io.ktor.client.*
import io.ktor.client.engine.js.*

@Suppress("FunctionName")
fun JsClient(): HttpClient = HttpClient(Js) {}

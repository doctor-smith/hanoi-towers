package hanoi.towers.api

import hanoi.towers.data.environment.Environment
import hanoi.towers.data.environment.getEnv
import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.request.*

suspend fun Environment.i18n(locale: String): String =
    with(HttpClient(Js)) {
        get<String>("$hanoiBackendURL/i18n/$locale"){
            port = hanoiBackendPort
        }
    }

suspend fun Environment.i18n(): Any =
    with(HttpClient(Js)) {
        get<Any>("$hanoiBackendURL/i18n/"){
            port = hanoiBackendPort
        }
    }




package hanoi.towers.api

import hanoi.towers.data.environment.Environment
import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.request.*

suspend fun Environment.i18n(locale: String): String =
    with(HttpClient(Js)) {
        get<String>("$hanoiFrontendURL/i18n/$locale"){
            port = hanoiFrontendPort
        }
    }

suspend fun Environment.i18n(): Any =
    with(HttpClient(Js)) {
        get<Any>("$hanoiFrontendURL/i18n/"){
            port = hanoiFrontendPort
        }
    }




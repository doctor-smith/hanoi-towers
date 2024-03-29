package hanoi.towers.api

import hanoi.towers.data.environment.getEnv
import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.request.*

suspend fun i18n(locale: String): String =
    with(HttpClient(Js)) {
        get<String>("${getEnv().hanoiBackendURL}/i18n/$locale"){
            port = getEnv().hanoiBackendPort
        }
    }

suspend fun i18n(): Any =
    with(HttpClient(Js)) {
        get<Any>("${getEnv().hanoiBackendURL}/i18n/"){
            port = getEnv().hanoiBackendPort
        }
    }




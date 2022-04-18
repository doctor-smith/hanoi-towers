package hanoi.towers.api

import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.request.*

suspend fun i18n(locale: String): String =
    with(HttpClient(Js)) {
        get<String>("/i18n/$locale"){
            port = 8080
        }
    }

suspend fun i18n(): Any =
    with(HttpClient(Js)) {
        val x = get<Any>("/i18n/"){
            port = 8080
        }
        console.log(x)
        x
    }

package hanoi.towers.api

import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.features.*
import io.ktor.client.features.BodyProgress.Feature.install
import io.ktor.client.features.cookies.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.browser.document
import lib.parser.*
import org.w3c.dom.url.URL

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

fun readCookie(): Cookie? {
    val parser: Parser<Cookie> = StartsWith("hanoi.cookie_consent=true") map { Cookie("hanoi.cookie_consent", "true") }

    return with(document.cookie) {
        val cookie = split(";")
            .mapNotNull { parser.run(it.trim()).result }
            .firstOrNull()
        console.log(cookie)
        cookie
    }
}

fun writeCookie() {
    document.cookie = with(Cookie("hanoi.cookie_consent", "true")){
        "$name=$value"
    }
}

fun readLang(): String? {
    val parser: Parser<String> = seqA(StartsWith("hanoi.lang"), Between('=',';')) map { it[1].trim() }//{ Cookie("hanoi.cookie_consent", "true") }

    return with(document.cookie) {
        val cookie = split(";")
            .mapNotNull { parser.run(it.trim()+";").result }
            .firstOrNull()
        console.log("lang from cookies: $cookie")
        cookie
    }
}

fun writeLang(locale: String) {
    document.cookie="hanoi.lang=$locale"
}




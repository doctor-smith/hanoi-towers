package hanoi.towers.api

import io.ktor.http.*
import kotlinx.browser.document
import lib.parser.*

fun readCookie(): Cookie? {
    val parser: Parser<Cookie> = StartsWith("hanoi.cookie_consent=true") map {
        Cookie("hanoi.cookie_consent", "true")
    }
    return document.cookie
        .split(";")
        .mapNotNull { parser.run(it.trim()).result }
        .firstOrNull()
    }


fun writeCookie() {
    document.cookie = with(Cookie("hanoi.cookie_consent", "true")){
        "$name=$value"
    }
}

fun readLang(): String? {
    val parser: Parser<String> = seqA(
        StartsWith("hanoi.lang"),
        Between('=',';')
    ) map {
        it[1].trim()
    }
    return document.cookie
        .split(";")
        .mapNotNull { parser.run(it.trim()+";").result }
        .firstOrNull()
}

fun writeLang(locale: String) {
    document.cookie="hanoi.lang=$locale; SameSite=None; Secure"
}

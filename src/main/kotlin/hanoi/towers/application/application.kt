package hanoi.towers.application

import androidx.compose.runtime.*
import hanoi.towers.api.*
import hanoi.towers.component.Body
import hanoi.towers.data.AppData
import hanoi.towers.data.Hanoi
import hanoi.towers.data.Moves
import kotlinx.coroutines.*
import lib.compose.Markup
import lib.language.Block
import lib.language.Lang
import lib.language.LanguageP
import lib.lens.Storage
import org.jetbrains.compose.web.css.justifySelf
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.ElementScope
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable
import org.w3c.dom.HTMLElement


@Markup
@Suppress("FunctionName")
fun Application() = renderComposable(rootElementId = "root") {
    var numberOfSlices by remember{ mutableStateOf(0) }
    var moves by remember{ mutableStateOf( Moves() ) }
    var hanoi by remember { mutableStateOf(Hanoi()) }
    var indexOfCurrentMove by remember { mutableStateOf(0) }
    var numberOfMoves by remember { mutableStateOf(0) }
    var isComputingMoves by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(false) }
    var movesPerSecond by remember { mutableStateOf(4) }
    var error by remember { mutableStateOf<String?>(null) }
    var locale by remember { mutableStateOf(readLang()?:"de") }
    var locales by remember { mutableStateOf(listOf<String>()) }
    var language by remember { mutableStateOf<Lang>( Block("de", listOf()) ) }
    var isCookieDisclaimerConfirmed by remember { mutableStateOf(
        with(readCookie()) {
            this != null
        }
    ) }
    var modals by remember { mutableStateOf<Map<Int,@Composable ElementScope<HTMLElement>.() -> Unit>>( mapOf()) }

    val langLoaded: ()->Boolean = {
        (language as Lang.Block).value.isNotEmpty() &&
        locales.isNotEmpty()
    }

    if(!langLoaded()) {
        CoroutineScope(Job()).launch {
            with(LanguageP().run(i18n(locale)).result) {
                if (this != null) {
                    language = this
                }
            }
            with(LanguageP().run(i18n("locales")).result) {
                if (this != null) {
                    locales = (this as Lang.Block).value.map { it.key }
                }
            }
        }
    }

    val storage = Storage<AppData> (
        {
            AppData(
                numberOfSlices,
                moves,
                hanoi,
                indexOfCurrentMove,
                numberOfMoves,
                isComputingMoves,
                isPlaying,
                movesPerSecond,
                isCookieDisclaimerConfirmed,
                locale,
                locales,
                language,
                modals,
                error
            )
        },
        {data ->
            numberOfSlices = data.numberOfSlices
            moves = data.moves
            hanoi = data.hanoi
            indexOfCurrentMove = data.indexOfCurrentMove
            numberOfMoves = data.numberOfMoves
            isComputingMoves = data.isComputingMoves
            isPlaying = data.isPlaying
            if(isCookieDisclaimerConfirmed != data.isCookieDisclaimerConfirmed) {
                if(data.isCookieDisclaimerConfirmed) {
                    CoroutineScope(Job()).launch {
                        writeCookie()
                    }
                }
            }
            isCookieDisclaimerConfirmed = data.isCookieDisclaimerConfirmed
            movesPerSecond = data.movesPerSecond
            modals = data.modals
            error = data.error

            if(data.locale != locale) {
                CoroutineScope(Job()).launch {
                    try {
                        with(LanguageP().run(i18n(data.locale)).result) {
                            if(this != null) {
                                locale = data.locale
                                writeLang(data.locale)
                                language = this
                            }
                        }
                    } catch (exception: Exception) {
                        console.log(exception)
                    }
                }
            }
        }
    )
    if(langLoaded()) {
        Body(storage)
    } else {
        // TODO("CSS load-spinner")
        //  Loading()
        Div({style { justifySelf("center") }}) { Text("Loading I18N") }
    }
}
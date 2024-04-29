package hanoi.towers.data

import androidx.compose.runtime.*
import hanoi.towers.api.*
import hanoi.towers.data.environment.getEnv
import hanoi.towers.data.hanoi.Hanoi
import hanoi.towers.data.hanoi.Moves
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import lib.compose.Markup
import lib.compose.modal.Modals
import lib.compose.storage.initialize
import lib.language.Lang
import lib.language.LanguageP
import lib.optics.storage.Storage
import lib.optics.transform.times
import hanoi.towers.data.language as languageLens
import hanoi.towers.data.locale as localeLens
import hanoi.towers.data.locales as localesLens
@Markup
@Composable
fun Storage(): Storage<AppData> {
    val environment = getEnv()

    // Data of Hanoi Solver
    var numberOfSlices by remember { mutableStateOf(0) }
    var moves by remember { mutableStateOf(Moves()) }
    var hanoi by remember { mutableStateOf(Hanoi()) }
    var indexOfCurrentMove by remember { mutableStateOf(0) }
    var numberOfMoves by remember { mutableStateOf(0) }
    var isComputingMoves by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(false) }
    var movesPerSecond by remember { mutableStateOf(4) }

    // Data of Hanoi Cheat
    var numberOfSlicesCheat by remember { mutableStateOf(0) }
    var hanoiCheat by remember { mutableStateOf(Hanoi()) }
    var numberOfMovesCheat by remember { mutableStateOf(0) }

    // Data of Hanoi Game
    var numberOfSlicesGame by remember { mutableStateOf(0) }
    var hanoiGame by remember { mutableStateOf(Hanoi()) }
    var numberOfMovesGame by remember { mutableStateOf(0) }

    // I18N
    var locale by remember { mutableStateOf(readLang() ?: "de") }
    var locales by remember { mutableStateOf(listOf<String>()) }
    var language by remember { mutableStateOf<Lang>(Lang.Block("de", listOf())) }

    // Cookie Disclaimer
    var isCookieDisclaimerConfirmed by remember {
        mutableStateOf(
            with(readCookie()) {
                this != null
            }
        )
    }

    // Errors and Modals
    var error by remember { mutableStateOf<String?>(null) }
    var modals by remember { mutableStateOf<Modals<Int>>(mapOf()) }

    val storage = Storage<AppData>(
        read = {
            AppData(
                numberOfSlices,
                numberOfSlicesCheat,
                numberOfSlicesGame,
                moves,
                hanoi,
                hanoiGame,
                hanoiCheat,
                indexOfCurrentMove,
                numberOfMoves,
                numberOfMovesCheat,
                numberOfMovesGame,
                isComputingMoves,
                isPlaying,
                movesPerSecond,
                isCookieDisclaimerConfirmed,
                locale,
                locales,
                language,
                modals,
                error,
                environment
            )
        },
        write = { data ->
            numberOfSlices = data.numberOfSlices
            numberOfSlicesCheat = data.numberOfSlicesCheat
            numberOfSlicesGame = data.numberOfSlicesGame
            moves = data.moves
            hanoi = data.hanoi
            hanoiGame = data.hanoiGame
            hanoiCheat = data.hanoiCheat
            indexOfCurrentMove = data.indexOfCurrentMove
            numberOfMoves = data.numberOfMoves
            numberOfMovesCheat = data.numberOfMovesCheat
            numberOfMovesGame = data.numberOfMovesGame
            isComputingMoves = data.isComputingMoves
            isPlaying = data.isPlaying
            if (isCookieDisclaimerConfirmed != data.isCookieDisclaimerConfirmed) {
                if (data.isCookieDisclaimerConfirmed) {
                    CoroutineScope(Job()).launch {
                        writeCookie()
                    }
                }
            }
            isCookieDisclaimerConfirmed = data.isCookieDisclaimerConfirmed
            movesPerSecond = data.movesPerSecond
            modals = data.modals
            error = data.error

            if (data.locale != locale) {
                CoroutineScope(Job()).launch {
                    try {
                        with(LanguageP().run(environment.i18n(data.locale)).result) {
                            if (this != null) {
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

    initialize {

        val languageStorage = (storage * languageLens)
        val localesStorage = (storage * localesLens)
        val localeStorage = (storage * localeLens)

        val langLoaded: () -> Boolean = {
            (languageStorage.read() as Lang.Block).value.isNotEmpty() &&
                localesStorage.read().isNotEmpty()
        }

        if (!langLoaded()) {
            LaunchedEffect(Unit) {
                with(LanguageP().run(environment.i18n(localeStorage.read())).result) {
                    if (this != null) {
                        language = this
                    }
                }
                with(LanguageP().run(environment.i18n("locales")).result) {
                    if (this != null) {
                        locales = (this as Lang.Block).value.map { it.key }
                    }
                }
            }
        }
    }

    return storage
}

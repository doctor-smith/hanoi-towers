package hanoi.towers.data

import androidx.compose.runtime.Composable
import hanoi.towers.data.hanoi.Hanoi
import hanoi.towers.data.hanoi.Moves
import lib.language.Lang
import lib.optics.lens.Lens
import lib.optics.storage.Storage
import lib.optics.transform.times
import org.jetbrains.compose.web.dom.ElementScope
import org.w3c.dom.HTMLElement


data class AppData(
    val numberOfSlices: Int,
    val moves: Moves,
    val hanoi: Hanoi,
    val indexOfCurrentMove: Int,
    val numberOfMoves: Int,
    val isComputingMoves: Boolean,
    val isPlaying: Boolean,
    val movesPerSecond: Int,
    val isCookieDisclaimerConfirmed: Boolean,
    val locale: String,
    val locales: List<String>,
    val language: Lang,
    val modals: Map<Int, @Composable ElementScope<HTMLElement>.() -> Unit>,
    val error: String?
)

val numberOfSlicesLens = Lens<AppData, Int>(
    {data -> data.numberOfSlices},
    {s: Int -> {data -> data.copy(numberOfSlices = s)}}
)

val movesLens = Lens<AppData, Moves>(
    {data -> data.moves},
    {s: Moves -> { data -> data.copy(moves = s)}}
)

val hanoiLens = Lens<AppData, Hanoi>(
    {data -> data.hanoi},
    {s: Hanoi -> { data -> data.copy(hanoi = s)}}
)

val indexOfCurrentMoveLens = Lens<AppData,Int>(
    {data -> data.indexOfCurrentMove},
    {s: Int -> {data -> data.copy(indexOfCurrentMove = s)}}
)

val numberOfMovesLens = Lens<AppData,Int>(
    {data -> data.numberOfMoves},
    {s: Int -> {data -> data.copy(numberOfMoves = s)}}
)

val movesPerSecondLens = Lens<AppData,Int>(
    {data -> data.movesPerSecond},
    {s: Int -> {data -> data.copy(movesPerSecond = s)}}
)

val isComputingMovesLens = Lens<AppData,Boolean>(
    {data -> data.isComputingMoves},
    {s: Boolean -> {data -> data.copy(isComputingMoves = s)}}
)

val isPlayingLens = Lens<AppData,Boolean>(
    {data -> data.isPlaying},
    {s: Boolean -> {data -> data.copy(isPlaying = s)}}
)

val isCookieDisclaimerConfirmedLens = Lens<AppData,Boolean>(
    {data -> data.isCookieDisclaimerConfirmed},
    {s: Boolean -> {data -> data.copy(isCookieDisclaimerConfirmed = s)}}
)

val localeLens = Lens<AppData,String>(
    {data -> data.locale},
    {s: String -> {data -> data.copy(locale = s)}}
)

val localesLens = Lens<AppData, List<String>>(
    {data -> data.locales},
    {s: List<String> ->  {data -> data.copy(locales = s)}}//TODO("Locales is to be Readonly")} //-> {data -> data.copy(locale = s)}}
)

val languageLens = Lens<AppData,Lang>(
    {data -> data.language},
    {s: Lang -> {data -> data.copy(language = s)}}
    //TODO("Language is to be Readonly")} //{data -> data.copy(language = s)}}
)


fun Storage<AppData>.langLoaded (): Boolean  {

    val languageStorage = (this * languageLens)
    val localesStorage = (this * localesLens)

    return (languageStorage.read() as Lang.Block).value.isNotEmpty() &&
            localesStorage.read().isNotEmpty()
}

val modalsLens = Lens<AppData, Map<Int, @Composable ElementScope<HTMLElement>.() -> Unit>> (
    {data -> data.modals},
    {s -> {data -> data.copy(modals = s)}}
)

val errorLens = Lens<AppData,String?>(
    {data -> data.error},
    {s: String? -> {data -> data.copy(error = s)}}
)

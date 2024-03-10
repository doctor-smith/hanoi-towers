package hanoi.towers.data

import androidx.compose.runtime.Composable
import hanoi.towers.data.hanoi.Hanoi
import hanoi.towers.data.hanoi.Moves
import hanoi.towers.data.hanoi.Tower
import hanoi.towers.data.i18n.I18N
import hanoi.towers.data.navigation.NavBar
import hanoi.towers.data.pages.game.HanoiGamePage
import hanoi.towers.data.pages.game.component.HanoiGame
import hanoi.towers.data.pages.main.Main
import lib.language.Block
import lib.language.Lang
import lib.language.component
import lib.optics.lens.Lens
import lib.optics.storage.Storage
import lib.optics.transform.times
import org.jetbrains.compose.web.dom.ElementScope
import org.w3c.dom.HTMLElement


data class AppData_Old(
    val numberOfSlices: Int,
    val numberOfSlicesCheat: Int,
    val numberOfSlicesGame: Int,
    val moves: Moves,
    val hanoi: Hanoi,
    val hanoiGame: Hanoi,
    val hanoiCheat: Hanoi,
    val indexOfCurrentMove: Int,
    val numberOfMoves: Int,
    val numberOfMovesCheat: Int,
    val numberOfMovesGame: Int,
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


val numberOfSlicesLens = Lens<AppData_Old, Int>(
    {data -> data.numberOfSlices},
    {s: Int -> {data -> data.copy(numberOfSlices = s)}}
)

val numberOfSlicesCheatLens = Lens<AppData_Old, Int>(
    {data -> data.numberOfSlicesCheat},
    {s: Int -> {data -> data.copy(numberOfSlicesCheat = s)}}
)

val movesLens = Lens<AppData_Old, Moves>(
    {data -> data.moves},
    {s: Moves -> { data -> data.copy(moves = s)}}
)

val hanoiLens = Lens<AppData_Old, Hanoi>(
    {data -> data.hanoi},
    {s: Hanoi -> { data -> data.copy(hanoi = s)}}
)

val main = Lens<AppData_Old, Main>(
    {whole -> Main((whole.language as Block).component("hanoi.mainPage"))},
    {_ -> { data -> data}} // read only
)

val navBar = Lens<AppData_Old, NavBar>(
    {whole -> NavBar(
        I18N(
            whole.locale,
            whole.locales,
            whole.language
        )
    ) },
    {part -> {whole -> whole.copy(locale = part.i18n.locale)}} // Rest is read only
)

val hanoiGamePage = Lens<AppData_Old, HanoiGamePage>(
    {whole -> HanoiGamePage(
        whole.language as Lang.Block,
        HanoiGame(
            whole.hanoiGame,
            whole.language,
            10
        ) ,
        whole.numberOfSlicesGame,
        whole.numberOfMovesGame,
        whole.moves,
        whole.isComputingMoves,
        whole.indexOfCurrentMove,
        whole.error,
    ) },
    {part: HanoiGamePage -> { whole -> whole.copy(
        numberOfSlicesGame = part.numberOfSlices,
        numberOfMovesGame = part.numberOfMoves,
        hanoiGame = part.game.hanoi,
        error = part.error
    )}} // other two values are read only
)

val hanoiCheatLens = Lens<AppData_Old, Hanoi>(
    {data -> data.hanoiCheat},
    {s: Hanoi -> { data -> data.copy(hanoiCheat = s)}}
)

val towerLens: (tower: Tower)->Lens<Hanoi, List<Int>> = {when(it){
    Tower.One -> Lens(
        {data -> data.one},
        {s: List<Int> -> {data -> data.copy(one = s)}}
    )
    Tower.Two -> Lens(
        {data -> data.two},
        {s: List<Int> -> {data -> data.copy(two = s)}}
    )
    Tower.Three -> Lens(
        {data -> data.three},
        {s: List<Int> -> {data -> data.copy(three = s)}}
    )
}}

val indexOfCurrentMoveLens = Lens<AppData_Old,Int>(
    {data -> data.indexOfCurrentMove},
    {s: Int -> {data -> data.copy(indexOfCurrentMove = s)}}
)

val numberOfMovesLens = Lens<AppData_Old,Int>(
    {data -> data.numberOfMoves},
    {s: Int -> {data -> data.copy(numberOfMoves = s)}}
)

val numberOfMovesCheatLens = Lens<AppData_Old,Int>(
    {data -> data.numberOfMovesCheat},
    {s: Int -> {data -> data.copy(numberOfMovesCheat = s)}}
)

val movesPerSecondLens = Lens<AppData_Old,Int>(
    {data -> data.movesPerSecond},
    {s: Int -> {data -> data.copy(movesPerSecond = s)}}
)

val isComputingMovesLens = Lens<AppData_Old,Boolean>(
    {data -> data.isComputingMoves},
    {s: Boolean -> {data -> data.copy(isComputingMoves = s)}}
)

val isPlayingLens = Lens<AppData_Old,Boolean>(
    {data -> data.isPlaying},
    {s: Boolean -> {data -> data.copy(isPlaying = s)}}
)

val isCookieDisclaimerConfirmedLens = Lens<AppData_Old,Boolean>(
    {data -> data.isCookieDisclaimerConfirmed},
    {s: Boolean -> {data -> data.copy(isCookieDisclaimerConfirmed = s)}}
)

val localeLens = Lens<AppData_Old,String>(
    {data -> data.locale},
    {s: String -> {data -> data.copy(locale = s)}}
)

val localesLens = Lens<AppData_Old, List<String>>(
    {data -> data.locales},
    {s: List<String> ->  {data -> data.copy(locales = s)}}//TODO("Locales is to be Readonly")} //-> {data -> data.copy(locale = s)}}
)

val languageLens = Lens<AppData_Old,Lang>(
    {data -> data.language},
    {s: Lang -> {data -> data.copy(language = s)}}
    //TODO("Language is to be Readonly")} //{data -> data.copy(language = s)}}
)


fun Storage<AppData_Old>.langLoaded (): Boolean  {

    val languageStorage = (this * languageLens)
    val localesStorage = (this * localesLens)

    return (languageStorage.read() as Lang.Block).value.isNotEmpty() &&
            localesStorage.read().isNotEmpty()
}

val modalsLens = Lens<AppData_Old, Map<Int, @Composable ElementScope<HTMLElement>.() -> Unit>> (
    {whole -> whole.modals},
    {part -> {whole -> whole.copy(modals = part)}}
)

val errorLens = Lens<AppData_Old,String?>(
    {whole -> whole.error},
    {part: String? -> {whole -> whole.copy(error = part)}}
)

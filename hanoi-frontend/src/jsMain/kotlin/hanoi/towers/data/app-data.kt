@file:Suppress("MatchingDeclarationName", "MagicNumber")
package hanoi.towers.data

import androidx.compose.runtime.Composable
import hanoi.towers.data.environment.*
import hanoi.towers.data.hanoi.Hanoi
import hanoi.towers.data.hanoi.Moves
import hanoi.towers.data.hanoi.Tower
import hanoi.towers.data.i18n.I18N
import hanoi.towers.data.navigation.NavBar
import hanoi.towers.data.pages.cheat.HanoiCheatPage
import hanoi.towers.data.pages.cheat.component.HanoiCheat
import hanoi.towers.data.pages.game.HanoiGamePage
import hanoi.towers.data.pages.game.component.HanoiGame
import hanoi.towers.data.pages.main.Main
import hanoi.towers.data.pages.solver.HanoiSolverPage
import hanoi.towers.data.pages.solver.component.HanoiSolver
import lib.language.Block
import lib.language.Lang
import lib.language.component
import lib.optics.lens.Lens
import lib.optics.storage.Storage
import lib.optics.transform.times
import org.jetbrains.compose.web.dom.ElementScope
import org.w3c.dom.HTMLElement


data class AppData(
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
    val error: String?,
    val environment: Environment = getEnv()
)

val main = Lens<AppData, Main>(
    {whole -> Main((whole.language as Block).component("hanoi.mainPage"))},
    {_ -> { data -> data}} // read only
)

val navBar = Lens<AppData, NavBar>(
    {whole -> NavBar(
        I18N(
            whole.locale,
            whole.locales,
            whole.language
        ),
        whole.environment
    ) },
    {part -> {whole -> whole.copy(locale = part.i18n.locale)}} // Rest is read only
)

val hanoiGamePage = Lens<AppData, HanoiGamePage>(
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


val hanoiCheatPage = Lens<AppData, HanoiCheatPage>(
    {whole -> HanoiCheatPage(
        whole.language as Lang.Block,
        HanoiCheat(
            whole.hanoiCheat,
            whole.language,
            10
        ) ,
        whole.numberOfSlicesCheat,
        whole.numberOfMovesCheat,
        whole.moves,
        whole.isComputingMoves,
        whole.indexOfCurrentMove,
        whole.error,
    ) },
    {part: HanoiCheatPage -> { whole -> whole.copy(
        numberOfSlicesCheat = part.numberOfSlices,
        numberOfMovesCheat = part.numberOfMoves,
        hanoiCheat = part.cheat.hanoi,
        error = part.error
    )}} // other two values are read only
)

val hanoiSolverPage = Lens<AppData, HanoiSolverPage>(
    {whole -> HanoiSolverPage(
        whole.language as Lang.Block,
        HanoiSolver(
            whole.hanoi,
            whole.language
        ) ,
        whole.numberOfSlices,
        whole.moves,
        whole.indexOfCurrentMove,
        whole.numberOfMoves,
        whole.isComputingMoves,
        whole.isPlaying,
        whole.movesPerSecond,
        whole.error,
    ) },
    {part: HanoiSolverPage -> { whole -> whole.copy(
        hanoi = part.solver.hanoi,
        numberOfSlices = part.numberOfSlices,
        moves = part.moves,
        indexOfCurrentMove = part.indexOfCurrentMove,
        numberOfMoves = part.numberOfMoves,
        isComputingMoves = part.isComputingMoves,
        isPlaying = part.isPlaying,
        error = part.error
    )}} // other values are read only
)

val tower: (tower: Tower)->Lens<Hanoi, List<Int>> = {when(it){
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


val isCookieDisclaimerConfirmed = Lens<AppData,Boolean>(
    {data -> data.isCookieDisclaimerConfirmed},
    {s: Boolean -> {data -> data.copy(isCookieDisclaimerConfirmed = s)}}
)

val locale = Lens<AppData,String>(
    {data -> data.locale},
    {s: String -> {data -> data.copy(locale = s)}}
)

val locales = Lens<AppData, List<String>>(
    {data -> data.locales},
    {s: List<String> ->  {data -> data.copy(locales = s)}}
    //TODO("Locales is to be Readonly")} //-> {data -> data.copy(locale = s)}}
)

val language = Lens<AppData,Lang>(
    {data -> data.language},
    {s: Lang -> {data -> data.copy(language = s)}}
    //TODO("Language is to be Readonly")} //{data -> data.copy(language = s)}}
)

val env = Lens<AppData,Environment>(
    {data -> data.environment},
    {{it}}
    //TODO("Language is to be Readonly")} //{data -> data.copy(language = s)}}
)
fun Storage<AppData>.langLoaded (): Boolean  {

    val languageStorage = (this * language)
    val localesStorage = (this * locales)

    return (languageStorage.read() as Lang.Block).value.isNotEmpty() &&
            localesStorage.read().isNotEmpty()
}

val modals = Lens<AppData, Map<Int, @Composable ElementScope<HTMLElement>.() -> Unit>> (
    {whole -> whole.modals},
    {part -> {whole -> whole.copy(modals = part)}}
)

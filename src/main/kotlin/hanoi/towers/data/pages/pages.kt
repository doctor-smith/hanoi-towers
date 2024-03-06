package hanoi.towers.data.pages

import hanoi.towers.data.hanoi.Hanoi
import hanoi.towers.data.hanoi.Moves
import lib.language.Lang


data class Pages(
    val hanoiSolver: HanoiSolver,
    val hanoiGame: HanoiGame,
    val hanoiCheat: HanoiCheat,
    val texts: Lang,
)

data class Main(
    val texts: Lang,
)

data class HanoiSolver(
    val numberOfSlices: Int,
    val moves: Moves,
    val hanoi: Hanoi,
    val indexOfCurrentMove: Int,
    val numberOfMoves: Int,
    val isComputingMoves: Boolean,
    val isPlaying: Boolean,
    val movesPerSecond: Int,
    val texts: Lang,
)

data class HanoiGame(
    val numberOfSlices: Int,
    val hanoi: Hanoi,
    val texts: Lang,
)

data class HanoiCheat(
    val numberOfSlices: Int,
    val hanoi: Hanoi,
    val texts: Lang,
)
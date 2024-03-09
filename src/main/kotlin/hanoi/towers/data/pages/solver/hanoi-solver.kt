package hanoi.towers.data.pages.solver

import hanoi.towers.data.hanoi.Hanoi
import hanoi.towers.data.hanoi.Moves
import lib.language.Lang


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
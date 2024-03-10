package hanoi.towers.data.pages.solver.component

import hanoi.towers.data.hanoi.Hanoi
import lib.language.Lang
import lib.optics.lens.Lens


data class HanoiSolver(
    val hanoi: Hanoi,
    val texts: Lang,
    /*
    val moves: Moves,
    val indexOfCurrentMove: Int,
    val numberOfMoves: Int,
    val isComputingMoves: Boolean,
    val isPlaying: Boolean,
    val movesPerSecond: Int,
    */
)


val hanoi = Lens<HanoiSolver, Hanoi>(
    {whole -> whole.hanoi},
    {part: Hanoi -> {whole -> whole.copy(hanoi = part)}}
)

val texts = Lens<HanoiSolver, Lang>(
    {whole -> whole.texts},
    {part: Lang -> {whole -> whole.copy(texts = part)}}
)
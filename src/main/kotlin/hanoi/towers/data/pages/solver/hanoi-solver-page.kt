package hanoi.towers.data.pages.solver

import hanoi.towers.data.hanoi.Moves
import hanoi.towers.data.pages.solver.component.HanoiSolver
import lib.language.Lang
import lib.optics.lens.Lens

data class HanoiSolverPage(
    val texts: Lang.Block,
    val solver: HanoiSolver,
    val numberOfSlices: Int,
    val moves: Moves,
    val indexOfCurrentMove: Int,
    val numberOfMoves: Int,
    val isComputingMoves: Boolean,
    val isPlaying: Boolean,
    val movesPerSecond: Int,
    val error: String?
)


val texts: Lens<HanoiSolverPage, Lang.Block> = Lens(
    {whole -> whole.texts},
    {_ -> {whole -> whole}}
)

val solver: Lens<HanoiSolverPage, HanoiSolver> = Lens(
    {whole -> whole.solver},
    {part -> {whole -> whole.copy(solver = part)}}
)

val numberOfSlices: Lens<HanoiSolverPage, Int> = Lens(
    {whole -> whole.numberOfSlices},
    {part -> {whole -> whole.copy(numberOfSlices = part)}}
)

val movesPerSecond: Lens<HanoiSolverPage, Int> = Lens(
    {whole -> whole.movesPerSecond},
    {part -> {whole -> whole.copy(movesPerSecond = part)}}
)

val numberOfMoves: Lens<HanoiSolverPage, Int> = Lens(
    {whole -> whole.numberOfMoves},
    {part -> {whole -> whole.copy(numberOfMoves = part)}}
)

val moves: Lens<HanoiSolverPage, Moves> = Lens(
    {whole -> whole.moves},
    {part -> {whole -> whole.copy(moves = part)}}
)

val isComputingMoves: Lens<HanoiSolverPage, Boolean> = Lens(
    {whole -> whole.isComputingMoves},
    {part -> {whole -> whole.copy(isComputingMoves = part)}}
)


val isPlaying: Lens<HanoiSolverPage, Boolean> = Lens(
    {whole -> whole.isPlaying},
    {part -> {whole -> whole.copy(isPlaying = part)}}
)

val indexOfCurrentMove: Lens<HanoiSolverPage, Int> = Lens(
    {whole -> whole.indexOfCurrentMove},
    {part -> {whole -> whole.copy(indexOfCurrentMove = part)}}
)

val error: Lens<HanoiSolverPage, String?> = Lens(
    {whole -> whole.error},
    {part -> {whole -> whole.copy(error = part)}}
)


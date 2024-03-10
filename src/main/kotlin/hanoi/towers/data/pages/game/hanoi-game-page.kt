package hanoi.towers.data.pages.game

import hanoi.towers.data.hanoi.Moves
import hanoi.towers.data.pages.game.component.HanoiGame
import lib.language.Lang
import lib.optics.lens.Lens

data class HanoiGamePage(
    val texts: Lang.Block,
    val game: HanoiGame,
    val numberOfSlices: Int,
    val numberOfMoves: Int,
    val moves: Moves,
    val isComputingMoves: Boolean,
    val indexOfCurrentMove: Int,
    val error: String?
)

val texts: Lens<HanoiGamePage, Lang.Block> = Lens(
    {whole -> whole.texts},
    {_ -> {whole -> whole}}
)

val game: Lens<HanoiGamePage, HanoiGame> = Lens(
    {whole -> whole.game},
    {part -> {whole -> whole.copy(game = part)}}
)

val numberOfSlices: Lens<HanoiGamePage, Int> = Lens(
    {whole -> whole.numberOfSlices},
    {part -> {whole -> whole.copy(numberOfSlices = part)}}
)

val numberOfMoves: Lens<HanoiGamePage, Int> = Lens(
    {whole -> whole.numberOfMoves},
    {part -> {whole -> whole.copy(numberOfMoves = part)}}
)

val moves: Lens<HanoiGamePage, Moves> = Lens(
    {whole -> whole.moves},
    {part -> {whole -> whole.copy(moves = part)}}
)

val isComputingMoves: Lens<HanoiGamePage, Boolean> = Lens(
    {whole -> whole.isComputingMoves},
    {part -> {whole -> whole.copy(isComputingMoves = part)}}
)

val indexOfCurrentMove: Lens<HanoiGamePage, Int> = Lens(
    {whole -> whole.indexOfCurrentMove},
    {part -> {whole -> whole.copy(indexOfCurrentMove = part)}}
)

val error: Lens<HanoiGamePage, String?> = Lens(
    {whole -> whole.error},
    {part -> {whole -> whole.copy(error = part)}}
)
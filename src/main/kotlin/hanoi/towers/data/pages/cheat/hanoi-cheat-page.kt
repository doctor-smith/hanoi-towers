package hanoi.towers.data.pages.cheat

import hanoi.towers.data.hanoi.Moves
import hanoi.towers.data.pages.cheat.component.HanoiCheat
import lib.language.Lang
import lib.optics.lens.Lens

data class HanoiCheatPage(
    val texts: Lang.Block,
    val cheat: HanoiCheat,
    val numberOfSlices: Int,
    val numberOfMoves: Int,
    val moves: Moves,
    val isComputingMoves: Boolean,
    val indexOfCurrentMove: Int,
    val error: String?
)

val texts: Lens<HanoiCheatPage, Lang.Block> = Lens(
    {whole -> whole.texts},
    {_ -> {whole -> whole}}
)

val cheat: Lens<HanoiCheatPage, HanoiCheat> = Lens(
    {whole -> whole.cheat},
    {part -> {whole -> whole.copy(cheat = part)}}
)

val numberOfSlices: Lens<HanoiCheatPage, Int> = Lens(
    {whole -> whole.numberOfSlices},
    {part -> {whole -> whole.copy(numberOfSlices = part)}}
)

val numberOfMoves: Lens<HanoiCheatPage, Int> = Lens(
    {whole -> whole.numberOfMoves},
    {part -> {whole -> whole.copy(numberOfMoves = part)}}
)

val moves: Lens<HanoiCheatPage, Moves> = Lens(
    {whole -> whole.moves},
    {part -> {whole -> whole.copy(moves = part)}}
)

val isComputingMoves: Lens<HanoiCheatPage, Boolean> = Lens(
    {whole -> whole.isComputingMoves},
    {part -> {whole -> whole.copy(isComputingMoves = part)}}
)

val indexOfCurrentMove: Lens<HanoiCheatPage, Int> = Lens(
    {whole -> whole.indexOfCurrentMove},
    {part -> {whole -> whole.copy(indexOfCurrentMove = part)}}
)

val error: Lens<HanoiCheatPage, String?> = Lens(
    {whole -> whole.error},
    {part -> {whole -> whole.copy(error = part)}}
)
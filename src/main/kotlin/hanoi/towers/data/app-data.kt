package hanoi.towers.data

import lib.language.Lang
import lib.language.Language
import lib.lens.Lens


data class AppData(
    val numberOfSlices: Int,
    val moves: Moves,
    val hanoi: Hanoi,
    val indexOfCurrentMove: Int,
    val numberOfMoves: Int,
    val isComputingMoves: Boolean,
    val isPlaying: Boolean,
    val movesPerSecond: Int,
    val locale: String,
    val locales: List<String>,
    val language: Lang,
    val error: String?
)

val numberOfSlicesLens = Lens<AppData, Int>(
    {data -> data.numberOfSlices},
    {s: Int -> {data -> data.copy(numberOfSlices = s)}}
)

val movesLens = Lens<AppData, Moves>(
    {data -> data.moves},
    {s: Moves -> {data -> data.copy(moves = s)}}
)

val hanoiLens = Lens<AppData, Hanoi>(
    {data -> data.hanoi},
    {s: Hanoi -> {data -> data.copy(hanoi = s)}}
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

val localeLens = Lens<AppData,String>(
    {data -> data.locale},
    {s: String -> {data -> data.copy(locale = s)}}
)

val localesLens = Lens<AppData, List<String>>(
    {data -> data.locales},
    {s: List<String> -> TODO("Locales is to be Readonly")} //-> {data -> data.copy(locale = s)}}
)

val languageLens = Lens<AppData,Lang>(
    {data -> data.language},
    {s: Lang -> TODO("Language is to be Readonly")} //{data -> data.copy(language = s)}}
)

val errorLens = Lens<AppData,String?>(
    {data -> data.error},
    {s: String? -> {data -> data.copy(error = s)}}
)

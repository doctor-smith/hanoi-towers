package application

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import component.Body
import data.AppData
import data.Hanoi
import data.Moves
import lib.compose.Markup
import lib.lens.Storage
import org.jetbrains.compose.web.renderComposable


@Markup
@Suppress("FunctionName")
fun Application() = renderComposable(rootElementId = "root") {
    var numberOfSlices by remember{ mutableStateOf(0) }
    var moves by remember{ mutableStateOf( Moves() ) }
    var hanoi by remember { mutableStateOf(Hanoi()) }
    var indexOfCurrentMove by remember { mutableStateOf(0) }
    var numberOfMoves by remember { mutableStateOf(0) }
    var isComputingMoves by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(false) }
    var movesPerSecond by remember { mutableStateOf(4) }
    var error by remember { mutableStateOf<String?>(null) }

    val storage = Storage<AppData> (
        {
            AppData(
                numberOfSlices,
                moves,
                hanoi,
                indexOfCurrentMove,
                numberOfMoves,
                isComputingMoves,
                isPlaying,
                movesPerSecond,
                error
            )
        },
        {data ->
            numberOfSlices = data.numberOfSlices
            moves = data.moves
            hanoi = data.hanoi
            indexOfCurrentMove = data.indexOfCurrentMove
            numberOfMoves = data.numberOfMoves
            isComputingMoves = data.isComputingMoves
            isPlaying = data.isPlaying
            movesPerSecond = data.movesPerSecond
            error = data.error
        }
    )
    Body(storage)
}
package component

import androidx.compose.runtime.Composable
import data.*
import lib.compose.Markup
import lib.lens.Storage
import org.jetbrains.compose.web.css.paddingLeft
import org.jetbrains.compose.web.css.pc
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Text

@Markup
@Composable
@Suppress("FunctionName")
fun HanoiVisualization(
    moves: Storage<Moves>,
    hanoi: Storage<Hanoi>,
    numberOfMoves: Storage<Int>,
    indexOfCurrentMove: Storage<Int>,
    movesPerSecond: Storage<Int>,
    isPlaying: Storage<Boolean>,
    maxNumberOfSlices: Int = 10
) = Div({
    style {
        width(75.pc)
        paddingLeft(10.px)
    }
}){
    H3{ Text("Visualisierung") }

    Hanoi(
        moves.read(),
        hanoi.read().one,
        hanoi.read().two,
        hanoi.read().three,
        capacity = maxNumberOfSlices
    )
    ActionBar(
        indexOfCurrentMove,
        hanoi,
        moves,
        numberOfMoves,
        isPlaying,
        movesPerSecond
    )
}
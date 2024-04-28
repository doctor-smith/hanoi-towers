package hanoi.towers.component.hanoi

import androidx.compose.runtime.Composable
import hanoi.towers.data.hanoi.Hanoi
import hanoi.towers.data.hanoi.Mode
import hanoi.towers.data.hanoi.Moves
import hanoi.towers.data.hanoi.Tower.*
import hanoi.towers.data.tower
import lib.compose.Markup
import lib.language.Lang.Block
import lib.language.get
import lib.optics.storage.Storage
import lib.optics.transform.times
import org.jetbrains.compose.web.css.*
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
    texts: Block,
    maxNumberOfSlices: Int = 10
) = Div({
    style {
        flex(1)
        width(75.percent)
        paddingLeft(10.px)
    }
}) {
    H3 { Text(texts["headline"]) }

    Hanoi(
        moves.read(),
        hanoi * tower(One),
        hanoi * tower(Two),
        hanoi * tower(Three),
        capacity = maxNumberOfSlices,
        mode = Mode.Automatic
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

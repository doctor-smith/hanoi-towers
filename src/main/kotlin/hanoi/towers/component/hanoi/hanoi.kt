package hanoi.towers.component.hanoi

import androidx.compose.runtime.Composable
import hanoi.towers.data.hanoi.Mode
import hanoi.towers.data.hanoi.Moves
import lib.compose.Markup
import lib.optics.storage.Storage
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.Color.black
import org.jetbrains.compose.web.dom.Div


@Markup
@Composable
@Suppress("FunctionName")
fun Hanoi(
    moves: Moves,
    one: Storage<List<Int>>,
    two: Storage<List<Int>>,
    three: Storage<List<Int>>,
    capacity: Int,
    mode: Mode
) {
    Div({ style {
        display(DisplayStyle("flex"))
        paddingLeft(10.px)
    }}) {
        Tower(one,capacity, mode)
        Tower(two,capacity, mode)
        Tower(three,capacity, mode)
    }

    Div({
        style {
            height(3.px)
            width(100.percent)
            color(black)
            backgroundColor(black)
            maxWidth(320.px)
        }
    }){}
}





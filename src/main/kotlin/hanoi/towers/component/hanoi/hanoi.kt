package hanoi.towers.component.hanoi

import androidx.compose.runtime.Composable
import hanoi.towers.data.hanoi.Moves
import lib.compose.Markup
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.Color.black
import org.jetbrains.compose.web.dom.Div


@Markup
@Composable
@Suppress("FunctionName")
fun Hanoi(
    moves: Moves,
    one: List<Int> = listOf(),
    two: List<Int>  = listOf(),
    three: List<Int>  = listOf(),
    capacity: Int
) {
    Div({ style {
        display(DisplayStyle("flex"))
        paddingLeft(10.px)
    }}) {
        Tower(one,capacity)
        Tower(two,capacity)
        Tower(three,capacity)
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





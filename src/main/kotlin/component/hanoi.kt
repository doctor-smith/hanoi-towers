package component

import androidx.compose.runtime.Composable
import data.Moves
import lib.compose.Markup
import org.jetbrains.compose.web.css.*
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
    }}) {
        Tower(one,capacity)
        Tower(two,capacity)
        Tower(three,capacity)
    }
}




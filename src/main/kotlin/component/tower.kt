package component

import androidx.compose.runtime.Composable
import lib.compose.Markup
import org.jetbrains.compose.web.dom.Div

@Markup
@Composable
@Suppress("FunctionName")
fun Tower(
    slizes: List<Int>,
    capacity: Int
) {
    val filled = listOf(
        *(1..(capacity-slizes.size)).map { 0 }.toTypedArray(),
        *slizes.toTypedArray()
    )
    Div({}) {
        filled.map{ Slice(it) }
    }
}
package component

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div

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
        filled.map{Slice(it)}
    }
}
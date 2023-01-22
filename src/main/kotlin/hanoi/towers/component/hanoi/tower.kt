package hanoi.towers.component.hanoi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import hanoi.towers.data.hanoi.Mode
import lib.compose.Markup
import lib.optics.storage.Storage
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.dom.Div

@Markup
@Composable
@Suppress("FunctionName")
fun Tower(
    slices: Storage<List<Int>>,
    capacity: Int,
    mode: Mode,
) {
    val filled = listOf(
        *(1..(capacity-slices.read().size)).map { 0 }.toTypedArray(),
        *slices.read().toTypedArray()
    )
    val sizeOfMinimalSlice = when(slices.read().isEmpty()) {
        true -> -1
        false -> slices.read().min()
    }
    var dragging by remember { mutableStateOf(false) }

    Div({
        onMouseMove { dragging = true }
        onMouseLeave { dragging = false }
        //onMouseEnter { dragging = true }
        //onMouseLeave { dragging = false }

        if(dragging){
            style {

                backgroundColor(Color("green"))
            }
        }

    }) {
        when(mode) {
            Mode.Play -> filled.map{ when(it){
                sizeOfMinimalSlice -> Slice(it, mode = mode)
                else -> Slice(it)
            } }
            Mode.Automatic -> filled.map{ Slice(it) }
        }
    }
}
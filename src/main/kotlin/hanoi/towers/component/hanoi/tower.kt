package hanoi.towers.component.hanoi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import hanoi.towers.data.geometry.Coordinates
import hanoi.towers.data.hanoi.*
import kotlinx.browser.document
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import lib.compose.Markup
import lib.optics.storage.Storage
import lib.optics.transform.times
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.dom.Div
import org.w3c.dom.HTMLElement

@Markup
@Composable
@Suppress("FunctionName")
fun Tower(
    id: Tower,
    slices: Storage<List<Int>>,
    capacity: Int,
    mode: Mode,
    mouse: Storage<DragEvent>,
) {
    val filled = listOf(
        *(1..(capacity-slices.read().size)).map { 0 }.toTypedArray(),
        *slices.read().toTypedArray()
    )
    val sizeOfMinimalSlice = when(slices.read().isEmpty()) {
        true -> -1
        false -> slices.read().min()
    }
    val element: HTMLElement? = document.elementsFromPoint(mouse.read().coordinates.x, mouse.read().coordinates.y).find {
        it.id === "tower_${id}"
    } as HTMLElement?

    if( element != null
        && mouse.read().target != id.toString().toInt()
        && (sizeOfMinimalSlice == -1 || sizeOfMinimalSlice > mouse.read().slice)
    ) {
        (mouse * targetLens).write(id.toString().toInt())
    }

    Div({
        id("tower_${id}")
        /*
        style {
            if(element != null){
                backgroundColor(
                    when{
                        sizeOfMinimalSlice == -1 -> Color.green
                        sizeOfMinimalSlice > mouse.read().slice -> Color.green
                        else -> Color.red
                    }
                )
            }
        }

         */

    }) {
        when(mode) {
            Mode.Play -> filled.map{ when(it){
                sizeOfMinimalSlice -> Slice(
                    it,
                    mode = mode,
                    setMouseCoordinates = (mouse * coordinatesLens).write,
                    onDrop = {mouse.read().drop(it)}
                )
                else -> Slice(it)
            } }
            Mode.Automatic -> filled.map{ Slice(it) }
        }
    }
}
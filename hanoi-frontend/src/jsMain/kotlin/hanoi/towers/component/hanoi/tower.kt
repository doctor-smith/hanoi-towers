package hanoi.towers.component.hanoi

// import hanoi.towers.component.hanoi.Slice as DndSlice
import androidx.compose.runtime.*
import hanoi.towers.data.hanoi.*
import kotlinx.browser.document
import lib.compose.Markup
import lib.compose.dnd.DragDropEnvironment
import lib.optics.storage.Storage
import lib.optics.transform.times
import org.jetbrains.compose.web.dom.Div
import org.w3c.dom.HTMLElement

@Markup
@Composable
@Suppress("FunctionName")
fun DragDropEnvironment.Tower(
    id: Tower,
    slices: Storage<List<Int>>,
    capacity: Int,
    mode: Mode,
) {
    val filled = listOf(
        *(1..(capacity - slices.read().size)).map { 0 }.toTypedArray(),
        *slices.read().toTypedArray()
    )
    val sizeOfMinimalSlice = when (slices.read().isEmpty()) {
        true -> -1
        false -> slices.read().min()
    }
    Div({
        id("tower_$id")
    }) {
        when (mode) {
            Mode.Play, Mode.Cheat -> filled.map {
                when (it) {
                    sizeOfMinimalSlice -> this@Tower.Slice(
                        it,
                        mode = mode,
                        source = id
                    )
                    else -> this@Tower.Slice(
                        it,
                        mode = mode,
                        source = id
                    )
                }
            }
            Mode.Automatic -> filled.map {
                this@Tower.Slice(
                    it,
                    mode = mode,
                    source = id
                )
            }
        }
    }
}

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
        *(1..(capacity - slices.read().size)).map { 0 }.toTypedArray(),
        *slices.read().toTypedArray()
    )
    val sizeOfMinimalSlice = when (slices.read().isEmpty()) {
        true -> -1
        false -> slices.read().min()
    }
    val element: HTMLElement? = document.elementsFromPoint(mouse.read().coordinates.x, mouse.read().coordinates.y).find {
        it.id === "tower_$id"
    } as HTMLElement?

    if (element != null &&
        mouse.read().target != id.toString().toInt() &&
        (sizeOfMinimalSlice == -1 || sizeOfMinimalSlice > mouse.read().slice)
    ) {
        (mouse * target).write(id.toString().toInt())
    }

    Div({
        id("tower_$id")
    }) {
        when (mode) {
            Mode.Play, Mode.Cheat -> filled.map {
                when (it) {
                    sizeOfMinimalSlice -> Slice(
                        it,
                        mode = mode,
                        setMouseCoordinates = (mouse * coordinates).write,
                        onDrop = { mouse.read().drop(it) }
                    )
                    else -> Slice(it)
                }
            }
            Mode.Automatic -> filled.map { Slice(it) }
        }
    }
}

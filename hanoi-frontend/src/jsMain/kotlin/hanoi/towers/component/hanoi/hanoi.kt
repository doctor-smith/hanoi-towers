package hanoi.towers.component.hanoi

import androidx.compose.runtime.*
import hanoi.towers.data.hanoi.DragEvent
import hanoi.towers.data.hanoi.Mode
import hanoi.towers.data.hanoi.Moves
import hanoi.towers.data.hanoi.Tower.*
import lib.compose.Markup
import lib.compose.dnd.Coordinates
import lib.compose.dnd.DragDropEnvironment
import lib.compose.dnd.resetCoordinatesOfDraggedElements
import lib.compose.dnd.sourceAndTarget
import lib.optics.storage.*
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
    val source: (Int)->Int? = {
        when (it) {
            in one.read() -> 1
            in two.read() -> 2
            in three.read() -> 3
            else -> null
        }
    }

    val move: (slice: Int, source: Int, target: Int)->Unit = {
            slice, source, target ->
        val s = when (source) {
            1 -> one
            2 -> two
            else -> three
        }
        val t = when(target) {
            1 -> one
            2 -> two
            else -> three
        }
        if(t.read().isEmpty() || t.read().min() > slice) {
            s.write(s.read().filter { it != slice })
            t.write(
                listOf(
                    slice, *t.read().toTypedArray()
                )
            )
        }
    }

    Div({ style {
        display(DisplayStyle("flex"))
        paddingLeft(10.px)
    }}) {
        var mouseCoordinates by remember { mutableStateOf( Coordinates(-1.0,-1.0) ) }
        var slice by remember { mutableStateOf(-1) }
        var target by remember { mutableStateOf<Int?>(null) }

        val drop: (slice: Int)->Unit = {
                slice: Int-> when(target){
            null -> Unit
            else -> move(slice, source(slice)!!, target!!)
        }
        }

        val mouseDragEventStorage = Storage(
            read = {DragEvent(mouseCoordinates, slice, target, drop = drop) },
            write = {event ->
                mouseCoordinates = event.coordinates
                target = event.target
                slice = event.slice
            }
        )

        Tower(One, one, capacity, mode, mouseDragEventStorage)
        Tower(Two, two, capacity, mode, mouseDragEventStorage)
        Tower(Three, three, capacity, mode, mouseDragEventStorage)
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


@Markup
@Composable
@Suppress("FunctionName")
fun Hanoi(
    one: Storage<List<Int>>,
    two: Storage<List<Int>>,
    three: Storage<List<Int>>,
    capacity: Int,
    mode: Mode
) {
    // Extract id / size from the name of the slice
    val convert: String.( ) -> Int = {dropWhile { it != '_' }.substring(1).toInt() }

    // compute source tower from integer
    val source: (Int) -> Int? = {
        when (it) {
            in one.read() -> 1
            in two.read() -> 2
            in three.read() -> 3
            else -> null
        }
    }

    // compute the data-representation (tower) from integer
    val tower: (Int)->Storage<List<Int>> = { when(it){
        1 -> one
        2 -> two
        3 -> three
        else -> throw Exception("No Tower_ $it")
    } }

    // move slices from one tower to the other
    val move: (slices: List<Int>, source: Int, target: Int) -> Unit = { slices, src, target ->
        val s = when (src) {
            1 -> one
            2 -> two
            else -> three
        }
        val t = when (target) {
            1 -> one
            2 -> two
            else -> three
        }
        s.write(s.read().filter { it !in slices })
        t.write(
            listOf(
                *slices.toTypedArray(),
                *t.read().toTypedArray()
            ).sortedBy { it }
        )
    }

    DragDropEnvironment(
        onDrag = { name -> when(mode) {
            Mode.Cheat -> with(name.convert()){
                val l = tower(source(this)!!).read()
                    .filter { it < this }
                    .map{"slice_$it"}
                dragged.add( l )
            }
            else -> Unit
        } },
        allowDrag = { name, src -> when(mode) {
            Mode.Play -> with(name.convert()){
                this > 0 &&
                tower(src!!.convert()).read().filter { it != 0 }.min() >= this
            }
            else -> name.convert() > 0
        } },
        allowDrop = { dragged ,target ->
            target != null && (
                with(tower(target.convert()).read()){
                    isEmpty() ||
                    dragged.map { it.convert() }.max() < filter { it > 0 }.min()
                }
            )
        },
        onDropRejected = { _, _ ->
            resetCoordinatesOfDraggedElements()
        },
        onDrop = {src, target ->
            if(src != null && target != null) {
                move(
                    dragged.read().map { it.convert() },
                    src.convert(),
                    target.convert()
                )
                resetCoordinatesOfDraggedElements()
            }
        }
    ) {
        Div({
            style {
                display(DisplayStyle.Flex)
                paddingLeft(10.px)
            }
        }) {
            sourceAndTarget("tower_1") {
                Tower(One, one, capacity, mode)
            }
            sourceAndTarget("tower_2") {
                Tower(Two, two, capacity, mode)
            }
            sourceAndTarget("tower_3") {
                Tower(Three, three, capacity, mode)
            }
        }
    }
    Div({
        style {
            height(3.px)
            width(100.percent)
            color(black)
            backgroundColor(black)
            maxWidth(320.px)
        }
    }) {}
}

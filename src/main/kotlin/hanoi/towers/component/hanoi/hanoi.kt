package hanoi.towers.component.hanoi

import androidx.compose.runtime.*
import hanoi.towers.data.geometry.Coordinates
import hanoi.towers.data.hanoi.DragEvent
import hanoi.towers.data.hanoi.Mode
import hanoi.towers.data.hanoi.Moves
import hanoi.towers.data.hanoi.Tower.One
import hanoi.towers.data.hanoi.Tower.Two
import hanoi.towers.data.hanoi.Tower.Three
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





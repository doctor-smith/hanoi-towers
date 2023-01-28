package hanoi.towers.component.hanoi

import androidx.compose.runtime.*
import hanoi.towers.data.geometry.Coordinates
import hanoi.towers.data.hanoi.Mode
import hanoi.towers.data.hanoi.Tower
import lib.compose.Markup
import lib.compose.dnd.DragDropEnvironment
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.Color.black
import org.jetbrains.compose.web.css.Color.transparent
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.ElementScope
import org.w3c.dom.HTMLElement
import lib.compose.dnd.Draggable as DndDraggable

@Markup
@Composable
@Suppress("FunctionName")
fun DragDropEnvironment.Slice(
    size: Int,
    maxWidth: Int = 100,
    mode: Mode = Mode.Automatic,
    source: Tower
) = when(mode) {
    Mode.Automatic ->
        Div({style {
            display(DisplayStyle("flex"))
            height(20.px)
            maxWidth(maxWidth.px)
        }
        }) {
            Space(size)
            Box(size)
            Space(size)
        }
    Mode.Play, Mode.Cheat -> when(size){
        0-> Div({style {
                display(DisplayStyle("flex"))
                height(20.px)
                maxWidth(maxWidth.px)
            }
        }) {
            Space(size)
            Box(size)
            Space(size)
        }

        else -> DndDraggable(
            "slice_$size",
            "tower_$source"
        ) {
            Div({
                style {
                    display(DisplayStyle("flex"))
                    height(20.px)
                    maxWidth(maxWidth.px)
                    cursor("inherit")
                }
            }) {
                Space(size)
                Box(size)
                Space(size)
            }
        }
    }
}


@Markup
@Composable
@Suppress("FunctionName")
fun Slice(
    size: Int,
    maxWidth: Int = 100,
    mode: Mode = Mode.Automatic,
    setMouseCoordinates: (Coordinates)->Unit = {},
    onDrop: ()->Unit = {}
) = when(mode) {
    Mode.Automatic ->
        Div({style {
            display(DisplayStyle("flex"))
            height(20.px)
            maxWidth(maxWidth.px)
        }
        }) {
            Space(size)
            Box(size)
            Space(size)
        }
    Mode.Play, Mode.Cheat -> Draggable(
        setMouseCoordinates,
        onDrop
    ) {
        Div({style {
            display(DisplayStyle("flex"))
            height(20.px)
            maxWidth(maxWidth.px)
        }
        }) {
            Space(size)
            Box(size)
            Space(size)
        }
    }

}


@Markup
@Composable
@Suppress("FunctionName")
private fun Space(size: Int) =  Div({
    style {
        width(((10-size)*5).pc)
    }
}) {  }

@Markup
@Composable
@Suppress("FunctionName")
private fun Box(size:Int) = Div({
    if(size > 0) {
        style {
            width((size*10).pc)
            border {
                style = LineStyle.Solid
                color = black
                width = 1.px
            }
            boxSizing("border-box")
        }
    } else {
        style {
            width(100.pc)
            border {
                color = transparent
            }
        }
    }
}) {}


@Markup
@Composable
@Suppress("FunctionName")
fun Draggable(notify: (Coordinates)->Unit, onDrop: ()->Unit, content: @Composable ElementScope<HTMLElement>.()->Unit) {
    var left by remember { mutableStateOf<Double>(0.0) }
    var top by remember { mutableStateOf<Double>(0.0) }
    var x by remember { mutableStateOf<Double>(0.0) }
    var y by remember { mutableStateOf<Double>(0.0) }
    var dX by remember { mutableStateOf<Double>(0.0) }
    var dY by remember { mutableStateOf<Double>(0.0) }

    var mousePressed by remember { mutableStateOf(false) }

    fun reset(){
        mousePressed = false
        x = 0.0
        y = 0.0
        dX = 0.0
        dY = 0.0
        notify(Coordinates(-1.0,-1.0))
    }

    Div({
        style {
            position(Position.Relative)
            left(left.px)
            top(top.px)
            cursor(
                when {
                    mousePressed -> "grabbing"
                    else -> "grab"
                }
            )
        }

        onMouseDown {
            mousePressed = true
            x = it.pageX
            y = it.pageY
        }

        onMouseMove {
            notify(Coordinates(
                it.pageX,
                it.pageY
            ))
            if (mousePressed) {
                dX = it.pageX - x
                dY = it.pageY - y
                x = it.pageX
                y = it.pageY

                left += dX
                top += dY
            }
        }
        onMouseLeave {
            if (mousePressed) {
                dX = it.pageX - x
                dY = it.pageY - y
                x = it.pageX
                y = it.pageY

                left += dX
                top += dY
            }
        }
        onMouseUp {
            reset()
            left = 0.0
            top = 0.0
            onDrop()
        }
        onMouseLeave {
            reset()
        }

    }) {
        content()
    }
}
package hanoi.towers.component.hanoi

import androidx.compose.runtime.*
import hanoi.towers.data.hanoi.Mode
import lib.compose.Markup
import org.jetbrains.compose.web.attributes.Draggable
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.Color.black
import org.jetbrains.compose.web.css.Color.transparent
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.ElementScope
import org.w3c.dom.HTMLElement

@Markup
@Composable
@Suppress("FunctionName")
fun Slice(
    size: Int,
    maxWidth: Int = 100,
    mode: Mode = Mode.Automatic
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
        Mode.Play -> Draggable {
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
fun Draggable(content: @Composable ElementScope<HTMLElement>.()->Unit) {
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
        }
        onMouseLeave {
            reset()
        }

    }) {
        content()
    }
}
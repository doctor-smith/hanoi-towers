package lib.compose.dnd

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import kotlinx.browser.document
import lib.compose.Markup
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLElement

@Markup
@Composable
@Suppress("FunctionName")
fun DragDropEnvironment(
    dragLayerIndex: Int = 100,
    dragLayerId: String = "drag-layer"
) = Div {
    var draggedId by remember { mutableStateOf(  null ) }
    var dragging by remember { mutableStateOf(false) }
    var dragged by remember { mutableStateOf<HTMLElement?>(null) }

    H1 { Text("Hello DragDropEnvironment") }

    Div({

        onMouseDown {
            dragging = true
        }
    }) {
        Text("press here")
    }

    // DragLayer
    if(dragging) {

        // document.getElementById(dragLayerId)?.appendChild(dragged!!)
        // var left by remember { mutableStateOf<Double>(0.0) }
        var dX by remember { mutableStateOf<Double>(0.0) }
        // var top by remember { mutableStateOf(0.0) }
        var dY by remember { mutableStateOf<Double>(0.0) }
        var x by remember { mutableStateOf<Double>(0.0) }
        var y by remember { mutableStateOf<Double>(0.0) }

        Div({
            id(dragLayerId)
            style {
                property("z-index", dragLayerIndex -1 )
                border {
                    color = Color.black
                    width = 1.px
                }
                //left(left.px)
                //top(top.px)
            }
        }) {

            Button({
                id("toDrag")
            }) { Text("Drag around") }
        }

        val dragLayer = document.getElementById(dragLayerId) as HTMLElement?

        Div({
            // id(dragLayerId)
            style {
                property("z-index", dragLayerIndex)
                position(Position.Absolute)
                opacity(1)
                width(100.vw)
                height(100.vh)
                //left(left.px)
                //top(top.px)
            }



            onMouseUp {
                //mousePressed = false
                dragging = false
            }

            onMouseDown {
                //mousePressed = true
                x = it.pageX
                y = it.pageY
            }

            onMouseMove {
                //if(dra) {
                    dX = it.pageX - x
                    dY = it.pageY - y
                    x = it.pageX
                    y = it.pageY

                dragLayer?.style?.left += dX
                dragLayer?.style?.top += dY
                    //left += dX
                    //top += dY

                console.log("dragging... dX = $dX, dY = $dY")
                // }
            }
            /*
            onMouseLeave {
                if(mousePressed) {
                    dX = it.pageX - x
                    dY = it.pageY - y
                    x = it.pageX
                    y = it.pageY

                    left += dX
                    top += dY
                }
            }

             */
        })
    }
}
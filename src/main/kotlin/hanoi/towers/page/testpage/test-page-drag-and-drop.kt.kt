package hanoi.towers.page.testpage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import hanoi.towers.component.layout.Container
import hanoi.towers.component.layout.Flex
import lib.compose.Markup
import lib.compose.dnd.DragDropEnvironment
import org.jetbrains.compose.web.attributes.Draggable
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

@Markup
@Composable
@Suppress("FunctionName")
fun DragDropTestPage() {

    DragDropEnvironment()


    Div {
        Text("hi test page")
        Div(
            attrs = {
                draggable(Draggable.True)
            }
        ) {
            Text("Drag me around - html d&d")
        }

        var left by remember { mutableStateOf<Double>(0.0) }
        var dX by remember { mutableStateOf<Double>(0.0) }
        var top by remember { mutableStateOf(0.0) }
        var dY by remember { mutableStateOf<Double>(0.0) }
        var x by remember { mutableStateOf<Double>(0.0) }
        var y by remember { mutableStateOf<Double>(0.0) }
        var mousePressed by remember { mutableStateOf(false) }
        Div({

            style {
                position(Position.Relative)
                left(left.px)
                top(top.px)
                height(30.px)
                width(100.px)
                border {
                    style = LineStyle.Solid
                    color = Color("black")
                }
                cursor(when{
                    mousePressed ->"grabbing"
                    else -> "grab"

                })
            }

            onMouseDown {
                mousePressed = true
                x = it.pageX
                y = it.pageY
            }

            onMouseMove {
                if(mousePressed) {
                    dX = it.pageX - x
                    dY = it.pageY - y
                    x = it.pageX
                    y = it.pageY

                    left += dX
                    top += dY
                }
            }
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
            onMouseUp {
                mousePressed = false
            }
        }) {
            Text("custom")
        }
    }


    Container {
        Flex {
            Div { Text("1") }
            Div { Text("2") }
        }
    }
}

/*

 <!DOCTYPE HTML>
<html>
<head>
<script>
function allowDrop(ev) {
  ev.preventDefault();
}

function drag(ev) {
  ev.dataTransfer.setData("text", ev.target.id);
}

function drop(ev) {
  ev.preventDefault();
  var data = ev.dataTransfer.getData("text");
  ev.target.appendChild(document.getElementById(data));
}
</script>
</head>
<body>

<div id="div1" ondrop="drop(event)" ondragover="allowDrop(event)"></div>

<img id="drag1" src="img_logo.gif" draggable="true" ondragstart="drag(event)" width="336" height="69">

</body>
</html>

 */

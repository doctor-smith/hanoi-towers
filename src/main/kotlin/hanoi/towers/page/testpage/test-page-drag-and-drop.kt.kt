package hanoi.towers.page.testpage

import androidx.compose.runtime.Composable
import lib.compose.Markup
import org.jetbrains.compose.web.attributes.Draggable
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

@Markup
@Composable
@Suppress("FunctionName")
fun DragDropTestPage() = Div{
    Text("hi test page")
    Div(
        attrs={
            draggable(Draggable.True)
        }
    ){
        Text("Drag me around")
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

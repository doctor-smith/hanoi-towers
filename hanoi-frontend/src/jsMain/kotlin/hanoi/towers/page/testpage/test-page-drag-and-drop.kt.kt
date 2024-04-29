package hanoi.towers.page.testpage

import androidx.compose.runtime.*
import hanoi.towers.component.layout.Flex
import lib.compose.Markup
import lib.compose.dnd.*
import lib.optics.storage.add
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

@Markup
@Composable
@Suppress("FunctionName")
fun DragDropTestPage() {

    var sourceData: List<String> by remember {
        mutableStateOf(
            listOf(
                "drag_0",
                "drag_1",
                "drag_2",
                "drag_3",
                "drag_4",
                "drag_5",
                "drag_6",
                "drag_7",
                "drag_8",
                "drag_9",
            )
        )
    }
    var dropped: List<String> by remember { mutableStateOf(listOf()) }

    val convert: String.() -> Int = { dropWhile { it != '_' }.substring(1).toInt() }

    DragDropEnvironment(
        onDrag = { name ->
            dragged.add(
                with(name.convert()) { sourceData.filter { it.convert() > this } }
            )
        },
        allowDrop = { dragged, target -> target == "area_2" },
        onDropRejected = { source, target ->
            resetCoordinatesOfDraggedElements()
        },
        onDrop = {
            source, target ->
            sourceData = sourceData.filter { it !in dragged.read() }
            resetCoordinatesOfDraggedElements()
            dropped = with(
                arrayListOf(
                    *dropped.toTypedArray(),
                    *dragged.read().filter { it !in dropped }.toTypedArray()
                )
            ) {
                sort()
                this
            }
        }
    ) {
        H1 { Text("Hello DragDropEnvironment") }
        Flex {
            sourceAndTarget("area_1") {
                H2 { Text("Area 1") }
                sourceData.forEach {
                    Draggable(name = it, "area_1") {
                        Button({ style { cursor("inherit") } }) { Text(it) }
                    }
                }
            }
            Div({
                style { width(500.px) } 
            }
            ) {}
            sourceAndTarget("area_2") {
                H2 { Text("Area 2") }
                Div({
                    id("dropzone")
                    style {
                        width(200.px)
                        height(300.px)
                        border {
                            style(LineStyle.Solid)
                            width(1.px)
                        }
                    }
                }) {
                    dropped.forEach {
                        Draggable(name = it) {
                            Button({ style { cursor("inherit") } }) { Text(it) }
                        }
                    }
                }
            }
        }
    }
}

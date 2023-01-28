package hanoi.towers.page.testpage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import hanoi.towers.component.layout.Flex
import lib.compose.Markup
import lib.compose.dnd.*
import lib.optics.storage.add
import lib.optics.storage.contains
import lib.optics.storage.onEach
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.svg.SVGTitleElement

@Markup
@Composable
@Suppress("FunctionName")
fun DragDropTestPage() {

    val sourceData: ArrayList<String> = arrayListOf(
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
    val dropped: ArrayList<String> = arrayListOf()

    val convert: String.( ) -> Int = {dropWhile { it != '_' }.substring(1).toInt() }

    DragDropEnvironment(
        onDrag = { name -> dragged.add(
            with(name.convert()){ sourceData.filter { it.convert() > this} }
        )},
        allowDrop = { dragged,target -> target == "area_2"},
        onDropRejected = { source, target ->
            draggables.onEach {
                when(it.name in dragged) {
                    true -> it.copy(
                        coordinates = Coordinates(
                            0.0,0.0
                        )
                    )
                    false -> it
                }
            }
        },
        onDrop = {
            source, target ->
                sourceData.removeAll(dragged.read().toSet())
                dropped.addAll(dragged.read().filter { it !in dropped })
                dropped.sort()
                draggables.onEach {
                    when(it.name in dragged) {
                        true -> it.copy(
                            coordinates = Coordinates(
                                0.0,0.0
                            )
                        )
                        false -> it
                    }
                }

        }
    ){
        H1 { Text("Hello DragDropEnvironment") }
        Flex {
            sourceAndTarget("area_1") {
                H2 { Text("Area 1") }
                sourceData.forEach {
                    Draggable(name = it) {
                        Button({style { cursor("inherit") }}) { Text(it) }
                    }
                }
            }
            Div({
                style { width(500.px) }}
            ){}
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
                            Button({style { cursor("inherit") }}) { Text(it) }
                        }
                    }
                }
            }
        }
    }
}

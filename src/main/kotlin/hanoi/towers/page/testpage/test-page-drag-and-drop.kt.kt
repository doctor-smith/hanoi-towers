package hanoi.towers.page.testpage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import hanoi.towers.component.layout.Container
import hanoi.towers.component.layout.Flex
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import lib.compose.Markup
import lib.compose.dnd.DragDropEnvironment
import lib.compose.dnd.Draggable
import lib.compose.dnd.source
import lib.compose.dnd.sourceAndTarget
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

@Markup
@Composable
@Suppress("FunctionName")
fun DragDropTestPage() {

    DragDropEnvironment{
        H1 { Text("Hello DragDropEnvironment") }
        Flex {
            sourceAndTarget("area_1") {
                H2 { Text("Area 1") }
                Draggable(name = "drag_1") {
                    Button({style { cursor("inherit") }}) { Text("DRAG 1") }
                }
                Draggable(name = "drag_2") {
                    Button({style { cursor("inherit") }}) { Text("DRAG 2") }
                }
                Draggable(name = "drag_3") {
                    Button({style { cursor("inherit") }}) { Text("DRAG 3") }
                }
                Draggable(name = "drag_4") {
                    Button({style { cursor("inherit") }}) { Text("DRAG 4") }
                }
            }
            Div({
                style { width(500.px) }}
            ){}
            sourceAndTarget("area_2") {
                H2 { Text("Area 2") }

                H3{ Text("Sources") }
                Ul {
                    sources.read().map { Li { Text(it) } }
                }

                H3{ Text("Targets") }
                Ul {
                    targets.read().map { Li { Text(it) } }
                }
            }
        }
    }
}

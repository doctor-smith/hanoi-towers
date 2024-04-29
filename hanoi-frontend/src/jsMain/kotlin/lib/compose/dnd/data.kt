package lib.compose.dnd

import androidx.compose.web.events.SyntheticMouseEvent
import lib.optics.storage.Storage
import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.StyleScope
import org.jetbrains.compose.web.css.backgroundColor

data class Coordinates(
    val x: Double,
    val y: Double
)

data class Velocity(
    val dX: Double,
    val dY: Double
)

data class Draggable(
    val name: String,
    val source: String?,
    val coordinates: Coordinates
)

data class DragDropEnvironment(
    val sources: Storage<List<String>>,
    val targets: Storage<List<String>>,
    val draggables: Storage<List<Draggable>>,
    val dragged: Storage<List<String>>,
    val source: Storage<String?>,
    val hitTarget: Storage<String?>,
    val dropAllowed: Storage<Boolean>,
    val mouseCoordinates: Storage<Coordinates>,
    val mouseVelocity: Storage<Velocity>,
    val onMouseMove: (SyntheticMouseEvent) -> Unit,
    val onMouseDown: (name: String, source: String?, SyntheticMouseEvent) -> Unit,
    val onMouseUp: (name: String, SyntheticMouseEvent) -> Unit,
    val onDrag: DragDropEnvironment.(name: String) -> Unit = {},
    val allowDrag: (name: String, source: String?) -> Boolean = { _, _ -> true },
    val allowDrop: (dragged: List<String>, target: String?) -> Boolean = { _, _ -> false },
    val onDrop: DragDropEnvironment.(source: String?, target: String?) -> Unit = { _, _ -> },
    val onDropRejected: DragDropEnvironment.(source: String?, target: String?) -> Unit = { _, _ -> }
)

data class DraggableConfig(
    val cursorDropped: String = "grab",
    val cursorDrag: String = "grabbing",
    val dragLayer: Int = 1000
)

data class SourceConfig(val x: String = "")
data class TargetConfig(
    val dropExpectedColor: CSSColorValue = Color.lightgreen,
    val dropRejectedColor: CSSColorValue = Color.lightpink,
    val style: StyleScope.(
        name: String,
        environment: DragDropEnvironment
    ) -> Unit = {
        name, environment ->
        if (
            name == environment.hitTarget.read() &&
            name != environment.source.read()
        ) {
            when (environment.dropAllowed.read()) {
                true -> backgroundColor(dropExpectedColor)
                false -> backgroundColor(dropRejectedColor)
            }
        }
    }
)

data class SourceTargetConfig(
    val source: SourceConfig = SourceConfig(),
    val target: TargetConfig = TargetConfig()
)

internal fun DragDropEnvironment.drop(): List<String> {
    val result = dragged.read()
    dragged.write(emptyList())
    return result
}

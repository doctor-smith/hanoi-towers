package lib.compose.dnd

import androidx.compose.web.events.SyntheticMouseEvent
import lib.optics.storage.Storage

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
    val mouseCoordinates: Storage<Coordinates>,
    val mouseVelocity: Storage<Velocity>,
    val onMouseMove: ( SyntheticMouseEvent ) -> Unit,
    val onMouseDown: (name: String, source: String?,  SyntheticMouseEvent)->Unit,
    val onMouseUp: (name: String, SyntheticMouseEvent)->Unit,
    val onDrag: DragDropEnvironment.(name: String)->Unit = {},
    val allowDrop: (dragged: List<String>, target: String?)->Boolean = {_,_->false},
    val onDrop: DragDropEnvironment.(source: String?, target: String?)->Unit = {_,_->},
    val onDropRejected: DragDropEnvironment.(source: String?, target: String?)->Unit = {_,_->}
)

data class DraggableConfig(
    val cursorDropped: String = "grab",
    val cursorDrag: String = "grabbing",
    val dragLayer: Int = 1000
)

internal fun DragDropEnvironment.drop(): List<String> {
    val result = dragged.read()
    dragged.write(emptyList())
    return result
}


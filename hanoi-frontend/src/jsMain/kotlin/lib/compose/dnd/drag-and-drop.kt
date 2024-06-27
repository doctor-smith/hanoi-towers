package lib.compose.dnd

import androidx.compose.runtime.*
import androidx.compose.web.events.SyntheticMouseEvent
import kotlinx.browser.document
import lib.compose.Markup
import lib.optics.storage.Storage
import lib.optics.storage.add
import lib.optics.storage.contains
import lib.optics.storage.onEach
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div

@Markup
@Composable
@Suppress("FunctionName", "UnusedPrivateProperty")
fun DragDropEnvironment(
    onDrag: DragDropEnvironment.(name: String)->Unit = {},
    allowDrag: (name: String, source: String?)->Boolean = {_,_->true},
    allowDrop: (dragged: List<String>, target: String?)->Boolean = {_,_->false},
    onDrop: DragDropEnvironment.(source: String?, target: String?)->Unit = {_,_->},
    onDropRejected: DragDropEnvironment.(source: String?, target: String?)->Unit = {_,_->},
    content: @Composable DragDropEnvironment.()->Unit
) = Div {

    var sources by remember { mutableStateOf(emptyList<String>()) }
    val sourcesStorage = Storage(
        read = {sources},
        write= {list -> sources = list}
    )

    var targets by remember { mutableStateOf(emptyList<String>()) }
    val targetsStorage = Storage(
        read = {targets},
        write= {list -> targets = list}
    )

    var dragged by remember { mutableStateOf(emptyList<String>()) }
    val draggedStorage = Storage(
        read = {dragged},
        write= {list -> dragged = list}
    )

    var draggables: List<Draggable> by remember {  mutableStateOf( arrayListOf() ) }
    val draggablesStorage = Storage(
        read = {draggables},
        write= {list -> draggables = list}
    )

    var dragging by remember { mutableStateOf(false) }

    var mouseX by remember { mutableStateOf(0.0) }
    var mouseY by remember { mutableStateOf(0.0) }
    val coordinatesStorage = Storage(
        read = {Coordinates(mouseX, mouseY)},
        write = {
            mouseX = it.x
            mouseY = it.y
        }
    )

    var dX by remember { mutableStateOf(0.0) }
    var dY by remember { mutableStateOf(0.0) }

    val velocityStorage = Storage(
        read = {Velocity(dX, dY)},
        write = {
            dX = it.dX
            dY = it.dY
        }
    )

    var dropAllowed by remember { mutableStateOf(false) }
    val dropAllowedStorage: Storage<Boolean> = Storage(
        read = {dropAllowed},
        write = {dropAllowed = it}
    )

    var source: String? by remember{ mutableStateOf( null ) }
    val sourceStorage: Storage<String?> = Storage(
        read = {source},
        write = {source = it}
    )

    var hitTarget: String? by remember{ mutableStateOf( null ) }
    val hitTargetStorage: Storage<String?> = Storage(
        read = {hitTarget},
        write = {hitTarget = it}
    )

    val dragDropEnvironment = with(DragDropEnvironment(
        sources = sourcesStorage,
        targets = targetsStorage,
        draggables = draggablesStorage,
        dragged = draggedStorage,
        source = sourceStorage,
        hitTarget = hitTargetStorage,
        dropAllowed = dropAllowedStorage,
        mouseCoordinates = coordinatesStorage,
        mouseVelocity = velocityStorage,
        onMouseMove = { syntheticMouseEvent: SyntheticMouseEvent ->
           // dragging = true
        },
        onMouseDown = {
            name, src,  syntheticMouseEvent ->
                draggedStorage.add(name)
                source = src
                if(allowDrag(name, source)) {
                    dragging = true
                }
        },
        onMouseUp = {
            name, syntheticMouseEvent ->
                dragging = false
        },
        onDrag = onDrag,
        allowDrag = allowDrag,
        allowDrop = allowDrop,
        onDrop = onDrop,
        onDropRejected = onDropRejected
    ) ) {
        document.onmousedown = { event ->
            mouseX = event.pageX
            mouseY = event.pageY
            mouseCoordinates.write(Coordinates(mouseX, mouseY))
            mouseVelocity.write(Velocity(0.0,0.0))
        }
        document.onmousemove = { event ->
            if (dragging) {
                dX = event.pageX - mouseX
                dY = event.pageY - mouseY
                mouseX = event.pageX
                mouseY = event.pageY

                mouseCoordinates.write(Coordinates(mouseX, mouseY))
                mouseVelocity.write(Velocity(dX, dY))

                draggablesStorage.onEach{
                    when(it.name in dragged) {
                        true -> it.copy(
                            coordinates = Coordinates(
                                it.coordinates.x + dX,
                                it.coordinates.y + dY
                            )
                        )
                        false -> it
                    }
                }

                hitTarget = document.elementsFromPoint(mouseX, mouseY)
                    .filter { it.id in targets }
                    .map{ it.id }.firstOrNull()

                dropAllowed = allowDrop(dragged, hitTarget)
            }
        }
        document.onmouseup = {
            when(dropAllowed) {
                true -> {
                    draggablesStorage.onEach {
                        when(it.name in dragged) {
                            true -> it.copy(
                                source = hitTarget
                            )
                            false -> it
                        }
                    }
                    onDrop(source, hitTarget)
                }
                false -> onDropRejected(source, hitTarget)
            }
            dragging = false
            source = null
            draggedStorage.write(emptyList())
            mouseVelocity.write(Velocity(0.0,0.0))
        }
        content()
        this
    }
}


@Markup
@Composable
@Suppress("FunctionName")
fun DragDropEnvironment.Draggable(
    name: String,
    source: String? = null,
    config: DraggableConfig = DraggableConfig(),
    render: @Composable DragDropEnvironment.()->Unit
) {

    var left by remember { mutableStateOf(0.0) }
    var top by remember { mutableStateOf(0.0) }
    var cursor by remember { mutableStateOf(config.cursorDropped) }

    with(draggables.read().find { it.name == name }) {
        when(this) {
            null -> draggables.add(Draggable(name,source, Coordinates(left,top)))
            else -> {
                left = coordinates.x
                top = coordinates.y
            }
        }
    }

    Div({
        id(name)
        style {
            position(Position.Relative)
            left(left.px)
            top(top.px)
            cursor(cursor)
            if (cursor == config.cursorDrag ) {
                property("z-index", config.dragLayer)
            }
        }
        onMouseDown {
            cursor = config.cursorDrag
            onMouseDown(name, source, it)
            onDrag(name)
        }
        onMouseUp {
            cursor = config.cursorDropped
            onMouseUp(name, it)
        }
    }) {
        render()
    }
}

@Markup
@Composable
@Suppress("FunctionName")
fun DragDropEnvironment.source(name: String, render: @Composable DragDropEnvironment.()->Unit) {
    sources.add(name)
    Div({id(name)}) {
        render()
    }
}

@Markup
@Composable
@Suppress("FunctionName")
fun DragDropEnvironment.target(name: String, render: @Composable DragDropEnvironment.()->Unit) {
    targets.add(name)
    Div({id(name)}) {
        render()
    }
}

@Markup
@Composable
@Suppress("FunctionName")
fun DragDropEnvironment.sourceAndTarget(
    name: String,
    config: SourceTargetConfig = SourceTargetConfig(),
    render: @Composable DragDropEnvironment.()->Unit
) {
    sources.add(name)
    targets.add(name)

    val styleBuilder = config.target.style

    Div({
        id(name)
        style {
            styleBuilder(
                name,
                this@sourceAndTarget
            )
        }
    }) {
        render()
    }
}

fun DragDropEnvironment.resetCoordinatesOfDraggedElements() =
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

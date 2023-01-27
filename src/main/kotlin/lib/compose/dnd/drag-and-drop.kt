package lib.compose.dnd

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.web.events.SyntheticMouseEvent
import kotlinx.browser.document
import lib.compose.Markup
import lib.optics.storage.Storage
import lib.optics.storage.add
import lib.optics.storage.remove
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLElement

@Markup
@Composable
@Suppress("FunctionName")
fun DragDropEnvironment(
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

    val dragDropEnvironment = with(DragDropEnvironment(
        sources = sourcesStorage,
        targets = targetsStorage,
        draggables = draggablesStorage,
        dragged = draggedStorage,
        mouseCoordinates = coordinatesStorage,
        mouseVelocity = velocityStorage,
        onMouseMove = { syntheticMouseEvent: SyntheticMouseEvent ->
           // dragging = true
        },
        onMouseDown = {
            name, syntheticMouseEvent ->
                draggedStorage.add(name)
                dragging = true
        },
        onMouseUp = {
            name, syntheticMouseEvent ->
                draggedStorage.remove { it === name }
                dragging = false
        }
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

                draggablesStorage.write(draggablesStorage.read().map{
                    when(it.name in dragged) {
                        true -> Draggable(
                            it.name,
                            Coordinates(
                                it.coordinates.x + dX,
                                it.coordinates.y + dY
                            )
                        )
                        false -> it
                    }
                })

                val hitTarget = document.elementsFromPoint(mouseX, mouseY)
                    .filter { it.id in targets }
                    .map{ it.id }.firstOrNull()
                    //.map { it as HTMLElement }
                    //.map { it.style.backgroundColor = "${Color.green}" }
            }
        }
        document.onmouseup = {
            dragging = false
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
fun DragDropEnvironment.Draggable(name: String, config: DraggableConfig = DraggableConfig(), render: @Composable DragDropEnvironment.()->Unit) {

    var left by remember { mutableStateOf(0.0) }
    var top by remember { mutableStateOf(0.0) }
    var cursor by remember { mutableStateOf(config.cursorDropped) }

    with(draggables.read().find { it.name == name }) {
        when(this) {
            null -> draggables.add(Draggable(name, Coordinates(left,top)))
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
        }
        onMouseDown {
            cursor = config.cursorDrag
            onMouseDown(name, it)
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
fun DragDropEnvironment.sourceAndTarget(name: String, render: @Composable DragDropEnvironment.()->Unit) {
    sources.add(name)
    targets.add(name)

    var bgColor by remember { mutableStateOf<CSSColorValue?>(null) }

    Div({
        id(name)
        onMouseEnter {
            bgColor = Color.azure
        }
        onMouseLeave{
            bgColor = null
        }
        style {
            if (bgColor != null) {
                backgroundColor(bgColor!!)
            }
        }
    }) {
        render()
    }
}



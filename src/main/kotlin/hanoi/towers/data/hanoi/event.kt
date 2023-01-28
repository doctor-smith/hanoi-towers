package hanoi.towers.data.hanoi

import lib.compose.dnd.Coordinates
import lib.optics.lens.Lens


data class DragEvent(
    val coordinates: Coordinates,
    val slice: Int,
    val target: Int? = null,
    val drop: (slice: Int)->Unit = { _ -> Unit}
)

val coordinatesLens: Lens<DragEvent, Coordinates> = Lens(
    get = {whole -> whole.coordinates},
    set = {part -> {whole -> whole.copy(coordinates = part)}}
)

val sliceLens: Lens<DragEvent, Int> = Lens(
    get = {whole -> whole.slice},
    set = {part -> {whole -> whole.copy(slice = part)}}
)

val targetLens: Lens<DragEvent, Int?> = Lens(
    get = {whole -> whole.target},
    set = {part -> {whole -> whole.copy(target = part)}}
)
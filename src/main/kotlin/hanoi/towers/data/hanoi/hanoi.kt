package hanoi.towers.data.hanoi

data class Hanoi (
    val one: List<Int> = listOf(),
    val two: List<Int> = listOf(),
    val three: List<Int> = listOf()
)

fun Hanoi.numberOfSlices() = with(this) {
    one.size+two.size+three.size
}

fun Hanoi.reset(): Hanoi = with(this){
    Hanoi(
    (1..numberOfSlices()).map { it }
)
}

sealed class Mode {
    object Automatic: Mode()
    object Play: Mode()
    object Cheat: Mode()
}
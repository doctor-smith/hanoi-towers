package hanoi.towers.data.hanoi

sealed class Tower {
    object One : Tower()
    object Two : Tower()
    object Three : Tower()

    override fun toString(): String = when(this) {
        is One -> "1"
        is Two -> "2"
        is Three -> "3"
    }
}

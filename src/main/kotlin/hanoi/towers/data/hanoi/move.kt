package hanoi.towers.data.hanoi


data class Move(
    val from: Tower,
    val to: Tower
) {
    override fun toString(): String = "$from -> $to"
}

data class Moves(val moves: ArrayList<Move>): List<Move> by moves {

    constructor(vararg moves : Move) : this(arrayListOf(*moves))

    override fun toString(): String = joinToString("\n") { "$it" }
}


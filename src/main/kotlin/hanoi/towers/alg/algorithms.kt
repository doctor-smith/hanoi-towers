package hanoi.towers.alg

import hanoi.towers.data.Hanoi
import hanoi.towers.data.Move
import hanoi.towers.data.Moves
import hanoi.towers.data.Tower
import lib.list.dropFirst
import lib.maths.Maths
import lib.maths.o
import lib.maths.x

fun moveTower(hight: Int, from: Tower, to: Tower, stack: Tower): (Moves)-> Moves = when(hight) {
    0 -> { moves -> moves }
    1 -> { moves -> Moves(
            *moves.moves.toTypedArray(),
            Move(from, to)
        )
    }
    else -> { moves ->
        (
            moveTower(
                hight = hight -1,
                from= stack,
                to= to,
                stack = from
            ) o
            moveTower(
                hight = 1,
                from = from,
                to = to,
                stack = stack
            ) o
            moveTower(
                hight = hight -1 ,
                from = from,
                to = stack,
                stack = to
            )
         )(
            moves
        )
    }
}

fun Hanoi.apply(move: Move): Hanoi = with(move) {
    when(from) {
        Tower.One -> when(to) {
            Tower.One -> this@apply
            Tower.Two -> with(
                (one x two).moveTop()
            ) {
                Hanoi(first,second, three)
            }
            Tower.Three -> with(
                (one x three).moveTop()
            ){
                Hanoi(first, two, second)
            }
        }
        Tower.Two -> when(to) {
            Tower.One -> with(
                (two x one).moveTop()
            ){
                Hanoi(second,first, three)
            }
            Tower.Two -> this@apply
            Tower.Three -> with(
                (two x three).moveTop()
            ) {
                Hanoi(one, first, second)
            }
        }
        Tower.Three -> when(to) {
            Tower.One -> with(
                (three x one).moveTop()
            ) {
                Hanoi(second,two, first)
            }
            Tower.Two -> with(
                (three x two).moveTop()
            ) {
                Hanoi(one, second, first)
            }
            Tower.Three -> this@apply
        }
    }
}

/**
 * Move top of the first list to the top of the second one
 */
fun Pair<List<Int>,List<Int>>.moveTop(): Pair<List<Int>,List<Int>> = with(first.dropFirst()) {
    when(first){
        null -> this@moveTop
        else -> Pair(second, listOf(
            first!!,
            *this@moveTop.second.toTypedArray()
        ))
    }
}

/**
 * Compute the inverse of a move
 */
@Maths
fun Move.inverse(): Move = Move(to,from)

/**
 * Compute the inverse of a sequence of moves
 */
@Maths
fun Moves.inverse(): Moves = Moves( *reversed().map { it.inverse() }.toTypedArray() )
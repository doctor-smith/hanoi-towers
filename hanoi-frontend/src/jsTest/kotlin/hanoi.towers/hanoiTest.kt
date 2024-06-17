package hanoi.towers

import hanoi.towers.alg.apply
import hanoi.towers.alg.moveTower
import hanoi.towers.data.hanoi.Hanoi
import hanoi.towers.data.hanoi.Move
import hanoi.towers.data.hanoi.Moves
import hanoi.towers.data.hanoi.Tower
import kotlin.test.Test
import kotlin.test.assertEquals


class hanoiTest {

    @Test fun testApplyMove() {

        // Ausgangszustand
        val initialState = Hanoi(
            one = listOf(3, 2, 1),
            two = listOf(),
            three = listOf()
        )

        // Zug: Bewege eine Scheibe von Tower One nach Tower Two
        val move = Move(Tower.One, Tower.Two)
        val newState = initialState.apply(move)

        // Erwarteter Endzustand
        val expectedState = Hanoi(
            one = listOf(2,1),
            two = listOf(3),
            three = listOf()
        )

        // Überprüfung
        assertEquals(expectedState, newState, "The disk should be moved from Tower 1 to Tower 2")
    }
}
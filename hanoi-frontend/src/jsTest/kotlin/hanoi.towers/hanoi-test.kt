package hanoi.towers

import hanoi.towers.alg.apply
import hanoi.towers.data.hanoi.Hanoi
import hanoi.towers.data.hanoi.Move
import hanoi.towers.data.hanoi.Tower
import kotlin.test.Test
import kotlin.test.assertEquals


class hanoiTest {

    @Test fun testApplyMove() {

        val initialState = Hanoi(
            one = listOf(3, 2, 1),
            two = listOf(),
            three = listOf()
        )

        // Move a slice from tower one to tower two
        val move = Move(Tower.One, Tower.Two)
        val newState = initialState.apply(move)

        val expectedState = Hanoi(
            one = listOf(2,1),
            two = listOf(3),
            three = listOf()
        )

        assertEquals(expectedState, newState, "The slice should be moved from Tower 1 to Tower 2")
    }
}
package org.evoleq.math.state

import kotlinx.coroutines.runBlocking
import org.evoleq.math.state.State
import org.evoleq.math.state.applyTo
import org.evoleq.math.state.bind
import org.evoleq.math.x
import kotlin.test.Test
import kotlin.test.assertEquals

class StateTest {
    @Test fun testBindOperator() = runBlocking{
        val x1 = 1
        val s1 = 5
        val state = State<Int,Int>(x1)  bind {
            r -> State { st: Int -> (st + r) x st }
        }
        val res = state(s1)

        assertEquals((x1 + s1) x s1, res)
    }

    @Test fun testApply() = runBlocking {
        val f: suspend (Int) -> String = {x -> "$x"}
        val state = State<Int,suspend (Int) -> String >(f)

        val stateS = State<Int,Int>(5)

        val result = state.applyTo(stateS) runOn 1

        assertEquals("5" x 1, result)
    }
}

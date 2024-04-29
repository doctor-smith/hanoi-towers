package lib.optics.iso

import lib.optics.P
import lib.optics.lens.Lens
import lib.optics.transform.times
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

// TODO()

class IsoTest {

    data class D1(val name: String)
    data class D2(val name: String)

    @Test
    fun multWithLens() {
        val iso = Iso<D1, D2>(
            { x -> D2(x.name) }
        ) {
            s ->
            D1(s.name)
        }

        val lens = Lens<P, D1>(
            { p -> D1(p.name) }
        ) {
            x ->
            { p -> p.copy(name = x.name) }
        }

        val result = lens * iso

        val d2 = result.get(P("name"))
        assertIs<D2>(d2)

        val p2 = result.set(D2("name_2"))(P("name"))
        assertIs<P>(p2)
        assertEquals("name_2", p2.name)
    }
}

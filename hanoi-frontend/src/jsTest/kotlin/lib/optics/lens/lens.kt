package lib.optics.lens

import lib.optics.P
import lib.optics.W
import kotlin.test.Test
import kotlin.test.assertEquals


class LensTest {
    @Test
    fun lensComposition() {

        val lens = Lens(
            {w: W -> w.p},
            {p: P -> { w : W -> w.copy(p = p)}}
        )
        val lens2 = Lens(
            {p: P ->p.name},
            {name: String -> {p: P -> p.copy(name = name) }}
        )

        val data = W(
            1, P("heinz")
        )

        val name = lens * lens2

        val result = name.set("joe") (data)
        val expected = W(1, P("joe"))

        assertEquals(expected, result)
    }
}

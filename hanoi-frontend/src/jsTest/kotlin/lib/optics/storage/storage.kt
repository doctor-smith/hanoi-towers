package lib.optics.storage

import lib.optics.P
import lib.optics.W
import lib.optics.lens.*
import lib.optics.transform.times
import kotlin.test.Test
import kotlin.test.assertEquals


class StorageTest {
    @Test
    fun composeStorageWithLenses() {
        var w: W = W(0, P(""))
        val storage = Storage<W>(
            {w},
            {w = it}
        )
        assertEquals(w, storage.read())
        val w1 = W(1, P(""))
        storage.write(W(1, P("")))
        assertEquals(w1, storage.read())

        val p = storage * Lens<W, P>(
            {w -> w.p},
            {p -> {w -> w.copy(p = p)}}
        )
        val name = p * Lens(
            {p -> p.name},
            {name -> {p -> p.copy(name = name)}}
        )
        name.write("flo")

        assertEquals("flo",name.read())
    }

    @Test
    fun storageDSL() {
        var x = 1
        val storage = Storage(
            {x},
            {s -> x = s}
        )

        val X = Read from storage
        assertEquals(1 ,X)
        write(5)  to storage
        assertEquals(5, read (storage))
    }
}

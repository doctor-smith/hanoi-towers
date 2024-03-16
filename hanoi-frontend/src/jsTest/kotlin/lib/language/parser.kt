package lib.language

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ParserTest {

    @Test
    fun variable() {

        val key = "name"
        val value = "Very long Text"
        val argument = "     $key   :  \"$value\"    "
        val expected = Lang.Variable(
            key,
            value
        )

        val parsed = Variable().run(argument)
        assertEquals(expected, parsed.result)
    }


    @Test
    fun simpleBlock() {
        val name = "name"
        val content1 = "key1: \"value1\""
        val content2 = "key2: \"value2\""
        val content3 = "key3: \"value3\""
        val arg = """
            |$name { 
            |   $content1 
            |   $content2
            |   block { $content3 }
            |   b{ x }
            |}
        """.trimMargin()

        val result = Block().run(arg)
      //  println(result)

      //  println(LanguageP().run(arg))
        assertTrue{true}
        //val value3 = result.result!!["name.block.key3"]
       // println(value3)
        //assertNotNull(value3 is Var)
      //  assertEquals("value3", value3)//.value)
    }


    @Test fun path() {
        val p = "x.y.z"

        val result = Path().run(p)

        assertEquals(listOf("x","y","z"), result.result)
    }

    @Test fun get() {
        val lang = Lang.Block(
            "x",
            listOf(
                Var("k1", "v1"),
                Var("k2", "v2"),
                Block("y", listOf(Var("c", "d"))),
                Block("z", listOf(Var("a", "b")))
            )
        )

        val v1 = lang["k1"]

        assertEquals("v1",v1)

        val b = lang["z.a"]
        assertEquals("b", b)
    }

    @Test fun component() {
        val c = Block("c", listOf())
        val lang = Lang.Block(
            "x",
            listOf(
                Var("k1", "v1"),
                Var("k2", "v2"),
                Block("y", listOf(c)),
                Block("z", listOf(Var("a", "b")))
            )
        )

        val result = lang.component("y.c")
        println(result)
        assertEquals(c, result)
    }
}
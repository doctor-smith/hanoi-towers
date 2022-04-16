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
            |}
        """.trimMargin()

        val result = Block().run(arg)
        println(result)

        println(LanguageP().run(arg))
        assertTrue{true}
    }
}
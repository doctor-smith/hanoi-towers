package lib.parser

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ParserTest {

    @Test fun create() {
        val p = ReturnParser(1)

        val x = p.run("hi")

        assertEquals(Result(1, "hi"), x)
    }

    @Test fun map() {
        val parser = ReturnParser(1)
        val mapped = parser map { x -> x + 1 }
        assertEquals(2, mapped.run("").result)
    }

    @Test fun apply() {
        val parser = ReturnParser { x: Int -> "$x" }

        val result = parser.apply()(Parser { s -> Result(s.length, "") }).run("asdfg").result
        assertEquals("5", result)
    }

    @Test fun first() {
        val s = "012"
        val result = First.run(s)
        assertEquals('0', result.result)
        assertEquals("12", result.rest)
    }

    @Test fun firstMatches() {
        val result = FirstMatches('1').run("12345")
        assertEquals('1', result.result)
        assertEquals("2345", result.rest)
    }

    @Test fun sequenceA() {
        val n = 5
        val list = listOf('1', '2', '3', '4', '5').map { FirstMatches(it) }
        val rest = "rest"
        val argument = (1..n).joinToString("") { "$it" } + rest

        val sequenced = list.sequenceA()

        val result: Result<List<Char>> = sequenced.run(argument)

        assertEquals(
            Result(listOf('1', '2', '3', '4', '5'), rest),
            result
        )
    }

    @Test fun startsWith() {
        val start = "fjdka"
        val end = "ööadfhgga"
        val arg = "$start$end"
        val expected = Result(
            start,
            end
        )

        assertEquals(
            expected,
            StartsWith(start).run(arg)
        )
    }

    @Test fun or() {
        val arg1 = "1"
        val arg2 = "2"
        val arg3 = "3"
        val parser = FirstMatches('1').OR(FirstMatches('2'))

        val result =
            parser.run(arg1).hasSucceeded() &&
                parser.run(arg2).hasSucceeded() &&
                parser.run(arg3).hasFailed()

        assertTrue(result)
    }

    @Test fun whitespace() {
        val arg = "fjdkla"
        with(Whitespace.run(" $arg")) {
            assertTrue(hasSucceeded())
            assertEquals(arg, rest)
        }
    }

    @Test fun dropAllWhitespace() {
        val arg = "fjdkla"
        with(DropAllWhitespace().run("     $arg")) {
            assertTrue(hasSucceeded())
            assertEquals("", result)
            assertEquals(arg, rest)
        }
    }

    @Test fun whenParser() {
        val predicate: (Char) -> Boolean = { it != ';' }

        val arg = ":abc"

        val expected = Result(':', "abc")

        val result = When(predicate).run(arg)

        assertEquals(expected, result)
    }

    @Test fun collectWhile() {
        val predicate: (Char) -> Boolean = { it != ':' }

        val arg = "name:abc"

        val expected = Result("name", ":abc")

        val result = CollectWhile(predicate).run(arg)

        assertEquals(expected, result)
    }

    @Test fun betweenChars() {
        val left = '"'
        val right = '"'
        val content = "fdjkaöfjdköa"

        val expected = Result(content, "")
        val result = Between(left, right).run("\"$content\"")

        assertEquals(expected, result)
    }

    @Test fun betweenNestedChars() {
        val left = '{'
        val right = '}'
        val content = """
            |nested{
            |    structure
            |}""".trimMargin()
        val arg = """
            |{
            |   $content
            |}""".trimMargin()
        val result = BetweenNested(left, right).run(arg)
        // println(result)

        assertEquals(content, result.result?.trim())
    }

    // @Test
    fun play() {

        val left = '{'
        val right = '}'

        val White = DropAllWhitespace()

        val Left = White dL (FirstMatches(left) map { "$it" }) dR White

        val Right = White dL FirstMatches(right) map { "$it" } dR White

        val IsLeft = When { it == left }

        val TillLeft = CollectWhile { it != left }

        val TillRight = CollectWhile { it != right }

        val TillLeftOrRight = CollectWhile { it != left && it != right }

        // println(Left.run("      {     }   "))
        // println(Right.run("      }    "))

        fun Parser(): Parser<String> =
            Left dL
                (
                    (
                        sequenceA(
                            White dL TillLeft, // OrRight,
                            IsLeft * { Parser() }
                        ) map {
                            "${it[0]}{${it[1]}}"
                        }
                        ) OR
                        (White dL TillRight dR Right) map { "$it}" }
                    ) dR
                Right

        val content = " { nested{} }"
        // val result = Parser().run(content)
        // println(result)

        val c1 = "{ how {do {you{ feel  } }} }---{}"

        // println(Parser().run(c1))

        // println(Balance(left, right).run(c1))
        // println(BetweenNested(left, right).run(c1))
    }

    @Test fun getResource() {
    }
}

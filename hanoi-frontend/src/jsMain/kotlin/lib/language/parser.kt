package lib.language

import lib.parser.*

typealias Var = Lang.Variable
typealias Block = Lang.Block


@Suppress("FunctionName")
fun Variable(): Parser<Lang> = sequenceA(
    Key(),
    Value()
)  map { list -> Var(list[0], list[1]) }


@Suppress("FunctionName")
private fun Key(): Parser<String> =
    DropAllWhitespace() dL
    CollectWhile { !it.isWhitespace() && it != ':'  } dR
    sequenceA(DropAllWhitespace(), FirstMatches(':') map {"$it"})

@Suppress("FunctionName")
private fun Value(): Parser<String> =
    DropAllWhitespace() dL
    Between('"','"') dR
    DropAllWhitespace()

@Suppress("FunctionName")
fun Block(): Parser<Lang> = sequenceA(
    DropAllWhitespace() dL
        CollectWhile { !it.isWhitespace() && it != '{' } dR DropAllWhitespace(),
    BetweenNested('{','}') dR DropAllWhitespace()
) map { list ->
    val name = list[0]
    val content = list[1]
    Content().run(content) map { Block(name, it) }
} map  {
    it.result!!
}

@Suppress("FunctionName")
fun Content(): Parser<List<Lang>> =
    sequenceA(DropAllWhitespace(), DropAllNewline()) dL
    LanguageP() * {x -> Content() map { list -> listOf(x, *list.toTypedArray())}} OR Succeed(listOf()) dR
    sequenceA(DropAllWhitespace(), DropAllNewline())

@Suppress("FunctionName")
fun Comment(): Parser<String> = seqA(DropAllWhitespace(), StartsWith("/*")) dL
        CollectWhile { it != '/' } * { collected -> when(collected.endsWith("*")){
            true -> Parser{ s -> Result(collected.dropLast(1), "*$s") }
            false -> Parser{ s -> Result("collected/", s.drop(1))  }
        }  } dR
        seqA(StartsWith("*/"), DropAllWhitespace())

@Suppress("FunctionName")
fun LanguageP(): Parser<Lang> = (Variable() OR Block())


@Suppress("FunctionName")
fun Segment(): Parser<String> = CollectWhile{ it != '.' } dR DropWhile { it == '.' }

@Suppress("FunctionName")
fun Path(): Parser<List<String>> = Segment() * { seg -> when(seg.isEmpty()){
   true -> Succeed(listOf())
   false -> Path() map{ list -> listOf(seg, *list.toTypedArray()) }
} }

package lib.language

import lib.maths.x
import lib.parser.*

typealias Var = Lang.Variable
typealias Block = Lang.Block


fun Variable(): Parser<Lang> = sequenceA(
    Key(),
    Value()
)  map { list -> Var(list[0], list[1]) }


private fun Key(): Parser<String> =
    DropAllWhitespace() dL
    CollectWhile { !it.isWhitespace() && it != ':'  } dR
    sequenceA(DropAllWhitespace(), FirstMatches(':') map {"$it"})

private fun Value(): Parser<String> =
    DropAllWhitespace() dL
    Between('"','"') dR
    DropAllWhitespace()

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

fun Content(): Parser<List<Lang>> =
    sequenceA(DropAllWhitespace(), DropAllNewline()) dL
    LanguageP() * {x -> Content() map { list -> listOf(x, *list.toTypedArray())}} OR Succeed(listOf()) dR
    sequenceA(DropAllWhitespace(), DropAllNewline())

fun LanguageP(): Parser<Lang> = (Variable() OR Block())
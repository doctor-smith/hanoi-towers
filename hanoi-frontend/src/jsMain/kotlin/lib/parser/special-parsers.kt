package lib.parser

import lib.maths.x

val All: Parser<String> = Parser { s -> Result(s, "") }

val First: Parser<Char> = Parser {
    s -> when{
        s.isEmpty() -> Result(null,"")
        else -> Result(
            s.first(),
            s.drop(1)
        )
    }
}

val FirstMatches: (Char)->Parser<Char> = {symbol:Char -> First * { first:Char ->
    when(symbol == first){
        true -> Parser { s -> Result(first, s) }
        false -> Fail()
    }
}}

val StartsWith: (String)->Parser<String> = { symbols ->
    symbols.map { FirstMatches(it) }
        .sequenceA()
        .map { list -> list.joinToString("") { "$it" } }
}

val Whitespace: Parser<String> = First * { s -> when("$s".isBlank()){
    true -> Parser { Result("", it) }
    else -> Fail()
} }

@Suppress("FunctionName")
fun DropAllWhitespace(): Parser<String> = (Whitespace * { DropAllWhitespace() }) OR Succeed("")

val Newline: Parser<String> = First * {
    s -> when("$s" == "\n") {
        true -> Parser {Result("", it)}
        false -> Fail()
    }
}

@Suppress("FunctionName")
fun DropAllNewline(): Parser<String> = (Newline * { DropAllNewline() }) OR Succeed("")


val When: ((Char)->Boolean)->Parser<Char> = {
    predicate -> First * { first ->
        when(predicate(first)){
            true -> Parser { state ->Result(
                first,
                state
            )}
            false -> Fail()
        }
    }
}

val Drop: Parser<String> = First map {""} OR Succeed("")

@Suppress("FunctionName")
fun CollectWhile(predicate:(Char)->Boolean): Parser<String> =
    ( When(predicate)  * { result -> CollectWhile(predicate) map { "$result$it" }} ) OR Succeed("")


@Suppress("FunctionName")
fun DropWhile(predicate:(Char)->Boolean): Parser<String> =
    When(predicate) * { DropWhile(predicate) } OR Succeed("")

@Suppress("FunctionName")
fun Between(left: Char, right: Char): Parser<String> = FirstMatches(left) dL CollectWhile { it != right } dR FirstMatches(right)


@Suppress("FunctionName")
fun BetweenNested(left: Char, right: Char): Parser<String> =
    Balance(left, right) map { it.drop(1).dropLast(1)}

@Suppress("FunctionName")
fun BalanceBase(left: Char, right: Char): Parser<Pair<Int, String>> =
    First * {first ->
        when (first) {
            left -> ReturnParser(1 x "$left")
            right -> ReturnParser(-1 x "$right")
            else -> ReturnParser(0 x "$first")
        }
    }

@Suppress("FunctionName")
fun Balance(left: Char, right: Char,pair: Pair<Int,String>): Parser<String> =
    BalanceBase(left,right) * {p -> with((pair.first + p.first) x "${pair.second}${p.second}"){
        if(first == 0) {
            ReturnParser(second)
        } else {
            Balance(left,right,this)
        }
    } }

@Suppress("FunctionName")
fun Balance(left: Char, right: Char): Parser<String> = (FirstMatches(left) map { 1 x "$left" }) * { pair ->  Balance(left,right,pair) }

@Suppress("FunctionName")
fun SplitAtFirst(separator: Char) = seqA(
    CollectWhile { it != separator },
    Drop
).map {list -> list[0]} * {
        start -> All map { rest -> Pair(start, rest)  }
}

@Suppress("FunctionName")
fun Split(separator: Char): Parser<List<String>> =
    Empty map { listOf<String>() } OR
    (DropWhile { it == separator } dL seqA(
        CollectWhile { it != separator },
        Drop
    ).map {list -> list[0]} * {
            segment -> Split(separator) map {list -> listOf(segment,*list.toTypedArray()) }
    })
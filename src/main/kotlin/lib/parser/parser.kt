package lib.parser


infix fun <S, T> S?.mapp(f:(S)->T): T? = try {
    f(this!!)
} catch (e: Exception) {
    null
}



data class ParsingResult<T>(
    val result: T?,
    val rest: String
)
typealias Result<T> = ParsingResult<T>

fun <T> Result<T>.hasFailed(): Boolean = result == null

fun <T> Result<T>.hasSucceeded(): Boolean = !hasFailed()

infix fun <S, T> Result<S>.map(f: (S) -> T): Result<T> = Result(
    result mapp f,
    rest
)


fun <T> Result<List<T>>.interchange(): List<Result<T>> = try{

    val x:List<T> = result!!
    x.map { a -> Result(a, rest) }
}catch(ex: Exception) {
    emptyList()
}


interface Parser<T> {
    val run: (String)->Result<T>

    companion object {
        fun <T> ret(): (T)->Parser<T> = { t -> ReturnParser(t) }


    }
}


@Suppress("FunctionName")
fun <T> Parser(run: (String)->Result<T>): Parser<T> = object : Parser<T> {
    override val run: (String) -> Result<T> = run
}

fun <T> ReturnParser(result: T): Parser<T> = Parser<T> {
    Result(result, it )
}

fun <T> Fail():Parser<T> = Parser { s-> Result(null, s) }



fun <T> Succeed(t: T): Parser<T> = ReturnParser(t)

infix fun <S, T> Parser<S>.map(f: (S)->T): Parser<T> = Parser<T>{
        s -> this@map.run(s) map f
}

fun <T> Parser<Parser<T>>.mult(): Parser<T> = Parser{
    s -> with(this@mult.run(s)) {
        when(result) {
            null -> Result(null,s)
            else -> result.run(rest)
        }
    }
}

fun <S, T> Parser<(S)->T>.apply() :(Parser<S>)->Parser<T> = {
        parser -> this * {f-> parser map f}
}

fun <T> Parser<T>.toList(): Parser<List<T>> = map { listOf(it) }

fun <T> List<Parser<T>>.sequenceA(): Parser<List<T>> =
    fold(
        ReturnParser(listOf()),
    ) {
        acc, parser -> acc * { s -> parser map { listOf( *s.toTypedArray(), it) } }
    }

fun <T> sequenceA(
    first: Parser<T>,
    vararg others: Parser<T>
): Parser<List<T>> = listOf(
    first,
    *others
).sequenceA()

fun <T> seqA(
    first: Parser<T>,
    vararg others: Parser<T>
): Parser<List<T>> = sequenceA(
    first,
    *others
)

infix fun <T> Parser<T>.then(next: Parser<T>): Parser<List<T>> = seqA(this,next)

infix fun <T> Parser<T>.o(previous: Parser<T>): Parser<List<T>> = seqA(previous,this)


operator fun <S, T> Parser<S>.times(kleisli: (S)->Parser<T>): Parser<T> = (this map kleisli).mult()

infix fun <S> Parser<S>.OR(other: Parser<S>): Parser<S> = Parser {
    s -> with(this@OR.run(s))  {
        when(hasFailed()) {
            true -> other.run(s)
            false -> this
        }
    }
}

//fun <T> Empty(): Parser<String>

infix fun <S, T> Parser<S>.discardLeft(right: Parser<T>): Parser<T> = this * { right }

infix fun <S, T> Parser<S>.dL(right: Parser<T>): Parser<T> = this discardLeft right

infix fun <S, T> Parser<S>.discardRight(right: Parser<T>): Parser<S> = this * { s -> right map { s } }

infix fun <S, T> Parser<S>.dR(right: Parser<T>): Parser<S> = this discardRight right




package lib.compose.routing

import lib.maths.x
import lib.parser.*

@Suppress("FunctionName")
fun Param(): Parser<Parameter> = SplitAtFirst('=') map {
    Parameter(it.first, it.second)
}

@Suppress("FunctionName")
fun Params(): Parser<List<Parameter>> = Split(';') map {
    it.map { string -> Param().run(string).result!! }
}

@Suppress("FunctionName")
fun Segment(): Parser<RouteSegment> =
    (FirstMatches(':') * { Parser { s -> Result(RouteSegment.Variable(s) as RouteSegment, "") } }) OR
        Parser { s -> Result(RouteSegment.Static(s) as RouteSegment, "") }

@Suppress("FunctionName")
fun Segments(): Parser<List<RouteSegment>> = Split('/') map { list -> list.map { Segment().run(it).result!! } }

@Suppress("FunctionName")
fun RouteParser(): Parser<Route> = SplitAtFirst('?') map {
    pair ->
    val (path, params) = pair
    Segments().run(path) x Params().run(params)
} map {
    Route(
        it.first.result!!,
        it.second.result!!
    )
}

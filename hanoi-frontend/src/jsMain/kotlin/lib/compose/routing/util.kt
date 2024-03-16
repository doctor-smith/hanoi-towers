package lib.compose.routing

import androidx.compose.runtime.Composable
import lib.maths.x

fun Routes.find(segment: RouteSegment): Routes? = children.find { it.segment == segment }

fun Routes.find(string: String): Routes? = children.find { it.segment.value == string }

fun Routes.merge(route: Route): Routes =
    if(route.segments.isNotEmpty()) {
        val rest = route.segments.drop(1)

        when(val segment = route.segments.first()) {
            is RouteSegment.Root -> if(this.segment is RouteSegment.Root){
                merge(Route(rest, listOf()))
            } else {
                throw Exception("Cannot merge Roots into other")
            }
            else -> {
                val found = find(segment)
                val newRoutes =
                    found?.merge(Route(rest, listOf())) ?:
                    Routes(segment, listOf()).merge(Route(rest, listOf()))

                if(segment is RouteSegment.Static) {
                    this@merge.prepend(newRoutes)
                }
                if(segment is RouteSegment.Variable) {
                    this@merge.append(newRoutes)
                }
                this@merge
            }
        }
    } else {
        this
    }


fun Routes.append(routes: Routes): Routes = Routes(
    segment,
    listOf(
        *children.toTypedArray(),
        routes
    )
)


fun Routes.prepend(routes: Routes): Routes = Routes(
    segment,
    listOf(
        routes,
        *children.toTypedArray()
    )
)

fun Route.append(segment: String): Route = Route(
    listOf(
        *segments.toTypedArray(),
        Segment().run(segment).result!!
    ),
    queryParameters
)

fun Route.append(segment: RouteSegment) = Route(
    listOf(
        *segments.toTypedArray(),
        segment
    ),
    queryParameters
)

fun ComposableRoute.append(segment: RouteSegment) = ComposableRoute(
    listOf(
        *segments.toTypedArray(),
        segment
    ),
    queryParameters,
    component
)

fun Routes.match(path: String): ComposableRoute? = with(
    path.dropWhile { it == '/' }.dropLastWhile { it == '/' }.split("/").map{it.trim()}.filter { it != "" }
) {
    if(component != null) {
        if(size == 0) {
            ComposableRoute(listOf(), listOf(), component)
        } else {
            this@match.match(ComposableRoute(listOf(), listOf(), component) x this).first
        }
    } else {
        this@match.match(ComposableRoute(listOf(), listOf()){} x this).first
    }
}

tailrec fun Routes.match(pair: Pair<ComposableRoute,List<String>>): Pair<ComposableRoute?, List<String>> {
    val (route, list) = pair

    if(list.isEmpty()) {
        return when(component != null) {
            true -> pair.copy(first = pair.first.copy(component = component))
            false -> null x list
        }
    }

    val (head, tail) = Pair(list.first(), list.drop(1))

    val found = find(head) ?: children.find { it.segment is RouteSegment.Variable }
    // return found?.match(route.append(head) x tail) ?: (null x list) -> not tailrec
    return if(found == null) {
        null x list
    } else {
        found.match(
            route.append(
                when(val s = found.segment){
                    is RouteSegment.Static -> s
                    is RouteSegment.Variable -> s.copy(value = head)
                    else -> s
                }
            ) x tail
        )
    }
}

fun ComposableRoute.parameter(name: String): String? = segments.filterIsInstance<RouteSegment.Variable>().find { it.name == name }?.value

@RoutingDsl
@Composable
fun Routes.compose(path: String): Boolean = with(match(path) ) {
    if(this != null) {
        val route = this
        route.component()
        true
    } else {
        false
    }
}
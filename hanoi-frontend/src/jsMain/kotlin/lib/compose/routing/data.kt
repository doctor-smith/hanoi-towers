package lib.compose.routing

import androidx.compose.runtime.Composable

sealed class Protocol(val name: String) {
    object HTTP : Protocol("http")
    object HTTPS : Protocol("https")

    override fun toString(): String = name
}

data class Parameter(
    val key: String,
    val value: String
)

data class Route(
    val segments: List<RouteSegment>,
    val queryParameters: List<Parameter>,
)

data class ComposableRoute(
    val segments: List<RouteSegment>,
    val queryParameters: List<Parameter>,
    val component: (@Composable ComposableRoute.()->Unit)
)

sealed class RouteSegment(open val value: String) {
    object Root : RouteSegment("") {
        override fun toString(): String = "Root"
    }
    data class Static(override val value: String): RouteSegment(value)
    data class Variable(val name: String, override val value: String = ""): RouteSegment(value)
}

data class Routes(
    val segment: RouteSegment,
    val children: List<Routes>,
    val component: (@Composable ComposableRoute.()->Unit)? = null
)
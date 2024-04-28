package lib.list

fun <T> List<T>.dropFirst(): Pair<T?, List<T>> = when {
    isEmpty() -> Pair(null, this)
    else -> Pair(first(), drop(1))
}

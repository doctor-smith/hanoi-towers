package lib.optics.storage

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import lib.maths.x
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

data class Storage<P>(
    val read: () -> P,
    val write: (P) -> Unit
)

fun <P> Storage<P>.onWrite(f: suspend (P) -> Unit): Storage<P> = Storage(
    read
) {
    write(it)
    CoroutineScope(Job()).launch {
        f(it)
    }
}

fun <P> Storage<P>.onChange(f: suspend (P) -> Unit): Storage<P> = Storage(
    read
) {
    if (read() != it) {
        write(it)
        CoroutineScope(Job()).launch {
            f(it)
        }
    }
}

class Read {
    companion object {

        infix fun <P> from(storage: Storage<P>): P = storage.read()
    } 
}

infix fun <P> Any?.read(storage: Storage<P>): P = storage.read()

infix fun <P> Any?.write(p: P): (Storage<P>) -> Unit = { storage -> storage.write(p) }

infix fun <P> ((Storage<P>) -> Unit).to(storage: Storage<P>): Unit = this(storage)

fun <T> Storage<List<T>>.filter(predicate: (T) -> Boolean): List<T> = read().filter { predicate(it) }

fun <T> Storage<List<T>>.remove(predicate: (T) -> Boolean): Unit = write(read().filter { predicate(it) })

fun <T> Storage<List<T>>.onEach(f: (T) -> T): Unit = write(read().map(f))

operator fun <T> Storage<List<T>>.contains(predicate: (T) -> Boolean): Boolean = filter(predicate).isNotEmpty()

operator fun <T> Storage<List<T>>.contains(item: T): Boolean = filter { it == item }.isNotEmpty()

fun <T> Storage<List<T>>.add(item: T): Unit = write(
    listOf(
        *read().toTypedArray(),
        item
    )
)

fun <T> Storage<List<T>>.add(items: List<T>): Unit = write(
    listOf(
        *read().toTypedArray(),
        *items.toTypedArray()
    )
)

fun <T, R : Comparable<R>> Storage<List<T>>.sortBy(f: (T) -> R) {
    write(
        with(arrayListOf(*read().toTypedArray())) {
            sortBy(f)
            this
        }
    )
}

fun <Id, T> Storage<Map<Id, T>>.readAndFilter(predicate: (Pair<Id, T>) -> Boolean): Map<Id, T> = read().filter { s -> predicate(Pair(s.key, s.value)) }

fun <Id, T> Storage<Map<Id, T>>.put(item: Pair<Id, T>): Unit = write(
    mapOf(
        *read().map { it.key x it.value }.toTypedArray(),
        item
    )
)

fun <Id, T> Storage<Map<Id, T>>.remove(id: Id): Unit = write(
    with(read().filter { it.key != id }) {
        this
    }
)

fun <Id, T> Storage<Map<Id, T>>.first(predicate: (Pair<Id, T>) -> Boolean): T = readAndFilter(predicate).values.first()

fun <T> Storage<Map<Int, T>>.nextId(): Int = read().keys.fold(1) { acc, next ->
    when {
        abs(acc - next) >= 2 -> min(acc, next) + 1
        else -> max(acc, next)
    }
}

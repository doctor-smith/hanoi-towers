package lib.optics.lens

import lib.maths.x
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


data class Storage<P>(
    val read: ()->P,
    val write: (P)->Unit
)
class  Read {companion object {

    infix fun <P> from(storage: Storage<P>): P = storage.read()

}}

infix fun <P> Any?.read(storage: Storage<P>): P = storage.read()

infix fun <P> Any?.write(p: P): (Storage<P>)->Unit = { storage -> storage.write(p)}

infix fun <P> ((Storage<P>)->Unit).to(storage: Storage<P>): Unit = this(storage)


fun <T> Storage<List<T>>.filter(predicate: (T)->Boolean): List<T> = read().filter { predicate(it) }

fun <T> Storage<List<T>>.remove(predicate: (T)->Boolean): Unit = write( read().filter { predicate(it) } )


fun <T> Storage<List<T>>.add(item: T): Unit = write(listOf(
    *read().toTypedArray(),
    item
))

fun <Id, T> Storage<Map<Id, T>>.readAndFilter(predicate: (Pair<Id, T>)->Boolean): Map<Id,T> = read().filter { s ->  predicate(Pair(s.key, s.value)) }

fun <Id, T> Storage<Map<Id, T>>.put(item: Pair<Id,T>): Unit = write(mapOf(
    *read().map { it.key x it.value }.toTypedArray(),
    item
))

fun <Id, T> Storage<Map<Id, T>>.remove(id: Id): Unit = write(
    with(read().filter { it.key != id }) {
        this
    }
)

fun <Id, T> Storage<Map<Id, T>>.first(predicate: (Pair<Id, T>)->Boolean): T = readAndFilter(predicate).values.first()

fun <T> Storage<Map<Int, T>>.nextId(): Int = read().keys.fold(1){ acc, next -> when{
    abs(acc-next) >= 2 -> min(acc,next) +1
    else -> max(acc, next)
} }


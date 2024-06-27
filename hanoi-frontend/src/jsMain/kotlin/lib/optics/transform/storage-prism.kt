package lib.optics.transform

import lib.optics.prism.Either
import lib.optics.prism.Prism
import lib.optics.storage.Storage
import lib.optics.storage.add
import lib.optics.storage.put

fun <Id, T> Storage<Map<Id, T>>.prism(): Prism<Id, T, Id, Pair<Id, T>> = Prism(
    {id ->  with(read()[id]){
        when(this){
            null -> Either.Left(id)
            else -> Either.Right(this)
        }
    } },
    {pair ->
        put(pair)
        pair.second
    }
)

fun <T> Storage<List<T>>.prism(): Prism<Int, T, Int, T> = Prism(
    {index -> try {
        Either.Right(read()[index]!!)
    }catch (exception: Exception) {
        Either.Left(index)
    }},
    { t ->
        add(t)
        t
    }
)

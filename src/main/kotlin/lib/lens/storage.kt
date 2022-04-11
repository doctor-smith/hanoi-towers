package lib.lens


data class Storage<P>(
    val read: ()->P,
    val write: (P)->Unit
)
class  Read {companion object {

    infix fun <P> from(storage: Storage<P>): P = storage.read()

}}

infix fun <P> Any?.read(storage: Storage<P>): P = storage.read()

infix fun <P> Any?.write(p: P): (Storage<P>)->Unit = {storage -> storage.write(p)}

infix fun <P> ((Storage<P>)->Unit).to(storage: Storage<P>): Unit = this(storage)
package lib.lens


data class Storage<P>(
    val read: ()->P,
    val write: (P)->Unit
)
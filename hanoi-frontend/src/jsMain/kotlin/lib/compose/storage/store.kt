package lib.compose.storage

import androidx.compose.runtime.*
import hanoi.towers.api.*
import lib.compose.Markup
import lib.optics.storage.Storage


@Markup
@Composable
@Suppress("FunctionName")
fun <D> Store(buildStorage: @Composable ()->Storage<D>,content: @Composable Storage<D>.()->Unit) {
    buildStorage().content()
}
@Markup
@Composable
@Suppress("FunctionName")
fun <D> Store(
    initialData: D,
    buildStorage: @Composable (D)->Storage<D>,
    content: @Composable Storage<D>.()->Unit
) {
    buildStorage(initialData).content()
}
@Markup
@Composable
fun initialize(block:@Composable  ()->Unit) {
    block()
}

/*

Keep as ideas


@Composable
fun <Data> storage(initialData: Data): Storage<Data> {
    var data by remember {
        mutableStateOf(
            initialData
        )
    }
    return Storage<Data>(
        read = { data },
        write = { newData: Data -> data = newData }
    )
}

@Markup
@Composable
@Suppress("FunctionName")
fun <D> Store(
    initialData: D,
    content: @Composable Storage<D>.()->Unit
) = Store(
    initialData = initialData,
    buildStorage = {data -> storage(data)},
    content = content
)
*/
/*
@Markup
@Composable
@Suppress("FunctionName")
inline fun <reified Data> Store(
    hanoi.towers.data: Data,
    content: @Composable /*ElementScope<HTMLElement>.*/(read: ()->Data, write:  Data.()->Unit)->Unit,

) {
    var store by remember { mutableStateOf(hanoi.towers.data) }

    var set: Unit.(Data.()->Data)->Unit  by remember {
        mutableStateOf({
        store = store.it()
    })}

    var read by remember{ mutableStateOf({store}) }

    content(read){
        Unit.set{this}
    }
}
*/
/*
@Markup
@Composable
@Suppress("FunctionName")
inline fun <reified Data> Store(
    data: Data,
    crossinline content: @Composable  ElementScope<HTMLElement>.(Storage<Data>)->Unit,

    ) = Div{
        var store by remember { mutableStateOf(data) }

        val storage: Storage<Data> = Storage(
            {store},
            {data -> store = data}
        )
        content(storage)
    }
*/

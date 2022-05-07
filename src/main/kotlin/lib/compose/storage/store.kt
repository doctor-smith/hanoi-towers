package lib.compose.storage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import lib.compose.Markup
import lib.optics.lens.Storage
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.ElementScope
import org.w3c.dom.HTMLElement
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

package lib.compose.storage



import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import hanoi.towers.api.*
import hanoi.towers.data.AppData
import hanoi.towers.data.hanoi.Hanoi
import hanoi.towers.data.hanoi.Moves
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import lib.compose.Markup
import lib.compose.modal.Modals
import lib.language.Block
import lib.language.Lang
import lib.language.LanguageP
import lib.optics.storage.Storage
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.ElementScope
import org.w3c.dom.HTMLElement

fun <D> Store( content: @Composable Storage<D>.()->Unit) {

}
@Composable
fun Storage(): Storage<AppData> {
    var numberOfSlices by remember{ mutableStateOf(0) }
    var moves by remember{ mutableStateOf( Moves() ) }
    var hanoi by remember { mutableStateOf(Hanoi()) }
    var indexOfCurrentMove by remember { mutableStateOf(0) }
    var numberOfMoves by remember { mutableStateOf(0) }
    var isComputingMoves by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(false) }
    var movesPerSecond by remember { mutableStateOf(4) }
    var error by remember { mutableStateOf<String?>(null) }
    var locale by remember { mutableStateOf(readLang() ?:"de") }
    var locales by remember { mutableStateOf(listOf<String>()) }
    var language by remember { mutableStateOf<Lang>( Block("de", listOf()) ) }
    var isCookieDisclaimerConfirmed by remember { mutableStateOf(
        with(readCookie()) {
            this != null
        }
    ) }
    var modals by remember { mutableStateOf<Modals<Int>>( mapOf()) }

    val storage = Storage<AppData> (
        {
            AppData(
                numberOfSlices,
                moves,
                hanoi,
                indexOfCurrentMove,
                numberOfMoves,
                isComputingMoves,
                isPlaying,
                movesPerSecond,
                isCookieDisclaimerConfirmed,
                locale,
                locales,
                language,
                modals,
                error
            )
        },
        {data ->
            numberOfSlices = data.numberOfSlices
            moves = data.moves
            hanoi = data.hanoi
            indexOfCurrentMove = data.indexOfCurrentMove
            numberOfMoves = data.numberOfMoves
            isComputingMoves = data.isComputingMoves
            isPlaying = data.isPlaying
            if(isCookieDisclaimerConfirmed != data.isCookieDisclaimerConfirmed) {
                if(data.isCookieDisclaimerConfirmed) {
                    CoroutineScope(Job()).launch {
                        writeCookie()
                    }
                }
            }
            isCookieDisclaimerConfirmed = data.isCookieDisclaimerConfirmed
            movesPerSecond = data.movesPerSecond
            modals = data.modals
            error = data.error

            if(data.locale != locale) {
                CoroutineScope(Job()).launch {
                    try {
                        with(LanguageP().run(i18n(data.locale)).result) {
                            if(this != null) {
                                locale = data.locale
                                writeLang(data.locale)
                                language = this
                            }
                        }
                    } catch (exception: Exception) {
                        console.log(exception)
                    }
                }
            }
        }
    )
    return storage
}

/*
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
package hanoi.towers.application

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import hanoi.towers.component.Body
import hanoi.towers.data.AppData
import hanoi.towers.data.Hanoi
import hanoi.towers.data.Moves
import hanoi.towers.language.De
import hanoi.towers.language.En
import lib.compose.Markup
import lib.language.Language
import lib.lens.Storage
import org.jetbrains.compose.web.renderComposable


@Markup
@Suppress("FunctionName")
fun Application() = renderComposable(rootElementId = "root") {
    var numberOfSlices by remember{ mutableStateOf(0) }
    var moves by remember{ mutableStateOf( Moves() ) }
    var hanoi by remember { mutableStateOf(Hanoi()) }
    var indexOfCurrentMove by remember { mutableStateOf(0) }
    var numberOfMoves by remember { mutableStateOf(0) }
    var isComputingMoves by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(false) }
    var movesPerSecond by remember { mutableStateOf(4) }
    var error by remember { mutableStateOf<String?>(null) }
    var locale by remember { mutableStateOf("de") }
    var language by remember { mutableStateOf<Language>( De ) }
    val languages = mapOf(
        "de" to De,
        "en" to En
    )
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
                locale,
                language,
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
            movesPerSecond = data.movesPerSecond
            error = data.error

            if(data.locale != locale) {
                try {
                    language = languages[locale]!!
                    locale = data.locale
                } catch (exception: Exception) {
                    console.log(exception)
                }
            }
        }
    )
    Body(storage)
}
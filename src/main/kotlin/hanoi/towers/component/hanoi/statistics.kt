package hanoi.towers.component.hanoi

import androidx.compose.runtime.Composable
import lib.compose.Markup
import lib.language.Lang
import lib.language.get
import lib.optics.storage.Storage
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Markup
@Composable
@Suppress("FunctionName")
fun Statistics(
    numberOfSlices: Storage<Int>,
    numberOfMoves: Storage<Int>,
    texts: Lang.Block
) {
    P {
        Text(
            texts["numberOfNecessaryMoves"]
                .replace("__NUMBER_OF_SLICES__", "${numberOfSlices.read()}")
                .replace("__NUMBER_OF_MOVES__", "${numberOfMoves.read()}")
        )
    }
}
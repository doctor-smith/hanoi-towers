package hanoi.towers.page.game

import androidx.compose.runtime.Composable
import hanoi.towers.component.hanoi.*
import hanoi.towers.component.layout.Flex
import hanoi.towers.data.*
import hanoi.towers.data.hanoi.Mode
import hanoi.towers.maxNumberOfSlices
import lib.compose.Markup
import lib.language.Lang
import lib.language.get
import lib.language.of
import lib.optics.storage.Storage
import lib.optics.transform.times
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text


@Markup
@Composable
@Suppress("FunctionName")
fun GamePage(storage: Storage<AppData>, texts: Lang.Block) {


    H1 { Text(texts["headline"]) }

    Form(
        Mode.Play,
        storage * numberOfSlicesGameLens,
        storage * numberOfMovesGameLens,
        storage * hanoiGameLens,
        storage * movesLens,
        storage * isComputingMovesLens,
        storage * indexOfCurrentMoveLens,
        storage * errorLens,
        "form" of texts,
        maxNumberOfSlices
    )

    Flex {
        HanoiGame(
            storage * movesLens,
            storage * hanoiGameLens,
            "visualization" of texts
        )
    }
}
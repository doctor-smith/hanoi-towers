package hanoi.towers.page.mainpage

import androidx.compose.runtime.Composable
import hanoi.towers.component.Flex
import hanoi.towers.component.Form
import hanoi.towers.component.HanoiVisualization
import hanoi.towers.component.ListOfMoves
import hanoi.towers.component.Statistics
import hanoi.towers.data.*
import hanoi.towers.maxNumberOfSlices
import lib.compose.Markup
import lib.language.Block
import lib.language.component
import lib.language.get
import lib.language.of
import lib.lens.Storage
import lib.lens.times
import org.jetbrains.compose.web.dom.*

@Markup
@Composable
@Suppress("FunctionName")
fun MainPage(storage: Storage<AppData>, texts: Block) {

    H1 { Text(texts["headline"]) }

    Form(
        storage * numberOfSlicesLens,
        storage * numberOfMovesLens,
        storage * hanoiLens,
        storage * movesLens,
        storage * isComputingMovesLens,
        storage * indexOfCurrentMoveLens,
        storage * errorLens,
        "form" of texts,
        maxNumberOfSlices
    )

    Statistics(
        storage * numberOfSlicesLens,
        storage * numberOfMovesLens,
        "statistics" of texts
    )

    Flex {
        ListOfMoves(
            storage * movesLens,
            storage * isComputingMovesLens,
            "listOfMoves" of texts
        )
        HanoiVisualization(
            storage * movesLens,
            storage * hanoiLens,
            storage * numberOfMovesLens,
            storage * indexOfCurrentMoveLens,
            storage * movesPerSecondLens,
            storage * isPlayingLens,
            "visualization" of texts
        )
    }
}
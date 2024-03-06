package hanoi.towers.page.solver


import androidx.compose.runtime.Composable
import hanoi.towers.component.hanoi.Form
import hanoi.towers.component.hanoi.HanoiVisualization
import hanoi.towers.component.hanoi.ListOfMoves
import hanoi.towers.component.hanoi.Statistics
import hanoi.towers.component.layout.Flex
import hanoi.towers.data.*
import hanoi.towers.data.hanoi.Mode
import hanoi.towers.maxNumberOfSlices
import lib.compose.Markup
import lib.language.Block
import lib.language.get
import lib.language.of
import lib.optics.storage.Storage
import lib.optics.transform.times
import org.jetbrains.compose.web.dom.*

@Markup
@Composable
@Suppress("FunctionName")
fun SolverPage(storage: Storage<AppData_Old>, texts: Block) {

    H1 { Text(texts["headline"]) }

    Form(
        Mode.Automatic,
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
package hanoi.towers.page.solver


import androidx.compose.runtime.Composable
import hanoi.towers.component.hanoi.Form
import hanoi.towers.component.hanoi.HanoiVisualization
import hanoi.towers.component.hanoi.ListOfMoves
import hanoi.towers.component.hanoi.Statistics
import hanoi.towers.component.layout.Flex
import hanoi.towers.data.*
import hanoi.towers.data.hanoi.Mode
import hanoi.towers.data.pages.solver.*
import hanoi.towers.data.pages.solver.component.hanoi
import hanoi.towers.maxNumberOfSlices
import lib.compose.Markup
import lib.language.component
import lib.language.get
import lib.language.of
import lib.optics.storage.Storage
import lib.optics.transform.times
import org.jetbrains.compose.web.dom.*

@Markup
@Composable
@Suppress("FunctionName")
fun SolverPage(storage: Storage<HanoiSolverPage>) {

    val texts = (storage * texts).read().component("hanoi.solverPage")

    H1 { Text(texts["headline"]) }

    Form(
        Mode.Automatic,
        storage * numberOfSlices,
        storage * numberOfMoves,
        storage * solver * hanoi,
        storage * moves,
        storage * isComputingMoves,
        storage * indexOfCurrentMove,
        storage * error,
        "form" of texts,
        maxNumberOfSlices
    )

    Statistics(
        storage * numberOfSlices,
        storage * numberOfMoves,
        "statistics" of texts
    )

    Flex {
        ListOfMoves(
            storage * moves,
            storage * isComputingMoves,
            "listOfMoves" of texts
        )
        HanoiVisualization(
            storage * moves,
            storage * solver * hanoi,
            storage * numberOfMoves,
            storage * indexOfCurrentMove,
            storage * movesPerSecond,
            storage * isPlaying,
            "visualization" of texts
        )
    }
}

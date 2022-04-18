package hanoi.towers.component

import androidx.compose.runtime.Composable
import hanoi.towers.data.*
import lib.compose.Markup
import lib.lens.Storage
import lib.lens.times
import hanoi.towers.maxNumberOfSlices
import lib.language.get
import org.jetbrains.compose.web.dom.*

@Markup
@Composable
@Suppress("FunctionName")
fun Body(storage: Storage<AppData>) {

    val texts = (storage * languageLens).read()

    Container{
        NavBar(
            storage * localesLens,
            storage * localeLens,
            texts
        )
        H1 { Text(texts["hanoi.mainPage.headline"]) }

        Form(
            storage * numberOfSlicesLens,
            storage * numberOfMovesLens,
            storage * hanoiLens,
            storage * movesLens,
            storage * isComputingMovesLens,
            storage * indexOfCurrentMoveLens,
            storage * errorLens,
            maxNumberOfSlices
        )
        OnError(storage * errorLens)

        P{ Text("Anzahl der nötigen Züge: 2^${storage.read().numberOfSlices} -1 = ${storage.read().numberOfMoves}") }
        Flex {
            ListOfMoves(
                storage * movesLens,
                storage * isComputingMovesLens
            )
            HanoiVisualization(
                storage * movesLens,
                storage * hanoiLens,
                storage * numberOfMovesLens,
                storage * indexOfCurrentMoveLens,
                storage * movesPerSecondLens,
                storage * isPlayingLens
            )
        }
    }
}
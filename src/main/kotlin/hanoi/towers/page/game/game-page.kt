package hanoi.towers.page.game

import androidx.compose.runtime.Composable
import hanoi.towers.component.hanoi.*
import hanoi.towers.component.layout.Flex
import hanoi.towers.data.*
import hanoi.towers.data.hanoi.Mode
import hanoi.towers.data.pages.game.*
import hanoi.towers.data.pages.game.component.hanoi
import hanoi.towers.maxNumberOfSlices
import lib.compose.Markup
import lib.language.component
import lib.language.get
import lib.language.of
import lib.optics.storage.Storage
import lib.optics.transform.times
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text


@Markup
@Composable
@Suppress("FunctionName")
fun GamePage(storage: Storage<HanoiGamePage>) {

    val texts = (storage * texts).read().component("hanoi.gamePage")

    H1 { Text(texts["headline"]) }

    Form(
        Mode.Play,
        storage * numberOfSlices,
        storage * numberOfMoves,
        storage * game * hanoi,
        storage * moves,
        storage * isComputingMoves,
        storage * indexOfCurrentMove,
        storage * error,
        "form" of texts,
        maxNumberOfSlices
    )

    Flex {
        HanoiGame(
            storage * game,
            "visualization" of texts
        )
    }
}
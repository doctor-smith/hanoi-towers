package hanoi.towers.page.cheat

import androidx.compose.runtime.Composable
import hanoi.towers.component.hanoi.*
import hanoi.towers.component.layout.Flex
import hanoi.towers.data.*
import hanoi.towers.data.hanoi.Mode
import hanoi.towers.data.pages.cheat.*
import hanoi.towers.data.pages.cheat.component.hanoi
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
fun CheatPage(storage: Storage<HanoiCheatPage>) {

    val texts = (storage * texts).read().component("hanoi.cheatPage")

    H1 { Text(texts["headline"]) }

    Form(
        Mode.Cheat,
        storage * numberOfSlices,
        storage * numberOfMoves,
        storage * cheat * hanoi,
        storage * moves,
        storage * isComputingMoves,
        storage * indexOfCurrentMove,
        storage * error,
        "form" of texts,
        maxNumberOfSlices
    )

    Flex {
        HanoiCheat(
            storage * cheat,
            "visualization" of texts
        )
    }
}
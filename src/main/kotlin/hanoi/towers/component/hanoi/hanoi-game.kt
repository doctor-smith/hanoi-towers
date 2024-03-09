package hanoi.towers.component.hanoi

import androidx.compose.runtime.Composable
import hanoi.towers.data.hanoi.Mode
import hanoi.towers.data.hanoi.Tower.*
import hanoi.towers.data.pages.game.HanoiGame
import hanoi.towers.data.pages.game.hanoi
import hanoi.towers.data.pages.game.maxNumberOfSlices
import hanoi.towers.data.towerLens
import lib.compose.Markup
import lib.language.Lang
import lib.language.get
import lib.optics.storage.Storage
import lib.optics.transform.times
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Text

@Markup
@Composable
@Suppress("FunctionName")
fun HanoiGame(
    hanoiGame: Storage<HanoiGame>,
    texts: Lang.Block,
) = Div({
    style {
        flex(1)
        width(75.percent)
        paddingLeft(10.px)
    }
}) {
    H3 { Text(texts["headline"]) }

    Hanoi(
        hanoiGame * hanoi * towerLens(One),
        hanoiGame * hanoi * towerLens(Two),
        hanoiGame * hanoi * towerLens(Three),
        capacity = (hanoiGame * maxNumberOfSlices).read(),
        mode = Mode.Play
    )
}
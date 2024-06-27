package hanoi.towers.component.hanoi

import androidx.compose.runtime.Composable
import hanoi.towers.data.hanoi.Mode
import hanoi.towers.data.hanoi.Tower.*
import hanoi.towers.data.pages.cheat.component.HanoiCheat

import hanoi.towers.data.pages.cheat.component.hanoi
import hanoi.towers.data.tower
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
fun HanoiCheat(
    hanoiCheat: Storage<HanoiCheat>,
    texts: Lang.Block,
    maxNumberOfSlices: Int = 10
) = Div({
    style {
        flex(1)
        width(75.percent)
        paddingLeft(10.px)
    }
}) {
    H3 { Text(texts["headline"]) }

    Hanoi(
        hanoiCheat * hanoi * tower(One),
        hanoiCheat * hanoi *tower(Two),
        hanoiCheat * hanoi * tower(Three),
        capacity = maxNumberOfSlices,
        mode = Mode.Cheat
    )
}

package hanoi.towers.data.pages.cheat.component

import hanoi.towers.data.hanoi.Hanoi
import lib.language.Lang
import lib.optics.lens.Lens


data class HanoiCheat(
    val hanoi: Hanoi,
    val texts: Lang,
    val maxNumberOfSlices: Int = 10
)

val hanoi = Lens<HanoiCheat, Hanoi>(
    {whole -> whole.hanoi},
    {part: Hanoi -> {whole -> whole.copy(hanoi = part)}}
)

val texts = Lens<HanoiCheat, Lang>(
    {whole -> whole.texts},
    {part: Lang -> {whole -> whole.copy(texts = part)}}
)

val maxNumberOfSlices = Lens<HanoiCheat, Int>(
    {whole -> whole.maxNumberOfSlices},
    {part: Int -> {whole -> whole.copy(maxNumberOfSlices = part)}}
)
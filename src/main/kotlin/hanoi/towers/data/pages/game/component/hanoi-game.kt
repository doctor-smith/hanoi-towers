package hanoi.towers.data.pages.game.component

import hanoi.towers.data.hanoi.Hanoi
import lib.language.Lang
import lib.optics.lens.Lens


data class HanoiGame(
    val hanoi: Hanoi,
    val texts: Lang,
    val maxNumberOfSlices: Int = 10
)

val hanoi = Lens<HanoiGame, Hanoi>(
    {whole -> whole.hanoi},
    {part: Hanoi -> {whole -> whole.copy(hanoi = part)}}
)

val texts = Lens<HanoiGame, Lang>(
    {whole -> whole.texts},
    {part: Lang -> {whole -> whole.copy(texts = part)}}
)

val maxNumberOfSlices = Lens<HanoiGame, Int>(
    {whole -> whole.maxNumberOfSlices},
    {part: Int -> {whole -> whole.copy(maxNumberOfSlices = part)}}
)
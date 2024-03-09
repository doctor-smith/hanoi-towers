package hanoi.towers.data.pages

import hanoi.towers.data.hanoi.Hanoi
import lib.language.Lang


data class HanoiCheat(
    val numberOfSlices: Int,
    val hanoi: Hanoi,
    val texts: Lang,
)
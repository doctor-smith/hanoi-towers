package hanoi.towers.data.pages

import hanoi.towers.data.pages.cheat.component.HanoiCheat
import hanoi.towers.data.pages.game.component.HanoiGame
import hanoi.towers.data.pages.solver.component.HanoiSolver
import lib.language.Lang


data class Pages(
    val hanoiSolver: HanoiSolver,
    val hanoiGame: HanoiGame,
    val hanoiCheat: HanoiCheat,
    val texts: Lang,
)




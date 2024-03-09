package hanoi.towers.data.pages

import hanoi.towers.data.pages.cheat.HanoiCheat
import hanoi.towers.data.pages.game.HanoiGame
import hanoi.towers.data.pages.solver.HanoiSolver
import lib.language.Lang


data class Pages(
    val hanoiSolver: HanoiSolver,
    val hanoiGame: HanoiGame,
    val hanoiCheat: HanoiCheat,
    val texts: Lang,
)




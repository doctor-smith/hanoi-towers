package hanoi.towers.language

import lib.language.Language

object En : Language {
    override val locale: String = "en"

    object MainPage {
        const val title = "Towers of Hanoi"

        const val headline = "The Towers of Hanoi"

        object Form {
            const val towerHeight = "Tower height"
        }

        object Stats {
            const val numberOfNecessaryMoves = "Minimal number of necessary moves"
        }

        object ListOfMoves {
            const val headline = "List ov moves"

            const val hint = "Chose the number of towers, please"
        }

        object Visualization {
            const val headline = "Visualization"
        }

        object Error {
            const val limitedNumberOfSlices = "The number of slices is limited to __MAX_NUMBER_OF_SLICES__"

            const val numberOfSlicesMustBeNonNegative = "The number of slices must be non-negative"

        }
    }
}
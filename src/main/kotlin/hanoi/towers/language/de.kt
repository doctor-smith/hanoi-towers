package hanoi.towers.language

import lib.language.Language

object De : Language {
    override val locale: String = "de"

    object MainPage {
        const val title = "Türmme von Hanoi"

        const val headline = "Die Türmme von Hanoi"

        object Form {
            const val towerHeight = "Turmhöhe"
        }

        object Stats {
            const val numberOfNecessaryMoves = "Anzahl der nötigen Züge"
        }

        object ListOfMoves {
            const val headline = "Spielzüge"

            const val hint = "Bitte wähle eine Turmhöhe > 0"
        }

        object Visualization {
            const val headline = "Visualisierung"
        }

        object Error {
            const val limitedNumberOfSlices = "Mehr als __MAX_NUMBER_OF_SLICES__ Scheiben sind nicht erlaubt."

            const val numberOfSlicesMustBeNonNegative = "Die Turmhöhe muss nicht-negativ sein."

        }
    }
}
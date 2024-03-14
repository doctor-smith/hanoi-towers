package hanoi.towers.data.navigation

import hanoi.towers.data.i18n.I18N
import lib.optics.lens.Lens

data class NavBar(
    val i18n: I18N,
)

val i18n = Lens<NavBar, I18N>(
    {whole -> whole.i18n},
    {part -> {whole -> whole.copy(i18n = part)}}
)
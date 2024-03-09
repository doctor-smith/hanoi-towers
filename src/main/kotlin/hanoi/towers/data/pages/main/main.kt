package hanoi.towers.data.pages.main

import lib.language.Lang
import lib.optics.lens.Lens


data class Main(
    val texts: Lang,
)

val texts =  Lens<Main, Lang>(
    {whole -> whole.texts},
    {part -> {whole -> whole.copy(texts = part)}}
)


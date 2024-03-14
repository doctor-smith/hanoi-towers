package hanoi.towers.data.i18n

import lib.language.Lang
import lib.optics.lens.Lens

data class I18N(
    val locale: String,
    val locales: List<String>,
    val language: Lang,
)

val locale: Lens<I18N, String> = Lens(
    {data -> data.locale},
    {part -> {whole -> whole.copy(locale = part)}}
)

val locales: Lens<I18N, List<String>> = Lens(
    {data -> data.locales},
    {part -> {whole -> whole.copy(locales = part)}}
)

val language: Lens<I18N, Lang> = Lens(
    {data -> data.language},
    {part -> {whole -> whole.copy(language = part)}}
)
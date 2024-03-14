package hanoi.towers.data.ui

import androidx.compose.runtime.Composable
import hanoi.towers.data.cookies.Cookies
import hanoi.towers.data.i18n.I18N
import hanoi.towers.data.pages.Pages
import lib.optics.lens.Lens
import org.jetbrains.compose.web.dom.ElementScope
import org.w3c.dom.HTMLElement

data class UI(
    val pages: Pages,
    val cookies: Cookies,
    val i18n: I18N,
    val modals: Map<Int, @Composable ElementScope<HTMLElement>.() -> Unit>,
    val error: String?
)

val pages: Lens<UI, Pages> = Lens(
    {whole -> whole.pages},
    {part -> {whole -> whole.copy(pages = part)}}
)

val cookies: Lens<UI, Cookies> = Lens(
    {whole -> whole.cookies},
    {part -> {whole -> whole.copy(cookies = part)}}
)

val i18N: Lens<UI, I18N> = Lens(
    {whole -> whole.i18n},
    {part -> {whole -> whole.copy(i18n = part)}}
)

val modals: Lens<UI, Map<Int, @Composable ElementScope<HTMLElement>.() -> Unit>> = Lens(
    {whole -> whole.modals},
    {part -> {whole -> whole.copy(modals = part)}}
)

val error: Lens<UI, String?> = Lens(
    {whole -> whole.error},
    {part -> {whole -> whole.copy(error = part)}}
)


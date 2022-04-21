package hanoi.towers.component

import androidx.compose.runtime.Composable
import lib.compose.Markup
import lib.compose.Modal
import lib.language.Block
import lib.language.component
import lib.language.get
import lib.lens.*
import lib.maths.x
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.ElementScope
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLElement

@Markup
@Composable
@Suppress("FunctionName")
fun CookieDisclaimer(
    texts: Block,
    modals: Storage<Map<Int,@Composable ElementScope<HTMLElement>.() -> Unit>>,
    isCookieDisclaimerConfirmed: Storage<Boolean>
) = Div {
    if (!isCookieDisclaimerConfirmed.read()) {
        with(modals.nextId()) {
            val id = this
            modals.put(
                id x CookieDisclaimerModal(
                    id,
                    texts,
                    modals,
                    isCookieDisclaimerConfirmed
                )
            )
        }
    }
}

@Markup
@Suppress("FunctionName")
fun CookieDisclaimerModal(
    id: Int,
    texts: Block,
    modals: Storage<Map<Int,@Composable ElementScope<HTMLElement>.() -> Unit>>,
    isCookieDisclaimerConfirmed: Storage<Boolean>
): @Composable ElementScope<HTMLElement>.()->Unit = Modal(
    id,
    texts,
    onOk = {
        isCookieDisclaimerConfirmed.write(true)
    },
    onCancel = null,
    close = { modals.remove(id) }
) {
    Div {
        with(texts.component("content")) {
            Text(this["hint"])
        }
    }
}

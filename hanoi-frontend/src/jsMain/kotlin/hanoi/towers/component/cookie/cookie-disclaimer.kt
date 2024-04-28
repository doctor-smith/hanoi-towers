package hanoi.towers.component.cookie

import androidx.compose.runtime.Composable
import lib.compose.Markup
import lib.compose.modal.Modal
import lib.compose.modal.Modals
import lib.language.Block
import lib.language.component
import lib.language.get
import lib.maths.x
import lib.optics.storage.Storage
import lib.optics.storage.nextId
import lib.optics.storage.put
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.ElementScope
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLElement

@Markup
@Composable
@Suppress("FunctionName")
fun CookieDisclaimer(
    texts: Block,
    modals: Storage<Modals<Int>>,
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
    modals: Storage<Modals<Int>>,
    isCookieDisclaimerConfirmed: Storage<Boolean>
): @Composable ElementScope<HTMLElement>.() -> Unit = Modal(
    id,
    modals,
    onOk = {
        isCookieDisclaimerConfirmed.write(true)
    },
    onCancel = null,
    texts = texts
) {
    Div {
        with(texts.component("content")) {
            Text(this["hint"])
        }
    }
}

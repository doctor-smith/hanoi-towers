package hanoi.towers.component

import androidx.compose.runtime.Composable
import hanoi.towers.component.cookie.CookieDisclaimer
import hanoi.towers.component.layout.Container
import hanoi.towers.data.*
import lib.compose.Markup
import lib.optics.storage.Storage
import lib.optics.transform.times
import hanoi.towers.page.mainpage.MainPage
import hanoi.towers.page.testpage.TestPage
import hanoi.towers.page.testpage.TestStorageComponent
import kotlinx.browser.document
import lib.compose.modal.ModalLayer
import lib.compose.routing.Routing
import lib.language.*

@Markup
@Composable
@Suppress("FunctionName")
fun UI(storage: Storage<AppData>) {

    val texts = (storage * languageLens).read() as Block
    val mainPageTexts = texts.component("hanoi.mainPage")

    document.title = mainPageTexts["title"]

    ModalLayer(
        1000,
        storage * modalsLens,
    true
    ) {
        CookieDisclaimer(
            texts.component("hanoi.cookieDisclaimer"),
            storage * modalsLens,
            storage * isCookieDisclaimerConfirmedLens
        )

        Container {
            NavBar(
                storage * localesLens,
                storage * localeLens,
                texts.component("hanoi.locales")
            )
            Routing("/") {
                component {
                    MainPage(storage, mainPageTexts)
                }
                route("test") {
                    route("loader") {
                        component {
                            TestPage()
                        }
                    }
                    route("storage") {
                        component{
                            TestStorageComponent()
                        }
                    }
                }
            }
        }
    }
}
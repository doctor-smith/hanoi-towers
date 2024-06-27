@file:Suppress("MagicNumber")
package hanoi.towers.application

import androidx.compose.runtime.Composable
import hanoi.towers.component.cookie.CookieDisclaimer
import hanoi.towers.component.dev.onDev
import hanoi.towers.component.layout.Container
import hanoi.towers.component.navigation.NavBar
import hanoi.towers.data.*
import hanoi.towers.page.cheat.CheatPage
import hanoi.towers.page.game.GamePage
import hanoi.towers.page.mainpage.MainPage
import hanoi.towers.page.solver.SolverPage
import hanoi.towers.page.testpage.DragDropTestPage
import hanoi.towers.page.testpage.LoadingSpinnerTestPage
import hanoi.towers.page.testpage.TestBackend
import hanoi.towers.page.testpage.TestStorageComponent
import kotlinx.browser.document
import lib.compose.Markup
import lib.compose.modal.ModalLayer
import lib.compose.routing.Routing
import lib.language.*
import lib.optics.storage.Storage
import lib.optics.storage.read
import lib.optics.transform.times

@Markup
@Composable
@Suppress("FunctionName")
fun UI(storage: Storage<AppData>) {

    val texts = (storage * language).read() as Block
    val mainPageTexts = texts.component("hanoi.mainPage")
    //val environment = (storage * env).read()
    document.title = mainPageTexts["title"]

    // The whole UI needs to be wrapped in a component
    // which is able to handle the interactive controlflow of the application,
    // namely: dialogs, cookie-disclaimers errors, etc
    // Note: Routing is done in the main container just below the navigation section
    ModalLayer(
        1000,
        storage * modals,
        true
    ) {
        // The Cookie disclaimer pops up, whenever as user
        // visits the page for the first time or cleared the cookies
        CookieDisclaimer(
            texts.component("hanoi.cookieDisclaimer"),
            storage * modals,
            storage * isCookieDisclaimerConfirmed
        )

        Container {
            // Top navigation bar
            NavBar( storage * navBar)

            // Routing section
            // Here, routes are mapped to components / pages
            Routing("/") {
                // Route to the Hanoi Towers Main / Welcome Page
                component {
                    MainPage(storage * main)
                }
                // Route to the Hanoi Towers Solver Page.
                route("solver") {
                    component{
                        SolverPage(storage * hanoiSolverPage)
                    }
                }
                // Route to the Hanoi Towers Game Page.
                // Here one can play the Game Hanoi Towers
                route("game") {
                    component {
                        GamePage(storage * hanoiGamePage)
                    }
                }
                // Route to the Hanoi Towers Cheat Page.
                // This page might be used to illustrate
                // strategies to solve the game
                route("cheat") {
                    component {
                        CheatPage(storage * hanoiCheatPage)
                    }
                }
                // routes for test-purposes
                // TODO (These routes shall not be available in production)
                //onDev(storage * env) {
                if((storage * env).read().environment == "DEV"){
                    route("test") {
                        route("loader") {
                            component {
                                LoadingSpinnerTestPage()
                            }
                        }
                        route("storage") {
                            component {
                                TestStorageComponent()
                            }
                        }
                        route("drag-and-drop") {
                            component {
                                DragDropTestPage()
                            }
                        }
                        route("backend") {
                            component{
                                TestBackend()
                            }
                        }
                    }
                }
            }
        }
    }
}

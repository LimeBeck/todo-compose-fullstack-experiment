import androidx.compose.runtime.mutableStateOf
import app.softwork.routingcompose.HashRouter
import common.BaseStyles
import common.Theme
import common.ThemeProvider
import common.applyStyle
import components.Layout
import components.PageContent
import components.PageFooter
import components.PageHeader
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Style
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposableInBody
import pages.HomePage

fun main() {
    renderComposableInBody {

        Style {
            root {
                applyStyle(ThemeProvider.theme)
            }
        }

        Style(BaseStyles)

        Layout {
            PageHeader {
                H1 { Text("LIMEBECK.DEV") }
                Div {
                    val themeState = mutableStateOf(false)
                    components.Switch("Theme", invertedColors = true, state = themeState) { checked ->
                        ThemeProvider.theme =
                            if (checked) {
                                Theme.Dark
                            } else {
                                Theme.Light
                            }
                    }
                }
            }
            PageContent {
                HashRouter("/") {
                    route("/") {
                        HomePage()
                    }
                }
            }
            PageFooter {
                Div { Text("Copyright by Limebeck (2024)") }
            }
        }
    }
}
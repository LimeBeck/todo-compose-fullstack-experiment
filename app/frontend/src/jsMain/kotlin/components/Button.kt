package components

import androidx.compose.runtime.Composable
import common.ThemeVariables
import org.jetbrains.compose.web.ExperimentalComposeWebApi
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.ElementScope
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLElement

internal object ButtonStyles : StyleSheet() {
    @OptIn(ExperimentalComposeWebApi::class)
    val button by style {
        display(DisplayStyle.InlineBlock)
        padding(10.px, 20.px)
        fontSize(16.px)
        fontWeight("bold")
        color(ThemeVariables.mainColor.value())
        backgroundColor(ThemeVariables.backgroundColor.value())
        border { style = LineStyle.None }
        borderRadius(8.px)
        property("box-shadow", "0 4px 8px rgba(0, 0, 0, 0.2)")
        cursor("pointer")
        transitions {
            "transform" { duration = 0.2.s }
            "box-shadow" { duration = 0.2.s }
        }
        textAlign("center")
        textDecoration("none")

        self + hover style {
            transform {
                translateY((-2).px)
            }
            property("box-shadow", "0 6px 12px rgba(0, 0, 0, 0.3)")
        }

        self + active style {
            transform {
                translateY(0.px)
            }
            property("box-shadow", "0 2px 4px rgba(0, 0, 0, 0.2)")
        }
    }
}

@Composable
fun Button(
    text: String,
    onClick: () -> Unit,
) {
    Button(onClick) { Text(text) }
}

@Composable
fun Button(
    onClick: () -> Unit,
    content: @Composable ElementScope<HTMLElement>.() -> Unit,
) {
    Style(ButtonStyles)
    org.jetbrains.compose.web.dom.Button(
        attrs = {
            classes(ButtonStyles.button)
            onClick {
                onClick.invoke()
            }
        },
    ) {
        content()
    }
}

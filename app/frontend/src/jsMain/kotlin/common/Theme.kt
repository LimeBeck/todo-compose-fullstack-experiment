package common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.StyleScope
import org.jetbrains.compose.web.css.StyleSheetBuilder

sealed class Theme(
    val mainColor: CSSColorValue,
    val accentColor: CSSColorValue,
    val backgroundColor: CSSColorValue,
) {
    data object Dark : Theme(
        mainColor = Color("#201e1f"),
        accentColor = Color("#1d7874"),
        backgroundColor = Color("#F8F4E3"),
    )

    data object Light : Theme(
        mainColor = Color("#F8F4E3"),
        accentColor = Color("#1d7874"),
        backgroundColor = Color("#201e1f"),
    )

    val opposite
        get() =
            when (this) {
                Light -> Dark
                Dark -> Light
            }
}

fun StyleScope.applyStyle(theme: Theme) {
    ThemeVariables.mainColor(theme.mainColor)
    ThemeVariables.accentColor(theme.accentColor)
    ThemeVariables.backgroundColor(theme.backgroundColor)
}

object ThemeProvider {
    var theme by mutableStateOf<Theme>(Theme.Light)
}

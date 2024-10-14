package components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import common.ThemeVariables
import kotlinx.uuid.UUID
import kotlinx.uuid.generateUUID
import org.jetbrains.compose.web.ExperimentalComposeWebApi
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.attributes.forId
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Label
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@OptIn(ExperimentalComposeWebApi::class)
internal class SwitchStyles(
    invertedColors: Boolean,
) : StyleSheet() {
    val animationDuration = 0.3.s

    val switch by style {
        position(Position.Relative)
        display(DisplayStyle.InlineBlock)
        width(60.px)
        height(34.px)
        margin(10.px)
    }

    val switchInput by style {
        opacity(0)
        width(0.px)
        height(0.px)
    }

    val switchSlider by style {
        position(Position.Absolute)
        cursor("pointer")
        top(0.px)
        left(0.px)
        right(0.px)
        bottom(0.px)
        backgroundColor(Color.transparent)
        border(
            2.px,
            LineStyle.Solid,
            if (invertedColors) {
                ThemeVariables.backgroundColor.value()
            } else {
                ThemeVariables.mainColor.value()
            },
        )
        transitions {
            "" { duration = animationDuration }
        }
        borderRadius(34.px)

        self + "::before" style {
            position(Position.Absolute)
            property("content", "\"\"")
            height(20.px)
            width(20.px)
            left(4.px)
            top(50.percent) // Центрируем по вертикали
            transform { translateY((-50).percent) } // Корректируем смещение
            backgroundColor(
                if (invertedColors) {
                    ThemeVariables.backgroundColor.value()
                } else {
                    ThemeVariables.mainColor.value()
                },
            )
            border(
                2.px,
                LineStyle.Solid,
                if (invertedColors) {
                    ThemeVariables.backgroundColor.value()
                } else {
                    ThemeVariables.mainColor.value()
                },
            )
            transitions {
                "" { duration = animationDuration }
            }
            borderRadius(50.percent)
        }
    }

    val switchLabel by style {
        display(DisplayStyle.InlineBlock)
        marginLeft(10.px)
        attr("vertical-align", "middle")
        fontSize(16.px)
    }

    init {
        (".$switchInput:checked + .$switchSlider") style {
            property(
                "border-color",
                if (invertedColors) {
                    ThemeVariables.backgroundColor.value()
                } else {
                    ThemeVariables.mainColor.value()
                },
            )
        }

        (".$switchInput:checked + .$switchSlider::before") style {
            transform {
                translateX(24.px)
                translateY((-50).percent)
            }
            property(
                "border-color",
                if (invertedColors) {
                    ThemeVariables.backgroundColor.value()
                } else {
                    ThemeVariables.mainColor.value()
                },
            )
        }

        (".$switchInput:disabled + .$switchSlider") style {
            property("borderColor", (rgb(224, 224, 224)))
            cursor("not-allowed")
        }

        (".$switchInput:disabled + .$switchSlider::before") style {
            backgroundColor(rgb(189, 189, 189))
            property("borderColor", (rgb(189, 189, 189)))
        }
    }
}

@Composable
fun Switch(
    label: String,
    disabled: Boolean = false,
    invertedColors: Boolean = false,
    state: MutableState<Boolean> = mutableStateOf(false),
    onSwitchChanged: (newValue: Boolean) -> Unit = {},
) {
    val styles = remember { SwitchStyles(invertedColors = invertedColors) }
    Style(styles)

    val inputId = UUID.generateUUID().toString()
    Label(
        attrs = {
            classes(styles.switch)
        },
    ) {
        Input(type = InputType.Checkbox, attrs = {
            id(inputId)
            if (disabled) {
                disabled()
            }
            classes(styles.switchInput)
            checked(state.value)
            onChange {
                state.value = it.value
                onSwitchChanged(state.value)
            }
        })
        Span(
            attrs = {
                classes(styles.switchSlider)
            },
        )
    }

    Label(
        attrs = {
            forId(inputId)
            classes(styles.switchLabel)
        },
    ) {
        Text(label)
    }
}

package common

import org.jetbrains.compose.web.css.*

object ThemeVariables {
    val backgroundColor by variable<CSSColorValue>()
    val mainColor by variable<CSSColorValue>()
    val accentColor by variable<CSSColorValue>()
}

object BaseStyles : StyleSheet() {
    init {
        "#root" style {
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Column)
            minHeight(100.vh)
            color(ThemeVariables.mainColor.value())
            backgroundColor(ThemeVariables.backgroundColor.value())
        }
    }

    val header by style {
        backgroundColor(ThemeVariables.accentColor.value())
        color(ThemeVariables.backgroundColor.value())
        flex(0, 0, 60.px)
        display(DisplayStyle.Flex)
        alignItems(AlignItems.Center)
        justifyContent(JustifyContent.SpaceBetween)
        fontSize(1.5.cssRem)
        padding(10.px)
    }

    val main by style {
        flex(1)
        display(DisplayStyle.Flex)
        alignItems(AlignItems.Center)
        justifyContent(JustifyContent.Center)
        padding(20.px)
        backgroundColor(ThemeVariables.backgroundColor.value())
    }

    val footer by style {
        flex(0, 0, 40.px)
        display(DisplayStyle.Flex)
        alignItems(AlignItems.Center)
        justifyContent(JustifyContent.Center)
        fontSize(1.cssRem)
        property("border-top", "2px solid")
        property("border-left", "none")
        property("border-right", "none")
        property("border-bottom", "none")
    }

    val container by style {
        backgroundColor(ThemeVariables.backgroundColor.value())
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        flexGrow(1)
        justifyContent(JustifyContent.Center)
        alignItems(AlignItems.Center)
        maxWidth(800.px)
        width(100.percent)
        padding(20.px)
    }
}

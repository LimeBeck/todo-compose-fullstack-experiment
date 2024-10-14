package components

import common.BaseStyles
import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.HTMLElement

object AppLayoutContext

@Composable
fun Layout(content: @Composable AppLayoutContext.() -> Unit) {
    with(AppLayoutContext) {
        content()
    }
}

@Composable
fun AppLayoutContext.PageHeader(
    attrs: AttrBuilderContext<HTMLElement>? = null,
    content: @Composable ElementScope<HTMLElement>.() -> Unit,
) {
    Header({
        classes(BaseStyles.header)
        attrs?.invoke(this)
    }) {
        content()
    }
}

@Composable
fun AppLayoutContext.PageContent(
    attrs: AttrBuilderContext<HTMLElement>? = null,
    content: @Composable ElementScope<HTMLElement>.() -> Unit,
) {
    Main({
        classes(BaseStyles.main)
        attrs?.invoke(this)
    }) {
        Div({
            classes(BaseStyles.container)
        }) {
            content()
        }
    }
}

@Composable
fun AppLayoutContext.PageFooter(
    attrs: AttrBuilderContext<HTMLElement>? = null,
    content: @Composable ElementScope<HTMLElement>.() -> Unit,
) {
    Footer({
        classes(BaseStyles.footer)
        attrs?.invoke(this)
    }) {
        content()
    }
}

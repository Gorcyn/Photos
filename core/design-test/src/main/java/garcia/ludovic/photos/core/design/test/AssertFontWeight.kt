package garcia.ludovic.photos.core.design.test

import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight

fun SemanticsNodeInteraction.assertFontWeight(
    fontWeight: FontWeight
): SemanticsNodeInteraction = assert(hasFontWeight(fontWeight))

private fun hasFontWeight(fontWeight: FontWeight): SemanticsMatcher = SemanticsMatcher(
    "${SemanticsProperties.Text.name} is of fontWeight '$fontWeight'"
) {
    val textLayoutResults = mutableListOf<TextLayoutResult>()
    it.config.getOrNull(SemanticsActions.GetTextLayoutResult)
        ?.action
        ?.invoke(textLayoutResults)
    return@SemanticsMatcher if (textLayoutResults.isEmpty()) {
        false
    } else {
        textLayoutResults.first().layoutInput.style.fontWeight == fontWeight
    }
}

package garcia.ludovic.photos.core.design.test

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.test.SemanticsNodeInteraction

fun SemanticsNodeInteraction.assertBackgroundColor(color: Color, shape: Shape = RectangleShape): SemanticsNodeInteraction {
    if (!hasBackgroundColor(color, shape)) {
        throw AssertionError("Assert failed: The component does not have background color! $color")
    }
    return this
}

private fun SemanticsNodeInteraction.hasBackgroundColor(color: Color, shape: Shape): Boolean {
    return fetchSemanticsNode().layoutInfo.getModifierInfo().filter { modifierInfo ->
        modifierInfo.modifier == Modifier.background(color, shape)
    }.size == 1
}

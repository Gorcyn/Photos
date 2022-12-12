package garcia.ludovic.photos.core.design.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import garcia.ludovic.photos.core.design.R

val fonts = FontFamily(
    Font(R.font.jet_brains_mono_thin, weight = FontWeight.Thin),
    Font(R.font.jet_brains_mono_thin_italic, weight = FontWeight.Thin, style = FontStyle.Italic),
    Font(R.font.jet_brains_mono_extra_light, weight = FontWeight.ExtraLight),
    Font(R.font.jet_brains_mono_extra_light_italic, weight = FontWeight.ExtraLight, style = FontStyle.Italic),
    Font(R.font.jet_brains_mono_light, weight = FontWeight.Light),
    Font(R.font.jet_brains_mono_light_italic, weight = FontWeight.Light, style = FontStyle.Italic),
    Font(R.font.jet_brains_mono_medium, weight = FontWeight.Medium),
    Font(R.font.jet_brains_mono_medium_italic, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(R.font.jet_brains_mono_regular, weight = FontWeight.Normal),
    Font(R.font.jet_brains_mono_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(R.font.jet_brains_mono_semi_bold, weight = FontWeight.SemiBold),
    Font(R.font.jet_brains_mono_semi_bold_italic, weight = FontWeight.SemiBold, style = FontStyle.Italic),
    Font(R.font.jet_brains_mono_bold, weight = FontWeight.Bold),
    Font(R.font.jet_brains_mono_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.jet_brains_mono_extra_bold, weight = FontWeight.ExtraBold),
    Font(R.font.jet_brains_mono_extra_bold_italic, weight = FontWeight.ExtraBold, style = FontStyle.Italic)
)

fun typographyFrom(typography: Typography): Typography = typography.run {
    copy(
        displayLarge = displayLarge.copy(fontFamily = fonts),
        displayMedium = displayMedium.copy(fontFamily = fonts),
        displaySmall = displaySmall.copy(fontFamily = fonts),
        headlineLarge = headlineLarge.copy(fontFamily = fonts),
        headlineMedium = headlineMedium.copy(fontFamily = fonts),
        headlineSmall = headlineSmall.copy(fontFamily = fonts),
        titleLarge = titleLarge.copy(fontFamily = fonts),
        titleMedium = titleMedium.copy(fontFamily = fonts),
        titleSmall = titleSmall.copy(fontFamily = fonts),
        bodyLarge = bodyLarge.copy(fontFamily = fonts),
        bodyMedium = bodyMedium.copy(fontFamily = fonts),
        bodySmall = bodySmall.copy(fontFamily = fonts),
        labelLarge = labelLarge.copy(fontFamily = fonts),
        labelMedium = labelMedium.copy(fontFamily = fonts),
        labelSmall = labelSmall.copy(fontFamily = fonts)
    )
}

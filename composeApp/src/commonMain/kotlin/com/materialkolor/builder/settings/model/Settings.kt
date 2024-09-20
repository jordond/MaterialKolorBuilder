package com.materialkolor.builder.settings.model

import androidx.compose.ui.graphics.Color
import com.materialkolor.Contrast
import com.materialkolor.PaletteStyle

data class Settings(
    val colors: ColorSettings,
    val isDarkMode: Boolean,
    val contrast: Contrast = Contrast.Default,
    val style: PaletteStyle = PaletteStyle.TonalSpot,
    val isExtendedFidelity: Boolean = false,
)

data class ColorSettings(
    val primary: Color,
    val secondary: Color? = null,
    val tertiary: Color? = null,
    val error: Color? = null,
    val neutral: Color? = null,
    val neutralVariant: Color? = null,
)

object Colors {
    val blue = Color(0xFFAEC6CF)
    val pink = Color(0xFFFFC1CC)
    val green = Color(0xFF77DD77)
    val yellow = Color(0xFFFDFD96)
    val purple = Color(0xFFB39EB5)
    val orange = Color(0xFFFFB347)
    val red = Color(0xFFFF6961)
    val brown = Color(0xFFCD9575)

    val all = listOf(blue, pink, green, yellow, purple, orange, red, brown)

    val default = blue
}
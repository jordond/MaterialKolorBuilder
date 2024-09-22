package com.materialkolor.builder.settings.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.materialkolor.Contrast
import com.materialkolor.PaletteStyle
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class Settings(
    val colors: ColorSettings,
    val isDarkMode: Boolean,
    val selectedImage: SeedImage?,
    val contrast: Contrast = Contrast.Default,
    val style: PaletteStyle = PaletteStyle.TonalSpot,
    val isExtendedFidelity: Boolean = false,
    val isModified: Boolean = false,
)

@Immutable
data class ColorSettings(
    val seed: Color,
    val primary: Color? = null,
    val secondary: Color? = null,
    val tertiary: Color? = null,
    val error: Color? = null,
    val neutral: Color? = null,
    val neutralVariant: Color? = null,
) {

    companion object {

        val colors = _colors
    }
}

private val _colors = persistentListOf(
    Color(0xFFFF1493), // Deep Pink
    Color(0xFF00FF00), // Lime
    Color(0xFF1E90FF), // Dodger Blue
    Color(0xFFFFD700), // Gold
    Color(0xFFFF4500), // Orange Red
    Color(0xFF00FFFF), // Cyan
    Color(0xFFFF69B4), // Hot Pink
    Color(0xFF32CD32), // Lime Green
    Color(0xFF8A2BE2), // Blue Violet
    Color(0xFFFFA500), // Orange
    Color(0xFF00CED1), // Dark Turquoise
    Color(0xFFFF6347), // Tomato
    Color(0xFF7FFF00), // Chartreuse
    Color(0xFFFF00FF), // Magenta
    Color(0xFF00FA9A), // Medium Spring Green
    Color(0xFFFF8C00), // Dark Orange
    Color(0xFF1E90FF), // Dodger Blue
    Color(0xFFFFFF00), // Yellow
    Color(0xFFFF1493), // Deep Pink
    Color(0xFF00BFFF), // Deep Sky Blue
    Color(0xFFADFF2F), // Green Yellow
    Color(0xFFFF69B4), // Hot Pink
    Color(0xFF00FF7F), // Spring Green
    Color(0xFFFF4500), // Orange Red
    Color(0xFF1E90FF), // Dodger Blue
)
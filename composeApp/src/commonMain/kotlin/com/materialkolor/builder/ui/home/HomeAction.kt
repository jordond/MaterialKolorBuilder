package com.materialkolor.builder.ui.home

import androidx.compose.ui.graphics.Color
import com.materialkolor.Contrast
import com.materialkolor.PaletteStyle
import com.materialkolor.builder.settings.model.SeedImage
import com.materialkolor.builder.ui.home.page.preview.model.ThemeColor

sealed interface HomeAction {
    data object ToggleDarkMode : HomeAction
    data object Reset : HomeAction
    data class UpdateContrast(val contrast: Contrast) : HomeAction
    data class UpdatePaletteStyle(val style: PaletteStyle) : HomeAction
    data class SelectPresetImage(val preset: SeedImage.Resource) : HomeAction
    data object SelectCustomImage : HomeAction
    data class OpenColorPicker(val type: ColorType) : HomeAction
    data class CopyColor(val name: String, val color: Color) : HomeAction
    data object RandomColor : HomeAction
    data object Export : HomeAction
}

enum class ColorType {
    Seed,
    Primary,
    Secondary,
    Tertiary,
    Error,
    Neutral,
    NeutralVariant,
}
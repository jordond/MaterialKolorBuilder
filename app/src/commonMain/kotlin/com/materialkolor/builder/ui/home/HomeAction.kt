package com.materialkolor.builder.ui.home

import androidx.compose.ui.graphics.Color
import com.materialkolor.Contrast
import com.materialkolor.PaletteStyle
import com.materialkolor.builder.export.model.ExportOptions
import com.materialkolor.builder.settings.model.KeyColor
import com.materialkolor.builder.settings.model.SeedImage
import com.materialkolor.builder.ui.home.preview.PreviewSection
import com.materialkolor.dynamiccolor.ColorSpec

sealed interface HomeAction {
    sealed interface ColorPicker : HomeAction

    data object ToggleDarkMode : HomeAction
    data object Reset : HomeAction
    data class UpdateContrast(val contrast: Contrast) : HomeAction
    data class UpdatePaletteStyle(val style: PaletteStyle) : HomeAction
    data class UpdateSpecVersion(val version: ColorSpec.SpecVersion) : HomeAction
    data object ToggleExpressive : HomeAction
    data class SelectImage(val image: SeedImage.Resource?) : HomeAction
    data class CopyColor(val name: String, val color: Color) : HomeAction
    data object RandomColor : HomeAction
    data class Nav(val screen: HomeScreens) : HomeAction
    data class Share(val section: PreviewSection) : HomeAction
    data object ToggleExportMode : HomeAction
    data class UpdateExportOptions(val options: ExportOptions) : HomeAction
    data object Export : HomeAction
    data object CancelExport : HomeAction

    data class OpenColorPicker(val key: KeyColor, val initial: Color) : ColorPicker
    data class UpdateColor(val color: Color) : ColorPicker
    data object TogglePickerMode : ColorPicker
    data object PickImageForColor : ColorPicker
    data object CloseColorPicker : ColorPicker
}

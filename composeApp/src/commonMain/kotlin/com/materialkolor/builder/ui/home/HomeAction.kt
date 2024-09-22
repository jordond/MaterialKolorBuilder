package com.materialkolor.builder.ui.home

import com.materialkolor.Contrast
import com.materialkolor.builder.core.UrlLink
import com.materialkolor.builder.settings.model.SeedImage

sealed interface HomeAction {
    data object ToggleDarkMode : HomeAction
    data class ChangeContrast(val contrast: Contrast) : HomeAction
    data class SelectPresetImage(val preset: SeedImage.Resource) : HomeAction
    data object SelectCustomImage : HomeAction
    data object OpenColorPicker : HomeAction
    data object RandomColor : HomeAction
    data object Export : HomeAction
}
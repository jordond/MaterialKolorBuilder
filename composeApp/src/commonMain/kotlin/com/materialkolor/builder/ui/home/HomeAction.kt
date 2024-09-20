package com.materialkolor.builder.ui.home

import com.materialkolor.Contrast
import com.materialkolor.builder.core.UrlLink
import com.materialkolor.builder.settings.model.Image

sealed interface HomeAction {
    data object ToggleDarkMode : HomeAction
    data class LaunchUrl(val url: UrlLink) : HomeAction
    data class ChangeContrast(val contrast: Contrast) : HomeAction
    data class SelectImage(val preset: Image.Resource) : HomeAction
    data object SelectCustomImage : HomeAction
}
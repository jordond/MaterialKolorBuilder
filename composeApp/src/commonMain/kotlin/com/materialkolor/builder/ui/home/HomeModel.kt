package com.materialkolor.builder.ui.home

import com.materialkolor.Contrast
import com.materialkolor.builder.core.DI
import com.materialkolor.builder.core.UrlLauncher
import com.materialkolor.builder.core.UrlLink
import com.materialkolor.builder.settings.SettingsRepo
import com.materialkolor.builder.settings.model.Image
import com.materialkolor.builder.settings.model.ImagePresets
import com.materialkolor.builder.settings.model.Settings
import com.materialkolor.builder.ui.ktx.StateViewModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

class HomeModel(
    private val settingsRepo: SettingsRepo = DI.settingsRepo,
    private val urlLauncher: UrlLauncher = DI.urlLauncher,
) : StateViewModel<HomeModel.State>(State(settingsRepo.settings.value)) {

    init {
        settingsRepo.settings.collectToState { state, value ->
            state.copy(settings = value)
        }
    }

    fun toggleDarkMode() {
        settingsRepo.update { it.copy(isDarkMode = !it.isDarkMode) }
    }

    fun updateContrast(contrast: Contrast) {
        settingsRepo.update { it.copy(contrast = contrast) }
    }

    fun selectImage(image: Image) {
        settingsRepo.update { it.copy(selectedImage = image) }
    }

    fun launchUrl(url: UrlLink) {
        urlLauncher.launch(url)
    }

    data class State(
        val settings: Settings,
        val imagePresets: PersistentList<Image> = ImagePresets.all.toPersistentList(),
    )
}
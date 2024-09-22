package com.materialkolor.builder.ui.home

import com.materialkolor.Contrast
import com.materialkolor.PaletteStyle
import com.materialkolor.builder.core.DI
import com.materialkolor.builder.settings.SettingsRepo
import com.materialkolor.builder.settings.model.ColorSettings
import com.materialkolor.builder.settings.model.ImagePresets
import com.materialkolor.builder.settings.model.SeedImage
import com.materialkolor.builder.settings.model.Settings
import com.materialkolor.builder.ui.ktx.StateViewModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlin.random.Random

class HomeModel(
    private val settingsRepo: SettingsRepo = DI.settingsRepo,
    private val random: Random = Random.Default,
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

    fun updatePaletteStyle(style: PaletteStyle) {
        settingsRepo.update { it.copy(style = style) }
    }

    fun selectImage(image: SeedImage.Resource) {
        settingsRepo.update { settings ->
            settings.copy(
                colors = settings.colors.copy(seed = image.color),
                selectedImage = image,
            )
        }
    }

    fun randomColor() {
        val color = ColorSettings.colors.random(random)
        settingsRepo.update { settings ->
            settings.copy(
                colors = ColorSettings(seed = color),
                selectedImage = null,
            )
        }
    }

    fun reset() {
        settingsRepo.clear()
    }

    data class State(
        val settings: Settings,
        val imagePresets: PersistentList<SeedImage> = ImagePresets.all.toPersistentList(),
    )
}
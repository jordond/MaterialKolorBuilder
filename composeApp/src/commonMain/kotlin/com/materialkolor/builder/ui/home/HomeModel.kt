package com.materialkolor.builder.ui.home

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.materialkolor.Contrast
import com.materialkolor.PaletteStyle
import com.materialkolor.builder.core.Clipboard
import com.materialkolor.builder.core.DI
import com.materialkolor.builder.core.readBytes
import com.materialkolor.builder.settings.SettingsRepo
import com.materialkolor.builder.settings.model.ColorSettings
import com.materialkolor.builder.settings.model.ImagePresets
import com.materialkolor.builder.settings.model.SeedImage
import com.materialkolor.builder.settings.model.Settings
import com.materialkolor.builder.ui.ktx.UiStateViewModel
import com.materialkolor.builder.ui.ktx.toHex
import com.materialkolor.ktx.themeColorOrNull
import com.mohamedrejeb.calf.io.KmpFile
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.decodeToImageBitmap
import kotlin.random.Random

class HomeModel(
    private val settingsRepo: SettingsRepo = DI.settingsRepo,
    private val clipboard: Clipboard = DI.clipboard,
    private val random: Random = Random.Default,
) : UiStateViewModel<HomeModel.State, HomeModel.Event>(State(settingsRepo.settings.value)) {

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

    fun selectImagePreset(image: SeedImage.Resource) {
        settingsRepo.updateImage(image)
    }

    fun copyColorToClipboard(name: String, color: Color) {
        val hex = color.toHex()
        val text = "Copied $name color: $hex to clipboard"
        if (clipboard.copy(hex)) {
            emit(Event.ShowSnackbar(text))
        } else {
            emit(Event.ShowSnackbar("Failed to copy color to clipboard"))
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

    fun handleImage(file: KmpFile?) {
        if (file == null) return

        updateState { it.copy(processingImage = true) }
        viewModelScope.launch {
            try {
                val seedImage = withContext(Dispatchers.Default) {
                    val bytes = file.readBytes()
                    val image = bytes.decodeToImageBitmap()
                    val color = image.themeColorOrNull() ?: error("No theme color found")

                    SeedImage.Custom(image, color)
                }

                settingsRepo.updateImage(seedImage)
            } catch (cause: Throwable) {
                Logger.e(cause) { "Failed to read image" }
                emit(Event.ShowSnackbar("Failed to read uploaded image"))
            } finally {
                updateState { it.copy(processingImage = false) }
            }
        }
    }

    data class State(
        val settings: Settings,
        val imagePresets: PersistentList<SeedImage> = ImagePresets.all.toPersistentList(),
        val processingImage: Boolean = false,
    ) {

        val customImage: ImageBitmap?
            get() = (settings.selectedImage as? SeedImage.Custom)?.image
    }

    sealed interface Event {
        data class ShowSnackbar(val message: String) : Event
    }
}
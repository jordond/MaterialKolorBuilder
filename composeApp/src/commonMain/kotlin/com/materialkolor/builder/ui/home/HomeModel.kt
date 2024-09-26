package com.materialkolor.builder.ui.home

import androidx.compose.ui.graphics.Color
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
import com.materialkolor.builder.ui.components.ColorPickerState
import com.materialkolor.builder.ui.ktx.UiStateViewModel
import com.materialkolor.ktx.themeColorOrNull
import com.materialkolor.ktx.toHex
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
        updateSettings { it.copy(isDarkMode = !it.isDarkMode) }
    }

    fun updateContrast(contrast: Contrast) {
        updateSettings { it.copy(contrast = contrast) }
    }

    fun updatePaletteStyle(style: PaletteStyle) {
        updateSettings { it.copy(style = style) }
    }

    fun handleColorPickerAction(action: HomeAction.ColorPicker) {
        when (action) {
            is HomeAction.CloseColorPicker -> updateState { it.copy(colorPickerState = null) }
            is HomeAction.OpenColorPicker -> {
                val state = ColorPickerState(action.key, action.initial)
                updateState { it.copy(colorPickerState = state) }
            }
            is HomeAction.PickImageForColor -> {
                imageLoading(true)
                emit(Event.PickImage)
            }
            is HomeAction.TogglePickerMode -> updateState { state ->
                val pickerState = state.colorPickerState?.toggleMode() ?: return@updateState state
                state.copy(colorPickerState = pickerState)
            }
            is HomeAction.UpdateColor -> {
                val key = state.value.colorPickerState?.keyColor ?: return
                updateSettings { settings ->
                    val colors = settings.colors.update(key, action.color)
                    settings.copy(colors = colors, selectedImage = null)
                }
            }
        }
    }

    fun selectImagePreset(image: SeedImage.Resource) {
        viewModelScope.launch {
            settingsRepo.updateImage(image)
        }
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
        updateSettings { settings ->
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
        if (file == null) {
            imageLoading(false)
            return
        }

        imageLoading(true)
        viewModelScope.launch {
            try {
                val seedImage = withContext(Dispatchers.Default) {
                    val bytes = file.readBytes()
                    val image = bytes.decodeToImageBitmap()
                    val color = image.themeColorOrNull() ?: error("No theme color found")

                    SeedImage.Custom(image, color)
                }

                if (state.value.colorPickerState != null) {
                    updateState { state ->
                        val pickerState = state.colorPickerState?.copy(image = seedImage.image)
                        state.copy(colorPickerState = pickerState)
                    }
                }

                settingsRepo.updateImage(seedImage)
            } catch (cause: Throwable) {
                Logger.e(cause) { "Failed to read image" }
                emit(Event.ShowSnackbar("Failed to read uploaded image"))
            } finally {
                imageLoading(false)
            }
        }
    }

    private fun updateSettings(block: (Settings) -> Settings) {
        viewModelScope.launch {
            settingsRepo.update(block)
        }
    }

    private fun imageLoading(value: Boolean) {
        updateState { state ->
            val pickerState = state.colorPickerState?.copy(loading = value)
            state.copy(processingImage = value, colorPickerState = pickerState)
        }
    }

    data class State(
        val settings: Settings,
        val imagePresets: PersistentList<SeedImage> = ImagePresets.all.toPersistentList(),
        val processingImage: Boolean = false,
        val colorPickerState: ColorPickerState? = null,
    )

    sealed interface Event {
        data class ShowSnackbar(val message: String) : Event
        data object PickImage : Event
    }
}

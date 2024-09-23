package com.materialkolor.builder.settings

import co.touchlab.kermit.Logger
import com.materialkolor.builder.settings.model.ColorSettings
import com.materialkolor.builder.settings.model.ImagePresets
import com.materialkolor.builder.settings.model.SeedImage
import com.materialkolor.builder.settings.model.Settings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface SettingsRepo {
    val settings: StateFlow<Settings>

    fun update(block: (Settings) -> Settings)

    fun update(settings: Settings) = update { settings }

    fun updateImage(image: SeedImage)

    fun clear()
}

class DefaultSettingsRepo(
    private val darkModeProvider: DarkModeProvider,
    scope: CoroutineScope,
) : SettingsRepo {
    private val logger = Logger.withTag("SettingsRepo")

    // TODO: Persist the settings to disk
    private val _settings = MutableStateFlow(defaults())
    override val settings = _settings.asStateFlow()

    init {
        scope.launch {
            darkModeProvider.isDarkMode.collect { isDarkMode ->
                _settings.update { settings ->
                    settings.copy(isDarkMode = isDarkMode)
                }
            }
        }
    }

    override fun update(block: (Settings) -> Settings) {
        _settings.update { settings ->
            logger.d { "Old settings: $settings" }

            block(settings).copy(isModified = true).also { value ->
                logger.d { "Updated settings: $value" }
            }
        }
    }

    override fun updateImage(image: SeedImage) {
        _settings.update { settings ->
            settings.copy(
                colors = settings.colors.copy(seed = image.color),
                selectedImage = image,
            )
        }
    }

    override fun clear() {
        _settings.update { defaults() }
    }

    private fun defaults(): Settings {
        val image = ImagePresets.all.first()
        return Settings(
            selectedImage = image,
            colors = ColorSettings(seed = image.color),
            isDarkMode = darkModeProvider.isDarkMode.value,
        )
    }
}
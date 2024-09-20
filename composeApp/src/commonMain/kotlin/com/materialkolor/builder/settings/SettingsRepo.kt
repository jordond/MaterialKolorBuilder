package com.materialkolor.builder.settings

import co.touchlab.kermit.Logger
import com.materialkolor.builder.settings.model.ColorSettings
import com.materialkolor.builder.settings.model.Colors
import com.materialkolor.builder.settings.model.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface SettingsRepo {
    val settings: Flow<Settings>

    fun update(block: (Settings) -> Settings)

    fun update(settings: Settings) = update { settings }

    fun clear()
}

class DefaultSettingsRepo(
    private val darkModeProvider: DarkModeProvider,
) : SettingsRepo {
    private val logger = Logger.withTag("SettingsRepo")

    // TODO: Persist the settings to disk
    private val _settings = MutableStateFlow(defaultSettings())
    override val settings: Flow<Settings> = _settings.asStateFlow()

    override fun update(block: (Settings) -> Settings) {
        _settings.update { settings ->
            logger.d { "Old settings: $settings" }

            block(settings).also { value ->
                logger.d { "Updated settings: $value" }
            }
        }
    }

    override fun clear() {
        _settings.update { defaultSettings() }
    }

    private fun defaultSettings() = Settings(
        colors = ColorSettings(primary = Colors.default),
        isDarkMode = darkModeProvider.isDarkMode,
    )
}
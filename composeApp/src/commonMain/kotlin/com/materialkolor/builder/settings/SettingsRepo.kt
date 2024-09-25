package com.materialkolor.builder.settings

import co.touchlab.kermit.Logger
import com.materialkolor.builder.settings.model.SeedImage
import com.materialkolor.builder.settings.model.Settings
import com.materialkolor.builder.settings.store.SettingsStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

interface SettingsRepo {
    val settings: StateFlow<Settings>

    suspend fun update(block: (Settings) -> Settings)

    suspend fun update(settings: Settings) = update { settings }

    suspend fun updateImage(image: SeedImage)

    fun clear()
}

class DefaultSettingsRepo(
    darkModeProvider: DarkModeProvider,
    private val store: SettingsStore,
    scope: CoroutineScope,
) : SettingsRepo {
    private val logger = Logger.withTag("SettingsRepo")

    override val settings = store.get().stateIn(
        scope = scope,
        started = SharingStarted.Lazily,
        initialValue = SettingsStore.defaults(darkModeProvider.isDarkMode.value),
    )

    override suspend fun update(block: (Settings) -> Settings) {
        val value = settings.value.let { settings ->
            logger.d { "Old settings: $settings" }
            block(settings).copy(isModified = true)
        }

        logger.d { "Updated settings: $value" }
        store.store(value)
    }

    override suspend fun updateImage(image: SeedImage) {
        update { settings ->
            settings.copy(
                colors = settings.colors.copy(seed = image.color),
                selectedImage = image,
            )
        }
    }

    override fun clear() {
        store.clear()
    }
}
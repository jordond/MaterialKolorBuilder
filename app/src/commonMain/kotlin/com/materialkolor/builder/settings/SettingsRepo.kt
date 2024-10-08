package com.materialkolor.builder.settings

import co.touchlab.kermit.Logger
import com.materialkolor.builder.core.baseUrl
import com.materialkolor.builder.settings.model.SeedImage
import com.materialkolor.builder.settings.model.Settings
import com.materialkolor.builder.settings.store.SettingsStore
import com.materialkolor.builder.settings.store.entity.toEntity
import com.materialkolor.builder.settings.store.entity.toQueryParams
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext

const val DESTINATION_QUERY_PARAM = "destination"

interface SettingsRepo {
    val settings: StateFlow<Settings>

    fun getUrl(name: String): String

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
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SettingsStore.defaults(darkModeProvider.isDarkMode.value),
    )

    override fun getUrl(name: String): String {
        val queryParams = settings.value.toEntity().toQueryParams()
        return "$baseUrl$queryParams&$DESTINATION_QUERY_PARAM=$name"
    }

    override suspend fun update(block: (Settings) -> Settings) {
        val value = settings.value.let { settings ->
            logger.d { "Old settings: $settings" }
            block(settings).copy(isModified = true)
        }

        logger.d { "Updated settings: $value" }
        withContext(Dispatchers.Default) {
            store.store(value)
        }
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

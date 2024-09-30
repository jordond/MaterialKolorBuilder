package com.materialkolor.builder.export

import androidx.compose.runtime.Immutable
import com.materialkolor.builder.export.library.createFiles
import com.materialkolor.builder.export.standard.createFiles
import com.materialkolor.builder.settings.model.Settings
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

sealed interface ExportOptions {
    val settings: Settings
    val multiplatform: Boolean
    val themeName: String
    val packageName: String
    val files: PersistentList<ExportFile>

    data class Standard(
        override val settings: Settings,
        override val multiplatform: Boolean = DEFAULT_MULTIPLATFORM,
        override val themeName: String = DEFAULT_THEME_NAME,
        override val packageName: String = DEFAULT_PACKAGE_NAME,
        val generateAllContrastOptions: Boolean = false,
    ) : ExportOptions {
        override val files: PersistentList<ExportFile> = createFiles().toPersistentList()
    }

    data class MaterialKolor(
        override val settings: Settings,
        override val multiplatform: Boolean = DEFAULT_MULTIPLATFORM,
        override val themeName: String = DEFAULT_THEME_NAME,
        override val packageName: String = DEFAULT_PACKAGE_NAME,
        val useVersionCatalog: Boolean = true,
        val animate: Boolean = true,
    ) : ExportOptions {
        override val files: PersistentList<ExportFile> = createFiles().toPersistentList()
    }

    fun update(settings: Settings): ExportOptions {
        return when (this) {
            is Standard -> copy(settings = settings)
            is MaterialKolor -> copy(settings = settings)
        }
    }

    companion object {

        const val DEFAULT_MULTIPLATFORM = true
        const val DEFAULT_THEME_NAME = "AppTheme"
        const val DEFAULT_PACKAGE_NAME = "com.example"

        fun default(settings: Settings) = MaterialKolor(settings)
    }
}

package com.materialkolor.builder.export

import com.materialkolor.builder.export.library.createMaterialKolorFiles
import com.materialkolor.builder.export.standard.createStandardFiles
import com.materialkolor.builder.settings.model.Settings
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

enum class ExportType(val displayName: String) {
    Standard("Standard"),
    MaterialKolor("Material Kolor"),
}

data class ExportOptions(
    val type: ExportType,
    val settings: Settings,
    val multiplatform: Boolean = DEFAULT_MULTIPLATFORM,
    val themeName: String = DEFAULT_THEME_NAME,
    val packageName: String = DEFAULT_PACKAGE_NAME,
    val useVersionCatalog: Boolean = DEFAULT_USE_VERSION_CATALOG,
    val animate: Boolean = DEFAULT_ANIMATE,
) {

    val files: PersistentList<ExportFile> = when (type) {
        ExportType.MaterialKolor -> createMaterialKolorFiles()
        ExportType.Standard -> createStandardFiles()
    }.toPersistentList()

    fun toggleType(): ExportOptions = copy(
        type = when (type) {
            ExportType.MaterialKolor -> ExportType.Standard
            ExportType.Standard -> ExportType.MaterialKolor
        },
    )

    companion object {

        const val DEFAULT_THEME_NAME = "AppTheme"
        const val DEFAULT_PACKAGE_NAME = "com.example"
        const val DEFAULT_MULTIPLATFORM = true
        const val DEFAULT_USE_VERSION_CATALOG = true
        const val DEFAULT_ANIMATE = true

        fun default(settings: Settings) = ExportOptions(ExportType.MaterialKolor, settings)
    }
}

package com.materialkolor.builder.export.library

import com.materialkolor.builder.export.ExportFile
import com.materialkolor.builder.export.ExportOptions

fun ExportOptions.MaterialKolor.createFiles(): List<ExportFile> {
    val gradle = gradleKts(isMultiplatform = multiplatform, useVersionCatalog = useVersionCatalog)
    val libs = if (useVersionCatalog) libsVersionsToml() else null
    val colors = mkColorsKt(packageName = packageName, colors = settings.colors)
    val theme = mkThemeKt(
        packageName = packageName,
        themeName = themeName,
        animate = animate,
        settings = settings,
    )

    return listOfNotNull(
        ExportFile(name = "build.gradle.kts", content = gradle),
        libs?.let { ExportFile(name = "libs.versions.toml", content = it) },
        ExportFile(name = "Color.kt", content = colors),
        ExportFile(name = "Theme.kt", content = theme),
    )
}

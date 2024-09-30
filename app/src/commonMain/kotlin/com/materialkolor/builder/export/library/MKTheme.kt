package com.materialkolor.builder.export.library

import androidx.compose.ui.graphics.Color
import com.materialkolor.Contrast
import com.materialkolor.builder.export.Header
import com.materialkolor.builder.settings.model.Settings

fun mkThemeKt(
    packageName: String,
    themeName: String,
    settings: Settings,
    animate: Boolean,
): String {
    val contrast = if (settings.contrast == Contrast.Default) null
    else "contrastLevel = ${settings.contrast.value}"

    val params = listOfNotNull(
        contrast,
        settings.isAmoled.parameter("isAmoled"),
        settings.isExtendedFidelity.parameter("extendedFidelity"),
        if (settings.colors.primary == null) settings.colors.seed.parameter("Seed")
        else settings.colors.primary.parameter("Primary"),
        settings.colors.secondary.parameter("Secondary"),
        settings.colors.tertiary.parameter("Tertiary"),
        settings.colors.error.parameter("Error"),
        settings.colors.neutral.parameter("Neutral"),
        settings.colors.neutralVariant.parameter("NeutralVariant"),
    ).joinToString(",\n          ")

    return """
    $Header
    package $packageName
    
    import androidx.compose.foundation.isSystemInDarkTheme
    import androidx.compose.runtime.Composable
    import com.materialkolor.DynamicMaterialTheme
    import com.materialkolor.PaletteStyle
    import com.materialkolor.rememberDynamicMaterialThemeState
    
    @Composable
    fun $themeName(
        isDarkTheme: Boolean = isSystemInDarkTheme(),
        content: @Composable () -> Unit,
    ) {
        val dynamicThemeState = rememberDynamicMaterialThemeState(
            isDark = isDarkTheme,
            style = PaletteStyle.${settings.style},
            $params
        )
        
        DynamicMaterialTheme(
            state = dynamicThemeState,
            animate = $animate,
            content = content,
        )
    }
    """.trimIndent()
}

private fun Boolean.parameter(name: String) = if (this) "$name = true" else null

private fun Color?.parameter(name: String): String? {
    if (this == null) return null
    return "${name.lowercase()} = ${name.capitalize()}"
}
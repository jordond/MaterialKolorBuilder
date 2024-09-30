package com.materialkolor.builder.export.standard

import com.materialkolor.Contrast
import com.materialkolor.builder.export.Header
import com.materialkolor.builder.settings.model.Settings

fun standardThemeKt(
    packageName: String,
    themeName: String,
    multiplatform: Boolean,
    settings: Settings,
): String {
    val lightColors = lightVariableNamePairs(settings)
    val lightSchemeName = settings.contrast.schemeName(isDark = false)
    val darkColors = darkVariableNamePairs(settings)
    val darkSchemeName = settings.contrast.schemeName(isDark = true)

    val colorSchemeVariable = if (!multiplatform) {
        """
        val colorScheme = when {
            darkTheme -> $darkSchemeName
            else -> $lightSchemeName
        }
        """.trimIndent()
    } else {
        """
        val colorScheme = when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }
            darkTheme -> $darkSchemeName
            else -> $lightSchemeName
        }
        """.trimIndent()
    }

    return """
    $Header
    package $packageName
    
    import androidx.compose.foundation.isSystemInDarkTheme
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.darkColorScheme
    import androidx.compose.material3.lightColorScheme
    ${
        if (!multiplatform) ""
        else {
            """
            import androidx.compose.material3.dynamicDarkColorScheme
            import androidx.compose.material3.dynamicLightColorScheme
            """.trimIndent()
        }
    }
    import androidx.compose.runtime.Composable
    
    private val $lightSchemeName = lightColorScheme(
    ${lightColors.toParamList()}
    )
    
    private val $darkSchemeName = darkColorScheme(
    ${darkColors.toParamList()}
    )
    
    @Composable
    fun $themeName(
        darkTheme: Boolean = isSystemInDarkTheme(),
        ${
        if (!multiplatform) ""
        else {
            """
                // Dynamic color is available on Android 12+
                dynamicColor: Boolean = true,
                """.trimIndent()
        }
    }
        content: @Composable() () -> Unit
    ) {
        $colorSchemeVariable

        MaterialTheme(
            colorScheme = colorScheme,
            content = content,
        )
    }
    """.trimIndent()
}

private fun Contrast.schemeName(isDark: Boolean): String {
    val mode = if (isDark) "Dark" else "Light"
    return when (this) {
        Contrast.Reduced -> "reducedContrast${mode}ColorScheme"
        Contrast.Default -> "${mode.lowercase()}ColorScheme"
        Contrast.Medium -> "mediumContrast${mode}ColorScheme"
        Contrast.High -> "highContrast${mode}ColorScheme"
    }
}

private fun Map<String, String>.toParamList(): String {
    return this.entries.joinToString(",\n") { (variableName, paramName) ->
        "    $paramName = $variableName"
    }
}

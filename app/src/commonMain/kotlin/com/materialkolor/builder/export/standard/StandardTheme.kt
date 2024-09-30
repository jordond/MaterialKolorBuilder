package com.materialkolor.builder.export.standard

import androidx.compose.ui.text.decapitalize
import androidx.compose.ui.text.intl.Locale
import com.materialkolor.Contrast
import com.materialkolor.builder.export.Header
import com.materialkolor.builder.settings.model.Settings

// TODO: Android only still needs some work
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

    val colorSchemeVariable = if (multiplatform) {
    """
    |    val colorScheme = when {
    |        darkTheme -> $darkSchemeName
    |        else -> $lightSchemeName
    |    }
    """.trimMargin()
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
    if (multiplatform) ""
    else {
        """
        import androidx.compose.material3.dynamicDarkColorScheme
            import androidx.compose.material3.dynamicLightColorScheme
        """.trimIndent()
    }
}
import androidx.compose.runtime.Composable

private val $lightSchemeName = lightColorScheme(
${lightColors.toParamList()},
)

private val $darkSchemeName = darkColorScheme(
${darkColors.toParamList()},
)

@Composable
fun $themeName(
    ${
    if (multiplatform) """
        |darkTheme: Boolean = isSystemInDarkTheme(),
    """.trimMargin()
    else {
        """
            |darkTheme: Boolean = isSystemInDarkTheme(),
            |// Dynamic color is available on Android 12+
            |dynamicColor: Boolean = true,
        """.trimMargin()
    }
}
    content: @Composable() () -> Unit,
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

private fun List<String>.toParamList(): String {
    return joinToString(",\n") { name ->
        "    ${name.decapitalize(Locale("EN"))} = $name"
    }
}

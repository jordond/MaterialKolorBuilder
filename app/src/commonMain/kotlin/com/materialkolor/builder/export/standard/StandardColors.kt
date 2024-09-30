package com.materialkolor.builder.export.standard

import com.materialkolor.Contrast
import com.materialkolor.builder.export.Header
import com.materialkolor.builder.export.variable
import com.materialkolor.builder.ktx.snakeToCamelCase
import com.materialkolor.builder.settings.model.Settings
import com.materialkolor.dynamiccolor.DynamicColor
import com.materialkolor.dynamiccolor.MaterialDynamicColors
import com.materialkolor.ktx.DynamicScheme
import com.materialkolor.ktx.getColor
import com.materialkolor.scheme.DynamicScheme

fun standardColorsKt(
    packageName: String,
    settings: Settings,
): String {
    val map = MaterialDynamicColors(settings.isExtendedFidelity).colorList()
    val light = createScheme(isDark = false, settings = settings)
    val dark = createScheme(isDark = true, settings = settings)


    return """
    $Header
    package $packageName
    
    import androidx.compose.ui.graphics.Color
    
    ${settings.colors.seed.variable("Seed")}
    
    ${map.toColorVariables(scheme = light)}
    
    ${map.toColorVariables(scheme = dark)}
    """.trimIndent()
}

/**
 * Return a list of color names:
 *
 * Example:
 * ```
 * "primaryLight" to "primary"
 * ```
 */
fun lightVariableNamePairs(settings: Settings): Map<String, String> {
    val list = MaterialDynamicColors(settings.isExtendedFidelity).colorList()
    val light = createScheme(isDark = false, settings = settings)
    return list.variableNamePair(light).mapValues { it.value.name.snakeToCamelCase() }
}

fun darkVariableNamePairs(settings: Settings): Map<String, String> {
    val list = MaterialDynamicColors(settings.isExtendedFidelity).colorList()
    val dark = createScheme(isDark = true, settings = settings)
    return list.variableNamePair(dark).mapValues { it.value.name.snakeToCamelCase() }
}

private fun createScheme(
    isDark: Boolean,
    settings: Settings,
) = DynamicScheme(
    isDark = isDark,
    seedColor = settings.colors.seed,
    primary = settings.colors.primary ?: settings.colors.seed,
    secondary = settings.colors.secondary,
    tertiary = settings.colors.tertiary,
    error = settings.colors.error,
    neutral = settings.colors.neutral,
    neutralVariant = settings.colors.neutralVariant,
    style = settings.style,
    contrastLevel = settings.contrast.value,
)

private fun MaterialDynamicColors.colorList(): List<DynamicColor> = listOf(
    primary(),
    onPrimary(),
    primaryContainer(),
    onPrimaryContainer(),
    secondary(),
    onSecondary(),
    secondaryContainer(),
    onSecondaryContainer(),
    tertiary(),
    onTertiary(),
    tertiaryContainer(),
    onTertiaryContainer(),
    error(),
    onError(),
    errorContainer(),
    onErrorContainer(),
    background(),
    onBackground(),
    surface(),
    onSurface(),
    surfaceVariant(),
    onSurfaceVariant(),
    outline(),
    outlineVariant(),
    scrim(),
    inverseSurface(),
    inverseOnSurface(),
    inversePrimary(),
    surfaceDim(),
    surfaceBright(),
    surfaceContainerLowest(),
    surfaceContainerLow(),
    surfaceContainer(),
    surfaceContainerHigh(),
    surfaceContainerHighest(),
)

fun List<DynamicColor>.variableNamePair(
    scheme: DynamicScheme,
): Map<String, DynamicColor> {
    val contrast = scheme.contrastSuffix()
    val mode = if (scheme.isDark) "Dark" else "Light"
    val suffix = "$mode$contrast"
    return associateBy { color -> "${color.name.snakeToCamelCase()}$suffix" }
}

private fun List<DynamicColor>.toColorVariables(
    scheme: DynamicScheme,
): String {
    val values = variableNamePair(scheme)
    return buildString {
        values.forEach { (name, color) ->
            appendLine(color.getColor(scheme).variable(name))
        }
    }
}

private fun DynamicScheme.contrastSuffix(): String = when (contrastLevel) {
    Contrast.Reduced.value -> "ReducedContrast"
    Contrast.Default.value -> ""
    Contrast.Medium.value -> "MediumContrast"
    Contrast.High.value -> "HighContrast"
    else -> ""
}
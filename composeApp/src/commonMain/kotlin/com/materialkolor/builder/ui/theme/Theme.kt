package com.materialkolor.builder.ui.theme

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.materialkolor.DynamicMaterialTheme
import com.materialkolor.builder.settings.model.Settings
import com.materialkolor.rememberDynamicMaterialThemeState

internal val LocalThemeIsDark: ProvidableCompositionLocal<State<Boolean>> = compositionLocalOf {
    error("Not initialized")
}

@Composable
internal fun AppTheme(
    settings: Settings,
    animate: Boolean = true,
    content: @Composable () -> Unit,
) {
    val isDarkState = remember(settings.isDarkMode) { mutableStateOf(settings.isDarkMode) }
    val dynamicThemeState = rememberDynamicMaterialThemeState(
        seedColor = settings.colors.seed,
        primary = settings.colors.primary,
        secondary = settings.colors.secondary,
        tertiary = settings.colors.tertiary,
        neutral = settings.colors.neutral,
        neutralVariant = settings.colors.neutralVariant,
        error = settings.colors.error,
        isDark = settings.isDarkMode,
        style = settings.style,
        extendedFidelity = settings.isExtendedFidelity,
        contrastLevel = settings.contrast.value,
    )

    CompositionLocalProvider(
        LocalThemeIsDark provides isDarkState,
    ) {
        DynamicMaterialTheme(
            state = dynamicThemeState,
            animate = animate,
            typography = AppTypography,
        ) {
            SystemAppearance(isDarkState.value)
            Surface(content = content)
        }
    }
}

@Composable
internal expect fun SystemAppearance(isDark: Boolean)
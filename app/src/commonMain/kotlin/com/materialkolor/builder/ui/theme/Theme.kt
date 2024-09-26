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
import com.materialkolor.DynamicMaterialThemeState
import com.materialkolor.MaterialKolors
import com.materialkolor.builder.core.UrlLauncher
import com.materialkolor.builder.settings.model.Settings
import com.materialkolor.ktx.colors
import com.materialkolor.rememberDynamicMaterialThemeState

internal val LocalThemeIsDark: ProvidableCompositionLocal<State<Boolean>> = compositionLocalOf {
    error("Not initialized")
}

internal val LocalDynamicThemeState = compositionLocalOf<DynamicMaterialThemeState> {
    error("Not initialized")
}

internal val LocalColors = compositionLocalOf<MaterialKolors> { error("Not initialized") }

@Composable
fun createThemeState(
    settings: Settings,
    isDark: Boolean = settings.isDarkMode,
): DynamicMaterialThemeState {
    return rememberDynamicMaterialThemeState(
        seedColor = settings.colors.seed,
        primary = settings.colors.primary,
        secondary = settings.colors.secondary,
        tertiary = settings.colors.tertiary,
        neutral = settings.colors.neutral,
        neutralVariant = settings.colors.neutralVariant,
        error = settings.colors.error,
        isDark = isDark,
        style = settings.style,
        extendedFidelity = settings.isExtendedFidelity,
        contrastLevel = settings.contrast.value,
    )
}

@Composable
internal fun AppTheme(
    settings: Settings,
    urlLauncher: UrlLauncher,
    animate: Boolean = true,
    content: @Composable () -> Unit,
) {
    val isDarkState = remember(settings.isDarkMode) { mutableStateOf(settings.isDarkMode) }
    val dynamicThemeState = createThemeState(settings)

    CompositionLocalProvider(
        LocalDynamicThemeState provides dynamicThemeState,
        LocalThemeIsDark provides isDarkState,
        LocalUrlLauncher provides urlLauncher,
    ) {
        DynamicMaterialTheme(
            state = dynamicThemeState,
            animate = animate,
            typography = AppTypography,
        ) {
            SystemAppearance(isDarkState.value)

            CompositionLocalProvider(LocalColors provides dynamicThemeState.colors) {
                Surface(content = content)
            }
        }
    }
}

@Composable
internal expect fun SystemAppearance(isDark: Boolean)
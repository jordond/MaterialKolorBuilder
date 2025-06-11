package com.materialkolor.builder.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.materialkolor.builder.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

val LocalWindowSizeClass = compositionLocalOf<WindowSizeClass> { error("Not initialized") }

@Composable
@Preview
fun App(destination: String? = null) {
    val isDarkMode = isSystemInDarkTheme()
    val model = viewModel { AppModel(isDarkMode = isDarkMode) }
    LaunchedEffect(isDarkMode) {
        model.updateDarkMode(isDarkMode)
    }

    val state by model.state.collectAsStateWithLifecycle()

    AppTheme(
        settings = state.settings,
        urlLauncher = model.urlLauncher,
    )
}

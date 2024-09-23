package com.materialkolor.builder.ui.home.page.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.materialkolor.DynamicMaterialTheme
import com.materialkolor.DynamicMaterialThemeState
import com.materialkolor.builder.settings.model.Settings
import com.materialkolor.builder.ui.theme.AppTypography
import com.materialkolor.builder.ui.theme.createThemeState

enum class ThemeMode {
    Light,
    Dark,
}

@Composable
fun LightThemeDisplay(
    settings: Settings,
    modifier: Modifier = Modifier,
) {
    ThemeDisplay(ThemeMode.Light, createThemeState(settings, isDark = false), modifier)
}

@Composable
fun DarkThemeDisplay(
    settings: Settings,
    modifier: Modifier = Modifier,
) {
    ThemeDisplay(ThemeMode.Dark, createThemeState(settings, isDark = true), modifier)
}

@Composable
fun ThemeDisplay(
    theme: ThemeMode,
    state: DynamicMaterialThemeState,
    modifier: Modifier = Modifier,
) {
    DynamicMaterialTheme(
        state = state,
        animate = true,
        typography = AppTypography,
    ) {
        ThemeDisplay(theme, modifier)
    }
}

@Composable
fun ThemeDisplay(
    theme: ThemeMode,
    modifier: Modifier = Modifier,
) {
    OutlinedCard(
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text("${theme.name} Theme", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                ColorBox("Primary", MaterialTheme.colorScheme.primary, "P-40", Modifier.weight(1f))
                ColorBox("Secondary", MaterialTheme.colorScheme.secondary, "S-40", Modifier.weight(1f))
                ColorBox("Tertiary", MaterialTheme.colorScheme.tertiary, "T-40", Modifier.weight(1f))
                ColorBox("Error", MaterialTheme.colorScheme.error, "E-40", Modifier.weight(1f))
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                ColorBox("On Primary", MaterialTheme.colorScheme.onPrimary, "P-100", Modifier.weight(1f))
                ColorBox("On Secondary", MaterialTheme.colorScheme.onSecondary, "S-100", Modifier.weight(1f))
                ColorBox("On Tertiary", MaterialTheme.colorScheme.onTertiary, "T-100", Modifier.weight(1f))
                ColorBox("On Error", MaterialTheme.colorScheme.onError, "E-100", Modifier.weight(1f))
            }

            // Add more rows for other color scheme elements...
        }
    }
}

@Composable
fun ColorBox(name: String, color: Color, code: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .height(100.dp)
            .background(color)
            .padding(8.dp)
    ) {
        Text(name, color = Color.White, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.weight(1f))
        Text(code, color = Color.White)
    }
}
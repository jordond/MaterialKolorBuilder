package com.materialkolor.builder.ui.home.page.preview.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class ThemeColor(
    val title: String,
    val swatchNumber: String,
    val color: @Composable () -> Color,
)

data class ThemeGroup(
    val main: ThemePair,
    val container: ThemePair,
)

data class ThemePair(
    val color: ThemeColor,
    val onColor: ThemeColor,
)
package com.materialkolor.builder.ui.ktx

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
expect fun windowSizeClass(): WindowSizeClass

fun WindowSizeClass.widthIsExpanded(): Boolean {
    return this.widthSizeClass == WindowWidthSizeClass.Expanded
}

fun WindowSizeClass.widthIsMedium(): Boolean {
    return this.widthSizeClass == WindowWidthSizeClass.Medium
}

fun WindowSizeClass.widthIsCompact(): Boolean {
    return this.widthSizeClass == WindowWidthSizeClass.Compact
}

fun WindowSizeClass.showBottomBar(): Boolean = widthIsCompact()

fun WindowSizeClass.sidePadding(): Dp {
    return when (widthSizeClass) {
        WindowWidthSizeClass.Expanded -> 32.dp
        WindowWidthSizeClass.Medium -> 16.dp
        WindowWidthSizeClass.Compact -> 8.dp
        else -> Dp.Unspecified
    }
}
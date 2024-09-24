package com.materialkolor.builder.ui.components

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.materialkolor.builder.settings.model.KeyColor

@Stable
data class ColorPickerState(
    val keyColor: KeyColor,
    val initial: Color,
)

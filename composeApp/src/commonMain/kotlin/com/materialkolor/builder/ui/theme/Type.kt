package com.materialkolor.builder.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontWeight

val AppTypography = Typography().let { type ->
    type.copy(
        bodyLarge = type.bodyLarge.copy(fontWeight = FontWeight.ExtraLight),
        bodyMedium = type.bodyMedium.copy(fontWeight = FontWeight.ExtraLight),
        bodySmall = type.bodySmall.copy(fontWeight = FontWeight.ExtraLight),
    )
}
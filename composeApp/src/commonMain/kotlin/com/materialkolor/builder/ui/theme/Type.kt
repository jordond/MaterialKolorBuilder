package com.materialkolor.builder.ui.theme

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

val AppTypography
    @Composable
    get() = Typography().let { type ->
        type.copy(
            bodyLarge = type.bodyLarge.copy(fontWeight = FontWeight.ExtraLight),
            bodyMedium = type.bodyMedium.copy(fontWeight = FontWeight.ExtraLight),
            bodySmall = type.bodySmall.copy(fontWeight = FontWeight.ExtraLight),
            labelLarge = type.labelLarge.copy(
                fontWeight = FontWeight.Light,
                color = LocalContentColor.current.copy(alpha = 0.8f)
            ),
            labelMedium = type.labelMedium.copy(
                fontWeight = FontWeight.Light,
                color = LocalContentColor.current.copy(alpha = 0.8f)
            ),
            labelSmall = type.labelSmall.copy(
                fontWeight = FontWeight.Light,
                color = LocalContentColor.current.copy(alpha = 0.8f)
            ),
        )
    }
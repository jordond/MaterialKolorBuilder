package com.materialkolor.builder.ui.home.page.customize.colors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.materialkolor.builder.ui.home.ColorType
import com.materialkolor.builder.ui.theme.LocalColors
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class CoreColor(
    val type: ColorType,
    val title: String,
    val subtitle: String?,
    val color: @Composable () -> Color,
)

val CoreColors = persistentListOf(
    CoreColor(
        type = ColorType.Primary,
        title = "Primary",
        subtitle = "Changing this will act as the new seed color for the color scheme",
        color = { LocalColors.current.primaryPaletteKeyColor() }
    ),
    CoreColor(
        type = ColorType.Secondary,
        title = "Secondary",
        subtitle = null,
        color = { LocalColors.current.secondaryPaletteKeyColor() }
    ),
    CoreColor(
        type = ColorType.Tertiary,
        title = "Tertiary",
        subtitle = null,
        color = { LocalColors.current.tertiaryPaletteKeyColor() }
    ),
    CoreColor(
        type = ColorType.Error,
        title = "Error",
        subtitle = null,
        color = { LocalColors.current.errorPaletteKeyColor() }
    ),
    CoreColor(
        type = ColorType.Neutral,
        title = "Neutral",
        subtitle = "Used for backgrounds and surfaces",
        color = { LocalColors.current.neutralPaletteKeyColor() }
    ),
    CoreColor(
        type = ColorType.NeutralVariant,
        title = "Neutral Variant",
        subtitle = "Used for medium emphasis and variants",
        color = { LocalColors.current.neutralVariantPaletteKeyColor() }
    ),
)
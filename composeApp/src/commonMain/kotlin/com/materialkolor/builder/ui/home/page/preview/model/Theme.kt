package com.materialkolor.builder.ui.home.page.preview.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.materialkolor.DynamicMaterialThemeState
import com.materialkolor.builder.ui.theme.LocalDynamicThemeState
import com.materialkolor.ktx.m3Colors
import com.materialkolor.ktx.toneColor

object Theme {

    object Colors {
        val Primary = ThemeColor(
            title = "Primary",
            swatchNumber = "P-40",
            color = tone { m3Colors.primary().palette(dynamicScheme).toneColor(40) },
        )

        val OnPrimary = ThemeColor(
            title = "On Primary",
            swatchNumber = "P-100",
            color = tone { m3Colors.onPrimary().palette(dynamicScheme).toneColor(100) },
        )

        val PrimaryContainer = ThemeColor(
            title = "Primary Container",
            swatchNumber = "P-90",
            color = tone { m3Colors.primaryContainer().palette(dynamicScheme).toneColor(90) },
        )

        val OnPrimaryContainer = ThemeColor(
            title = "On Primary Container",
            swatchNumber = "P-10",
            color = tone { m3Colors.onPrimaryContainer().palette(dynamicScheme).toneColor(100) },
        )

        val Secondary = ThemeColor(
            title = "Secondary",
            swatchNumber = "S-40",
            color = tone { m3Colors.secondary().palette(dynamicScheme).toneColor(40) },
        )

        val OnSecondary = ThemeColor(
            title = "On Secondary",
            swatchNumber = "S-100",
            color = tone { m3Colors.onSecondary().palette(dynamicScheme).toneColor(100) },
        )

        val SecondaryContainer = ThemeColor(
            title = "Secondary Container",
            swatchNumber = "S-90",
            color = tone { m3Colors.secondaryContainer().palette(dynamicScheme).toneColor(90) },
        )

        val OnSecondaryContainer = ThemeColor(
            title = "On Secondary Container",
            swatchNumber = "S-10",
            color = tone { m3Colors.onSecondaryContainer().palette(dynamicScheme).toneColor(100) },
        )

        val Tertiary = ThemeColor(
            title = "Tertiary",
            swatchNumber = "T-40",
            color = tone { m3Colors.tertiary().palette(dynamicScheme).toneColor(40) },
        )

        val OnTertiary = ThemeColor(
            title = "On Tertiary",
            swatchNumber = "T-100",
            color = tone { m3Colors.onTertiary().palette(dynamicScheme).toneColor(100) },
        )

        val TertiaryContainer = ThemeColor(
            title = "Tertiary Container",
            swatchNumber = "T-90",
            color = tone { m3Colors.tertiaryContainer().palette(dynamicScheme).toneColor(90) },
        )

        val OnTertiaryContainer = ThemeColor(
            title = "On Tertiary Container",
            swatchNumber = "T-10",
            color = tone { m3Colors.onTertiaryContainer().palette(dynamicScheme).toneColor(100) },
        )

        val Error = ThemeColor(
            title = "Error",
            swatchNumber = "E-40",
            color = tone { m3Colors.error().palette(dynamicScheme).toneColor(40) },
        )

        val OnError = ThemeColor(
            title = "On Error",
            swatchNumber = "E-100",
            color = tone { m3Colors.onError().palette(dynamicScheme).toneColor(100) },
        )

        val ErrorContainer = ThemeColor(
            title = "Error Container",
            swatchNumber = "E-90",
            color = tone { m3Colors.errorContainer().palette(dynamicScheme).toneColor(90) },
        )

        val OnErrorContainer = ThemeColor(
            title = "On Error Container",
            swatchNumber = "E-10",
            color = tone { m3Colors.onErrorContainer().palette(dynamicScheme).toneColor(100) },
        )

        val SurfaceDim = ThemeColor(
            title = "Surface Dim",
            swatchNumber = "N-87",
            color = tone { m3Colors.surfaceDim().palette(dynamicScheme).toneColor(87) },
        )

        val Surface = ThemeColor(
            title = "Surface",
            swatchNumber = "N-98",
            color = tone { m3Colors.surface().palette(dynamicScheme).toneColor(98) },
        )

        val SurfaceBright = ThemeColor(
            title = "Surface Bright",
            swatchNumber = "N-98",
            color = tone { m3Colors.surfaceBright().palette(dynamicScheme).toneColor(98) },
        )

        val SurfaceContainerLowest = ThemeColor(
            title = "Surface Container Lowest",
            swatchNumber = "N-100",
            color = tone { m3Colors.surfaceContainerLowest().palette(dynamicScheme).toneColor(100) },
        )

        val SurfaceContainerLow = ThemeColor(
            title = "Surface Container Low",
            swatchNumber = "N-96",
            color = tone { m3Colors.surfaceContainerLow().palette(dynamicScheme).toneColor(96) },
        )

        val SurfaceContainer = ThemeColor(
            title = "Surface Container",
            swatchNumber = "N-94",
            color = tone { m3Colors.surfaceContainer().palette(dynamicScheme).toneColor(94) },
        )

        val SurfaceContainerHigh = ThemeColor(
            title = "Surface Container High",
            swatchNumber = "N-92",
            color = tone { m3Colors.surfaceContainerHigh().palette(dynamicScheme).toneColor(92) },
        )

        val SurfaceContainerHighest = ThemeColor(
            title = "Surface Container Highest",
            swatchNumber = "N-90",
            color = tone { m3Colors.surfaceContainerHighest().palette(dynamicScheme).toneColor(90) },
        )

        val OnSurface = ThemeColor(
            title = "On Surface",
            swatchNumber = "N-10",
            color = tone { m3Colors.onSurface().palette(dynamicScheme).toneColor(10) },
        )

        val OnSurfaceVariant = ThemeColor(
            title = "On Surface Variant",
            swatchNumber = "NV-30",
            color = tone { m3Colors.onSurfaceVariant().palette(dynamicScheme).toneColor(30) },
        )

        val Outline = ThemeColor(
            title = "Outline",
            swatchNumber = "NV-50",
            color = tone { m3Colors.outline().palette(dynamicScheme).toneColor(50) },
        )

        val OutlineVariant = ThemeColor(
            title = "Outline Variant",
            swatchNumber = "NV-80",
            color = tone { m3Colors.outlineVariant().palette(dynamicScheme).toneColor(80) },
        )

        val InverseSurface = ThemeColor(
            title = "Inverse Surface",
            swatchNumber = "N-20",
            color = tone { m3Colors.inverseSurface().palette(dynamicScheme).toneColor(20) },
        )

        val InverseOnSurface = ThemeColor(
            title = "Inverse On Surface",
            swatchNumber = "N-95",
            color = tone { m3Colors.inverseOnSurface().palette(dynamicScheme).toneColor(95) },
        )

        val InversePrimary = ThemeColor(
            title = "Inverse Primary",
            swatchNumber = "P-80",
            color = tone { m3Colors.inversePrimary().palette(dynamicScheme).toneColor(80) },
        )

        val Scrim = ThemeColor(
            title = "Scrim",
            swatchNumber = "N-0",
            color = tone { m3Colors.scrim().palette(dynamicScheme).toneColor(0) },
        )

        val Shadow = ThemeColor(
            title = "Shadow",
            swatchNumber = "N-0",
            color = tone { m3Colors.shadow().palette(dynamicScheme).toneColor(0) },
        )
    }

    object Groups {
        val Primary = ThemeGroup(
            main = ThemePair(Colors.Primary, Colors.OnPrimary),
            container = ThemePair(Colors.PrimaryContainer, Colors.OnPrimaryContainer),
        )

        val Secondary = ThemeGroup(
            main = ThemePair(Colors.Secondary, Colors.OnSecondary),
            container = ThemePair(Colors.SecondaryContainer, Colors.OnSecondaryContainer),
        )

        val Tertiary = ThemeGroup(
            main = ThemePair(Colors.Tertiary, Colors.OnTertiary),
            container = ThemePair(Colors.TertiaryContainer, Colors.OnTertiaryContainer),
        )

        val Error = ThemeGroup(
            main = ThemePair(Colors.Error, Colors.OnError),
            container = ThemePair(Colors.ErrorContainer, Colors.OnErrorContainer),
        )

        val InverseSurface = ThemeGroup(
            main = ThemePair(Colors.InverseSurface, Colors.InverseOnSurface),
            container = ThemePair(Colors.InverseSurface, Colors.InverseOnSurface),
        )
    }
}

private fun tone(
    selector: @Composable DynamicMaterialThemeState.() -> Color,
): @Composable () -> Color = {
    val colors = LocalDynamicThemeState.current
    colors.selector()
}
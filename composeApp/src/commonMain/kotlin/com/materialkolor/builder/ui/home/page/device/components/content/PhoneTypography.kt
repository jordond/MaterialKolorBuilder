package com.materialkolor.builder.ui.home.page.device.components.content

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontFamily
import materialkolorbuilder.composeapp.generated.resources.PlayfairDisplay
import materialkolorbuilder.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

private val Font: FontFamily
    @Composable
    get() = FontFamily(Font(Res.font.PlayfairDisplay))

val PhoneTypography: Typography
    @Composable
    get() = createTypographyWith(Font)

@Composable
private fun createTypographyWith(family: FontFamily): Typography {
    val base = Typography()
    if (LocalInspectionMode.current) return base

    return Typography().copy(
        displayLarge = base.displayLarge.copy(fontFamily = family),
        displayMedium = base.displayMedium.copy(fontFamily = family),
        displaySmall = base.displaySmall.copy(fontFamily = family),
        headlineLarge = base.headlineLarge.copy(fontFamily = family),
        headlineMedium = base.headlineMedium.copy(fontFamily = family),
        headlineSmall = base.headlineSmall.copy(fontFamily = family),
        titleLarge = base.titleLarge.copy(fontFamily = family),
        titleMedium = base.titleMedium.copy(fontFamily = family),
        titleSmall = base.titleSmall.copy(fontFamily = family),
        bodyLarge = base.bodyLarge.copy(fontFamily = family),
        bodyMedium = base.bodyMedium.copy(fontFamily = family),
        bodySmall = base.bodySmall.copy(fontFamily = family),
        labelLarge = base.labelLarge.copy(fontFamily = family),
        labelMedium = base.labelMedium.copy(fontFamily = family),
        labelSmall = base.labelSmall.copy(fontFamily = family),
    )
}
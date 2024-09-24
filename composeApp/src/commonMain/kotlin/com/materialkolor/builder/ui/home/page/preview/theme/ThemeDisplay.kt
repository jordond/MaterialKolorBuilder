package com.materialkolor.builder.ui.home.page.preview.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.materialkolor.DynamicMaterialTheme
import com.materialkolor.DynamicMaterialThemeState
import com.materialkolor.builder.settings.model.Settings
import com.materialkolor.builder.ui.home.page.preview.model.Theme
import com.materialkolor.builder.ui.home.page.preview.theme.ThemeDisplayDefaults.InnerDivider
import com.materialkolor.builder.ui.home.page.preview.theme.ThemeDisplayDefaults.InverseSurfacePair
import com.materialkolor.builder.ui.home.page.preview.theme.ThemeDisplayDefaults.MainColors
import com.materialkolor.builder.ui.home.page.preview.theme.ThemeDisplayDefaults.MiscColors
import com.materialkolor.builder.ui.home.page.preview.theme.ThemeDisplayDefaults.SectionDivider
import com.materialkolor.builder.ui.home.page.preview.theme.ThemeDisplayDefaults.Surface
import com.materialkolor.builder.ui.home.page.preview.theme.ThemeDisplayDefaults.SurfaceContainer
import com.materialkolor.builder.ui.home.page.preview.theme.ThemeDisplayDefaults.inverse
import com.materialkolor.builder.ui.theme.AppTypography
import com.materialkolor.builder.ui.theme.createThemeState

enum class ThemeMode {
    Light,
    Dark,
}

@Composable
fun ThemeDisplay(
    settings: Settings,
    onCopyColor: (String, Color) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier,
    ) {
        LightThemeDisplay(settings, onClick = onCopyColor)
        DarkThemeDisplay(settings, onClick = onCopyColor)
    }
}

@Composable
fun LightThemeDisplay(
    settings: Settings,
    modifier: Modifier = Modifier,
    onClick: (String, Color) -> Unit = { _, _ -> },
) {
    ThemeDisplay(ThemeMode.Light, createThemeState(settings, isDark = false), onClick, modifier)
}

@Composable
fun DarkThemeDisplay(
    settings: Settings,
    modifier: Modifier = Modifier,
    onClick: (String, Color) -> Unit = { _, _ -> },
) {
    ThemeDisplay(ThemeMode.Dark, createThemeState(settings, isDark = true), onClick, modifier)
}

@Composable
private fun ThemeDisplay(
    theme: ThemeMode,
    state: DynamicMaterialThemeState,
    onClick: (String, Color) -> Unit,
    modifier: Modifier = Modifier,
) {
    DynamicMaterialTheme(
        state = state,
        animate = true,
        typography = AppTypography,
    ) {
        ThemeDisplay(theme, onClick, modifier)
    }
}

@Composable
private fun ThemeDisplay(
    theme: ThemeMode,
    onClick: (String, Color) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedCard(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "${theme.name} Theme",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = SectionDivider),
            )

            CompositionLocalProvider(LocalTextStyle provides ThemeDisplayDefaults.TextStyle) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(SectionDivider),
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(SectionDivider),
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(InnerDivider),
                        ) {
                            MainColors.forEach { group ->
                                ColorGroupContainer(group, onClick, modifier = Modifier.weight(1f))
                            }
                        }

                        Column(
                            verticalArrangement = Arrangement.spacedBy(InnerDivider),
                        ) {
                            Row {
                                Surface.forEach { themeColor ->
                                    ColorBox(
                                        themeColor = themeColor,
                                        onClick = onClick,
                                        lines = 4,
                                        modifier = Modifier.weight(1f),
                                    )
                                }
                            }

                            Row {
                                SurfaceContainer.forEach { themeColor ->
                                    ColorBox(
                                        themeColor = themeColor,
                                        onClick = onClick,
                                        lines = 4,
                                        modifier = Modifier.weight(1f),
                                    )
                                }
                            }

                            Row {
                                MiscColors.forEach { (themeColor, onColor) ->
                                    SingleLineColorBox(
                                        themeColor = themeColor,
                                        onClick = onClick,
                                        textColor = onColor,
                                        modifier = Modifier.weight(1f),
                                    )
                                }
                            }
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(SectionDivider),
                        modifier = Modifier.weight(0.3f)
                    ) {
                        ColorGroupContainer(Theme.Groups.Error, onClick)

                        Column(
                            verticalArrangement = Arrangement.spacedBy(InnerDivider),
                        ) {
                            ColorPairContainer(InverseSurfacePair, onClick)

                            SingleLineColorBox(
                                themeColor = Theme.Colors.InversePrimary,
                                onClick = onClick,
                                modifier = Modifier.fillMaxWidth(),
                            )

                            SingleLineColorBox(
                                themeColor = Theme.Colors.Scrim,
                                onClick = onClick,
                                textColor = Theme.Colors.Scrim.color().inverse(),
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    }
                }
            }
        }
    }
}

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
private fun ThemeDisplay(
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
private fun ThemeDisplay(
    theme: ThemeMode,
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
                                ColorGroupContainer(group, modifier = Modifier.weight(1f))
                            }
                        }

                        Column(
                            verticalArrangement = Arrangement.spacedBy(InnerDivider),
                        ) {
                            Row {
                                Surface.forEach { themeColor ->
                                    ColorBox(
                                        themeColor = themeColor,
                                        lines = 4,
                                        modifier = Modifier.weight(1f),
                                    )
                                }
                            }

                            Row {
                                SurfaceContainer.forEach { themeColor ->
                                    ColorBox(
                                        themeColor = themeColor,
                                        lines = 4,
                                        modifier = Modifier.weight(1f),
                                    )
                                }
                            }

                            Row {
                                MiscColors.forEach { (themeColor, onColor) ->
                                    SingleLineColorBox(
                                        themeColor = themeColor,
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
                        ColorGroupContainer(Theme.Groups.Error)

                        Column(
                            verticalArrangement = Arrangement.spacedBy(InnerDivider),
                        ) {
                            ColorPairContainer(pair = InverseSurfacePair)

                            SingleLineColorBox(
                                themeColor = Theme.Colors.InversePrimary,
                                modifier = Modifier.fillMaxWidth(),
                            )

                            SingleLineColorBox(
                                themeColor = Theme.Colors.Scrim,
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
